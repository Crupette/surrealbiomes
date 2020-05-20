package me.crupette.surrealbiomes.world.biome;

import me.crupette.surrealbiomes.SBConfig;
import net.fabricmc.fabric.api.biomes.v1.FabricBiomes;
import net.fabricmc.fabric.api.biomes.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biomes.v1.OverworldClimate;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class SurrealBiomes {

    public static final Biome CRYSTALINE_PLAINS = Registry.register(Registry.BIOME, new Identifier("surrealbiomes", "crystaline_plains"), new CrystalinePlainsBiome());
    public static final Biome CRYSTALINE_FOREST = Registry.register(Registry.BIOME, new Identifier("surrealbiomes", "crystaline_forest"), new CrystalineForestBiome());

    public static void initialize(){
        OverworldBiomes.addContinentalBiome(CRYSTALINE_PLAINS, OverworldClimate.TEMPERATE, SBConfig.config.crystaline_plains_chance);
        OverworldBiomes.addContinentalBiome(CRYSTALINE_FOREST, OverworldClimate.TEMPERATE, SBConfig.config.crystaline_forest_chance);

        FabricBiomes.addSpawnBiome(CRYSTALINE_PLAINS);
        FabricBiomes.addSpawnBiome(CRYSTALINE_FOREST);
    }
}
