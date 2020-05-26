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

public class CrystalFeatureConfig implements FeatureConfig {

    public final int minRadius, maxRadius, minHeight, maxHeight, spread, density;
    public final float tilt;
    public final List<BlockState> composition;

    public CrystalFeatureConfig(SBConfig.Config.CrystalStructureGroup config){
        this.minRadius = config.crystal_radius_min;
        this.maxRadius = config.crystal_radius_max;
        this.minHeight = config.crystal_height_min;
        this.maxHeight = config.crystal_height_max;
        this.spread = config.crystal_spread;
        this.density = config.crystal_density;
        this.tilt = config.crystal_tilt;
        this.composition = config.crystal_growth_blocks.stream().map(Block::getDefaultState).collect(Collectors.toList());
    }

    @Override
    public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
        return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(
                ops.createString("radius"), ops.createInt(
                        this.minRadius | (this.maxRadius << 16)),
                ops.createString("height"), ops.createInt(
                        this.minHeight | (this.maxHeight << 16)),
                ops.createString("spread"), ops.createInt(this.spread),
                ops.createString("tilt"), ops.createFloat(this.tilt),
                ops.createString("composition"), ops.createList(
                        this.composition.stream().map(blockstate -> BlockState.serialize(ops, blockstate).getValue())
                )
                )));
    }

    public static <T> CrystalFeatureConfig deserialize(Dynamic<T> dynamic){
        SBConfig.Config.CrystalStructureGroup newConfig = new SBConfig.Config.CrystalStructureGroup();
        int radiusMix = dynamic.get("radius").asInt(
                SBConfig.Config.CrystalStructureGroup.CRYSTAL_RADIUS_MIN_DEFAULT | (SBConfig.Config.CrystalStructureGroup.CRYSTAL_RADIUS_MAX_DEFAULT << 16));
        int heightMix = dynamic.get("height").asInt(
                SBConfig.Config.CrystalStructureGroup.CRYSTAL_HEIGHT_MIN_DEFAULT | (SBConfig.Config.CrystalStructureGroup.CRYSTAL_HEIGHT_MAX_DEFAULT << 16));
        newConfig.crystal_spread = dynamic.get("spread").asInt(SBConfig.Config.CrystalStructureGroup.CRYSTAL_SPREAD_DEFAULT);
        newConfig.crystal_tilt = dynamic.get("tilt").asFloat(SBConfig.Config.CrystalStructureGroup.CRYSTAL_TILT_DEFAULT);
        newConfig.crystal_growth_blocks = dynamic.get("composition").asList(BlockState::deserialize).stream().map(BlockState::getBlock).collect(Collectors.toList());

        newConfig.crystal_radius_min = radiusMix & 0xFFFF;
        newConfig.crystal_radius_max = (radiusMix >> 16);
        newConfig.crystal_height_min = heightMix & 0xFFFF;
        newConfig.crystal_height_max = (heightMix >> 16);
        return new CrystalFeatureConfig(newConfig);
    }

}
