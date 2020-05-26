package me.crupette.surrealbiomes.world.feature;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import me.crupette.surrealbiomes.SBConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.List;
import java.util.stream.Collectors;

public class SpikesFeatureConfig implements FeatureConfig {

    public final int minRadius, maxRadius, maxHeight;
    public final float falloff;
    public final List<BlockState> surfaceBlocks, compositionBlocks;

    public SpikesFeatureConfig(SBConfig.Config.RainbowFeaturesGroup config){
        this.minRadius = config.rainbow_spike_radius_min;
        this.maxRadius = config.rainbow_spike_radius_max;
        this.maxHeight = config.rainbow_spike_height_max;
        this.falloff = config.rainbow_spike_falloff;
        this.surfaceBlocks = config.rainbow_spike_root_blocks.stream().map(Block::getDefaultState).collect(Collectors.toList());
        this.compositionBlocks = config.rainbow_spike_composition_blocks.stream().map(Block::getDefaultState).collect(Collectors.toList());
    }

    @Override
    public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
        return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(
                ops.createString("radius"), ops.createInt(
                        this.minRadius | (this.maxRadius << 16)),
                ops.createString("height"), ops.createInt(this.maxHeight),
                ops.createString("falloff"), ops.createFloat(this.falloff),
                ops.createString("root"), ops.createList(
                        this.surfaceBlocks.stream().map(blockState -> BlockState.serialize(ops, blockState).getValue())),
                ops.createString("composition"), ops.createList(
                        this.compositionBlocks.stream().map(blockState -> BlockState.serialize(ops, blockState).getValue()))
                )));
    }

    public static <T> SpikesFeatureConfig deserialize(Dynamic<T> dynamic){
        SBConfig.Config.RainbowFeaturesGroup newConfig = new SBConfig.Config.RainbowFeaturesGroup();
        int radiusMix = dynamic.get("radius").asInt(
                SBConfig.Config.RainbowFeaturesGroup.RAINBOW_SPIKE_RADIUS_MIN_DEFAULT | (SBConfig.Config.RainbowFeaturesGroup.RAINBOW_SPIKE_RADIUS_MAX_DEFAULT << 16));
        newConfig.rainbow_spike_height_max = dynamic.get("height").asInt(SBConfig.Config.RainbowFeaturesGroup.RAINBOW_SPIKE_HEIGHT_MAX_DEFAULT);
        newConfig.rainbow_spike_falloff = dynamic.get("falloff").asFloat(SBConfig.Config.RainbowFeaturesGroup.RAINBOW_SPIKE_FALLOFF_DEFAULT);

        newConfig.rainbow_spike_root_blocks = dynamic.get("root").asList(BlockState::deserialize).stream().map(BlockState::getBlock).collect(Collectors.toList());
        newConfig.rainbow_spike_composition_blocks = dynamic.get("composition").asList(BlockState::deserialize).stream().map(BlockState::getBlock).collect(Collectors.toList());

        newConfig.rainbow_spike_radius_min = radiusMix & 0xFFFF;
        newConfig.rainbow_spike_radius_max = (radiusMix >> 16);

        return new SpikesFeatureConfig(newConfig);
    }
}
