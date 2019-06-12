package mcjty.lib.varia;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockTools {
    private static final Random random = new Random();

    public static void emptyInventoryInWorld(World world, int x, int y, int z, Block block, IInventory inventory) {
        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
            ItemStack itemstack = inventory.getStackInSlot(i);
            spawnItemStack(world, x, y, z, itemstack);
            inventory.setInventorySlotContents(i, ItemStack.EMPTY);
        }
        //TODO: What was this?
        //world.func_147453_f(x, y, z, block);
    }

    public static void spawnItemStack(World world, int x, int y, int z, ItemStack itemstack) {
        if (!itemstack.isEmpty()) {
            ItemEntity entityItem = new ItemEntity(world, x, y, z, itemstack);
            world.addEntity(entityItem);
        }
    }

    public static Block getBlock(ItemStack stack) {
        if (stack.getItem() instanceof BlockItem) {
            return ((BlockItem) stack.getItem()).getBlock();
        } else {
            return null;
        }
    }

    public static String getModid(ItemStack stack) {
        if (!stack.isEmpty()) {
            return stack.getItem().getRegistryName().getNamespace();
        } else {
            return "";
        }
    }

    public static String getModidForBlock(Block block) {
        ResourceLocation nameForObject = block.getRegistryName();
        if (nameForObject == null) {
            return "?";
        }
        return nameForObject.getNamespace();
    }

    public static String getReadableName(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return getReadableName(state.getBlock().getItem(world, pos, state));
    }

    public static String getReadableName(ItemStack stack) {
        return stack.getDisplayName().getFormattedText();
    }

    public static BlockState placeStackAt(PlayerEntity player, ItemStack blockStack, World world, BlockPos pos, @Nullable BlockState origState) {
        BlockRayTraceResult trace = new BlockRayTraceResult(new Vec3d(0, 0, 0), Direction.UP, pos, false);
        BlockItemUseContext context = new BlockItemUseContext(new ItemUseContext(player, Hand.MAIN_HAND, trace));
        if (blockStack.getItem() instanceof BlockItem) {
            BlockItem itemBlock = (BlockItem) blockStack.getItem();
            if (origState == null) {
                origState = itemBlock.getBlock().getStateForPlacement(context);
            }
            if (itemBlock.tryPlace(context) == ActionResultType.SUCCESS) {
                blockStack.shrink(1);
            }
            return origState;
        } else {
            player.setHeldItem(Hand.MAIN_HAND, blockStack);
            player.setPosition(pos.getX()+.5, pos.getY()+1.5, pos.getZ()+.5);
            blockStack.getItem().onItemUse(context);
            return world.getBlockState(pos);
        }
    }

}
