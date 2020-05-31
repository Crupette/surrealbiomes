package me.crupette.surrealbiomes.world.feature;

import com.mojang.datafixers.Dynamic;
import me.crupette.surrealbiomes.block.SurrealBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
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
        float roll = random.nextFloat() * 360.f;
        float pitch = (random.nextFloat() * 5.f) + 87.5f;
        float tilt = random.nextFloat() * config.tilt;
        int spread = config.spread;
        BlockPos pos = new BlockPos(center.getX() + (random.nextInt(spread * 2) - spread),
                center.getY(),
                center.getZ() + (random.nextInt(spread * 2) - spread));

        while (world.isAir(pos) && pos.getY() > 2) pos = pos.down();
        if(!world.getBlockState(pos).getBlock().equals(SurrealBlocks.CRYSTAL_GRASS)) return;
        pos = pos.up();

        Vec3d rot = new Vec3d(Math.toRadians(roll), Math.toRadians(pitch),0);
        Vec3d forward = new Vec3d(Math.cos(rot.y) * Math.sin(rot.x),
                Math.sin(rot.y),
                Math.sin(rot.y) * Math.cos(rot.x));

        int color = random.nextInt(config.composition.size());
        BlockState block = config.composition.get(color);

        for(int len = 0; len < height; len++){
            BlockPos placePos = new BlockPos(forward.x * len * tilt, len, forward.z * len * tilt);//new BlockPos(forward.x * len, forward.y * len, forward.z * len);
            for(int x = -radius; x <= radius; x++) for(int y = -radius; y <= radius; y++) for(int z = -radius; z <= radius; z++){
                if(Math.abs(x) + Math.abs(y) + Math.abs(z) > radius) continue;
                if(!world.getBlockState(placePos.add(x, y, z).add(pos)).getBlock().equals(Blocks.AIR)) continue;
                world.setBlockState(placePos.add(x, y, z).add(pos), block, 2);
            }
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
