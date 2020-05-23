package me.crupette.surrealbiomes.mixin;

import net.minecraft.block.*;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;

@Mixin(CactusBlock.class)
public abstract class CactusBlockMixin extends Block {

    public CactusBlockMixin(Settings settings) {
        super(settings);
    }

    //I'm bad at mixins.
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Iterator var4 = Direction.Type.HORIZONTAL.iterator();

        Direction direction;
        Material material;
        do {
            if (!var4.hasNext()) {
                BlockState blockState2 = world.getBlockState(pos.down());
                return (blockState2.isOf(Blocks.CACTUS) || blockState2.getBlock() instanceof SandBlock) && !world.getBlockState(pos.up()).getMaterial().isLiquid();
            }

            direction = (Direction)var4.next();
            BlockState blockState = world.getBlockState(pos.offset(direction));
            material = blockState.getMaterial();
        } while(!material.isSolid() && !world.getFluidState(pos.offset(direction)).matches(FluidTags.LAVA));

        return false;
    }
}
