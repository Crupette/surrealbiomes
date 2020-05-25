package me.crupette.surrealbiomes;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonObject;
import com.google.gson.JsonSerializationContext;
import io.github.fablabsmc.fablabs.api.fiber.v1.FiberId;
import io.github.fablabsmc.fablabs.api.fiber.v1.annotation.AnnotatedSettings;
import io.github.fablabsmc.fablabs.api.fiber.v1.annotation.Setting;
import io.github.fablabsmc.fablabs.api.fiber.v1.annotation.Setting.Constrain;
import io.github.fablabsmc.fablabs.api.fiber.v1.annotation.Settings;
import io.github.fablabsmc.fablabs.api.fiber.v1.annotation.collect.MemberCollector;
import io.github.fablabsmc.fablabs.api.fiber.v1.annotation.convention.SnakeCaseConvention;
import io.github.fablabsmc.fablabs.api.fiber.v1.exception.FiberException;
import io.github.fablabsmc.fablabs.api.fiber.v1.exception.ValueDeserializationException;
import io.github.fablabsmc.fablabs.api.fiber.v1.schema.type.derived.ConfigType;
import io.github.fablabsmc.fablabs.api.fiber.v1.schema.type.derived.ConfigTypes;
import io.github.fablabsmc.fablabs.api.fiber.v1.schema.type.derived.StringConfigType;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.JanksonValueSerializer;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.JsonTypeSerializer;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.ConfigTree;
import io.github.fablabsmc.fablabs.impl.fiber.serialization.FiberSerialization;
import me.crupette.surrealbiomes.SBBase;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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
    private static AnnotatedSettings annotatedSettings = null;
    private static JanksonValueSerializer SERIALIZER = new JanksonValueSerializer(false);

    public static Config config;

    public static final StringConfigType<Block> BLOCK_ID = ConfigTypes.STRING.derive(
            Block.class, s -> Registry.BLOCK.get(new Identifier(
                    s.substring(0, s.indexOf(':')), s.substring(s.indexOf(':') + 1))),
            block -> Registry.BLOCK.getId(block).toString());

    public static void init(){
        annotatedSettings = AnnotatedSettings.builder()
                .registerTypeMapping(Block.class, BLOCK_ID)
                .build();

        config = new Config();
        configTree = ConfigTree.builder().applyFromPojo(config, annotatedSettings).build();

        loadConfig();
        saveConfig();
    }

    private static void loadConfig(){
        File configFile = new File(FabricLoader.getInstance().getConfigDirectory(), CONFIG_NAME);
        if(!configFile.exists()) return;
        try {
            InputStream configIn = new FileInputStream(configFile);
            FiberSerialization.deserialize(configTree, configIn, SERIALIZER);
        } catch (IOException | ValueDeserializationException e) {
            SBBase.log(Level.ERROR, "Could not load configuration file \"" + configFile + "\" : " + e.getMessage() + " Using defaults");
        }
    }

    public static void saveConfig(){
        try {
            annotatedSettings.applyToNode(configTree, config);
        } catch (FiberException e) {
            e.printStackTrace();
        }
        File configFile = new File(FabricLoader.getInstance().getConfigDirectory(), CONFIG_NAME);
        try {
            OutputStream configOut = new FileOutputStream(configFile);
            FiberSerialization.serialize(configTree, configOut, SERIALIZER);
        } catch (IOException e) {
            SBBase.log(Level.ERROR, "Could not write configuration file \"" + configFile + "\" : " + e.getMessage());
        }
    }

    @Settings
    public static class Config {

        @Setting.Group(name = "crystaline_biome_settings")
        public CrystalGroup crystal = new CrystalGroup();

        @Setting.Group(name = "crystaline_structures_settings")
        public CrystalStructureGroup crystalStructure = new CrystalStructureGroup();

        @Setting.Group(name = "rainbow_desert_biome_settings")
        public RainbowGroup rainbow = new RainbowGroup();

        @Setting.Group(name = "rainbow_spike_structure_settings")
        public RainbowFeaturesGroup rainbowFeatures = new RainbowFeaturesGroup();

        public static class CrystalGroup {
            @Setting(ignore = true)
            public static final double PLAINS_CHANCE_DEFUALT = 0.03D;
            @Constrain.Range(min = 0.D, max = 1.D, step = 0.01D)
            public double plains_chance = PLAINS_CHANCE_DEFUALT;

            @Setting(ignore = true)
            public static final double FOREST_CHANCE_DEFUALT = 0.03D;
            @Constrain.Range(min = 0.D, max = 1.D, step = 0.01D)
            public double forest_chance = FOREST_CHANCE_DEFUALT;

            @Setting(ignore = true)
            public static final boolean CRYSTAL_GRASS_TICK_DEFAULT = true;
            public boolean crystal_grass_tick = CRYSTAL_GRASS_TICK_DEFAULT;

            @Setting(ignore = true)
            public static final boolean TAKEOVER_DEFAULT = false;
            public boolean takeover = TAKEOVER_DEFAULT;

            @Setting(ignore = true)
            public static final Block CRYSTAL_GRASS_SPREAD_BLOCK_DEFAULT = Blocks.GRASS_BLOCK;
            public Block crystal_grass_spread_block = CRYSTAL_GRASS_SPREAD_BLOCK_DEFAULT;
        }

        public class CrystalStructureGroup {
            @Setting(ignore = true)
            public static final int CRYSTAL_RADIUS_MIN_DEFAULT = 2;
            @Constrain.Range(min = 1, max = 16, step = 1)
            public int crystal_radius_min = CRYSTAL_RADIUS_MIN_DEFAULT;

            @Setting(ignore = true)
            public static final int CRYSTAL_RADIUS_MAX_DEFAULT = 4;
            @Constrain.Range(min = 1, max = 16, step = 1)
            public int crystal_radius_max = CRYSTAL_RADIUS_MAX_DEFAULT;

            @Setting(ignore = true)
            public static final int CRYSTAL_HEIGHT_MIN_DEFAULT = 12;
            @Constrain.Range(min = 1, max = 128, step = 1)
            public int crystal_height_min = CRYSTAL_HEIGHT_MIN_DEFAULT;

            @Setting(ignore = true)
            public static final int CRYSTAL_HEIGHT_MAX_DEFAULT = 22;
            @Constrain.Range(min = 1, max = 128, step = 1)
            public int crystal_height_max = CRYSTAL_HEIGHT_MAX_DEFAULT;

            @Setting(ignore = true)
            public static final float CRYSTAL_TILT_DEFAULT = 0.4f;
            @Constrain.Range(min = 0.f, max = 4.f, step = 0.1f)
            public float crystal_tilt = CRYSTAL_TILT_DEFAULT;

            @Setting(ignore = true)
            public static final int CRYSTAL_SPREAD_DEFAULT = 2;
            @Constrain.Range(min = 0, max = 8, step = 1)
            public int crystal_spread = CRYSTAL_SPREAD_DEFAULT;

            @Setting(ignore = true)
            public static final int CRYSTAL_DENSITY_DEFAULT = 6;
            @Constrain.Range(min = 1, max = 16, step = 1)
            public int crystal_density = CRYSTAL_DENSITY_DEFAULT;

            public List<Block> crystal_growth_blocks = new ArrayList<>(Arrays.asList(
                    Blocks.RED_STAINED_GLASS, Blocks.ORANGE_STAINED_GLASS,
                    Blocks.YELLOW_STAINED_GLASS, Blocks.GREEN_STAINED_GLASS,
                    Blocks.BLUE_STAINED_GLASS, Blocks.PURPLE_STAINED_GLASS,
                    Blocks.PINK_STAINED_GLASS, Blocks.CYAN_STAINED_GLASS,
                    Blocks.WHITE_STAINED_GLASS, Blocks.WHITE_STAINED_GLASS
            ));
        }

        public class RainbowGroup {
            @Setting(ignore = true)
            public static final double CHANCE_DEFAULT = 0.03D;
            @Constrain.Range(min = 0.D, max = 1.D, step = 0.01D)
            public double chance = CHANCE_DEFAULT;

            @Setting(ignore = true)
            public static final double FREQUENCY_DEFAULT = 0.01D;
            @Constrain.Range(min = 0.01D, max = 1.D, step = 0.01D)
            public double frequency = FREQUENCY_DEFAULT;
        }

        public class RainbowFeaturesGroup {
            @Setting(ignore = true)
            public static final int RAINBOW_SPIKE_RADIUS_MIN_DEFAULT = 8;
            @Constrain.Range(min = 4, max = 16)
            public int rainbow_spike_radius_min = RAINBOW_SPIKE_RADIUS_MIN_DEFAULT;

            @Setting(ignore = true)
            public static final int RAINBOW_SPIKE_RADIUS_MAX_DEFAULT = 16;
            @Constrain.Range(min = 4, max = 16)
            public int rainbow_spike_radius_max = RAINBOW_SPIKE_RADIUS_MAX_DEFAULT;

            @Setting(ignore = true)
            public static final int RAINBOW_SPIKE_HEIGHT_MAX_DEFAULT = 16;
            @Constrain.Range(min = 8, max = 128)
            public int rainbow_spike_height_max = RAINBOW_SPIKE_HEIGHT_MAX_DEFAULT;

            @Setting(ignore = true)
            public static final int RAINBOW_SPIKE_FALLOFF_DEFAULT = 1;
            @Constrain.Range(min = 1, max = 16)
            public int rainbow_spike_falloff = RAINBOW_SPIKE_FALLOFF_DEFAULT;

            public List<Block> rainbow_spike_root_blocks = new ArrayList<>(Arrays.asList(
                    Blocks.SANDSTONE, Blocks.STONE,
                    Blocks.ANDESITE, Blocks.DIORITE,
                    Blocks.GRANITE
            ));

            public List<Block> rainbow_spike_composition_blocks = new ArrayList<>(Arrays.asList(
                    Blocks.TERRACOTTA, Blocks.RED_TERRACOTTA, Blocks.ORANGE_TERRACOTTA,
                    Blocks.YELLOW_TERRACOTTA, Blocks.GREEN_TERRACOTTA, Blocks.BLUE_TERRACOTTA,
                    Blocks.PURPLE_TERRACOTTA, Blocks.PINK_TERRACOTTA, Blocks.CYAN_TERRACOTTA
            ));
        };
    }
}

