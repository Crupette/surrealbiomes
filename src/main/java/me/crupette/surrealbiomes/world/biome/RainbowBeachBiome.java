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

public class RainbowBeachBiome extends Biome {

    protected RainbowBeachBiome() {
        super((new Settings()).configureSurfaceBuilder(SurrealSurfaceBuilders.RAINBOW_SANDS,
                SurfaceBuilder.SAND_CONFIG).precipitation(Precipitation.RAIN).category(Category.BEACH)
                .depth(0.0F).scale(0.025F).temperature(0.8F).downfall(0.4F)
                .effects((new BiomeEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).moodSound(BiomeMoodSound.CAVE).build())
                .parent((String)null)
                .noises(ImmutableList.of(new MixedNoisePoint(0.0F, 0.0F, -0.1F, 0.0F, 0.9935F))));

        this.addStructureFeature(DefaultBiomeFeatures.field_24688);
        this.addStructureFeature(DefaultBiomeFeatures.field_24704);
        this.addStructureFeature(DefaultBiomeFeatures.field_24695);
        this.addStructureFeature(DefaultBiomeFeatures.field_24711);
        DefaultBiomeFeatures.addLandCarvers(this);
        DefaultBiomeFeatures.addDefaultLakes(this);
        DefaultBiomeFeatures.addDungeons(this);
        DefaultBiomeFeatures.addMineables(this);
        DefaultBiomeFeatures.addDefaultOres(this);
        DefaultBiomeFeatures.addDefaultDisks(this);
        DefaultBiomeFeatures.addDefaultFlowers(this);
        DefaultBiomeFeatures.addDefaultGrass(this);
        DefaultBiomeFeatures.addDefaultMushrooms(this);
        DefaultBiomeFeatures.addDefaultVegetation(this);
        DefaultBiomeFeatures.addSprings(this);
        DefaultBiomeFeatures.addFrozenTopLayer(this);
        this.addSpawn(SpawnGroup.CREATURE, new SpawnEntry(EntityType.TURTLE, 5, 2, 5));
        this.addSpawn(SpawnGroup.AMBIENT, new SpawnEntry(EntityType.BAT, 10, 8, 8));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.SPIDER, 100, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.ZOMBIE, 95, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.SKELETON, 100, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.CREEPER, 100, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.SLIME, 100, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.ENDERMAN, 10, 1, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.WITCH, 5, 1, 1));
    }
}
