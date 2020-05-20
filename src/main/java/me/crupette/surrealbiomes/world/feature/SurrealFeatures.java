package me.crupette.surrealbiomes.world.feature;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class SurrealFeatures {

    public static final Feature<DefaultFeatureConfig> CRYSTAL = Registry.register(
            Registry.FEATURE, new Identifier("surrealbiomes", "crystal"), new CrystalFeature(DefaultFeatureConfig::deserialize));
}
