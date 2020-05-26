package me.crupette.surrealbiomes;

import me.crupette.surrealbiomes.block.SurrealBlocks;
import me.crupette.surrealbiomes.world.biome.SurrealBiomes;
import net.fabricmc.api.ModInitializer;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SBBase implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger();
    public static final String MOD_NAME = "Surreal Biomes";
    public static final String MOD_ID = "surrealbiomes";

    @Override
    public void onInitialize() {
        SurrealBlocks.registerBlocks();
        SBConfig.init();

        SurrealBiomes.initialize();
    }
    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }
}