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
                .setTitle(new TranslatableText("title.surrealbiomes.config"));
        builder.setSavingRunnable(SBConfig::saveConfig);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory crystalCategory = builder.getOrCreateCategory(new TranslatableText("category.surrealbiomes.crystal_category"));
        crystalCategory.addEntry(entryBuilder.startDoubleField(new TranslatableText("option.surrealbiomes.crystal_plains_chance"), SBConfig.config.crystal.plains_chance)
                .setDefaultValue(SBConfig.Config.CrystalGroup.PLAINS_CHANCE_DEFUALT)
                .setTooltip(new TranslatableText("tooltip.surrealbiomes.biome_chance"))
                .setMin(0.0).setMax(1.0).requireRestart().setSaveConsumer(newValue -> {
                    SBConfig.config.crystal.plains_chance = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        crystalCategory.addEntry(entryBuilder.startDoubleField(new TranslatableText("option.surrealbiomes.crystal_forest_chance"), SBConfig.config.crystal.forest_chance)
                .setDefaultValue(SBConfig.Config.CrystalGroup.FOREST_CHANCE_DEFUALT)
                .setTooltip(new TranslatableText("tooltip.surrealbiomes.biome_chance"))
                .setMin(0.0).setMax(1.0).requireRestart().setSaveConsumer(newValue -> {
                    SBConfig.config.crystal.forest_chance = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        crystalCategory.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("option.surrealbiomes.crystal_grass_tick"), SBConfig.config.crystal.crystal_grass_tick)
                .setDefaultValue(SBConfig.Config.CrystalGroup.CRYSTAL_GRASS_TICK_DEFAULT)
                .setTooltip(new TranslatableText("Determines if crystal grass can spread in the world. (note, crystal grass requires shardlings to grow)"))
                .setSaveConsumer(newValue -> {
                    SBConfig.config.crystal.crystal_grass_tick = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        crystalCategory.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("option.surrealbiomes.crystaline_takeover"), SBConfig.config.crystal.takeover)
                .setDefaultValue(SBConfig.Config.CrystalGroup.TAKEOVER_DEFAULT)
                .setTooltip(new TranslatableText("Gives crystal grass the ability to create it's own shardlings while it spreads. (will cause indefinite spreading)"))
                .setSaveConsumer(newValue -> {
                    SBConfig.config.crystal.takeover = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        crystalCategory.addEntry(entryBuilder.startDropdownMenu(new TranslatableText("option.surrealbiomes.crystal_grass_medium"),
                DropdownMenuBuilder.TopCellElementBuilder.ofBlockObject(SBConfig.config.crystal.crystal_grass_spread_block),
                DropdownMenuBuilder.CellCreatorBuilder.ofBlockObject())
                .setDefaultValue(SBConfig.Config.CrystalGroup.CRYSTAL_GRASS_SPREAD_BLOCK_DEFAULT)
                .setSelections(Registry.BLOCK.stream().collect(Collectors.toSet()))
                .setSaveConsumer(block -> {
                    SBConfig.config.crystal.crystal_grass_spread_block = block;
                    SBConfig.saveConfig();
                }).requireRestart()
                .build());

        ConfigCategory crystalStructCategory = builder.getOrCreateCategory(new TranslatableText("category.surrealbiomes.crystal_struct"));
        crystalStructCategory.addEntry(entryBuilder.startIntSlider(new TranslatableText("option.surrealbiomes.crystal_width_min"),
                SBConfig.config.crystalStructure.crystal_radius_min, 1, 16)
                .setDefaultValue(SBConfig.Config.CrystalStructureGroup.CRYSTAL_RADIUS_MIN_DEFAULT)
                .setTooltip(new TranslatableText("Minimum radius of the crystal structures in the crystaline biomes")).requireRestart()
                .setSaveConsumer(newValue -> {
                    SBConfig.config.crystalStructure.crystal_radius_min = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        crystalStructCategory.addEntry(entryBuilder.startIntSlider(new TranslatableText("option.surrealbiomes.crystal_width_max"),
                SBConfig.config.crystalStructure.crystal_radius_max, 1, 16)
                .setDefaultValue(SBConfig.Config.CrystalStructureGroup.CRYSTAL_RADIUS_MAX_DEFAULT)
                .setTooltip(new TranslatableText("Maximum radius of the crystal structures in the crystaline biomes")).requireRestart()
                .setSaveConsumer(newValue -> {
                    SBConfig.config.crystalStructure.crystal_radius_max = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        crystalStructCategory.addEntry(entryBuilder.startIntSlider(new TranslatableText("option.surrealbiomes.crystal_height_min"),
                SBConfig.config.crystalStructure.crystal_height_min, 1, 128)
                .setDefaultValue(SBConfig.Config.CrystalStructureGroup.CRYSTAL_HEIGHT_MIN_DEFAULT)
                .setTooltip(new TranslatableText("Minimum height of the crystal structures in the crystaline biomes")).requireRestart()
                .setSaveConsumer(newValue -> {
                    SBConfig.config.crystalStructure.crystal_height_min = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        crystalStructCategory.addEntry(entryBuilder.startIntSlider(new TranslatableText("option.surrealbiomes.crystal_height_max"),
                SBConfig.config.crystalStructure.crystal_height_max, 1, 128)
                .setDefaultValue(SBConfig.Config.CrystalStructureGroup.CRYSTAL_HEIGHT_MAX_DEFAULT)
                .setTooltip(new TranslatableText("Maximum height of the crystal structures in the crystaline biomes")).requireRestart()
                .setSaveConsumer(newValue -> {
                    SBConfig.config.crystalStructure.crystal_height_max = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        crystalStructCategory.addEntry(entryBuilder.startIntSlider(new TranslatableText("option.surrealbiomes.crystal_spread"),
                SBConfig.config.crystalStructure.crystal_spread, 0, 8)
                .setDefaultValue(SBConfig.Config.CrystalStructureGroup.CRYSTAL_SPREAD_DEFAULT)
                .setTooltip(new TranslatableText("Maximum distance of each crystal from the central crystal structure")).requireRestart()
                .setSaveConsumer(newValue -> {
                    SBConfig.config.crystalStructure.crystal_spread = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        crystalStructCategory.addEntry(entryBuilder.startFloatField(new TranslatableText("option.surrealbiomes.crystal_tilt"),
                SBConfig.config.crystalStructure.crystal_tilt)
                .setDefaultValue(SBConfig.Config.CrystalStructureGroup.CRYSTAL_TILT_DEFAULT)
                .setTooltip(new TranslatableText("Controls how far the crystals can lean from their origin"))
                .setMin(0.0F).setMax(4.0F).requireRestart().setSaveConsumer(newValue -> {
                    SBConfig.config.crystalStructure.crystal_tilt = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        crystalStructCategory.addEntry(entryBuilder.startTextDescription(new TranslatableText("Currently, you cannot change crystal blocks ingame. You need to go to the config file: \"config/surrealbiomes.json\" and change values in the \"crystal growth blocks\" list.")).build());

        ConfigCategory rainbowDesertCategory = builder.getOrCreateCategory(new TranslatableText("category.surrealbiomes.rainbow_desert"));
        rainbowDesertCategory.addEntry(entryBuilder.startDoubleField(new TranslatableText("option.surrealbiomes.rainbow_desert_chance"), SBConfig.config.rainbow.chance)
                .setDefaultValue(SBConfig.Config.RainbowGroup.CHANCE_DEFAULT)
                .setTooltip(new TranslatableText("tooltip.surrealbiomes.biome_chance"))
                .setMax(0.0).setMax(1.0).requireRestart().setSaveConsumer(newValue -> {
                    SBConfig.config.rainbow.chance = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        rainbowDesertCategory.addEntry(entryBuilder.startDoubleField(new TranslatableText("option.surrealbiomes.rainbow_frequency"), SBConfig.config.rainbow.frequency)
                .setDefaultValue(SBConfig.Config.RainbowGroup.FREQUENCY_DEFAULT)
                .setTooltip(new TranslatableText("How spread the rainbow effect is"))
                .setMax(0.01).setMax(1.0).setSaveConsumer(newValue -> {
                    SBConfig.config.rainbow.frequency = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        return builder.build();
    }
}
