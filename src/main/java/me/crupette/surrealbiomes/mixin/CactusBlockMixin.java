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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;

@Mixin(CactusBlock.class)
public abstract class CactusBlockMixin{

    @Inject(method = "canPlaceAt",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldView;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;", ordinal = 1),
            cancellable = true)
    public void colorPlacePatch(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> ci) {
        BlockState low = world.getBlockState(pos.down());
        if(low.getBlock() instanceof SandBlock && !world.getBlockState(pos.up()).getMaterial().isLiquid()) ci.setReturnValue(true);
    }
}
