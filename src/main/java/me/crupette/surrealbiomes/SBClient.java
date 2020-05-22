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
        crystalCategory.addEntry(entryBuilder.startDoubleField(new TranslatableText("option.surrealbiomes.crystal_plains_chance"), SBConfig.config.crystaline_plains_chance)
                .setDefaultValue(SBConfig.Config.CRYSTALINE_PLAINS_CHANCE_DEFUALT)
                .setTooltip(new TranslatableText("tooltip.surrealbiomes.crystal_plains_chance"))
                .setMin(0.0).setMax(1.0).requireRestart().setSaveConsumer(newValue -> {
                    SBConfig.config.crystaline_plains_chance = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        crystalCategory.addEntry(entryBuilder.startDoubleField(new TranslatableText("option.surrealbiomes.crystal_forest_chance"), SBConfig.config.crystaline_forest_chance)
                .setDefaultValue(SBConfig.Config.CRYSTALINE_FOREST_CHANCE_DEFUALT)
                .setTooltip(new TranslatableText("Sets the chance for this biome to generate in the world (0 to disable, 1 to make it as common as normal biomes)"))
                .setMin(0.0).setMax(1.0).requireRestart().setSaveConsumer(newValue -> {
                    SBConfig.config.crystaline_forest_chance = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        crystalCategory.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("option.surrealbiomes.crystal_grass_tick"), SBConfig.config.crystaline_grass_spread)
                .setDefaultValue(SBConfig.Config.CRYSTALINE_GRASS_SPREAD_DEFAULT)
                .setTooltip(new TranslatableText("Determines if crystal grass can spread in the world. (note, crystal grass requires shardlings to grow)"))
                .setSaveConsumer(newValue -> {
                    SBConfig.config.crystaline_grass_spread = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        crystalCategory.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("option.surrealbiomes.crystaline_takeover"), SBConfig.config.crystaline_takeover)
                .setDefaultValue(SBConfig.Config.CRYSTALINE_GRASS_SPREAD_DEFAULT)
                .setTooltip(new TranslatableText("Gives crystal grass the ability to create it's own shardlings while it spreads. (will cause indefinite spreading)"))
                .setSaveConsumer(newValue -> {
                    SBConfig.config.crystaline_takeover = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        crystalCategory.addEntry(entryBuilder.startDropdownMenu(new TranslatableText("option.surrealbiomes.crystal_grass_medium"),
                DropdownMenuBuilder.TopCellElementBuilder.ofBlockObject(SBConfig.config.crystal_grass_spread_block),
                DropdownMenuBuilder.CellCreatorBuilder.ofBlockObject())
                .setDefaultValue(SBConfig.Config.CRYSTAL_GRASS_SPREAD_MEDIUM_DEFAULT)
                .setSelections(Registry.BLOCK.stream().collect(Collectors.toSet()))
                .setSaveConsumer(block -> {
                    SBConfig.config.crystal_grass_spread_medium = Registry.BLOCK.getId(block).toString();
                    SBConfig.config.crystal_grass_spread_block = block;
                    SBConfig.saveConfig();
                }).requireRestart()
                .build());

        ConfigCategory crystalStructCategory = builder.getOrCreateCategory(new TranslatableText("category.surrealbiomes.crystal_struct"));
        crystalStructCategory.addEntry(entryBuilder.startIntSlider(new TranslatableText("option.surrealbiomes.crystal_width_min"),
                SBConfig.config.crystal_radius_min, 1, 16)
                .setDefaultValue(SBConfig.Config.CRYSTAL_RADIUS_MIN_DEFAULT)
                .setTooltip(new TranslatableText("Minimum radius of the crystal structures in the crystaline biomes"))
                .setSaveConsumer(newValue -> {
                    SBConfig.config.crystal_radius_min = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        crystalStructCategory.addEntry(entryBuilder.startIntSlider(new TranslatableText("option.surrealbiomes.crystal_width_max"),
                SBConfig.config.crystal_radius_max, 1, 16)
                .setDefaultValue(SBConfig.Config.CRYSTAL_RADIUS_MAX_DEFAULT)
                .setTooltip(new TranslatableText("Maximum radius of the crystal structures in the crystaline biomes"))
                .setSaveConsumer(newValue -> {
                    SBConfig.config.crystal_radius_max = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        crystalStructCategory.addEntry(entryBuilder.startIntSlider(new TranslatableText("option.surrealbiomes.crystal_height_min"),
                SBConfig.config.crystal_height_min, 1, 128)
                .setDefaultValue(SBConfig.Config.CRYSTAL_HEIGHT_MIN_DEFAULT)
                .setTooltip(new TranslatableText("Minimum height of the crystal structures in the crystaline biomes"))
                .setSaveConsumer(newValue -> {
                    SBConfig.config.crystal_height_min = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        crystalStructCategory.addEntry(entryBuilder.startIntSlider(new TranslatableText("option.surrealbiomes.crystal_height_max"),
                SBConfig.config.crystal_height_max, 1, 128)
                .setDefaultValue(SBConfig.Config.CRYSTAL_HEIGHT_MAX_DEFAULT)
                .setTooltip(new TranslatableText("Maximum height of the crystal structures in the crystaline biomes"))
                .setSaveConsumer(newValue -> {
                    SBConfig.config.crystal_height_max = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        crystalStructCategory.addEntry(entryBuilder.startIntSlider(new TranslatableText("option.surrealbiomes.crystal_spread"),
                SBConfig.config.crystal_spread, 0, 8)
                .setDefaultValue(SBConfig.Config.CRYSTAL_SPREAD_DEFAULT)
                .setTooltip(new TranslatableText("Maximum distance of each crystal from the central crystal structure"))
                .setSaveConsumer(newValue -> {
                    SBConfig.config.crystal_spread = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        crystalStructCategory.addEntry(entryBuilder.startFloatField(new TranslatableText("option.surrealbiomes.crystal_tilt"), SBConfig.config.crystal_tilt)
                .setDefaultValue(SBConfig.Config.CRYSTAL_TILT_DEFAULT)
                .setTooltip(new TranslatableText("Controls how far the crystals can lean from their origin"))
                .setMin(0.0F).setMax(4.0F).setSaveConsumer(newValue -> {
                    SBConfig.config.crystal_tilt = newValue;
                    SBConfig.saveConfig();
                })
                .build());

        return builder.build();
    }
}
