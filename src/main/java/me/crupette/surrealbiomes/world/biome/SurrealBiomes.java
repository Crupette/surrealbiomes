package me.crupette.surrealbiomes.world.biome;

import me.crupette.surrealbiomes.SBBase;
import me.crupette.surrealbiomes.SBConfig;
import net.fabricmc.fabric.api.biomes.v1.FabricBiomes;
import net.fabricmc.fabric.api.biomes.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biomes.v1.OverworldClimate;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class SurrealBiomes {

    public static final Biome CRYSTALINE_PLAINS = Registry.register(Registry.BIOME, new Identifier(SBBase.MOD_ID, "crystaline_plains"), new CrystalinePlainsBiome());
    public static final Biome CRYSTALINE_FOREST = Registry.register(Registry.BIOME, new Identifier(SBBase.MOD_ID, "crystaline_forest"), new CrystalineForestBiome());

    public static final Biome RAINBOW_DESERT = Registry.register(Registry.BIOME, new Identifier(SBBase.MOD_ID, "rainbow_desert"), new RainbowDesertBiome());
    public static final Biome RAINBOW_DESERT_HILLS = Registry.register(Registry.BIOME, new Identifier(SBBase.MOD_ID, "rainbow_desert_hills"), new RainbowDesertHillsBiome());
    public static final Biome RAINBOW_BEACH = Registry.register(Registry.BIOME, new Identifier(SBBase.MOD_ID, "rainbow_beach"), new RainbowBeachBiome());

    public static void initialize(){
        OverworldBiomes.addContinentalBiome(CRYSTALINE_PLAINS, OverworldClimate.TEMPERATE, SBConfig.config.crystal.plains_chance);
        OverworldBiomes.addContinentalBiome(CRYSTALINE_FOREST, OverworldClimate.TEMPERATE, SBConfig.config.crystal.forest_chance);

        OverworldBiomes.addContinentalBiome(RAINBOW_DESERT, OverworldClimate.DRY, SBConfig.config.rainbow.chance);
        OverworldBiomes.addHillsBiome(RAINBOW_DESERT, RAINBOW_DESERT_HILLS, 100.D);
        OverworldBiomes.addShoreBiome(RAINBOW_DESERT, RAINBOW_BEACH, 100.D);

        FabricBiomes.addSpawnBiome(CRYSTALINE_PLAINS);
        FabricBiomes.addSpawnBiome(CRYSTALINE_FOREST);
        FabricBiomes.addSpawnBiome(RAINBOW_DESERT);
        FabricBiomes.addSpawnBiome(RAINBOW_BEACH);
    }
}
