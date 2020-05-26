package me.crupette.surrealbiomes;

import me.crupette.surrealbiomes.block.SurrealBlocks;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.DropdownMenuBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.registry.Registry;

import java.util.stream.Collectors;

@Environment(EnvType.CLIENT)
public class SBClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        SurrealBlocks.registerBlocksClient();
    }

    public static Screen createConfigScreen(Screen parent){
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(("title.surrealbiomes.config"));
        builder.setSavingRunnable(SBConfig::saveConfig);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory crystalCategory = builder.getOrCreateCategory(("category.surrealbiomes.crystal"));
        crystalCategory.addEntry(entryBuilder.startDoubleField(("option.surrealbiomes.crystal.plains_chance"), SBConfig.config.crystal.plains_chance)
                .setDefaultValue(SBConfig.Config.CrystalGroup.PLAINS_CHANCE_DEFUALT)
                .setTooltip(("tooltip.surrealbiomes.biome_chance"))
                .setMin(0.0).setMax(1.0).requireRestart().setSaveConsumer(newValue -> {
                    SBConfig.config.crystal.plains_chance = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        crystalCategory.addEntry(entryBuilder.startDoubleField(("option.surrealbiomes.crystal.forest_chance"), SBConfig.config.crystal.forest_chance)
                .setDefaultValue(SBConfig.Config.CrystalGroup.FOREST_CHANCE_DEFUALT)
                .setTooltip(("tooltip.surrealbiomes.biome_chance"))
                .setMin(0.0).setMax(1.0).requireRestart().setSaveConsumer(newValue -> {
                    SBConfig.config.crystal.forest_chance = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        crystalCategory.addEntry(entryBuilder.startBooleanToggle(("option.surrealbiomes.crystal.crystal_grass_tick"), SBConfig.config.crystal.crystal_grass_tick)
                .setDefaultValue(SBConfig.Config.CrystalGroup.CRYSTAL_GRASS_TICK_DEFAULT)
                .setTooltip(("tooltip.surrealbiomes.crystal.crystal_grass_tick"))
                .setSaveConsumer(newValue -> {
                    SBConfig.config.crystal.crystal_grass_tick = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        crystalCategory.addEntry(entryBuilder.startBooleanToggle(("option.surrealbiomes.crystal.takeover"), SBConfig.config.crystal.takeover)
                .setDefaultValue(SBConfig.Config.CrystalGroup.TAKEOVER_DEFAULT)
                .setTooltip(("tooltip.surrealbiomes.crystal.takeover"))
                .setSaveConsumer(newValue -> {
                    SBConfig.config.crystal.takeover = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        crystalCategory.addEntry(entryBuilder.startDropdownMenu(("option.surrealbiomes.crystal.crystal_grass_medium"),
                DropdownMenuBuilder.TopCellElementBuilder.ofBlockObject(SBConfig.config.crystal.crystal_grass_spread_block),
                DropdownMenuBuilder.CellCreatorBuilder.ofBlockObject())
                .setDefaultValue(SBConfig.Config.CrystalGroup.CRYSTAL_GRASS_SPREAD_BLOCK_DEFAULT)
                .setSelections(Registry.BLOCK.stream().collect(Collectors.toSet()))
                .setSaveConsumer(block -> {
                    SBConfig.config.crystal.crystal_grass_spread_block = block;
                    SBConfig.saveConfig();
                }).requireRestart()
                .build());

        ConfigCategory crystalFeaturesCategory = builder.getOrCreateCategory(("category.surrealbiomes.crystal_features"));
        crystalFeaturesCategory.addEntry(entryBuilder.startIntSlider(("option.surrealbiomes.crystal_features.crystal_radius_min"),
                SBConfig.config.crystalFeatures.crystal_radius_min, 1, 16)
                .setDefaultValue(SBConfig.Config.CrystalStructureGroup.CRYSTAL_RADIUS_MIN_DEFAULT)
                .setTooltip(("tooltip.surrealbiomes.crystal_features.crystal_radius_min")).requireRestart()
                .setSaveConsumer(newValue -> {
                    SBConfig.config.crystalFeatures.crystal_radius_min = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        crystalFeaturesCategory.addEntry(entryBuilder.startIntSlider(("option.surrealbiomes.crystal_features.crystal_radius_max"),
                SBConfig.config.crystalFeatures.crystal_radius_max, 1, 16)
                .setDefaultValue(SBConfig.Config.CrystalStructureGroup.CRYSTAL_RADIUS_MAX_DEFAULT)
                .setTooltip(("tooltip.surrealbiomes.crystal_features.crystal_radius_max")).requireRestart()
                .setSaveConsumer(newValue -> {
                    SBConfig.config.crystalFeatures.crystal_radius_max = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        crystalFeaturesCategory.addEntry(entryBuilder.startIntSlider(("option.surrealbiomes.crystal_features.crystal_height_min"),
                SBConfig.config.crystalFeatures.crystal_height_min, 1, 128)
                .setDefaultValue(SBConfig.Config.CrystalStructureGroup.CRYSTAL_HEIGHT_MIN_DEFAULT)
                .setTooltip(("tooltip.surrealbiomes.crystal_features.crystal_height_min")).requireRestart()
                .setSaveConsumer(newValue -> {
                    SBConfig.config.crystalFeatures.crystal_height_min = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        crystalFeaturesCategory.addEntry(entryBuilder.startIntSlider(("option.surrealbiomes.crystal_features.crystal_height_max"),
                SBConfig.config.crystalFeatures.crystal_height_max, 1, 128)
                .setDefaultValue(SBConfig.Config.CrystalStructureGroup.CRYSTAL_HEIGHT_MAX_DEFAULT)
                .setTooltip(("tooltip.surrealbiomes.crystal_features.crystal_height_max")).requireRestart()
                .setSaveConsumer(newValue -> {
                    SBConfig.config.crystalFeatures.crystal_height_max = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        crystalFeaturesCategory.addEntry(entryBuilder.startIntSlider(("option.surrealbiomes.crystal_features.crystal_spread"),
                SBConfig.config.crystalFeatures.crystal_spread, 0, 8)
                .setDefaultValue(SBConfig.Config.CrystalStructureGroup.CRYSTAL_SPREAD_DEFAULT)
                .setTooltip(("tooltip.surrealbiomes.crystal_features.crystal_spread")).requireRestart()
                .setSaveConsumer(newValue -> {
                    SBConfig.config.crystalFeatures.crystal_spread = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        crystalFeaturesCategory.addEntry(entryBuilder.startFloatField(("option.surrealbiomes.crystal_features.crystal_tilt"),
                SBConfig.config.crystalFeatures.crystal_tilt)
                .setDefaultValue(SBConfig.Config.CrystalStructureGroup.CRYSTAL_TILT_DEFAULT)
                .setTooltip(("tooltip.surrealbiomes.crystal_features.crystal_tilt"))
                .setMin(0.0F).setMax(4.0F).requireRestart().setSaveConsumer(newValue -> {
                    SBConfig.config.crystalFeatures.crystal_tilt = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        crystalFeaturesCategory.addEntry(entryBuilder.startTextDescription(("To change which blocks generate in the crystal structure, change the values in the 'crystal_growth_blocks' field using the config file at config/surrealbiomes.json")).build());

        ConfigCategory rainbowCategory = builder.getOrCreateCategory(("category.surrealbiomes.rainbow"));
        rainbowCategory.addEntry(entryBuilder.startDoubleField(("option.surrealbiomes.rainbow.desert_chance"), SBConfig.config.rainbow.chance)
                .setDefaultValue(SBConfig.Config.RainbowGroup.CHANCE_DEFAULT)
                .setTooltip(("tooltip.surrealbiomes.biome_chance"))
                .setMax(0.0).setMax(1.0).requireRestart().setSaveConsumer(newValue -> {
                    SBConfig.config.rainbow.chance = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        rainbowCategory.addEntry(entryBuilder.startDoubleField(("option.surrealbiomes.rainbow.frequency"), SBConfig.config.rainbow.frequency)
                .setDefaultValue(SBConfig.Config.RainbowGroup.FREQUENCY_DEFAULT)
                .setTooltip(("tooltip.surrealbiomes.rainbow_desert.frequency"))
                .setMax(0.01).setMax(1.0).setSaveConsumer(newValue -> {
                    SBConfig.config.rainbow.frequency = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        rainbowCategory.addEntry(entryBuilder.startTextDescription(("To change which blocks generate at the surface of rainbow deserts, change the values in the 'color_blocks' field using the config file at config/surrealbiomes.json")).build());

        ConfigCategory rainbowFeaturesCategory = builder.getOrCreateCategory(("category.surrealbiomes.rainbow_features"));
        rainbowFeaturesCategory.addEntry(entryBuilder.startIntSlider(
                ("option.surrealbiomes.rainbow_features.rainbow_spike_radius_min"),
                SBConfig.config.rainbowFeatures.rainbow_spike_radius_min, 4, 16)
                .setTooltip(("tooltip.surrealbiomes.rainbow_features.rainbow_spike_radius_min"))
                .requireRestart().setSaveConsumer(newValue -> {
                    SBConfig.config.rainbowFeatures.rainbow_spike_radius_min = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        rainbowFeaturesCategory.addEntry(entryBuilder.startIntSlider(
                ("option.surrealbiomes.rainbow_features.rainbow_spike_radius_max"),
                SBConfig.config.rainbowFeatures.rainbow_spike_radius_max, 4, 16)
                .setTooltip(("tooltip.surrealbiomes.rainbow_features.rainbow_spike_radius_max"))
                .requireRestart().setSaveConsumer(newValue -> {
                    SBConfig.config.rainbowFeatures.rainbow_spike_radius_max = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        rainbowFeaturesCategory.addEntry(entryBuilder.startIntSlider(
                ("option.surrealbiomes.rainbow_features.rainbow_spike_height_max"),
                SBConfig.config.rainbowFeatures.rainbow_spike_height_max, 8, 128)
                .setTooltip(("tooltip.surrealbiomes.rainbow_features.rainbow_spike_height_max"))
                .requireRestart().setSaveConsumer(newValue -> {
                    SBConfig.config.rainbowFeatures.rainbow_spike_height_max = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        rainbowFeaturesCategory.addEntry(entryBuilder.startFloatField(
                ("option.surrealbiomes.rainbow_features.rainbow_spike_falloff"), SBConfig.config.rainbowFeatures.rainbow_spike_falloff)
                .setDefaultValue(SBConfig.config.rainbowFeatures.rainbow_spike_falloff)
                .setTooltip(("tooltip.surrealbiomes.rainbow_features.rainbow_spike_falloff"))
                .setMin(0.01F).setMax(8.F).requireRestart().setSaveConsumer(newValue -> {
                    SBConfig.config.rainbowFeatures.rainbow_spike_falloff = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        rainbowFeaturesCategory.addEntry(entryBuilder.startTextDescription(("To change which blocks generate in the spike structure, change the values in the 'root_blocks' and 'composition_blocks' fields using the config file at config/surrealbiomes.json")).build());

        return builder.build();
    }
}
