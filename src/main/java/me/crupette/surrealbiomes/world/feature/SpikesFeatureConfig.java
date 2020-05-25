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

public class SpikesFeatureConfig implements FeatureConfig {
    public static final Codec<SpikesFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(
                Codec.INT.fieldOf("min_radius")
                .withDefault(SBConfig.Config.RainbowFeaturesGroup.RAINBOW_SPIKE_RADIUS_MIN_DEFAULT)
                .forGetter(spikesFeatureConfig -> {
                    return spikesFeatureConfig.minRadius;
                }),
                Codec.INT.fieldOf("max_radius")
                        .withDefault(SBConfig.Config.RainbowFeaturesGroup.RAINBOW_SPIKE_RADIUS_MAX_DEFAULT)
                        .forGetter(spikesFeatureConfig -> {
                            return spikesFeatureConfig.maxRadius;
                        }),
                Codec.INT.fieldOf("max_height")
                        .withDefault(SBConfig.Config.RainbowFeaturesGroup.RAINBOW_SPIKE_HEIGHT_MAX_DEFAULT)
                        .forGetter(spikesFeatureConfig -> {
                            return spikesFeatureConfig.maxHeight;
                        }),
                Codec.FLOAT.fieldOf("falloff")
                        .withDefault(SBConfig.Config.RainbowFeaturesGroup.RAINBOW_SPIKE_FALLOFF_DEFAULT)
                        .forGetter(spikesFeatureConfig -> {
                            return spikesFeatureConfig.falloff;
                        }),
                BlockState.field_24734.listOf().fieldOf("surface_blocks")
                        .forGetter((spikesFeatureConfig) -> {
                            return ImmutableList.copyOf(spikesFeatureConfig.surfaceBlocks);
                        }),
                BlockState.field_24734.listOf().fieldOf("composition_blocks")
                        .forGetter((spikesFeatureConfig) -> {
                            return ImmutableList.copyOf(spikesFeatureConfig.compositionBlocks);
                        })
        ).apply(instance, SpikesFeatureConfig::new);
    });

    public final int minRadius, maxRadius, maxHeight;
    public final float falloff;
    public final List<BlockState> surfaceBlocks, compositionBlocks;

    protected SpikesFeatureConfig(int minRadius, int maxRadius,
                                  int maxHeight, float falloff,
                                  List<BlockState> surfaceBlocks,
                                  List<BlockState> compositionBlocks){
        this.minRadius = minRadius;
        this.maxRadius = maxRadius;
        this.maxHeight = maxHeight;
        this.falloff = falloff;
        this.surfaceBlocks = surfaceBlocks;
        this.compositionBlocks = compositionBlocks;
    }

    public static class Builder {
        public final int minRadius, maxRadius, maxHeight;
        public final float falloff;
        public final List<BlockState> surfaceBlocks, compositionBlocks;

        public Builder(SBConfig.Config.RainbowFeaturesGroup config){
            this.minRadius = config.rainbow_spike_radius_min;
            this.maxRadius = config.rainbow_spike_radius_max;
            this.maxHeight = config.rainbow_spike_height_max;
            this.falloff = config.rainbow_spike_falloff;
            this.surfaceBlocks = config.rainbow_spike_root_blocks.stream().map(Block::getDefaultState).collect(Collectors.toList());
            this.compositionBlocks = config.rainbow_spike_composition_blocks.stream().map(Block::getDefaultState).collect(Collectors.toList());
        }

        public SpikesFeatureConfig build(){
            return new SpikesFeatureConfig(
                    this.minRadius, this.maxRadius,
                    this.maxHeight, this.falloff,
                    this.surfaceBlocks, this.compositionBlocks);
        }
    }
}
