package me.crupette.surrealbiomes.world.surfacebuilder;

import me.crupette.surrealbiomes.SBBase;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class SurrealSurfaceBuilders {

    public static final SurfaceBuilder<TernarySurfaceConfig> RAINBOW_SANDS = Registry.register(
            Registry.SURFACE_BUILDER, new Identifier(SBBase.MOD_ID, "rainbow_sands"), new RainbowSandsSurfaceBuilder(TernarySurfaceConfig.field_25017));
}
