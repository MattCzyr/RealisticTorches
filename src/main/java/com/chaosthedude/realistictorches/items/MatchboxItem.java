package com.chaosthedude.realistictorches.items;

import com.chaosthedude.realistictorches.config.ConfigHandler;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class MatchboxItem extends Item {

	public static final String NAME = "matchbox";

	public MatchboxItem(int maxDamage) {
		super(new Item.Properties().group(ItemGroup.TOOLS).maxDamage(maxDamage).defaultMaxDamage(maxDamage));
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		if (!ConfigHandler.matchboxCreatesFire.get()) {
			return ActionResultType.FAIL;
		}

		PlayerEntity player = context.getPlayer();
		IWorld world = context.getWorld();
		BlockPos pos = context.getPos();
		BlockPos offsetPos = pos.offset(context.getFace());
		if (canPlaceFire(world.getBlockState(offsetPos), world, offsetPos)) {
			world.playSound(player, offsetPos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F,
					random.nextFloat() * 0.4F + 0.8F);
			BlockState blockstate1 = ((FireBlock) Blocks.FIRE).getStateForPlacement(world, offsetPos);
			world.setBlockState(offsetPos, blockstate1, 11);
			ItemStack itemstack = context.getItem();
			if (player instanceof ServerPlayerEntity) {
				CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) player, offsetPos, itemstack);
				itemstack.damageItem(1, player, (p) -> {
					p.sendBreakAnimation(context.getHand());
				});
			}

			return ActionResultType.SUCCESS;
		} else {
			BlockState blockstate = world.getBlockState(pos);
			if (isUnlitCampfire(blockstate)) {
				world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F,
						random.nextFloat() * 0.4F + 0.8F);
				world.setBlockState(pos, blockstate.with(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
				if (player != null) {
					context.getItem().damageItem(1, player, (p) -> {
						p.sendBreakAnimation(context.getHand());
					});
				}

				return ActionResultType.SUCCESS;
			} else {
				return ActionResultType.FAIL;
			}
		}
	}

	public static boolean isUnlitCampfire(BlockState state) {
		return state.getBlock() == Blocks.CAMPFIRE && !state.get(BlockStateProperties.WATERLOGGED)
				&& !state.get(BlockStateProperties.LIT);
	}

	public static boolean canPlaceFire(BlockState state, IWorld world, BlockPos pos) {
		BlockState blockstate = ((FireBlock) Blocks.FIRE).getStateForPlacement(world, pos);
		boolean flag = false;
		for (Direction direction : Direction.Plane.HORIZONTAL) {
			BlockPos framePos = pos.offset(direction);
			if (world.getBlockState(framePos).isPortalFrame(world, framePos)
					&& ((NetherPortalBlock) Blocks.NETHER_PORTAL).isPortal(world, pos) != null) {
				flag = true;
			}
		}
		return state.isAir() && (blockstate.isValidPosition(world, pos) || flag);
	}
}
