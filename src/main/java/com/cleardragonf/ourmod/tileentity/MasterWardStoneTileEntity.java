package com.cleardragonf.ourmod.tileentity;

import com.cleardragonf.ourmod.Data.Wards;
import com.cleardragonf.ourmod.container.MasterWardStoneContainer;
import com.cleardragonf.ourmod.essence.CustomEnergyStorage;
import com.cleardragonf.ourmod.init.BlockInitNew;
import com.cleardragonf.ourmod.init.ModTileEntityTypes;
import com.cleardragonf.ourmod.objects.blocks.BoundaryWardStoneBlock;
import com.cleardragonf.ourmod.objects.blocks.MasterWardStoneBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;


public class MasterWardStoneTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

	public List<BlockPos> boundaryWardStones = new LinkedList<>();
	public List<Wards> activeWardList;
	public int wardHeight = 5;


	protected int numPlayersUsing;
	private boolean initialized = false;
	private CompoundNBT tag = new CompoundNBT();
	public int tick, y;


	public MasterWardStoneTileEntity(TileEntityType<?> typeIn) {
		super(typeIn);
	}

	public MasterWardStoneTileEntity() {
		this(ModTileEntityTypes.MASTER_WARD_STONE.get());
	}

	public final CustomEnergyStorage FireEnergy = new CustomEnergyStorage(100000, 0);

	public final CustomEnergyStorage WaterEnergy = new CustomEnergyStorage(100000, 0);

	public final CustomEnergyStorage AirEnergy = new CustomEnergyStorage(100000, 0);

	public final CustomEnergyStorage EarthEnergy = new CustomEnergyStorage(100000, 0);

	public final CustomEnergyStorage DarkEnergy = new CustomEnergyStorage(100000, 0);

	public final CustomEnergyStorage LightEnergy = new CustomEnergyStorage(100000, 0);



	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		compound.putInt("fireenergy", this.FireEnergy.getEnergyStored());
		compound.putInt("waterenergy", this.WaterEnergy.getEnergyStored());
		compound.putInt("airenergy", this.AirEnergy.getEnergyStored());
		compound.putInt("earthenergy", this.EarthEnergy.getEnergyStored());
		compound.putInt("darkenergy", this.DarkEnergy.getEnergyStored());
		compound.putInt("lightenergy", this.LightEnergy.getEnergyStored());
		return compound;
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		readRestorableNBT(compound);
	}

	@Override

	public ITextComponent getDisplayName() {

		return new StringTextComponent(this.getType().getRegistryName().getPath());

	}

	private void playSound(SoundEvent sound) {
		double dx = (double) this.pos.getX() + 0.5D;
		double dy = (double) this.pos.getY() + 0.5D;
		double dz = (double) this.pos.getZ() + 0.5D;
		this.world.playSound((PlayerEntity) null, dx, dy, dz, sound, SoundCategory.BLOCKS, 0.5f,
				this.world.rand.nextFloat() * 0.1f + 0.9f);
	}

	@Override
	public boolean receiveClientEvent(int id, int type) {
		if (id == 1) {
			this.numPlayersUsing = type;
			return true;
		} else {
			return super.receiveClientEvent(id, type);
		}
	}

	protected void onOpenOrClose() {
		Block block = this.getBlockState().getBlock();
		if (block instanceof MasterWardStoneBlock) {
			this.world.addBlockEvent(this.pos, block, 1, this.numPlayersUsing);
			this.world.notifyNeighborsOfStateChange(this.pos, block);
		}
	}

	public static int getPlayersUsing(IBlockReader reader, BlockPos pos) {
		BlockState blockstate = reader.getBlockState(pos);
		if (blockstate.hasTileEntity()) {
			TileEntity tileentity = reader.getTileEntity(pos);
			if (tileentity instanceof MasterWardStoneTileEntity) {
				return ((MasterWardStoneTileEntity) tileentity).numPlayersUsing;
			}
		}
		return 0;
	}

	@Override
	public void updateContainingBlockInfo() {
		super.updateContainingBlockInfo();
	}

	
	@Override
	public void remove() {
		super.remove();
	}


	@Override
	public void tick() {
		if (!initialized)
			init();
		tick++;
		if (tick == 40) {
			tick = 0;
			if (y > 2)
				execute();
		}


	}

	private void execute() {
		boundaryWardStones.clear();
		BlockPos test1 = new BlockPos(0,0,5);
		BlockPos test2 = new BlockPos(0,0,-5);
		BlockPos test3 = new BlockPos(5,0,0);
		BlockPos test4 = new BlockPos(-5,0,0);
		boundaryWardStones.add(test1);
		boundaryWardStones.add(test2);
		boundaryWardStones.add(test3);
		boundaryWardStones.add(test4);
		boolean needsSave = false;
			if(boundaryWardStones != null){

				if(boundaryWardStones.size() == 4){
					System.out.println("mmmm");
					//4 * pie * r * r
					boolean stonePlacementAccurate = false;
					TileEntity tileEntity = world.getTileEntity(pos);
					//sqrt((x1-x2)^2 + (y1-y2) ^2+( z1 - z1)^2)
					int x1 = tileEntity.getPos().getX();
					int y1 = tileEntity.getPos().getY();
					int z1 = tileEntity.getPos().getZ();
					//
					int radius1 = (int) Math.sqrt(Math.pow(x1 - boundaryWardStones.get(0).getX() , 2) + Math.pow(y1 - boundaryWardStones.get(0).getY() , 2) + Math.pow(z1 - boundaryWardStones.get(0).getZ() , 2));
					System.out.println(radius1);
					int radius2 = (int) Math.sqrt(Math.pow(x1 - boundaryWardStones.get(1).getX(),2) + Math.pow(y1 - boundaryWardStones.get(1).getY(),2)  + Math.pow(z1 - boundaryWardStones.get(1).getZ(),2));
					System.out.println(radius2);
					int radius3 = (int) Math.sqrt(Math.pow(x1 - boundaryWardStones.get(2).getX(),2) + Math.pow(y1 - boundaryWardStones.get(2).getY(),2)  + Math.pow(z1 - boundaryWardStones.get(2).getZ(),2));
					System.out.println(radius3);
					int radius4 = (int) Math.sqrt(Math.pow(x1 - boundaryWardStones.get(3).getX(),2) + Math.pow(y1 - boundaryWardStones.get(3).getY(),2)  + Math.pow(z1 - boundaryWardStones.get(3).getZ(),2));
					System.out.println(radius4);
					if(radius1 == radius2 && radius1 == radius3 && radius1 == radius4) {
						System.out.println("radius's are correct");
						if(world.getTileEntity(boundaryWardStones.get(0)) instanceof BoundaryWardStoneTileEntity && world.getTileEntity(boundaryWardStones.get(1)) instanceof BoundaryWardStoneTileEntity
						&&world.getTileEntity(boundaryWardStones.get(2)) instanceof BoundaryWardStoneTileEntity&&world.getTileEntity(boundaryWardStones.get(3)) instanceof BoundaryWardStoneTileEntity) {
							stonePlacementAccurate = true;
							System.out.println("all are correct");
						}else{
							stonePlacementAccurate = false;
							System.out.println("none are");
						}
					}
					//TODO: start adding a list view here that'll cycle through all wards attatched to this TE
					if(stonePlacementAccurate == true){
						double d0 = (double)(1 * 10 + 10);
						AxisAlignedBB axisalignedbb = (new AxisAlignedBB(this.pos)).grow(d0).expand(0.0D, (double)this.world.getHeight(), 0.0D);
						List<PlayerEntity> list = this.world.getEntitiesWithinAABB(PlayerEntity.class, axisalignedbb);

						for(PlayerEntity playerentity : list) {
							playerentity.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 50, 1, true, true));
						}
						//set with a border right now it works for replacing air with glass
						//TODO: set to WardBarrier
						try(BlockPos.PooledMutable pooledMutable = BlockPos.PooledMutable.retain()){
							final int posX = this.getPos().getX();
							final int posY = this.getPos().getY();
							final int posZ = this.getPos().getZ();

							for(int z = -50; z <= 50; ++z){
								for(int x = -50; x <= 50; ++x){
									for(int y = -50; y <=50; y++){
										final int dist = (x*x) + (y*y) + (z*z);
										//2, 0 is the ratio for testing based on 1 being the distance working now on the aua
										if (dist > ((radius1 + 1) * radius1)){
											continue;
										}

										if (dist < ((radius1-1) * radius1)){
											continue;
										}

										pooledMutable.setPos(posX + x,posY + y, posZ + z);
										final BlockState blockState = world.getBlockState(pooledMutable);
										final IFluidState fluidState = world.getFluidState(pooledMutable);
										final Block block = blockState.getBlock();

										if(block == Blocks.AIR){
											world.setBlockState(pooledMutable, Blocks.GLASS.getDefaultState(), 1);
										}
									}
								}
							}
						}
					}

				}
				else{
					System.out.println(boundaryWardStones.size());
				}
			}
		if(needsSave){
			this.markDirty();
		}
	}

	private boolean destroyBlock(BlockPos pos, boolean dropBlock, @Nullable Entity entity) {
		BlockState blockstate = world.getBlockState(pos);
		if(blockstate.isAir(world, pos))return false;
		else {
			IFluidState ifluidstate = world.getFluidState(pos);
			world.playEvent(2001, pos, Block.getStateId(blockstate));
			if(dropBlock) {
				TileEntity tileentity= blockstate.hasTileEntity() ? world.getTileEntity(pos) : null;
				Block.spawnDrops(blockstate, world, this.pos.add(0, 1.5, 0), tileentity, entity, ItemStack.EMPTY);
			}
			return world.setBlockState(pos, ifluidstate.getBlockState(), 3);
		}

	}

	@Nullable
	@Override
	public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
		return new MasterWardStoneContainer(id, inventory, this);
	}

	private void init() {
		initialized = true;
		y = 3;

	}

	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		this.write(tag);
		return super.getUpdatePacket();
	}

	@Override
	public CompoundNBT getUpdateTag() {
		write(tag);
		return tag;
	}

	@Override
	public void handleUpdateTag(CompoundNBT tag) {
		this.read(tag);
	}


	public void readRestorableNBT(CompoundNBT tag) {
		this.FireEnergy.setEnergy(tag.getInt("fireenergy"));
		this.WaterEnergy.setEnergy(tag.getInt("waterenergy"));
		this.AirEnergy.setEnergy(tag.getInt("airenergy"));
		this.EarthEnergy.setEnergy(tag.getInt("earthenergy"));
		this.DarkEnergy.setEnergy(tag.getInt("darkenergy"));
		this.LightEnergy.setEnergy(tag.getInt("lightenergy"));
	}
}
