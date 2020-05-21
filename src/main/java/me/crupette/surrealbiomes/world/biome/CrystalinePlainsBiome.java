package me.crupette.surrealbiomes.world.biome;

import com.google.common.collect.ImmutableSet;
import me.crupette.surrealbiomes.block.SurrealBlocks;
import me.crupette.surrealbiomes.world.feature.SurrealFeatures;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
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
                .precipitation(Precipitation.RAIN).category(Category.FOREST).depth(0.125F).scale(0.05F).temperature(0.9F).downfall(1.F)
                        .waterColor(4159204).waterFogColor(329011).parent(null));

        this.addStructureFeature(Feature.MINESHAFT.configure(new MineshaftFeatureConfig(0.004D, MineshaftFeature.Type.NORMAL)));
        this.addStructureFeature(Feature.STRONGHOLD.configure(FeatureConfig.DEFAULT));
        this.addFeature(GenerationStep.Feature.RAW_GENERATION, SurrealFeatures.CRYSTAL.configure(FeatureConfig.DEFAULT).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(0, 0.1F, 1))));

        DefaultBiomeFeatures.addLandCarvers(this);
        DefaultBiomeFeatures.addDefaultStructures(this);
        DefaultBiomeFeatures.addDefaultLakes(this);
        DefaultBiomeFeatures.addDungeons(this);
        DefaultBiomeFeatures.addMineables(this);
        DefaultBiomeFeatures.addDefaultOres(this);
        DefaultBiomeFeatures.addDefaultDisks(this);
        DefaultBiomeFeatures.addSprings(this);

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(
                new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                        .addState(SurrealBlocks.REDDER_CRYSTAL_SHARDLING.getDefaultState(), 1)
                        .addState(SurrealBlocks.BLUER_CRYSTAL_SHARDLING.getDefaultState(), 1)
                        .addState(SurrealBlocks.GREENER_CRYSTAL_SHARDLING.getDefaultState(), 1),
                        new SimpleBlockPlacer())
                        .whitelist(ImmutableSet.of(SurrealBlocks.CRYSTAL_GRASS)).tries(32).build()
        ).createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_DOUBLE.configure(new CountDecoratorConfig(25))));

        this.addSpawn(EntityCategory.AMBIENT, new SpawnEntry(EntityType.BAT, 10, 8, 8));
        this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.SPIDER, 100, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.ZOMBIE, 95, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.SKELETON, 100, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.CREEPER, 100, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.SLIME, 100, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.ENDERMAN, 10, 1, 4));
        this.addSpawn(EntityCategory.MONSTER, new SpawnEntry(EntityType.WITCH, 5, 1, 1));
    }
}
