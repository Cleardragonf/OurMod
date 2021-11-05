package com.cleardragonf.ourmod.tileentity;

import com.cleardragonf.ourmod.MCM.IMCMValueCapability;
import com.cleardragonf.ourmod.MCM.MCMValueProvider;
import com.cleardragonf.ourmod.MCM.MCMValues;
import com.cleardragonf.ourmod.container.MCMChestContainer;
import com.cleardragonf.ourmod.essence.CustomEnergyStorage;
import com.cleardragonf.ourmod.init.ModTileEntityTypes;
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
import net.minecraft.state.properties.BlockStateProperties;
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


public class MCMChestTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

	public CompoundNBT tag = new CompoundNBT();

	public INBT energyblocks;





	public LazyOptional<IItemHandler> handler = LazyOptional.of(this::createHandler);
	//public LazyOptional<IEnergyStorage> energy = LazyOptional.of(this::createEnergy);
	public final CustomEnergyStorage MCMEnergy = new CustomEnergyStorage(6000000,0);
	public final CustomEnergyStorage FireEnergy = new CustomEnergyStorage(1000000,0);
	public final CustomEnergyStorage AirEnergy = new CustomEnergyStorage(1000000,0);
	public final CustomEnergyStorage EarthEnergy = new CustomEnergyStorage(1000000,0);
	public final CustomEnergyStorage WaterEnergy = new CustomEnergyStorage(1000000,0);
	public final CustomEnergyStorage LightEnergy = new CustomEnergyStorage(1000000,0);
	public final CustomEnergyStorage DarkEnergy = new CustomEnergyStorage(1000000,0);

	public final ItemStackHandler inventory = new ItemStackHandler(120){

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
			MCMChestTileEntity.this.setChanged();
		}
	};


	private int counter;

	public MCMChestTileEntity() {
		super(ModTileEntityTypes.MCM_Chest.get());
	}

	public void updateBlock(){
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
			executeEnergySearch();
			executeMCMEnergyConvert();
			ItemStack stack = new ItemStack(inventory.getStackInSlot(0).getStack().getItem());
			Item mcmValueItem = stack.getItem();
				//Takes the Clone slot and Existing MCM value and Begins Duplicating the Item.
					inventory.getStackInSlot(0).getStack().getCapability(MCMValueProvider.MCMValue).ifPresent(a -> {
							if(this.MCMEnergy.getEnergyStored() >= MCMREader(mcmValueItem, a)){
								ItemStack stack2;
								for (int i = 5; i < 59; i++) {
									if(inventory.getStackInSlot(i).isEmpty()){
										stack.setCount(this.inventory.getStackInSlot(i).getCount() + 1);
										inventory.setStackInSlot(i, stack);
										break;
									}else {
									if(this.inventory.getStackInSlot(i).getStack().getItem().equals(mcmValueItem)) {
										stack.setCount(this.inventory.getStackInSlot(i).getCount() + 1);
										inventory.setStackInSlot(i, stack);
										break;
									}

								}

								}

								this.MCMEnergy.consumeEnergy(MCMREader(mcmValueItem, a));
							}


					});


				//Removes inputs and Adds MCM Value to the Chest
				for (int i = 1; i < 5; i++) {
					setChanged();
					if(!inventory.getStackInSlot(i).isEmpty()){

						setChanged();
						Item inputstack = inventory.getStackInSlot(i).getStack().getItem();
						inventory.getStackInSlot(i).getStack().getCapability(MCMValueProvider.MCMValue).ifPresent(a ->{
							this.MCMEnergy.addEnergy(MCMREader(inputstack, a));
						});
						inventory.extractItem(i,1,false);
						setChanged();

					}



				}
				counter = 10;
				setChanged();
		}

		BlockState blockState = level.getBlockState(worldPosition);
		if (blockState.getValue(BlockStateProperties.POWERED) != counter > 0) {
			level.setBlock(worldPosition, blockState.setValue(BlockStateProperties.POWERED, counter > 0), 3);
		}

		//sendOutPower();
	}

	private void executeMCMEnergyConvert() {
		if(this.MCMEnergy.getEnergyStored() < 6000000){
			if(this.FireEnergy.getEnergyStored() > 0){
				int transfer = this.FireEnergy.getEnergyStored();
				this.MCMEnergy.addEnergy(transfer);
				this.FireEnergy.consumeEnergy(transfer);
				save(tag);
				setChanged();
			}
			if(this.WaterEnergy.getEnergyStored() > 0){
				int transfer = this.WaterEnergy.getEnergyStored();
				this.MCMEnergy.addEnergy(transfer);
				this.WaterEnergy.consumeEnergy(transfer);
				save(tag);
				setChanged();
			}
			if(this.AirEnergy.getEnergyStored() > 0){
				int transfer = this.AirEnergy.getEnergyStored();
				this.MCMEnergy.addEnergy(transfer);
				this.AirEnergy.consumeEnergy(transfer);
				save(tag);
				setChanged();
			}
			if(this.EarthEnergy.getEnergyStored() > 0){
				int transfer = this.EarthEnergy.getEnergyStored();
				this.MCMEnergy.addEnergy(transfer);
				this.EarthEnergy.consumeEnergy(transfer);
				save(tag);
				setChanged();
			}
			if(this.DarkEnergy.getEnergyStored() > 0){
				int transfer = this.DarkEnergy.getEnergyStored();
				this.MCMEnergy.addEnergy(transfer);
				this.DarkEnergy.consumeEnergy(transfer);
				save(tag);
				setChanged();
			}
			if(this.LightEnergy.getEnergyStored() > 0){
				int transfer = this.LightEnergy.getEnergyStored();
				this.MCMEnergy.addEnergy(transfer);
				this.LightEnergy.consumeEnergy(transfer);
				save(tag);
				setChanged();
			}
		}
	}



	private void executeEnergySearch() {
		if(energyblocks!= null){

			CompoundNBT tagger = (CompoundNBT) energyblocks;

				BlockPos pos = new BlockPos(tagger.getInt("x"),tagger.getInt("y"),tagger.getInt("z"));
				TileEntity tileEntity = level.getBlockEntity(pos);
				if(tileEntity instanceof EssenceCollectorTileEntity){
					EssenceCollectorTileEntity tileTarget = (EssenceCollectorTileEntity) tileEntity;


					if(tileTarget.FireEnergy.getEnergyStored() > 0 && this.FireEnergy.getEnergyStored() < 1000000){
						int transfer = tileTarget.FireEnergy.getEnergyStored();
						tileTarget.FireEnergy.consumeEnergy(transfer);
						this.FireEnergy.addEnergy(transfer);
						save(tag);
						setChanged();
					}
					if(tileTarget.WaterEnergy.getEnergyStored() > 0 && this.WaterEnergy.getEnergyStored() < 1000000){
						int transfer = tileTarget.WaterEnergy.getEnergyStored();
						tileTarget.WaterEnergy.consumeEnergy(transfer);
						this.WaterEnergy.addEnergy(transfer);
						save(tag);
						setChanged();
					}
					if(tileTarget.AirEnergy.getEnergyStored() > 0 && this.AirEnergy.getEnergyStored() < 1000000){
						int transfer = tileTarget.AirEnergy.getEnergyStored();
						tileTarget.AirEnergy.consumeEnergy(transfer);
						this.AirEnergy.addEnergy(transfer);
						save(tag);
						setChanged();
					}
					if(tileTarget.EarthEnergy.getEnergyStored() > 0 && this.EarthEnergy.getEnergyStored() < 1000000){
						int transfer = tileTarget.EarthEnergy.getEnergyStored();
						tileTarget.EarthEnergy.consumeEnergy(transfer);
						this.EarthEnergy.addEnergy(transfer);
						save(tag);
						setChanged();
					}
					if(tileTarget.DarkEnergy.getEnergyStored() > 0 && this.DarkEnergy.getEnergyStored() < 1000000){
						int transfer = tileTarget.DarkEnergy.getEnergyStored();
						tileTarget.DarkEnergy.consumeEnergy(transfer);
						this.DarkEnergy.addEnergy(transfer);
						save(tag);
						setChanged();
					}
					if(tileTarget.LightEnergy.getEnergyStored() > 0 && this.LightEnergy.getEnergyStored() < 1000000){
						int transfer = tileTarget.LightEnergy.getEnergyStored();
						tileTarget.LightEnergy.consumeEnergy(transfer);
						this.LightEnergy.addEnergy(transfer);
						save(tag);
						setChanged();
					}
				}else{
					energyblocks = null;
				}

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
/*
	private void sendOutPower() {
			AtomicInteger capacity = new AtomicInteger(this.FireEnergy.getEnergyStored());
			if (capacity.get() > 0) {
				for (Direction direction : Direction.values()) {
					TileEntity te = world.getTileEntity(pos.offset(direction));
					if (te != null) {
						boolean doContinue = te.getCapability(CapabilityEnergy.ENERGY, direction).map(handler -> {
									if (handler.canReceive()) {
										int received = handler.receiveEnergy(Math.min(capacity.get(), 10), false);
										capacity.addAndGet(-received);
										((CustomEnergyStorage) energy).consumeEnergy(received);
										markDirty();
										return capacity.get() > 0;
									} else {
										return true;
									}
								}
						).orElse(true);
						if (!doContinue) {
							return;
						}
					}
				}
			}
	}
*/
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
	public ITextComponent getDisplayName() {
		return new StringTextComponent(getType().getRegistryName().getPath());
	}

	@Nullable
	@Override
	public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		return new MCMChestContainer(i, playerInventory, this);
	}



	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT tag = new CompoundNBT();
		this.save(tag);

		// the number here is generally ignored for non-vanilla TileEntities, 0 is safest
		return new SUpdateTileEntityPacket(this.getBlockPos(), 0 , tag);
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
}
