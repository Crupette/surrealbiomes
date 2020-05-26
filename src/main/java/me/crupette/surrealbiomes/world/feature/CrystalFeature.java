package me.crupette.surrealbiomes.world.feature;

import com.mojang.datafixers.Dynamic;
import me.crupette.surrealbiomes.block.SurrealBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;
import java.util.function.Function;

public class CrystalFeature extends Feature<CrystalFeatureConfig> {

    public CrystalFeature(Function<Dynamic<?>, ? extends CrystalFeatureConfig> configFactory) {
        super(configFactory);
    }

    private void generateCrystal(IWorld world, Random random, BlockPos center, CrystalFeatureConfig config){
        int height = random.nextInt(config.maxHeight - config.minHeight) + config.minHeight;
        int radius = random.nextInt(config.maxRadius - config.minRadius) + config.minRadius;
        float slant = random.nextFloat() * config.tilt;
        float dir = random.nextFloat() * 360.f;
        int spread = config.spread;
        BlockPos pos = new BlockPos(center.getX() + (random.nextInt(spread) - spread),
                                    center.getY(),
                                    center.getZ() + (random.nextInt(spread) - spread));

        while (world.isAir(pos) && pos.getY() > 2) pos = pos.down();
        if(world.getBlockState(pos).getBlock() != SurrealBlocks.CRYSTAL_GRASS) return;
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
                        if(world.isAir(pos.add(x, 0, z))) world.setBlockState(pos.add(x, 0, z), block, 2);
                    }else {
                        world.setBlockState(pos.add(x, 0, z), block, 2);
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
    public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, BlockPos pos, CrystalFeatureConfig config) {
        int count = random.nextInt(config.density - 4) + config.density;
        while (count > 0){
            generateCrystal(world, random, pos, config);
            count--;
        }
        return true;
    }
}
