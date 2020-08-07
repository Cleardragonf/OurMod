package com.cleardragonf.ourmod.tileentity;

import com.cleardragonf.ourmod.container.EssenceCollectorContainer;
import com.cleardragonf.ourmod.essence.CustomEnergyStorage;
import com.cleardragonf.ourmod.init.BlockInitNew;
import com.cleardragonf.ourmod.init.ModTileEntityTypes;
import com.cleardragonf.ourmod.objects.blocks.EssenceCollector;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.energy.EnergyStorage;

import javax.annotation.Nullable;


public class EssenceCollectorTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

	protected int numPlayersUsing;
	private boolean initialized = false;
	private CompoundNBT tag = new CompoundNBT();
	public int tick, y;


	public EssenceCollectorTileEntity(TileEntityType<?> typeIn) {
		super(typeIn);
	}

	public EssenceCollectorTileEntity() {
		this(ModTileEntityTypes.ESSENCE_COLLECTOR.get());
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

		return new StringTextComponent("Essence Collector");

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
		if (block instanceof EssenceCollector) {
			this.world.addBlockEvent(this.pos, block, 1, this.numPlayersUsing);
			this.world.notifyNeighborsOfStateChange(this.pos, block);
		}
	}

	public static int getPlayersUsing(IBlockReader reader, BlockPos pos) {
		BlockState blockstate = reader.getBlockState(pos);
		if (blockstate.hasTileEntity()) {
			TileEntity tileentity = reader.getTileEntity(pos);
			if (tileentity instanceof EssenceCollectorTileEntity) {
				return ((EssenceCollectorTileEntity) tileentity).numPlayersUsing;
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
		final BlockPos tilePos = this.pos;
		final World world = this.world;
		if (world == null){
			return;
		}
		int fireBlocksFound = 0;
		int waterBlocksFound = 0;
		int airBlocksFound = 0;
		int earthBlocksFound = 0;
		int darkBlocksFound = 0;
		int lightBlocksFound = 0;
		int multiplierBlocksFound = 0;

		try(BlockPos.PooledMutable pooledMutable = BlockPos.PooledMutable.retain()){
			final int posX = tilePos.getX();
			final int posY = tilePos.getY();
			final int posZ = tilePos.getZ();

			for(int z = -50; z <= 50; ++z){
				for(int x = -50; x <= 50; ++x){
					for(int y = -50; y <=50; y++){
						final int dist = (x*x) + (y*y) + (z*z);
						if (dist > 2500){
							continue;
						}

						if (dist < 1){
							continue;
						}

						pooledMutable.setPos(posX + x,posY + y, posZ + z);
						final BlockState blockState = world.getBlockState(pooledMutable);
						final IFluidState fluidState = world.getFluidState(pooledMutable);
						final Block block = blockState.getBlock();

						if(block instanceof FireBlock || block == BlockInitNew.FIRE_MANA.get() || block == Blocks.FIRE || (!fluidState.isEmpty() && fluidState.isTagged(FluidTags.LAVA)) || block==Blocks.CAMPFIRE){
							++fireBlocksFound;
						}else if(block == Blocks.WATER || block == BlockInitNew.WATER_MANA.get()|| (!fluidState.isEmpty() && fluidState.isTagged(FluidTags.WATER))){
							++waterBlocksFound;
						}else if(block == Blocks.AIR|| block == BlockInitNew.AIR_MANA.get()){
							++airBlocksFound;
						}else if(block == Blocks.DIRT || block == BlockInitNew.EARTH_MANA.get()|| block ==  Blocks.GRASS ||block ==  Blocks.GRANITE ||block ==  Blocks.STONE ||block ==  Blocks.ANDESITE
								|| block == Blocks.CLAY || block == Blocks.DIORITE || block == Blocks.GRAVEL|| block == Blocks.ICE || block == Blocks.MOSSY_COBBLESTONE
								|| block == Blocks.NETHERRACK || block == Blocks.OBSIDIAN || block == Blocks.PODZOL || block == Blocks.PRISMARINE ||block ==  Blocks.QUARTZ_BLOCK
						|| block == Blocks.SAND){
							++earthBlocksFound;
						}else if(block == BlockInitNew.DARK_MANA.get() || block.getLightValue(block.getDefaultState(), world, pos) < 2) {
							++darkBlocksFound;
						}
						else if(block == BlockInitNew.LIGHT_MANA.get() || block.getLightValue(block.getDefaultState(), world, pos) > 1){
							++lightBlocksFound;
						}
					}
				}
			}
		}

		if(multiplierBlocksFound > 0){
			fireBlocksFound *= multiplierBlocksFound;
			waterBlocksFound *= multiplierBlocksFound;
			airBlocksFound *= multiplierBlocksFound;
			earthBlocksFound *= multiplierBlocksFound;
			darkBlocksFound *= multiplierBlocksFound;
			lightBlocksFound *= multiplierBlocksFound;
		}
		boolean needsSave = false;

		if(this.FireEnergy.getEnergyStored() >= 100000){

		}else{
			FireEnergy.addEnergy(fireBlocksFound * 1);
			needsSave = true;
			write(tag);
		}

		if(this.WaterEnergy.getEnergyStored() >= 100000){

		}else{
			WaterEnergy.addEnergy(waterBlocksFound * 1);
			needsSave = true;
			write(tag);
		}
		if(this.AirEnergy.getEnergyStored() >= 100000){

		}else{
			AirEnergy.addEnergy(airBlocksFound * 1);
			needsSave = true;
			write(tag);
		}
		if(this.DarkEnergy.getEnergyStored() >= 100000){

		}else{
			DarkEnergy.addEnergy(darkBlocksFound * 1);
			needsSave = true;
			write(tag);
		}
		if(this.EarthEnergy.getEnergyStored() >= 100000){

		}else{
			EarthEnergy.addEnergy(earthBlocksFound * 1);
			needsSave = true;
			write(tag);
		}
		if(this.LightEnergy.getEnergyStored() >= 100000){

		}else{
			LightEnergy.addEnergy(lightBlocksFound * 1);
			needsSave = true;
			write(tag);
		}
		if(needsSave){
			this.markDirty();
		}
	}

	@Nullable
	@Override
	public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
		return new EssenceCollectorContainer(id, inventory, this);
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
