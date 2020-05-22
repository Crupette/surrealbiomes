package me.crupette.surrealbiomes.block;

import io.github.fablabsmc.fablabs.api.fiber.v1.FiberId;
import me.crupette.surrealbiomes.SBBase;
import me.crupette.surrealbiomes.SBConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.WorldView;
import org.apache.logging.log4j.Level;

import java.util.Random;

public class CrystalGrassBlock extends Block {

    private static Block spreadMedium;
    public CrystalGrassBlock(Settings settings) {
        super(settings);

        spreadMedium = SBConfig.config.crystal_grass_spread_block;
        if(spreadMedium.is(Blocks.AIR)){
            SBBase.log(Level.ERROR, "Value for crystalSpreadMedium given invalid block id: \"" + SBConfig.config.crystal_grass_spread_medium + "\". Using minecraft:grass_block");
            spreadMedium = Blocks.GRASS_BLOCK;
        }
    }

    private static boolean canSurvive(BlockState state, WorldView worldView, BlockPos pos){
        BlockPos blockPos = pos.up();
        return worldView.getFluidState(blockPos).getFluid() == Fluids.EMPTY;
    }

    private static boolean canSpread(BlockState state, WorldView worldView, BlockPos pos){
        if(!canSurvive(state, worldView, pos)) return false;
        for(int y = -3; y <= 3; y++) for(int z = -3; z <= 3; z++) for(int x = -3; x <= 3; x++){
            if(Math.abs(x) + Math.abs(z) > 4) continue;
            BlockPos chkpos = pos.add(x, y + 1, z);
            if(worldView.getBlockState(chkpos).isOf(SurrealBlocks.REDDER_CRYSTAL_SHARDLING)) return true;
            if(worldView.getBlockState(chkpos).isOf(SurrealBlocks.GREENER_CRYSTAL_SHARDLING)) return true;
            if(worldView.getBlockState(chkpos).isOf(SurrealBlocks.BLUER_CRYSTAL_SHARDLING)) return true;
        }
        return false;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if(!SBConfig.config.crystaline_grass_spread) return;
        if(!canSpread(state, world, pos)){
            world.setBlockState(pos, Blocks.GRASS_BLOCK.getDefaultState());
            return;
        }
            BlockPos spreadpos = pos.add(
                    random.nextInt(3) - 1,
                    random.nextInt(5) - 3,
                    random.nextInt(3) - 1);

            if (world.getBlockState(spreadpos).isOf(spreadMedium) && canSpread(state, world, spreadpos)) {
                world.setBlockState(spreadpos, SurrealBlocks.CRYSTAL_GRASS.getDefaultState());
                int growchance = random.nextInt(4);
                if(growchance == 0 && SBConfig.config.crystaline_takeover){
                    int color = random.nextInt(3);
                    if(color == 0) world.setBlockState(spreadpos.up(), SurrealBlocks.REDDER_CRYSTAL_SHARDLING.getDefaultState());
                    if(color == 1) world.setBlockState(spreadpos.up(), SurrealBlocks.GREENER_CRYSTAL_SHARDLING.getDefaultState());
                    if(color == 2) world.setBlockState(spreadpos.up(), SurrealBlocks.BLUER_CRYSTAL_SHARDLING.getDefaultState());
            }
        }
    }
}
