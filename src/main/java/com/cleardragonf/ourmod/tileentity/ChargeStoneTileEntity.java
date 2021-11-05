package com.cleardragonf.ourmod.tileentity;

import com.cleardragonf.ourmod.MCM.IMCMValueCapability;
import com.cleardragonf.ourmod.MCM.MCMValues;
import com.cleardragonf.ourmod.container.ChargeStoneContainer;
import com.cleardragonf.ourmod.essence.CustomEnergyStorage;
import com.cleardragonf.ourmod.init.ModTileEntityTypes;
import com.cleardragonf.ourmod.objects.blocks.ChargeStone;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;


public class ChargeStoneTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

	public boolean isMaster = false;
	public BlockPos masterBlock = this.worldPosition;
	public int maxStoredInt = this.getBatteryBlocks().size() * 1000000;

	public CompoundNBT tag = new CompoundNBT();

	public INBT energyblocks;
	public List<ChargeStone> battery = new ArrayList<ChargeStone>();
	public List<BlockPos> powerINBlocks = new ArrayList<BlockPos>();

	public List<BlockPos> getPowerINBlocks() {
		return powerINBlocks;
	}

	public void setPowerINBlocks(List<BlockPos> powerINBlocks) {
		this.powerINBlocks = powerINBlocks;
	}

	public List<ChargeStone> getBattery() {
		return battery;
	}

	public void setBattery(List<ChargeStone> battery) {
		this.battery = battery;
	}

	public LazyOptional<IItemHandler> handler = LazyOptional.of(this::createHandler);
	//public LazyOptional<IEnergyStorage> energy = LazyOptional.of(this::createEnergy);
	public final CustomEnergyStorage MCMEnergy = new CustomEnergyStorage(maxStoredInt, 0);
	public final CustomEnergyStorage FireEnergy = new CustomEnergyStorage(maxStoredInt, 0);
	public final CustomEnergyStorage AirEnergy = new CustomEnergyStorage(maxStoredInt, 0);
	public final CustomEnergyStorage EarthEnergy = new CustomEnergyStorage(maxStoredInt, 0);
	public final CustomEnergyStorage WaterEnergy = new CustomEnergyStorage(maxStoredInt, 0);
	public final CustomEnergyStorage LightEnergy = new CustomEnergyStorage(maxStoredInt, 0);
	public final CustomEnergyStorage DarkEnergy = new CustomEnergyStorage(maxStoredInt, 0);

	public final ItemStackHandler inventory = new ItemStackHandler(120) {

		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			if (stack.getItem() == null) {
				return stack;
			}
			return super.insertItem(slot, stack, simulate);
		}

		@Override
		public boolean isItemValid(int slot, ItemStack stack) {
			return stack.getItem() != null;
		}

		@Override
		protected void onContentsChanged(int slot) {
			ChargeStoneTileEntity.this.setChanged();
		}
	};


	private int counter;

	public ChargeStoneTileEntity() {
		super(ModTileEntityTypes.CHARGE_STONE.get());
	}

	public void updateBlock() {
		level.sendBlockUpdated(worldPosition, this.getBlockState(), this.getBlockState(), 1);
	}

	@Override
	public void tick() {
		save(tag);
		setChanged();
		if (level.isClientSide()) {
			return;
		}

		if (counter > 0) {
			counter--;

			setChanged();
		}

		if (counter <= 0) {
			//check if this current one is or isn't the master ChargeStone
			if (isMaster()) {
				for (BlockPos inEnergyBlock :
						this.getPowerINBlocks()) {
					if(level.getBlockEntity(inEnergyBlock) instanceof EssenceCollectorTileEntity){
						EssenceCollectorTileEntity te = (EssenceCollectorTileEntity) level.getBlockEntity(inEnergyBlock);
						executeEnergySearch(te);
					}else{
						this.powerINBlocks.remove(inEnergyBlock);
					}


				}
			}
		}

		//sendOutPower();
	}


	private void executeMCMEnergyConvert() {
		if (this.MCMEnergy.getEnergyStored() < maxStoredInt) {
			if (this.FireEnergy.getEnergyStored() > 0) {
				int transfer = this.FireEnergy.getEnergyStored();
				this.MCMEnergy.addEnergy(transfer);
				this.FireEnergy.consumeEnergy(transfer);
				save(tag);
				setChanged();
			}
			if (this.WaterEnergy.getEnergyStored() > 0) {
				int transfer = this.WaterEnergy.getEnergyStored();
				this.MCMEnergy.addEnergy(transfer);
				this.WaterEnergy.consumeEnergy(transfer);
				save(tag);
				setChanged();
			}
			if (this.AirEnergy.getEnergyStored() > 0) {
				int transfer = this.AirEnergy.getEnergyStored();
				this.MCMEnergy.addEnergy(transfer);
				this.AirEnergy.consumeEnergy(transfer);
				save(tag);
				setChanged();
			}
			if (this.EarthEnergy.getEnergyStored() > 0) {
				int transfer = this.EarthEnergy.getEnergyStored();
				this.MCMEnergy.addEnergy(transfer);
				this.EarthEnergy.consumeEnergy(transfer);
				save(tag);
				setChanged();
			}
			if (this.DarkEnergy.getEnergyStored() > 0) {
				int transfer = this.DarkEnergy.getEnergyStored();
				this.MCMEnergy.addEnergy(transfer);
				this.DarkEnergy.consumeEnergy(transfer);
				save(tag);
				setChanged();
			}
			if (this.LightEnergy.getEnergyStored() > 0) {
				int transfer = this.LightEnergy.getEnergyStored();
				this.MCMEnergy.addEnergy(transfer);
				this.LightEnergy.consumeEnergy(transfer);
				save(tag);
				setChanged();
			}
		}
	}


	private void executeEnergySearch(EssenceCollectorTileEntity te) {
		if (energyblocks != null) {

			CompoundNBT tagger = (CompoundNBT) energyblocks;

			BlockPos pos = new BlockPos(tagger.getInt("x"), tagger.getInt("y"), tagger.getInt("z"));
			TileEntity tileEntity = level.getBlockEntity(pos);


			if (te.FireEnergy.getEnergyStored() > 0 && this.FireEnergy.getEnergyStored() < maxStoredInt) {
				int transfer = te.FireEnergy.getEnergyStored();
				te.FireEnergy.consumeEnergy(transfer);
				this.FireEnergy.addEnergy(transfer);
				save(tag);
				setChanged();
			}
			if (te.WaterEnergy.getEnergyStored() > 0 && this.WaterEnergy.getEnergyStored() < maxStoredInt) {
				int transfer = te.WaterEnergy.getEnergyStored();
				te.WaterEnergy.consumeEnergy(transfer);
				this.WaterEnergy.addEnergy(transfer);
				save(tag);
				setChanged();
			}
			if (te.AirEnergy.getEnergyStored() > 0 && this.AirEnergy.getEnergyStored() < maxStoredInt) {
				int transfer = te.AirEnergy.getEnergyStored();
				te.AirEnergy.consumeEnergy(transfer);
				this.AirEnergy.addEnergy(transfer);
				save(tag);
				setChanged();
			}
			if (te.EarthEnergy.getEnergyStored() > 0 && this.EarthEnergy.getEnergyStored() < maxStoredInt) {
				int transfer = te.EarthEnergy.getEnergyStored();
				te.EarthEnergy.consumeEnergy(transfer);
				this.EarthEnergy.addEnergy(transfer);
				save(tag);
				setChanged();
			}
			if (te.DarkEnergy.getEnergyStored() > 0 && this.DarkEnergy.getEnergyStored() < maxStoredInt) {
				int transfer = te.DarkEnergy.getEnergyStored();
				te.DarkEnergy.consumeEnergy(transfer);
				this.DarkEnergy.addEnergy(transfer);
				save(tag);
				setChanged();
			}
			if (te.LightEnergy.getEnergyStored() > 0 && this.LightEnergy.getEnergyStored() < maxStoredInt) {
				int transfer = te.LightEnergy.getEnergyStored();
				te.LightEnergy.consumeEnergy(transfer);
				this.LightEnergy.addEnergy(transfer);
				save(tag);
				setChanged();
			}
		} else {
			energyblocks = null;
		}

	}


	private int MCMREader(Item a, IMCMValueCapability b) {
		MCMValues itemValue = new MCMValues();
		if(itemValue.getMap().containsKey(a)){
			return itemValue.getMap().get(a);
		}else{
			return 1;
		}
	}
	
	@Override
	public void load(BlockState blockState,CompoundNBT tag) {
		CompoundNBT invTag = tag.getCompound("inv");
		handler.ifPresent(h -> ((INBTSerializable<CompoundNBT>) inventory).deserializeNBT(invTag));
		super.load(blockState,tag);
		readRestorableNBT(tag);
	}

	@Override
	public CompoundNBT save(CompoundNBT tag) {
		super.save(tag);
		handler.ifPresent(h -> {
			CompoundNBT compound = ((INBTSerializable<CompoundNBT>) inventory).serializeNBT();
			tag.put("inv", compound);
		});

		tag.put("inv", inventory.serializeNBT());
		if (energyblocks != null){

			tag.put("energypos", this.energyblocks);
		}
		tag.putInt("mcmenergy", this.MCMEnergy.getEnergyStored());
		tag.putInt("fireenergy", this.FireEnergy.getEnergyStored());
		tag.putInt("waterenergy", this.WaterEnergy.getEnergyStored());
		tag.putInt("airenergy", this.AirEnergy.getEnergyStored());
		tag.putInt("earthenergy", this.EarthEnergy.getEnergyStored());
		tag.putInt("darkenergy", this.DarkEnergy.getEnergyStored());
		tag.putInt("lightenergy", this.LightEnergy.getEnergyStored());
		return tag;
	}

	private IItemHandler createHandler() {
		return inventory;
	}


	private IEnergyStorage createEnergy() {
		return new CustomEnergyStorage(100000, 0);
	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return handler.cast();
		}
		return super.getCapability(cap, side);
	}


	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT tag = new CompoundNBT();
		this.save(tag);

		// the number here is generally ignored for non-vanilla TileEntities, 0 is safest
		return new SUpdateTileEntityPacket(this.getBlockPos(), 0 , tag);
	}

	public boolean isMaster() {
		return isMaster;
	}

	public void setMaster(boolean master) {
		isMaster = master;
	}

	@Override
	public CompoundNBT getUpdateTag() {
		CompoundNBT tag = new CompoundNBT();
		save(tag);
		return tag;
	}

	@Override
	public void handleUpdateTag(BlockState blockState,CompoundNBT tag) {
		this.load(blockState,tag);
	}

	// this method gets called on the client when it receives the packet that was sent in the previous method
	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		this.load(this.getBlockState(), pkt.getTag());
	}


	public void readRestorableNBT(CompoundNBT tag){
		energyblocks = tag.get("energypos");
		this.inventory.deserializeNBT(tag.getCompound("inv"));
		this.FireEnergy.setEnergy(tag.getInt("fireenergy"));
		this.WaterEnergy.setEnergy(tag.getInt("waterenergy"));
		this.AirEnergy.setEnergy(tag.getInt("airenergy"));
		this.EarthEnergy.setEnergy(tag.getInt("earthenergy"));
		this.LightEnergy.setEnergy(tag.getInt("lightenergy"));
		this.DarkEnergy.setEnergy(tag.getInt("darkenergy"));
		this.MCMEnergy.setEnergy(tag.getInt("mcmenergy"));
	}
	public List<BlockPos> batteryBlocks = new ArrayList<BlockPos>();

	public List<BlockPos> getBatteryBlocks() {
		return batteryBlocks;
	}

	public void setBatteryBlocks(List<BlockPos> batteryBlocks) {
		this.batteryBlocks = batteryBlocks;
	}

	public void setMasterSlave() {
		if((level.getBlockState(this.worldPosition.above()).getBlock() instanceof ChargeStone)){
			this.isMaster = true;
			ChargeStoneTileEntity block = (ChargeStoneTileEntity) level.getBlockEntity(this.worldPosition.above());
				((ChargeStoneTileEntity)block).setMaster(false);
				this.setBatteryBlocks(block.getBatteryBlocks());
				this.setPowerINBlocks(block.getPowerINBlocks());
				block.masterBlock = this.worldPosition;


		}else if((level.getBlockState(this.worldPosition.below()).getBlock() instanceof ChargeStone)){
			this.isMaster = true;
			ChargeStoneTileEntity block = (ChargeStoneTileEntity) level.getBlockEntity(this.worldPosition.below());
			((ChargeStoneTileEntity)block).setMaster(false);
			this.setBatteryBlocks(block.getBatteryBlocks());
			this.setPowerINBlocks(block.getPowerINBlocks());
			block.masterBlock = this.worldPosition;
		}else if((level.getBlockState(this.worldPosition.north()).getBlock() instanceof ChargeStone)){
			this.isMaster = true;
			ChargeStoneTileEntity block = (ChargeStoneTileEntity) level.getBlockEntity(this.worldPosition.north());
			((ChargeStoneTileEntity)block).setMaster(false);
			this.setBatteryBlocks(block.getBatteryBlocks());
			this.setPowerINBlocks(block.getPowerINBlocks());
			block.masterBlock = this.worldPosition;
		}else if((level.getBlockState(this.worldPosition.south()).getBlock() instanceof ChargeStone)){
			this.isMaster = true;
			ChargeStoneTileEntity block = (ChargeStoneTileEntity) level.getBlockEntity(this.worldPosition.south());
			((ChargeStoneTileEntity)block).setMaster(false);
			this.setBatteryBlocks(block.getBatteryBlocks());
			this.setPowerINBlocks(block.getPowerINBlocks());
			block.masterBlock = this.worldPosition;
		}else if((level.getBlockState(this.worldPosition.east()).getBlock() instanceof ChargeStone)){
			this.isMaster = true;
			ChargeStoneTileEntity block = (ChargeStoneTileEntity) level.getBlockEntity(this.worldPosition.east());
			((ChargeStoneTileEntity)block).setMaster(false);
			this.setBatteryBlocks(block.getBatteryBlocks());
			this.setPowerINBlocks(block.getPowerINBlocks());
			block.masterBlock = this.worldPosition;
		}else if((level.getBlockState(this.worldPosition.west()).getBlock() instanceof ChargeStone)){
			this.isMaster = true;
			ChargeStoneTileEntity block = (ChargeStoneTileEntity) level.getBlockEntity(this.worldPosition.west());
			((ChargeStoneTileEntity)block).setMaster(false);
			this.setBatteryBlocks(block.getBatteryBlocks());
			this.setPowerINBlocks(block.getPowerINBlocks());
			block.masterBlock = this.worldPosition;
		}
				else{
			isMaster = true;
		}
	}

	@Override
	public ITextComponent getDisplayName() {
		return new StringTextComponent(getType().getRegistryName().getPath());
	}

	@Nullable
	@Override
	public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		return new ChargeStoneContainer(i, playerInventory, this);
	}
}
