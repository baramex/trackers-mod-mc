package ch.baramex.trackersmod.machines;

import java.util.List;

import ch.baramex.trackersmod.Main;
import ch.baramex.trackersmod.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Antenne extends Block {
	
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	private static AxisAlignedBB AABB_EAST = new AxisAlignedBB(0.0625*4, 0, 0.0625*3, 0.0625*12, 0.0625*13, 0.0625*13);
	private static AxisAlignedBB AABB_WEST = new AxisAlignedBB(0.0625*4, 0, 0.0625*3, 0.0625*12, 0.0625*13, 0.0625*13);
	private static AxisAlignedBB AABB_NORTH = new AxisAlignedBB(0.0625*3, 0, 0.0625*4, 0.0625*13, 0.0625*13, 0.0625*12);
	private static AxisAlignedBB AABB_SOUTH = new AxisAlignedBB(0.0625*3, 0, 0.0625*4, 0.0625*13, 0.0625*13, 0.0625*12);
	
	List<String> tooltip;

	public Antenne(Material materialIn, String name, List<String> tt) {
		super(materialIn);
		
		tooltip = tt;
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(Main.tabsMod);
		this.setHardness(3.0F);
		this.setResistance(15.0F);
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
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		if(state.getValue(FACING) == EnumFacing.NORTH) {
			return AABB_NORTH;
		}
		else if(state.getValue(FACING) == EnumFacing.EAST) {
			return AABB_EAST;
		}
		else if(state.getValue(FACING) == EnumFacing.WEST) {
			return AABB_WEST;
		}
		else if(state.getValue(FACING) == EnumFacing.SOUTH) {
			return AABB_SOUTH;
		}
		else {
			return AABB_NORTH;
		}
	}
	
	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
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
		
		if(active) worldIn.setBlockState(pos, ModBlocks.antenne.getDefaultState().withProperty(FACING, state.getValue(FACING)), 3);
		else worldIn.setBlockState(pos, ModBlocks.antenne.getDefaultState().withProperty(FACING, state.getValue(FACING)), 3);
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
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
}
