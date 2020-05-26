package me.crupette.surrealbiomes.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.SandBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class ColoredSandBlock extends SandBlock {
    public ColoredSandBlock(int color, FabricBlockSettings settings) {
        super(color, settings
                .allowsSpawning((state, world, pos, type) -> type == EntityType.RABBIT));
    }
}
