package me.crupette.surrealbiomes.world.feature;

import com.mojang.serialization.Codec;
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

public class CrystalFeature extends Feature<CrystalFeatureConfig> {

    public CrystalFeature(Codec<CrystalFeatureConfig> codec) {
        super(codec);
    }

    private void generateCrystal(ServerWorldAccess serverWorldAccess, StructureAccessor accessor, ChunkGenerator generator, Random random, BlockPos center, CrystalFeatureConfig config){
        int height = random.nextInt(config.maxHeight - config.minHeight) + config.minHeight;
        int radius = random.nextInt(config.maxRadius - config.minRadius) + config.minRadius;
        float slant = random.nextFloat() * config.tilt;
        float dir = random.nextFloat() * 360.f;
        int spread = config.spread;
        BlockPos pos = new BlockPos(center.getX() + (random.nextInt(spread) - spread),
                                    center.getY(),
                                    center.getZ() + (random.nextInt(spread) - spread));

        while (serverWorldAccess.isAir(pos) && pos.getY() > 2) pos = pos.down();
        if(!serverWorldAccess.getBlockState(pos).isOf(SurrealBlocks.CRYSTAL_GRASS)) return;
        pos = pos.up();

        double addx = 0, addz = 0;
        int color = random.nextInt(config.composition.size());
        BlockState block = config.composition.get(color);
        for(int y = 0; y <= height; y++){
            if(y >= height - 4) radius--;
            for(int x = -radius; x <= radius; x++) {
                for(int z = -radius; z <= radius; z++) {
                    int distcheck = Math.abs(x) + Math.abs(z);
                    if(distcheck > radius) continue;
                    if(distcheck == radius || y == height){
                        if(serverWorldAccess.isAir(pos.add(x, 0, z))) serverWorldAccess.setBlockState(pos.add(x, 0, z), block, 2);
                    }else {
                        serverWorldAccess.setBlockState(pos.add(x, 0, z), block, 2);
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
    public boolean generate(ServerWorldAccess serverWorldAccess, StructureAccessor accessor, ChunkGenerator generator, Random random, BlockPos pos, CrystalFeatureConfig config) {
        int count = random.nextInt(config.density - 4) + config.density;
        while (count > 0){
            generateCrystal(serverWorldAccess, accessor, generator, random, pos, config);
            count--;
        }
        return true;
    }
}
