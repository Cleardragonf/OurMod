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
import net.minecraft.fluid.FluidState;
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
	public CompoundNBT save(CompoundNBT compound) {
		super.save(compound);
		compound.putInt("fireenergy", this.FireEnergy.getEnergyStored());
		compound.putInt("waterenergy", this.WaterEnergy.getEnergyStored());
		compound.putInt("airenergy", this.AirEnergy.getEnergyStored());
		compound.putInt("earthenergy", this.EarthEnergy.getEnergyStored());
		compound.putInt("darkenergy", this.DarkEnergy.getEnergyStored());
		compound.putInt("lightenergy", this.LightEnergy.getEnergyStored());
		return compound;
	}

	@Override
	public void load(BlockState blockState,CompoundNBT compound) {
		super.load(blockState, compound);
		readRestorableNBT(compound);
	}

	@Override

	public ITextComponent getDisplayName() {

		return new StringTextComponent("Essence Collector");

	}

	private void playSound(SoundEvent sound) {
		double dx = (double) this.worldPosition.getX() + 0.5D;
		double dy = (double) this.worldPosition.getY() + 0.5D;
		double dz = (double) this.worldPosition.getZ() + 0.5D;
		this.level.playSound((PlayerEntity) null, dx, dy, dz, sound, SoundCategory.BLOCKS, 0.5f,
				this.level.random.nextFloat() * 0.1f + 0.9f);
	}

	@Override
	public boolean triggerEvent(int id, int type) {
		if (id == 1) {
			this.numPlayersUsing = type;
			return true;
		} else {
			return super.triggerEvent(id, type);
		}
	}

	protected void onOpenOrClose() {
		Block block = this.getBlockState().getBlock();
		if (block instanceof EssenceCollector) {
			this.level.blockEvent(this.worldPosition, block, 1, this.numPlayersUsing);
			this.level.updateNeighborsAt(this.worldPosition, block);
		}
	}

	public static int getPlayersUsing(IBlockReader reader, BlockPos pos) {
		BlockState blockstate = reader.getBlockState(pos);
		if (blockstate.hasTileEntity()) {
			TileEntity tileentity = reader.getBlockEntity(pos);
			if (tileentity instanceof EssenceCollectorTileEntity) {
				return ((EssenceCollectorTileEntity) tileentity).numPlayersUsing;
			}
		}
		return 0;
	}

	@Override
	public void clearCache() {
		super.clearCache();
	}

	
	@Override
	public void setRemoved() {
		super.setRemoved();
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
		final BlockPos tilePos = this.worldPosition;
		final World world = this.level;
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

		Iterable<BlockPos> mutable = BlockPos.betweenClosed(worldPosition.getX()-5,worldPosition.getY()-5,worldPosition.getZ()-5,worldPosition.getX() +5,worldPosition.getY() +5,worldPosition.getZ()+5);

		for (BlockPos block : mutable) {
			//the cause for issues is the getX is depending on the X axis along with the others...need it to loop through...and then utilize only that loops number...
			//final int dist = (block.getX()*block.getX()) + (block.getY()*block.getY()) + (block.getZ() * block.getZ());
			int posX = this.getBlockPos().getX() - block.getX();
			int posY = this.getBlockPos().getY() - block.getY();
			int posZ = this.getBlockPos().getZ() - block.getZ();
			final int dist = (posX * posX) + (posY * posY) + (posZ * posZ);
			if(dist>25){
				continue;
			}
			if(dist< 1){
				continue;
			}


			final BlockState blockState = world.getBlockState(block);
			final FluidState fluidState = world.getFluidState(block);
			final Block targetblock = blockState.getBlock();
			if(targetblock instanceof FireBlock || targetblock == BlockInitNew.FIRE_MANA.get() || targetblock == Blocks.FIRE || (!fluidState.isEmpty() && fluidState.is(FluidTags.LAVA)) || targetblock==Blocks.CAMPFIRE){
				++fireBlocksFound;
			}else if(targetblock == Blocks.WATER || targetblock == BlockInitNew.WATER_MANA.get()|| (!fluidState.isEmpty() && fluidState.is(FluidTags.WATER))){
				++waterBlocksFound;
			}else if(targetblock == Blocks.AIR|| targetblock == BlockInitNew.AIR_MANA.get()){
				++airBlocksFound;
			}else if(targetblock == Blocks.DIRT || targetblock == BlockInitNew.EARTH_MANA.get()|| targetblock ==  Blocks.GRASS ||targetblock ==  Blocks.GRANITE ||targetblock ==  Blocks.STONE ||targetblock ==  Blocks.ANDESITE
					|| targetblock == Blocks.CLAY || targetblock == Blocks.DIORITE || targetblock == Blocks.GRAVEL|| targetblock == Blocks.ICE || targetblock == Blocks.MOSSY_COBBLESTONE
					|| targetblock == Blocks.NETHERRACK || targetblock == Blocks.OBSIDIAN || targetblock == Blocks.PODZOL || targetblock == Blocks.PRISMARINE ||targetblock ==  Blocks.QUARTZ_BLOCK
					|| targetblock == Blocks.SAND){
				++earthBlocksFound;
			}else if(targetblock == BlockInitNew.DARK_MANA.get() || targetblock.getLightValue(targetblock.defaultBlockState(), world, worldPosition) < 2) {
				++darkBlocksFound;
			}
			else if(targetblock == BlockInitNew.LIGHT_MANA.get() || targetblock.getLightValue(targetblock.defaultBlockState(), world, worldPosition) > 2){
				++lightBlocksFound;
			}else{
				System.out.println(targetblock.toString());
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
			save(tag);
		}

		if(this.WaterEnergy.getEnergyStored() >= 100000){

		}else{
			WaterEnergy.addEnergy(waterBlocksFound * 1);
			needsSave = true;
			save(tag);
		}
		if(this.AirEnergy.getEnergyStored() >= 100000){

		}else{
			AirEnergy.addEnergy(airBlocksFound * 1);
			needsSave = true;
			save(tag);
		}
		if(this.DarkEnergy.getEnergyStored() >= 100000){

		}else{
			DarkEnergy.addEnergy(darkBlocksFound * 1);
			needsSave = true;
			save(tag);
		}
		if(this.EarthEnergy.getEnergyStored() >= 100000){

		}else{
			EarthEnergy.addEnergy(earthBlocksFound * 1);
			needsSave = true;
			save(tag);
		}
		if(this.LightEnergy.getEnergyStored() >= 100000){

		}else{
			LightEnergy.addEnergy(lightBlocksFound * 1);
			needsSave = true;
			save(tag);
		}
		if(needsSave){
			this.setChanged();
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
		this.save(tag);
		return super.getUpdatePacket();
	}

	@Override
	public CompoundNBT getUpdateTag() {
		save(tag);
		return tag;
	}

	@Override
	public void handleUpdateTag(BlockState blockState,CompoundNBT tag) {
		this.load(blockState, tag);
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
