package me.crupette.surrealbiomes.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import me.crupette.surrealbiomes.SBBase;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.nbt.Tag;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.EntityTypePredicate;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SurrealBlocks {

    public static final Block CRYSTAL_GRASS = new CrystalGrassBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC).sounds(BlockSoundGroup.NETHER_STEM).breakByHand(true).breakByTool(FabricToolTags.SHOVELS).strength(0.5F).ticksRandomly());
    public static final Block REDDER_CRYSTAL_SHARDLING = new Block(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).nonOpaque().noCollision().sounds(BlockSoundGroup.GLASS).breakByTool(FabricToolTags.PICKAXES).strength(0.1F));
    public static final Block BLUER_CRYSTAL_SHARDLING = new Block(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).nonOpaque().noCollision().sounds(BlockSoundGroup.GLASS).breakByTool(FabricToolTags.PICKAXES).strength(0.1F));
    public static final Block GREENER_CRYSTAL_SHARDLING = new Block(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).nonOpaque().noCollision().sounds(BlockSoundGroup.GLASS).breakByTool(FabricToolTags.PICKAXES).strength(0.1F));

    public static final Block BLACK_SAND = new ColoredSandBlock(0x000000, FabricBlockSettings.of(Material.AGGREGATE, MaterialColor.BLACK).strength(0.5F).sounds(BlockSoundGroup.SAND));
    public static final Block RED_SAND = new ColoredSandBlock(0xDD8080, FabricBlockSettings.of(Material.AGGREGATE, MaterialColor.RED).strength(0.5F).sounds(BlockSoundGroup.SAND));
    public static final Block GREEN_SAND = new ColoredSandBlock(0x80DD80, FabricBlockSettings.of(Material.AGGREGATE, MaterialColor.GREEN).strength(0.5F).sounds(BlockSoundGroup.SAND));
    public static final Block BROWN_SAND = new ColoredSandBlock(0x8B4513, FabricBlockSettings.of(Material.AGGREGATE, MaterialColor.BROWN).strength(0.5F).sounds(BlockSoundGroup.SAND));
    public static final Block BLUE_SAND = new ColoredSandBlock(0x8080DD, FabricBlockSettings.of(Material.AGGREGATE, MaterialColor.BLUE).strength(0.5F).sounds(BlockSoundGroup.SAND));
    public static final Block PURPLE_SAND = new ColoredSandBlock(0xAA80DD, FabricBlockSettings.of(Material.AGGREGATE, MaterialColor.PURPLE).strength(0.5F).sounds(BlockSoundGroup.SAND));
    public static final Block CYAN_SAND = new ColoredSandBlock(0x80DDAA, FabricBlockSettings.of(Material.AGGREGATE, MaterialColor.CYAN).strength(0.5F).sounds(BlockSoundGroup.SAND));
    public static final Block LIGHT_GRAY_SAND = new ColoredSandBlock(0xAAAAAA, FabricBlockSettings.of(Material.AGGREGATE, MaterialColor.LIGHT_GRAY).strength(0.5F).sounds(BlockSoundGroup.SAND));
    public static final Block GRAY_SAND = new ColoredSandBlock(0x808080, FabricBlockSettings.of(Material.AGGREGATE, MaterialColor.GRAY).strength(0.5F).sounds(BlockSoundGroup.SAND));
    public static final Block PINK_SAND = new ColoredSandBlock(0xDDAAAA, FabricBlockSettings.of(Material.AGGREGATE, MaterialColor.PINK).strength(0.5F).sounds(BlockSoundGroup.SAND));
    public static final Block LIME_SAND = new ColoredSandBlock(0xAADD80, FabricBlockSettings.of(Material.AGGREGATE, MaterialColor.LIME).strength(0.5F).sounds(BlockSoundGroup.SAND));
    public static final Block YELLOW_SAND = new ColoredSandBlock(0xDDDD80, FabricBlockSettings.of(Material.AGGREGATE, MaterialColor.YELLOW).strength(0.5F).sounds(BlockSoundGroup.SAND));
    public static final Block LIGHT_BLUE_SAND = new ColoredSandBlock(0xAAAADD, FabricBlockSettings.of(Material.AGGREGATE, MaterialColor.LIGHT_BLUE).strength(0.5F).sounds(BlockSoundGroup.SAND));
    public static final Block MAGENTA_SAND = new ColoredSandBlock(0xDDAADD, FabricBlockSettings.of(Material.AGGREGATE, MaterialColor.MAGENTA).strength(0.5F).sounds(BlockSoundGroup.SAND));
    public static final Block ORANGE_SAND = new ColoredSandBlock(0xDDAA80, FabricBlockSettings.of(Material.AGGREGATE, MaterialColor.ORANGE).strength(0.5F).sounds(BlockSoundGroup.SAND));
    public static final Block WHITE_SAND = new ColoredSandBlock(0xEEEEEE, FabricBlockSettings.of(Material.AGGREGATE, MaterialColor.WHITE).strength(0.5F).sounds(BlockSoundGroup.SAND));

    private static void registerBlock(Identifier identifier, Block block, ItemGroup group){
        Registry.register(Registry.BLOCK, identifier, block);
        Registry.register(Registry.ITEM, identifier, new BlockItem(block, new Item.Settings().group(group)));
    }

    public static void registerBlocks(){
        registerBlock(new Identifier(SBBase.MOD_ID, "crystal_grass"), CRYSTAL_GRASS, ItemGroup.BUILDING_BLOCKS);
        registerBlock(new Identifier(SBBase.MOD_ID, "redder_crystal_shardling"), REDDER_CRYSTAL_SHARDLING, ItemGroup.DECORATIONS);
        registerBlock(new Identifier(SBBase.MOD_ID, "bluer_crystal_shardling"), BLUER_CRYSTAL_SHARDLING, ItemGroup.DECORATIONS);
        registerBlock(new Identifier(SBBase.MOD_ID, "greener_crystal_shardling"), GREENER_CRYSTAL_SHARDLING, ItemGroup.DECORATIONS);

        registerBlock(new Identifier(SBBase.MOD_ID, "black_sand"), BLACK_SAND, ItemGroup.BUILDING_BLOCKS);
        registerBlock(new Identifier(SBBase.MOD_ID, "red_sand"), RED_SAND, ItemGroup.BUILDING_BLOCKS);
        registerBlock(new Identifier(SBBase.MOD_ID, "green_sand"), GREEN_SAND, ItemGroup.BUILDING_BLOCKS);
        registerBlock(new Identifier(SBBase.MOD_ID, "brown_sand"), BROWN_SAND, ItemGroup.BUILDING_BLOCKS);
        registerBlock(new Identifier(SBBase.MOD_ID, "blue_sand"), BLUE_SAND, ItemGroup.BUILDING_BLOCKS);
        registerBlock(new Identifier(SBBase.MOD_ID, "purple_sand"), PURPLE_SAND, ItemGroup.BUILDING_BLOCKS);
        registerBlock(new Identifier(SBBase.MOD_ID, "cyan_sand"), CYAN_SAND, ItemGroup.BUILDING_BLOCKS);
        registerBlock(new Identifier(SBBase.MOD_ID, "light_gray_sand"), LIGHT_GRAY_SAND, ItemGroup.BUILDING_BLOCKS);
        registerBlock(new Identifier(SBBase.MOD_ID, "gray_sand"), GRAY_SAND, ItemGroup.BUILDING_BLOCKS);
        registerBlock(new Identifier(SBBase.MOD_ID, "pink_sand"), PINK_SAND, ItemGroup.BUILDING_BLOCKS);
        registerBlock(new Identifier(SBBase.MOD_ID, "lime_sand"), LIME_SAND, ItemGroup.BUILDING_BLOCKS);
        registerBlock(new Identifier(SBBase.MOD_ID, "yellow_sand"), YELLOW_SAND, ItemGroup.BUILDING_BLOCKS);
        registerBlock(new Identifier(SBBase.MOD_ID, "light_blue_sand"), LIGHT_BLUE_SAND, ItemGroup.BUILDING_BLOCKS);
        registerBlock(new Identifier(SBBase.MOD_ID, "magenta_sand"), MAGENTA_SAND, ItemGroup.BUILDING_BLOCKS);
        registerBlock(new Identifier(SBBase.MOD_ID, "orange_sand"), ORANGE_SAND, ItemGroup.BUILDING_BLOCKS);
        registerBlock(new Identifier(SBBase.MOD_ID, "white_sand"), WHITE_SAND, ItemGroup.BUILDING_BLOCKS);

    }
  
    @Environment(EnvType.CLIENT)
    public static void registerBlocksClient(){
        BlockRenderLayerMap.INSTANCE.putBlock(REDDER_CRYSTAL_SHARDLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(GREENER_CRYSTAL_SHARDLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BLUER_CRYSTAL_SHARDLING, RenderLayer.getCutout());
    }


}
