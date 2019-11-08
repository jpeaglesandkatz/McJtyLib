package mcjty.lib.varia;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.registries.ForgeRegistries;

public class WorldTools {

    public static boolean chunkLoaded(World world, BlockPos pos) {
        if (world == null || pos == null) {
            return false;
        }
        return world.isBlockLoaded(pos);
//        return world.getChunkProvider().getLoadedChunk(pos.getX() >> 4, pos.getZ() >> 4) != null && world.getChunkFromBlockCoords(pos).isLoaded();
    }

    public static ServerWorld getOverworld() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        return DimensionManager.getWorld(server, DimensionType.OVERWORLD, false, false);
    }

    public static ServerWorld getOverworld(World world) {
        MinecraftServer server = world.getServer();
        return DimensionManager.getWorld(server, DimensionType.OVERWORLD, false, false);
    }

    public static ServerWorld loadWorld(DimensionType type) {
        ServerWorld world = getWorld(type);
        if (world == null) {
            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            return server.getWorld(type);
        }
        return world;
    }

    public static ServerWorld getWorld(DimensionType type) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        return DimensionManager.getWorld(server, type, false, false);
    }

    public static ServerWorld getWorld(World world, DimensionType type) {
        MinecraftServer server = world.getServer();
        return DimensionManager.getWorld(server, type, false, false);
    }

    /**
     * Find a biome based on ID or registry name
     */
    public static Biome findBiome(String biomeId) {
        Biome biome = ForgeRegistries.BIOMES.getValue(new ResourceLocation(biomeId));
        if (biome == null) {
            for (Biome b : ForgeRegistries.BIOMES) {
                ResourceLocation registryName = b.getRegistryName();
                if (registryName != null && biomeId.equals(registryName.getPath())) {
                    biome = b;
                    break;
                }
            }
        }
        return biome;
    }


}
