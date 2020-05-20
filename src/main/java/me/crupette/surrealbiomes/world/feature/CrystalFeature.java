package me.crupette.surrealbiomes.world.feature;

import com.mojang.datafixers.Dynamic;
import me.crupette.surrealbiomes.SBBase;
import me.crupette.surrealbiomes.SBConfig;
import me.crupette.surrealbiomes.block.SurrealBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class CrystalFeature extends Feature<DefaultFeatureConfig> {

    private static final List<BlockState> COLORS = new ArrayList<>();

    public CrystalFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configDeserializer) {
        super(configDeserializer);

        if(COLORS.size() != 0) return;
        for(String configstr : SBConfig.config.crystal_growth_blocks){
            Identifier id = new Identifier(configstr.substring(0, configstr.indexOf(':')), configstr.substring(configstr.indexOf(':') + 1));
            Block retrieved = Registry.BLOCK.get(id);
            if(retrieved.is(Blocks.AIR)){
                SBBase.log(Level.ERROR, "For crystalGrowthBlocks, passed invalid block id: \"" + configstr + "\". Using minecraft:glass");
                retrieved = Blocks.GLASS;
            }
            COLORS.add(retrieved.getDefaultState());
        }

        int swpbuf;
        if(SBConfig.config.crystal_height_min > SBConfig.config.crystal_height_max){
            swpbuf = SBConfig.config.crystal_height_min;
            SBConfig.config.crystal_height_min = SBConfig.config.crystal_height_max;
            SBConfig.config.crystal_height_max = swpbuf;
            SBBase.log(Level.WARN, "crystal_feature_height_min greater than maximum, switching");
        }
        if(SBConfig.config.crystal_radius_min > SBConfig.config.crystal_radius_max){
            swpbuf = SBConfig.config.crystal_radius_min;
            SBConfig.config.crystal_radius_min = SBConfig.config.crystal_radius_max;
            SBConfig.config.crystal_radius_max = swpbuf;
            SBBase.log(Level.WARN, "crystal_feature_radius_min greater than maximum, switching");
        }
    }

    private void generateCrystal(ServerWorldAccess serverWorldAccess, StructureAccessor accessor, ChunkGenerator generator, Random random, BlockPos center, DefaultFeatureConfig config){
        int height = random.nextInt(SBConfig.config.crystal_height_max - SBConfig.config.crystal_height_min) + SBConfig.config.crystal_height_min;
        int radius = random.nextInt(SBConfig.config.crystal_radius_max - SBConfig.config.crystal_radius_min) + SBConfig.config.crystal_radius_min;
        float slant = random.nextFloat() * SBConfig.config.crystal_tilt;
        float dir = random.nextFloat() * 360.f;
        int spread = SBConfig.config.crystal_spread;
        BlockPos pos = new BlockPos(center.getX() + (random.nextInt(spread * 2) - spread),
                                    center.getY(),
                                    center.getZ() + (random.nextInt(spread * 2) - spread));

        while (serverWorldAccess.isAir(pos) && pos.getY() > 2) pos = pos.down();
        if(!serverWorldAccess.getBlockState(pos).isOf(SurrealBlocks.CRYSTAL_GRASS)) return;
        pos = pos.up();

        double addx = 0, addz = 0;
        int color = random.nextInt(COLORS.size());
        BlockState inside = COLORS.get(color);
        BlockState outside = COLORS.get(color);
        for(int y = 0; y <= height; y++){
            if(y >= height - 4) radius--;
            for(int x = -radius; x <= radius; x++) {
                for(int z = -radius; z <= radius; z++) {
                    int distcheck = Math.abs(x) + Math.abs(z);
                    if(distcheck > radius) continue;
                    if(distcheck == radius || y == height){
                        if(serverWorldAccess.isAir(pos.add(x, 0, z))) serverWorldAccess.setBlockState(pos.add(x, 0, z), outside, 2);
                    }else {
                        serverWorldAccess.setBlockState(pos.add(x, 0, z), inside, 2);
                    }
                }
            }
            addx += Math.sin(dir) * slant;
            addz += Math.sin(dir) * slant;

            pos = pos.add(addx > 1 ? 1 : 0, 1, addz > 1 ? 1 : 0);
            addx = addx > 1 ? 0 : addx;
            addz = addz > 1 ? 0 : addz;
        }
    }

    @Override
    public boolean generate(ServerWorldAccess serverWorldAccess, StructureAccessor accessor, ChunkGenerator generator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        int count = random.nextInt(4) + 3;
        while (count > 0){
            generateCrystal(serverWorldAccess, accessor, generator, random, pos, config);
            count--;
        }
        return true;
    }
}
