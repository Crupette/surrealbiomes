package me.crupette.surrealbiomes.mixin;

import me.crupette.surrealbiomes.block.ColoredSandBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SandBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Random;

@Mixin(RabbitEntity.class)
public abstract class RabbitEntityMixin {

    @Inject(method = "canSpawn",
            at = @At(value = "HEAD"), cancellable = true)
    private static void checkSpawn(EntityType<RabbitEntity> entity, WorldAccess world,
                                   SpawnReason spawnReason, BlockPos pos, Random random,
                                   CallbackInfoReturnable<Boolean> ci){
        BlockState below = world.getBlockState(pos.down(1));
        if(below.getBlock() instanceof ColoredSandBlock && world.getBaseLightLevel(pos, 0) > 8) ci.setReturnValue(true);
    }
}
