package me.crupette.surrealbiomes;

import io.github.fablabsmc.fablabs.api.fiber.v1.FiberId;
import io.github.fablabsmc.fablabs.api.fiber.v1.annotation.AnnotatedSettings;
import io.github.fablabsmc.fablabs.api.fiber.v1.annotation.Setting;
import io.github.fablabsmc.fablabs.api.fiber.v1.annotation.Setting.Constrain;
import io.github.fablabsmc.fablabs.api.fiber.v1.annotation.Settings;
import io.github.fablabsmc.fablabs.api.fiber.v1.annotation.convention.SnakeCaseConvention;
import io.github.fablabsmc.fablabs.api.fiber.v1.exception.FiberException;
import io.github.fablabsmc.fablabs.api.fiber.v1.schema.type.derived.ConfigTypes;
import io.github.fablabsmc.fablabs.api.fiber.v1.schema.type.derived.StringConfigType;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.JanksonSerializer;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.ConfigTree;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.Level;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SBConfig {
    private static final String CONFIG_NAME = "surrealbiomes.json";
    private static ConfigTree configTree = null;
    private static final JanksonSerializer JANKSON_SERIALIZER = new JanksonSerializer();

    public static Config config;

    public static final StringConfigType<FiberId> FIBER_ID = ConfigTypes.STRING.derive(
            FiberId.class, s -> new FiberId(s.substring(0, s.indexOf(':')), s.substring(s.indexOf(':') + 1)),
            FiberId::toString);

    public static void init(){
        AnnotatedSettings settings = AnnotatedSettings.create();
        settings.registerTypeMapping(FiberId.class, FIBER_ID);

        config = new Config();
        configTree = null;
        try {
            configTree = ConfigTree.builder().applyFromPojo(config, settings).build();
        } catch (FiberException e) {
            e.printStackTrace();
        }

        loadConfig();
        saveConfig();
    }

    private static void loadConfig(){
        File configFile = new File(FabricLoader.getInstance().getConfigDirectory(), CONFIG_NAME);
        if(!configFile.exists()) return;
        try {
            InputStream configIn = new FileInputStream(configFile);
            JANKSON_SERIALIZER.deserialize(configTree, configIn);
        } catch (IOException | FiberException e) {
            SBBase.log(Level.ERROR, "Could not load configuration file \"" + configFile + "\" : " + e.getMessage() + " Using defaults");
        }

        config.crystal_grass_spread_block = Registry.BLOCK.get(new Identifier(
                config.crystal_grass_spread_medium.substring(0, config.crystal_grass_spread_medium.indexOf(':')),
                config.crystal_grass_spread_medium.substring(config.crystal_grass_spread_medium.indexOf(':') + 1)));
    }

    public static void saveConfig(){
        try {
            AnnotatedSettings.DEFAULT_SETTINGS.applyToNode(configTree, config);
        } catch (FiberException e) {
            e.printStackTrace();
        }
        File configFile = new File(FabricLoader.getInstance().getConfigDirectory(), CONFIG_NAME);
        try {
            OutputStream configOut = new FileOutputStream(configFile);
            JANKSON_SERIALIZER.serialize(configTree, configOut);
        } catch (IOException | FiberException e) {
            SBBase.log(Level.ERROR, "Could not write configuration file \"" + configFile + "\" : " + e.getMessage());
        }
    }

    @Settings(namingConvention = SnakeCaseConvention.class, onlyAnnotated = true)
    public static class Config {

        public static final double CRYSTALINE_PLAINS_CHANCE_DEFUALT = 0.03D;
        @Setting(comment = "Crystalne plains rarity (0 disables, 1 is very common.) default 0.03")
        @Constrain.Range(min = 0.D, max = 1.D, step = 0.01D)
        public double crystaline_plains_chance = CRYSTALINE_PLAINS_CHANCE_DEFUALT;

        public static final double CRYSTALINE_FOREST_CHANCE_DEFUALT = 0.03D;
        @Setting(comment = "Crystalne forest rarity (0 disables, 1 is very common.) default 0.03")
        @Constrain.Range(min = 0.D, max = 1.D, step = 0.01D)
        public double crystaline_forest_chance = CRYSTALINE_FOREST_CHANCE_DEFUALT;

        public static final boolean CRYSTALINE_GRASS_SPREAD_DEFAULT = true;
        @Setting(comment = "If Crystal grass is spreadable (disable to stop crystal grass ticking. default: true)")
        public boolean crystaline_grass_spread = CRYSTALINE_GRASS_SPREAD_DEFAULT;

        public static final boolean CRYSTALINE_TAKEOVER_DEFAULT = false;
        @Setting(comment = "Enable this to allow crystal grass to create shardlings. (Will spread indefinitely. default: false)")
        public boolean crystaline_takeover = CRYSTALINE_TAKEOVER_DEFAULT;

        public static final Block CRYSTAL_GRASS_SPREAD_MEDIUM_DEFAULT = Blocks.GRASS_BLOCK;
        @Setting(comment = "What crystal grass will spread to (default minecraft:grass_block)")
        public String crystal_grass_spread_medium = Registry.BLOCK.getId(CRYSTAL_GRASS_SPREAD_MEDIUM_DEFAULT).toString();
        public Block crystal_grass_spread_block = CRYSTAL_GRASS_SPREAD_MEDIUM_DEFAULT;

        public static final int CRYSTAL_RADIUS_MIN_DEFAULT = 2;
        @Setting(comment = "Minimum radius for crystal structures (1 to 16) default 2")
        @Constrain.Range(min = 1, max = 16, step = 1)
        public int crystal_radius_min = CRYSTAL_RADIUS_MIN_DEFAULT;

        public static final int CRYSTAL_RADIUS_MAX_DEFAULT = 4;
        @Setting(comment = "Maximum radius for crystal structures (1 to 16) default 4")
        @Constrain.Range(min = 1, max = 16, step = 1)
        public int crystal_radius_max = CRYSTAL_RADIUS_MAX_DEFAULT;

        public static final int CRYSTAL_HEIGHT_MIN_DEFAULT = 12;
        @Setting(comment = "Minimum height for crystal structures (1 to 128) default 12")
        @Constrain.Range(min = 1, max = 128, step = 1)
        public int crystal_height_min = CRYSTAL_HEIGHT_MIN_DEFAULT;

        public static final int CRYSTAL_HEIGHT_MAX_DEFAULT = 22;
        @Setting(comment = "Maximum height for crystal structures (1 to 128) default 22")
        @Constrain.Range(min = 1, max = 128, step = 1)
        public int crystal_height_max = CRYSTAL_HEIGHT_MAX_DEFAULT;

        public static final float CRYSTAL_TILT_DEFAULT = 0.4f;
        @Setting(comment = "How far the crystals can potentially tilt (0 disables tilt, 4 is maximum tilt) default 0.4")
        @Constrain.Range(min = 0.f, max = 4.f, step = 0.1f)
        public float crystal_tilt = CRYSTAL_TILT_DEFAULT;

        public static final int CRYSTAL_SPREAD_DEFAULT = 2;
        @Setting(comment = "Potential distance between each crystal in crystal structure (0 to 8) default 2")
        @Constrain.Range(min = 0, max = 8, step = 1)
        public int crystal_spread = CRYSTAL_SPREAD_DEFAULT;

        //@Setting(comment = "What block crystal growths are composed of (list of block id's)")
        public List<String> crystal_growth_blocks = new ArrayList<>(Arrays.asList(
                "minecraft:red_stained_glass",
                "minecraft:orange_stained_glass",
                "minecraft:yellow_stained_glass",
                "minecraft:green_stained_glass",
                "minecraft:blue_stained_glass",
                "minecraft:purple_stained_glass",
                "minecraft:pink_stained_glass",
                "minecraft:cyan_stained_glass",
                "minecraft:white_stained_glass",
                "minecraft:white_stained_glass"));
        //Bug with fiber. Can't handle parsing a list of strings ("JsonPrimitive cannot be cast to java.lang.String") fixed in 0.22

        public static final double RAINBOW_DESERT_CHANCE_DEFAULT = 0.03D;
        @Setting(comment = "Rainbow desert rarity (0 disables, 1 is very common) default 0.03")
        @Constrain.Range(min = 0.D, max = 1.D, step = 0.01D)
        public double rainbow_desert_chance = RAINBOW_DESERT_CHANCE_DEFAULT;


        public static final double RAINBOW_FREQUENCY_DEFAULT = 0.01D;
        @Setting(comment = "Rainbow desert rainbow frequency")
        @Constrain.Range(min = 0.01D, max = 1.D, step = 0.01D)
        public double rainbow_frequency = RAINBOW_FREQUENCY_DEFAULT;
    }
}

