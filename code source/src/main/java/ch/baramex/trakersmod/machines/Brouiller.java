package ch.baramex.trakersmod.machines;

import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import ch.baramex.trakersmod.Main;
import ch.baramex.trakersmod.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Brouiller extends Block {
	
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	private static final AxisAlignedBB AABB_EAST = new AxisAlignedBB(0, 0, 0.125+0.0625, 0.1875, 1, 0.9375-0.125);
	private static final AxisAlignedBB AABB_WEST = new AxisAlignedBB(0.8125, 0, 0.125+0.0625, 1, 1, 0.9375-0.125);
	private static final AxisAlignedBB AABB_NORTH = new AxisAlignedBB(0.125+0.0625, 0, 0.8125, 0.9375-0.125, 1, 1);
	private static final AxisAlignedBB AABB_SOUTH = new AxisAlignedBB(0.125+0.0625, 0, 0, 0.9375-0.125, 1, 0.1875);

	public Brouiller(Material materialIn, String name) {
		super(materialIn);
		
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(Main.tabsMod);
		this.setHardness(1.0F);
		this.setResistance(10.0F);
		this.setHarvestLevel("pickaxe", 2);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));

		ModBlocks.INSTANCE.getBlocks().add(this);
	}
	
	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return false;
	}
	
	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
		if(side == EnumFacing.DOWN || side == EnumFacing.UP) {
			return false;
		}
		if(!worldIn.getBlockState(getPosByFacing(pos, side)).isFullBlock()) {
			return false;
		}
		return true;
	}
	
	
	
	public static BlockPos getPosByFacing(BlockPos pos, EnumFacing side) {
		BlockPos b = null;
		side = side.getOpposite();
		if(side == EnumFacing.NORTH) {
			b = pos.north();
		}
		if(side == EnumFacing.SOUTH) {
			b = pos.south();
		}
		if(side == EnumFacing.EAST) {
			b = pos.east();
		}
		if(side== EnumFacing.WEST) {
			b = pos.west();
		}
		return b;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return new AxisAlignedBB(0, 0, 0, 0, 0, 0);
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
		worldIn.scheduleBlockUpdate(pos, this, 5, 0);
	}
	
	public static void setState(boolean active, World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		
		if(active) worldIn.setBlockState(pos, ModBlocks.tracker.getDefaultState().withProperty(FACING, state.getValue(FACING)), 3);
		else worldIn.setBlockState(pos, ModBlocks.tracker.getDefaultState().withProperty(FACING, state.getValue(FACING)), 3);
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(FACING, facing);
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
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if(!worldIn.getBlockState(Brouiller.getPosByFacing(pos, worldIn.getBlockState(pos).getValue(Brouiller.FACING))).isFullBlock()) {
			worldIn.destroyBlock(pos, true);
		}
		worldIn.scheduleBlockUpdate(pos, this, 5, 0);
		super.updateTick(worldIn, pos, state, rand);
	}
}
