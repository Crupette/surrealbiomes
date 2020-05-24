package me.crupette.surrealbiomes.mixin;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(DeadBushBlock.class)
public abstract class DeadBushBlockMixin{

    @Inject(method="canPlantOnTop",
    at = @At(value="HEAD"), cancellable = true)
    public void colorsandpatch(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> ci) {
        Block block = floor.getBlock();
        if(block instanceof SandBlock) ci.setReturnValue(true);
    }
}
