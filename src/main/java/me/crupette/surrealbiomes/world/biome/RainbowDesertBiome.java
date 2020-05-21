package me.crupette.surrealbiomes.world.biome;

import com.google.common.collect.ImmutableList;
import me.crupette.surrealbiomes.world.surfacebuilder.SurrealSurfaceBuilders;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

public class RainbowDesertBiome extends Biome {
    protected RainbowDesertBiome() {
        super(new Biome.Settings().configureSurfaceBuilder(SurrealSurfaceBuilders.RAINBOW_SANDS, SurfaceBuilder.SAND_CONFIG)
                .precipitation(Precipitation.NONE).category(Category.DESERT).depth(0.125F).scale(0.05F).temperature(2.F).downfall(0.F)
                .effects(new BiomeEffects.Builder().waterColor(4159204).waterFogColor(329011).fogColor(12638463)
                        .moodSound(BiomeMoodSound.CAVE).build())
                .parent(null).noises(ImmutableList.of(new MixedNoisePoint(0.5F, -0.5F, 0.0F, 0.0F, 1.0F))));

        this.addStructureFeature(DefaultBiomeFeatures.field_24707);
        this.addStructureFeature(DefaultBiomeFeatures.field_24687);
        this.addStructureFeature(DefaultBiomeFeatures.field_24692);
        DefaultBiomeFeatures.method_28440(this);
        this.addStructureFeature(DefaultBiomeFeatures.field_24712);
        DefaultBiomeFeatures.addLandCarvers(this);
        DefaultBiomeFeatures.addDesertLakes(this);
        DefaultBiomeFeatures.addDungeons(this);
        DefaultBiomeFeatures.addMineables(this);
        DefaultBiomeFeatures.addDefaultOres(this);
        DefaultBiomeFeatures.addDefaultDisks(this);
        DefaultBiomeFeatures.addDefaultFlowers(this);
        DefaultBiomeFeatures.addDefaultGrass(this);
        DefaultBiomeFeatures.addDesertDeadBushes(this);
        DefaultBiomeFeatures.addDefaultMushrooms(this);
        DefaultBiomeFeatures.addDesertVegetation(this);
        DefaultBiomeFeatures.addSprings(this);
        DefaultBiomeFeatures.addDesertFeatures(this);
        DefaultBiomeFeatures.addFrozenTopLayer(this);
        this.addSpawn(SpawnGroup.CREATURE, new SpawnEntry(EntityType.RABBIT, 4, 2, 3));
        this.addSpawn(SpawnGroup.AMBIENT, new SpawnEntry(EntityType.BAT, 10, 8, 8));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.SPIDER, 100, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.SKELETON, 100, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.CREEPER, 100, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.SLIME, 100, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.ENDERMAN, 10, 1, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.WITCH, 5, 1, 1));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.ZOMBIE, 19, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.ZOMBIE_VILLAGER, 1, 1, 1));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.HUSK, 80, 4, 4));
    }
}
