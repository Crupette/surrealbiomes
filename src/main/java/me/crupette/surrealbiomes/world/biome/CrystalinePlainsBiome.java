package me.crupette.surrealbiomes.world.biome;

import com.google.common.collect.ImmutableSet;
import me.crupette.surrealbiomes.SBConfig;
import me.crupette.surrealbiomes.block.SurrealBlocks;
import me.crupette.surrealbiomes.world.feature.CrystalFeatureConfig;
import me.crupette.surrealbiomes.world.feature.SurrealFeatures;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountDecoratorConfig;
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class CrystalinePlainsBiome extends Biome {
    public CrystalinePlainsBiome() {
        super(new Biome.Settings().configureSurfaceBuilder(SurfaceBuilder.DEFAULT, new TernarySurfaceConfig(
                SurrealBlocks.CRYSTAL_GRASS.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.GRAVEL.getDefaultState()))
                .precipitation(Precipitation.RAIN).category(Category.FOREST).depth(0.125F).scale(0.05F).temperature(0.9F).downfall(1.F).effects(
                        new BiomeEffects.Builder().waterColor(4159204).waterFogColor(329011).fogColor(12638463)
                                .moodSound(BiomeMoodSound.CAVE).build()
                ).parent(null));

        DefaultBiomeFeatures.method_28440(this);             //Default biome features
        this.addStructureFeature(DefaultBiomeFeatures.field_24711); //Stronghold
        this.addFeature(GenerationStep.Feature.RAW_GENERATION, SurrealFeatures.CRYSTAL.configure(
                new CrystalFeatureConfig.Builder(SBConfig.config.crystalStructure).build())
                .createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, 0.1F, 1))));

        DefaultBiomeFeatures.addLandCarvers(this);
        DefaultBiomeFeatures.addDefaultLakes(this);
        DefaultBiomeFeatures.addDungeons(this);
        DefaultBiomeFeatures.addMineables(this);
        DefaultBiomeFeatures.addDefaultOres(this);
        DefaultBiomeFeatures.addDefaultDisks(this);
        DefaultBiomeFeatures.addSprings(this);
        DefaultBiomeFeatures.addDefaultMushrooms(this);
        DefaultBiomeFeatures.addDefaultVegetation(this);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(
                new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                        .addState(SurrealBlocks.REDDER_CRYSTAL_SHARDLING.getDefaultState(), 1)
                        .addState(SurrealBlocks.BLUER_CRYSTAL_SHARDLING.getDefaultState(), 1)
                        .addState(SurrealBlocks.GREENER_CRYSTAL_SHARDLING.getDefaultState(), 1),
                        new SimpleBlockPlacer())
                        .whitelist(ImmutableSet.of(SurrealBlocks.CRYSTAL_GRASS)).tries(32).build()
        ).createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_DOUBLE.configure(new CountDecoratorConfig(25))));

        this.addSpawn(SpawnGroup.AMBIENT, new Biome.SpawnEntry(EntityType.BAT, 10, 8, 8));
        this.addSpawn(SpawnGroup.MONSTER, new Biome.SpawnEntry(EntityType.SPIDER, 100, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new Biome.SpawnEntry(EntityType.ZOMBIE, 95, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new Biome.SpawnEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        this.addSpawn(SpawnGroup.MONSTER, new Biome.SpawnEntry(EntityType.SKELETON, 100, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new Biome.SpawnEntry(EntityType.CREEPER, 100, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new Biome.SpawnEntry(EntityType.SLIME, 100, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new Biome.SpawnEntry(EntityType.ENDERMAN, 10, 1, 4));
        this.addSpawn(SpawnGroup.MONSTER, new Biome.SpawnEntry(EntityType.WITCH, 5, 1, 1));
    }
}
