package me.crupette.surrealbiomes.world.surfacebuilder;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import me.crupette.surrealbiomes.SBConfig;
import me.crupette.surrealbiomes.block.SurrealBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.SandBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;

public class RainbowSandsSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {

    protected OctaveSimplexNoiseSampler colorNoise;
    protected long seed;

    private static final BlockState[] COLOR_BLOCKS = {
            SurrealBlocks.RED_SAND.getDefaultState(),
            SurrealBlocks.ORANGE_SAND.getDefaultState(),
            SurrealBlocks.YELLOW_SAND.getDefaultState(),
            SurrealBlocks.GREEN_SAND.getDefaultState(),
            SurrealBlocks.BLUE_SAND.getDefaultState(),
            SurrealBlocks.PURPLE_SAND.getDefaultState()
    };

    public RainbowSandsSurfaceBuilder(Codec<TernarySurfaceConfig> codec) {
        super(codec);
    }

    public void generate(Random random, Chunk chunk, Biome biome, int i, int j, int k, double d, BlockState blockState, BlockState blockState2, int l, long m, TernarySurfaceConfig ternarySurfaceConfig) {
        this.generate(random, chunk, biome, i, j, k, d, blockState, blockState2, ternarySurfaceConfig.getUnderwaterMaterial(), l);
    }

    protected void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState replacedBlock, BlockState fluidBlock, BlockState underwaterBlock, int seaLevel) {
        int color = (int)(this.colorNoise.sample(
                (double)x * SBConfig.config.rainbow_frequency,
                (double)z * SBConfig.config.rainbow_frequency * 2,
                false) * COLOR_BLOCKS.length);

        BlockState topBlock = COLOR_BLOCKS[Math.abs(color)];
        BlockState topState = topBlock;
        BlockState bottomState = topBlock;
        BlockPos.Mutable blockPos = new BlockPos.Mutable();
        int surfaceRemaining = -1;
        int surfaceThickness = (int)(noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        int xrel = x & 15;
        int zrel = z & 15;

        for(int y = height; y >= 0; --y) {
            blockPos.set(xrel, y, zrel);
            BlockState blockState = chunk.getBlockState(blockPos);
            if (blockState.isAir()) {
                surfaceRemaining = -1;
            } else if (blockState.isOf(replacedBlock.getBlock())) {
                if (surfaceRemaining == -1) {
                    if (surfaceThickness <= 0) {
                        topState = Blocks.AIR.getDefaultState();
                        bottomState = replacedBlock;
                    } else if (y >= seaLevel - 4 && y <= seaLevel + 1) {
                        topState = topBlock;
                        bottomState = topBlock;
                    }

                    if (y < seaLevel && (topState == null || topState.isAir())) {
                        if (biome.getTemperature(blockPos.set(x, y, z)) < 0.15F) {
                            topState = Blocks.ICE.getDefaultState();
                        } else {
                            topState = fluidBlock;
                        }

                        blockPos.set(xrel, y, zrel);
                    }

                    surfaceRemaining = surfaceThickness;
                    if (y >= seaLevel - 1) {
                        chunk.setBlockState(blockPos, topState, false);
                    } else if (y < seaLevel - 7 - surfaceThickness) {
                        topState = Blocks.AIR.getDefaultState();
                        bottomState = replacedBlock;
                        chunk.setBlockState(blockPos, underwaterBlock, false);
                    } else {
                        chunk.setBlockState(blockPos, bottomState, false);
                    }
                } else if (surfaceRemaining > 0) {
                    --surfaceRemaining;
                    chunk.setBlockState(blockPos, bottomState, false);
                    if (surfaceRemaining == 0 && bottomState.getBlock() instanceof SandBlock && surfaceThickness > 1) {
                        surfaceRemaining = random.nextInt(4) + Math.max(0, y - 63);
                        bottomState = bottomState.isOf(Blocks.RED_SAND) ? Blocks.RED_SANDSTONE.getDefaultState() : Blocks.SANDSTONE.getDefaultState();
                    }
                }
            }
        }

    }

    public void initSeed(long seed){
        if(this.seed != seed){
            ChunkRandom chunkRandom = new ChunkRandom(seed);
            colorNoise = new OctaveSimplexNoiseSampler(chunkRandom, ImmutableList.of(0));
            this.seed = seed;
        }
    }
}
