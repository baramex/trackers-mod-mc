package ch.baramex.trackersmod.machines;

import java.util.List;

import ch.baramex.trackersmod.Main;
import ch.baramex.trackersmod.init.ModBlocks;
import ch.baramex.trackersmod.tileentity.TileEntityTracker;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Tracker extends Block implements ITileEntityProvider {
	
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	List<String> tooltip;

	public Tracker(Material materialIn, String name, List<String> tt) {
		super(materialIn);
		
		tooltip = tt;
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
		tt.addAll(tooltip);
		
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
		TileEntityTracker tileentity = (TileEntityTracker) worldIn.getTileEntity(pos);
		
		if(active) worldIn.setBlockState(pos, ModBlocks.tracker.getDefaultState().withProperty(FACING, state.getValue(FACING)), 3);
		else worldIn.setBlockState(pos, ModBlocks.tracker.getDefaultState().withProperty(FACING, state.getValue(FACING)), 3);
		
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
		return new TileEntityTracker();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (!worldIn.isRemote && te instanceof TileEntityTracker) {
			playerIn.openGui(Main.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
}
