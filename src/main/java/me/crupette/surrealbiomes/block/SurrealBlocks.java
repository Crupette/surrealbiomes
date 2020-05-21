package me.crupette.surrealbiomes.block;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.FernBlock;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.Material;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.nbt.Tag;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SurrealBlocks {

    public static final Block CRYSTAL_GRASS = new CrystalGrassBlock(FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.GRASS).breakByHand(true).breakByTool(FabricToolTags.SHOVELS).strength(0.5F).ticksRandomly());
    public static final Block REDDER_CRYSTAL_SHARDLING = new Block(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).nonOpaque().noCollision().sounds(BlockSoundGroup.GLASS).breakByTool(FabricToolTags.PICKAXES).strength(0.1F));
    public static final Block BLUER_CRYSTAL_SHARDLING = new Block(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).nonOpaque().noCollision().sounds(BlockSoundGroup.GLASS).breakByTool(FabricToolTags.PICKAXES).strength(0.1F));
    public static final Block GREENER_CRYSTAL_SHARDLING = new Block(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).nonOpaque().noCollision().sounds(BlockSoundGroup.GLASS).breakByTool(FabricToolTags.PICKAXES).strength(0.1F));

    private static void registerBlock(Identifier identifier, Block block, ItemGroup group){
        Registry.register(Registry.BLOCK, identifier, block);
        Registry.register(Registry.ITEM, identifier, new BlockItem(block, new Item.Settings().group(group)));
    }

    public static void registerBlocks(){
        registerBlock(new Identifier("surrealbiomes", "crystal_grass"), CRYSTAL_GRASS, ItemGroup.BUILDING_BLOCKS);
        registerBlock(new Identifier("surrealbiomes", "redder_crystal_shardling"), REDDER_CRYSTAL_SHARDLING, ItemGroup.DECORATIONS);
        registerBlock(new Identifier("surrealbiomes", "bluer_crystal_shardling"), BLUER_CRYSTAL_SHARDLING, ItemGroup.DECORATIONS);
        registerBlock(new Identifier("surrealbiomes", "greener_crystal_shardling"), GREENER_CRYSTAL_SHARDLING, ItemGroup.DECORATIONS);

        BlockRenderLayerMap.INSTANCE.putBlock(REDDER_CRYSTAL_SHARDLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(GREENER_CRYSTAL_SHARDLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BLUER_CRYSTAL_SHARDLING, RenderLayer.getCutout());
    }
}
