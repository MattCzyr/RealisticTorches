package com.chaosthedude.realistictorches.blocks;

import java.util.Random;

import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.chaosthedude.realistictorches.items.RealisticTorchesItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TorchBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class RealisticTorchBlock extends TorchBlock {
	
	public static final String NAME = "torch";
	
    public static final int TICK_INTERVAL = 1200;
    protected static final int INITIAL_BURN_TIME = ConfigHandler.torchBurnoutTime.get();
    protected static final IntegerProperty BURNTIME = IntegerProperty.create("burntime", 0, INITIAL_BURN_TIME);
    protected static final IntegerProperty LITSTATE = IntegerProperty.create("litstate", 0, 2);
    
    public static final int LIT = 2;
    public static final int SMOLDERING = 1;
    public static final int UNLIT = 0;

    public RealisticTorchBlock() {
        super(Block.Properties.from(Blocks.TORCH), ParticleTypes.FLAME);
        setDefaultState(this.getDefaultState().with(LITSTATE, 0).with(BURNTIME, 0));
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        if (state.get(LITSTATE) == LIT) {
            return 14;
        } else if (state.get(LITSTATE) == SMOLDERING) {
            return 12;
        }
        return 0;
    }

    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LITSTATE) == LIT || (state.get(LITSTATE) == SMOLDERING && world.getRandom().nextInt(2) == 1)) {
        	super.animateTick(state, world, pos, random);
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
    	if (player.getHeldItem(hand).getItem() == Items.FLINT_AND_STEEL ||
    			player.getHeldItem(hand).getItem() == RealisticTorchesItems.MATCHBOX) {
            playLightingSound(world, pos);
            if (!player.isCreative()) {
                ItemStack heldStack = player.getHeldItem(hand);
                heldStack.damageItem(1, player, playerEntity -> {
                    playerEntity.sendBreakAnimation(hand);
                });}
            if (world.isRainingAt(pos)) {
                playExtinguishSound(world, pos);
            } else {
                changeToLit(world, pos, state);
            }
            return ActionResultType.SUCCESS;
        }
        return super.onBlockActivated(state, world, pos, player, hand, hit);
    }


    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isRemote() && INITIAL_BURN_TIME > 0 && state.get(LITSTATE) > UNLIT) {
        	if (world.isRainingAt(pos)) {
                playExtinguishSound(world, pos);
                changeToUnlit(world, pos, state);
                return;
            }
            int newBurnTime = state.get(BURNTIME) - 1;
            if (newBurnTime <= 0) {
                playExtinguishSound(world, pos);
                changeToUnlit(world, pos, state);
                world.notifyNeighborsOfStateChange(pos, this);
            } else if (state.get(LITSTATE) == LIT && (newBurnTime <= INITIAL_BURN_TIME / 10 || newBurnTime <= 1)) {
            	changeToSmoldering(world, pos, state, newBurnTime);
            	world.notifyNeighborsOfStateChange(pos, this);
            } else {
	            world.setBlockState(pos, state.with(BURNTIME, newBurnTime));
	            world.getPendingBlockTicks().scheduleTick(pos, this, TICK_INTERVAL);
            }
        }
    }

     @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
    	super.onBlockPlacedBy(world, pos, state, placer, stack);
    	world.getPendingBlockTicks().scheduleTick(pos, this, TICK_INTERVAL);
    }

    @Override
    public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!isMoving && state.getBlock() != newState.getBlock()) {
            getDefaultState().updateNeighbours(world, pos, 3);
        }
        super.onReplaced(state, world, pos, newState, isMoving);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(BURNTIME);
        builder.add(LITSTATE);
    }
    
    public static IntegerProperty getBurnTime() {
        return BURNTIME;
    }

    public static IntegerProperty getLitState() {
        return LITSTATE;
    }

    public static int getInitialBurnTime() {
        return INITIAL_BURN_TIME;
    }

    public void changeToLit(World world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, RealisticTorchesBlocks.TORCH.getDefaultState().with(LITSTATE, LIT).with(BURNTIME, INITIAL_BURN_TIME));
        world.getPendingBlockTicks().scheduleTick(pos, this, TICK_INTERVAL);
    }

    public void changeToSmoldering(World world, BlockPos pos, BlockState state, int newBurnTime) {
        world.setBlockState(pos, RealisticTorchesBlocks.TORCH.getDefaultState().with(LITSTATE, SMOLDERING).with(BURNTIME, newBurnTime));
        world.getPendingBlockTicks().scheduleTick(pos, this, TICK_INTERVAL);
    }

    public void changeToUnlit(World world, BlockPos pos, BlockState state) {
    	if (ConfigHandler.noRelightEnabled.get()) {
    		world.setBlockState(pos, Blocks.AIR.getDefaultState());
    	} else {
	        world.setBlockState(pos, RealisticTorchesBlocks.TORCH.getDefaultState());
	        world.getPendingBlockTicks().scheduleTick(pos, this, TICK_INTERVAL);
    	}
    }

    public void playLightingSound(World world, BlockPos pos) {
        world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.rand.nextFloat() * 0.1F + 0.9F);
    }

    public void playExtinguishSound(World world, BlockPos pos) {
        world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, world.rand.nextFloat() * 0.1F + 0.9F);
    }

}
