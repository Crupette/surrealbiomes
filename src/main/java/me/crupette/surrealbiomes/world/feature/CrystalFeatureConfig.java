package me.crupette.surrealbiomes.world.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.crupette.surrealbiomes.SBConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.List;
import java.util.stream.Collectors;

public class CrystalFeatureConfig implements FeatureConfig {
    public static final Codec<CrystalFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> {
       return instance.group(
               Codec.INT.fieldOf("min_radius")
               .withDefault(SBConfig.Config.CrystalStructureGroup.CRYSTAL_RADIUS_MIN_DEFAULT)
               .forGetter((crystalFeatureConfig) -> {
                   return crystalFeatureConfig.minRadius;
               }),
               Codec.INT.fieldOf("max_radius")
               .withDefault(SBConfig.Config.CrystalStructureGroup.CRYSTAL_RADIUS_MAX_DEFAULT)
               .forGetter((crystalFeatureConfig) -> {
                   return crystalFeatureConfig.maxRadius;
               }),
               Codec.INT.fieldOf("min_height")
               .withDefault(SBConfig.Config.CrystalStructureGroup.CRYSTAL_HEIGHT_MIN_DEFAULT)
               .forGetter((crystalFeatureConfig) -> {
                   return crystalFeatureConfig.minHeight;
               }),
               Codec.INT.fieldOf("max_height")
               .withDefault(SBConfig.Config.CrystalStructureGroup.CRYSTAL_RADIUS_MAX_DEFAULT)
               .forGetter((crystalFeatureConfig) -> {
                   return crystalFeatureConfig.maxHeight;
               }),
               Codec.INT.fieldOf("spread")
               .withDefault(SBConfig.Config.CrystalStructureGroup.CRYSTAL_SPREAD_DEFAULT)
               .forGetter((crystalFeatureConfig) -> {
                   return crystalFeatureConfig.spread;
               }),
               Codec.INT.fieldOf("density")
               .withDefault(SBConfig.Config.CrystalStructureGroup.CRYSTAL_DENSITY_DEFAULT)
               .forGetter((crystalFeatureConfig) -> {
                   return crystalFeatureConfig.density;
               }),
               Codec.FLOAT.fieldOf("tilt")
               .withDefault(SBConfig.Config.CrystalStructureGroup.CRYSTAL_TILT_DEFAULT)
               .forGetter((crystalFeatureConfig) -> {
                   return crystalFeatureConfig.tilt;
               }),
               BlockState.field_24734.listOf().fieldOf("composition")
                .forGetter((crystalFeatureConfig) -> {
                    return ImmutableList.copyOf(crystalFeatureConfig.composition);
                })
               ).apply(instance, CrystalFeatureConfig::new);
    });

    public final int minRadius, maxRadius, minHeight, maxHeight, spread, density;
    public final float tilt;
    public final List<BlockState> composition;

    private CrystalFeatureConfig(int minRadius, int maxRadius, int minHeight, int maxHeight,
                                int spread, int density, float tilt, List<BlockState> composition){
        this.minRadius = minRadius;
        this.maxRadius = maxRadius;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.spread = spread;
        this.density = density;
        this.tilt = tilt;
        this.composition = composition;
    }

    public static class Builder {
        public final int minRadius, maxRadius, minHeight, maxHeight, spread, density;
        public final float tilt;
        public final List<BlockState> composition;

        public Builder(SBConfig.Config.CrystalStructureGroup config){
            this.minRadius = config.crystal_radius_min;
            this.maxRadius = config.crystal_radius_max;
            this.minHeight = config.crystal_height_min;
            this.maxHeight = config.crystal_height_max;
            this.spread = config.crystal_spread;
            this.density = config.crystal_density;
            this.tilt = config.crystal_tilt;
            this.composition = config.crystal_growth_blocks.stream().map(Block::getDefaultState).collect(Collectors.toList());
        }

        public CrystalFeatureConfig build(){
            return new CrystalFeatureConfig(
                    this.minRadius, this.maxRadius,
                    this.minHeight, this.maxHeight,
                    this.spread, this.density,
                    this.tilt, this.composition
            );
        }
    }

}
