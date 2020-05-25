package me.crupette.surrealbiomes.world.feature;

import me.crupette.surrealbiomes.SBBase;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class SurrealFeatures {

    public static final CrystalFeature CRYSTAL = Registry.register(
            Registry.FEATURE, new Identifier(SBBase.MOD_ID, "crystal"), new CrystalFeature(CrystalFeatureConfig.CODEC));

    public static final SpikesFeature SPIKES = Registry.register(
            Registry.FEATURE, new Identifier(SBBase.MOD_ID, "spikes"), new SpikesFeature(SpikesFeatureConfig.CODEC));
}
