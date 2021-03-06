package ch.baramex.trackersmod.machines;

import java.util.Arrays;
import java.util.List;

import ch.baramex.trackersmod.Main;
import ch.baramex.trackersmod.config.Config;
import ch.baramex.trackersmod.init.ModBlocks;
import ch.baramex.trackersmod.tileentity.TileEntityTrackerAdv;
import ch.baramex.trackersmod.utils.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class TrackerAdv extends Block implements ITileEntityProvider {
	
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	List<String> tooltip = Arrays.asList(new String[]{TextFormatting.YELLOW + new TextComponentTranslation("tooltip." + Reference.MODID + ".tracker.1").getFormattedText(), TextFormatting.DARK_GREEN + new TextComponentTranslation("tooltip." + Reference.MODID + ".tracker.2").getFormattedText().replace("MAX_BLOCK", String.valueOf(Config.MAX_RANGE_TRACKER_ADV)), TextFormatting.YELLOW + new TextComponentTranslation("tooltip." + Reference.MODID + ".tracker.3").getFormattedText().replace("MIN_PRECISION", String.valueOf(Config.MIN_PRECISION_TRACKER_ADV)), TextFormatting.YELLOW + new TextComponentTranslation("tooltip." + Reference.MODID + ".tracker.4").getFormattedText().replace("MIN_ANTENNA", String.valueOf(Config.MIN_ANTENNA_ADV)).replace("MAX_ANTENNA", String.valueOf(Config.MAX_ANTENNA_ADV)), TextFormatting.DARK_RED + new TextComponentTranslation("tooltip." + Reference.MODID + ".tracker.5").getFormattedText().replace("ENERGY_CONSUME", String.valueOf(Config.ENERGY_CONSUME_TRACKER_ADV))});

	public TrackerAdv(Material materialIn, String name) {
		super(materialIn);

		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(Main.tabsMod);
		this.setHardness(4.0F);
		this.setResistance(25.0F);
		this.setHarvestLevel("pickaxe", 2);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));

		ModBlocks.INSTANCE.getBlocks().add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tt, ITooltipFlag advanced) {
		if(GuiScreen.isShiftKeyDown()) {
			tt.addAll(tooltip);
		}
		else {
			tt.add(new TextComponentTranslation("tooltip." + Reference.MODID + ".showmore").getFormattedText());
		}
		
		super.addInformation(stack, player, tt, advanced);
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if(!worldIn.isRemote) {
			IBlockState north = worldIn.getBlockState(pos.north());
			IBlockState south = worldIn.getBlockState(pos.south());
			IBlockState west = worldIn.getBlockState(pos.west());
			IBlockState east = worldIn.getBlockState(pos.east());
			EnumFacing face = (EnumFacing)state.getValue(FACING);
			
			if(face == EnumFacing.NORTH && north.isFullBlock() && south.isFullBlock()) face = EnumFacing.SOUTH;
			if(face == EnumFacing.SOUTH && south.isFullBlock() && north.isFullBlock()) face = EnumFacing.NORTH;
			if(face == EnumFacing.WEST && west.isFullBlock() && east.isFullBlock()) face = EnumFacing.EAST;
			if(face == EnumFacing.EAST && east.isFullBlock() && west.isFullBlock()) face = EnumFacing.WEST;
			worldIn.setBlockState(pos, state.withProperty(FACING, face), 2);
		}
	}
	
	public static void setState(boolean active, World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		TileEntityTrackerAdv tileentity = (TileEntityTrackerAdv) worldIn.getTileEntity(pos);
		
		if(active) worldIn.setBlockState(pos, ModBlocks.trackerAdv.getDefaultState().withProperty(FACING, state.getValue(FACING)), 3);
		else worldIn.setBlockState(pos, ModBlocks.trackerAdv.getDefaultState().withProperty(FACING, state.getValue(FACING)), 3);
		
		if(tileentity != null) {
			tileentity.validate();
			worldIn.setTileEntity(pos, tileentity);
		}
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {FACING});
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing facing = EnumFacing.getFront(meta);
		if(facing.getAxis() == EnumFacing.Axis.Y) {
			facing = EnumFacing.NORTH;
		}
		return this.getDefaultState().withProperty(FACING, facing);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityTrackerAdv();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (!worldIn.isRemote && te instanceof TileEntityTrackerAdv) {
			playerIn.openGui(Main.instance, 2, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
}
