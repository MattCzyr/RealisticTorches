package com.chaosthedude.realistictorches.blocks;

import com.chaosthedude.realistictorches.config.ConfigHandler;
import com.chaosthedude.realistictorches.items.RealisticTorchesItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TorchBlock;
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
import net.minecraft.world.World;

public class RealisticTorchBlock extends TorchBlock {
	
    public static final int TICK_RATE = 1200;

    protected static int initialBurnTime = ConfigHandler.torchBurnoutTime.get();
    protected static final IntegerProperty BURNTIME = IntegerProperty.create("burntime", 0, initialBurnTime);

    public RealisticTorchBlock() {
        super(Block.Properties.from(Blocks.TORCH), ParticleTypes.FLAME);
        setDefaultState(this.getDefaultState().with(BURNTIME, 0));
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
    }
    
    public static IntegerProperty getBurnTime() {
        return BURNTIME;
    }

    public static int getInitialBurnTime() {
        return initialBurnTime;
    }

    public void changeToLit(World world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, RealisticTorchesBlocks.LIT_TORCH.getDefaultState().with(BURNTIME, initialBurnTime));
        world.getPendingBlockTicks().scheduleTick(pos, this, TICK_RATE);
    }

    public void changeToSmoldering(World world, BlockPos pos, BlockState state, int newBurnTime) {
        world.setBlockState(pos, RealisticTorchesBlocks.SMOLDERING_TORCH.getDefaultState().with(BURNTIME, newBurnTime));
        world.getPendingBlockTicks().scheduleTick(pos, this, TICK_RATE);
    }

    public void changeToUnlit(World world, BlockPos pos, BlockState state) {
    	if (ConfigHandler.noRelightEnabled.get()) {
    		world.setBlockState(pos, Blocks.AIR.getDefaultState());
    	} else {
	        world.setBlockState(pos, RealisticTorchesBlocks.UNLIT_TORCH.getDefaultState());
	        world.getPendingBlockTicks().scheduleTick(pos, this, TICK_RATE);
    	}
    }

    public void playLightingSound(World world, BlockPos pos) {
        world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.rand.nextFloat() * 0.1F + 0.9F);
    }

    public void playExtinguishSound(World world, BlockPos pos) {
        world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, world.rand.nextFloat() * 0.1F + 0.9F);
    }

}
