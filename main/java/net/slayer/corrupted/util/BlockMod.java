package net.slayer.corrupted.util;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.slayer.corrupted.CorruptedBlocks;
import net.slayer.corrupted.CorruptedItems;
import net.slayer.corrupted.CorruptedOnes;

public class BlockMod extends Block {

	protected EnumMaterialTypes blockType;
	protected Item drop = null;
	protected Random rand;
	public int boostBrightnessLow;
	public int boostBrightnessHigh;
	public boolean enhanceBrightness;
	public String name;
	protected boolean isOpaque = true, isNormalCube = true;
	
	public BlockMod(String name, String finalName, float hardness) {
		this(EnumMaterialTypes.STONE, name, finalName, hardness, CorruptedTabs.ctab);
	}

	public BlockMod(String name, String finalName) {
		this(EnumMaterialTypes.STONE, name, finalName, 2.0F, CorruptedTabs.ctab);
	}

	public BlockMod(EnumMaterialTypes type, String name, String finalName, float hardness) {
		this(type, name, finalName, hardness, CorruptedTabs.ctab);
	}

	public BlockMod(String name, String finalName, boolean breakable, CreativeTabs tab) {
		this(EnumMaterialTypes.STONE, name, finalName, tab);
	}

	public BlockMod(String name, String finalName, boolean breakable) {
		this(name, finalName, breakable, CorruptedTabs.ctab);
	}

	public BlockMod(EnumMaterialTypes blockType, String name, String finalName, CreativeTabs tab) {
		super(blockType.getMaterial());
		LangRegistry.addBlock(name, finalName);
		this.blockType = blockType;
		setHardness(2.0F);
		rand = new Random();
		setSoundType(blockType.getSound());
		setCreativeTab(tab);
		setUnlocalizedName(name);
		this.name = name; 
		CorruptedBlocks.blocks.add(this);
		CorruptedBlocks.blockNames.add(SlayerAPI.PREFIX + name);
		setRegistryName(SlayerAPI.MOD_ID, name);
		CorruptedItems.items.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	public BlockMod(EnumMaterialTypes blockType, String name, String finalName, float hardness, CreativeTabs tab) {
		super(blockType.getMaterial());
		LangRegistry.addBlock(name, finalName);
		this.blockType = blockType;
		rand = new Random();
		setSoundType(blockType.getSound());
		setCreativeTab(tab);
		setUnlocalizedName(name);
		setHardness(hardness);
		this.name = name;
		CorruptedBlocks.blockNames.add(SlayerAPI.PREFIX + name);
		CorruptedBlocks.blocks.add(this);
		setRegistryName(SlayerAPI.MOD_ID, name);
		CorruptedItems.items.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	public void registerItemModel(Item itemBlock) {
		CorruptedOnes.proxy.registerItemRenderer(itemBlock, 0, name);
	}
	
	public Item createItemBlock() {
		return new ItemBlock(this).setRegistryName(getRegistryName());
	}

	public Block addName(String name) {
		CorruptedBlocks.blockNames.add(SlayerAPI.PREFIX + name);
		return this;
	}

	public String getName() {
		return name;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		if(drop == null) return SlayerAPI.toItem(this);
		return drop;
	}

    @Override
	@SideOnly(Side.CLIENT) 
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public int quantityDropped(Random rand) {
		return 1;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return isOpaque;
	}
	
	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) { }

	@Override
	public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {
		return true;
	}
}