package me.crupette.surrealbiomes;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.api.SyntaxError;
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
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Level;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SBConfig {
    private static final String CONFIG_NAME = "surrealbiomes.json";
    public static Config config;

    public static final StringConfigType<FiberId> FIBER_ID = ConfigTypes.STRING.derive(
            FiberId.class, s -> new FiberId(s.substring(0, s.indexOf(':')), s.substring(s.indexOf(':') + 1)),
            FiberId::toString);

    public static void init(){
        AnnotatedSettings settings = AnnotatedSettings.create();
        settings.registerTypeMapping(FiberId.class, FIBER_ID);

        config = new Config();
        ConfigTree tree = null;
        try {
            tree = ConfigTree.builder().applyFromPojo(config, settings).build();
        } catch (FiberException e) {
            e.printStackTrace();
        }

        JanksonSerializer serializer = new JanksonSerializer();
        File configFile = new File(FabricLoader.getInstance().getConfigDirectory(), CONFIG_NAME);

        if(configFile.exists()){
            try {
                InputStream configIn = new FileInputStream(configFile);
                serializer.deserialize(tree, configIn);
            } catch (IOException | FiberException e) {
                SBBase.log(Level.ERROR, "Could not load configuration file \"" + configFile + "\" : " + e.getMessage() + " Using defaults");
            }
        }else{
            try {
                OutputStream configOut = new FileOutputStream(configFile);
                serializer.serialize(tree, configOut);
            } catch (IOException | FiberException e) {
                SBBase.log(Level.ERROR, "Could not write configuration file \"" + configFile + "\" : " + e.getMessage());
            }
        }
    }

    @Settings(namingConvention = SnakeCaseConvention.class, onlyAnnotated = true)
    public static class Config {

        @Setting(comment = "Crystalne plains rarity (0 disables, 1 is very common.) default 0.03")
        @Constrain.Range(min = 0.D, max = 1.D, step = 0.01D)
        public double crystaline_plains_chance = 0.03D;

        @Setting(comment = "Crystalne forest rarity (0 disables, 1 is very common.) default 0.03")
        @Constrain.Range(min = 0.D, max = 1.D, step = 0.01D)
        public double crystaline_forest_chance = 0.03D;

        @Setting(comment = "If Crystal grass is spreadable (disable to stop crystal grass ticking. default: true)")
        public boolean crystaline_grass_spread = true;

        @Setting(comment = "Enable this to allow crystal grass to create shardlings. (Will spread indefinitely. default: false)")
        public boolean crystaline_takeover = false;

        @Setting(comment = "What crystal grass will spread to (default minecraft:grass_block)")
        public FiberId crystal_grass_spread_medium = new FiberId("minecraft", "grass_block");

        @Setting(comment = "Minimum radius for crystal structures (1 to 16) default 2")
        @Constrain.Range(min = 1, max = 16, step = 1)
        public int crystal_radius_min = 2;

        @Setting(comment = "Maximum radius for crystal structures (1 to 16) default 4")
        @Constrain.Range(min = 1, max = 16, step = 1)
        public int crystal_radius_max = 4;

        @Setting(comment = "Minimum height for crystal structures (1 to 128) default 12")
        @Constrain.Range(min = 1, max = 128, step = 1)
        public int crystal_height_min = 12;

        @Setting(comment = "Maximum height for crystal structures (1 to 128) default 22")
        @Constrain.Range(min = 1, max = 128, step = 1)
        public int crystal_height_max = 22;

        @Setting(comment = "How far the crystals can potentially tilt (0 disables tilt, 4 is maximum tilt) default 0.4")
        @Constrain.Range(min = 0.f, max = 4.f, step = 0.1f)
        public float crystal_tilt = 0.4f;

        @Setting(comment = "Potential distance between each crystal in crystal structure (0 to 8) default 2")
        @Constrain.Range(min = 0, max = 8, step = 1)
        public int crystal_spread = 2;

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
        //Bug with fiber. Can't handle parsing a list of strings ("JsonPrimitive cannot be cast to java.lang.String")
    }
}

