package com.cleardragonf.ourmod.tileentity;

import com.cleardragonf.ourmod.MCM.IMCMValueCapability;
import com.cleardragonf.ourmod.MCM.MCMValueCapability;
import com.cleardragonf.ourmod.MCM.MCMValueProvider;
import com.cleardragonf.ourmod.MCM.MCMValues;
import com.cleardragonf.ourmod.container.MCMChestContainer;
import com.cleardragonf.ourmod.essence.CustomEnergyStorage;
import com.cleardragonf.ourmod.init.ModTileEntityTypes;
import com.cleardragonf.ourmod.objects.blocks.EssenceCollector;
import com.cleardragonf.ourmod.objects.blocks.MCMChest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;


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
			MCMChestTileEntity.this.markDirty();
		}
	};


	private int counter;

	public MCMChestTileEntity() {
		super(ModTileEntityTypes.MCM_Chest.get());
	}

	@Override
	public void tick() {

		System.out.println(this.energyblocks);
		write(tag);
		markDirty();
		if (world.isRemote) {
			return;
		}

		if (counter > 0) {
			counter--;

			markDirty();
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
					markDirty();
					if(!inventory.getStackInSlot(i).isEmpty()){

						markDirty();
						Item inputstack = inventory.getStackInSlot(i).getStack().getItem();
						inventory.getStackInSlot(i).getStack().getCapability(MCMValueProvider.MCMValue).ifPresent(a ->{
							this.MCMEnergy.addEnergy(MCMREader(inputstack, a));
						});
						inventory.extractItem(i,1,false);
						markDirty();

					}



				}
				counter = 10;
				markDirty();
		}

		BlockState blockState = world.getBlockState(pos);
		if (blockState.get(BlockStateProperties.POWERED) != counter > 0) {
			world.setBlockState(pos, blockState.with(BlockStateProperties.POWERED, counter > 0), 3);
		}

		//sendOutPower();
	}

	private void executeMCMEnergyConvert() {
		if(this.MCMEnergy.getEnergyStored() < 6000000){
			if(this.FireEnergy.getEnergyStored() > 0){
				int transfer = this.FireEnergy.getEnergyStored();
				this.MCMEnergy.addEnergy(transfer);
				this.FireEnergy.consumeEnergy(transfer);
				write(tag);
				markDirty();
			}
			if(this.WaterEnergy.getEnergyStored() > 0){
				int transfer = this.WaterEnergy.getEnergyStored();
				this.MCMEnergy.addEnergy(transfer);
				this.WaterEnergy.consumeEnergy(transfer);
				write(tag);
				markDirty();
			}
			if(this.AirEnergy.getEnergyStored() > 0){
				int transfer = this.AirEnergy.getEnergyStored();
				this.MCMEnergy.addEnergy(transfer/100);
				this.AirEnergy.consumeEnergy(transfer);
				write(tag);
				markDirty();
			}
			if(this.EarthEnergy.getEnergyStored() > 0){
				int transfer = this.EarthEnergy.getEnergyStored();
				this.MCMEnergy.addEnergy(transfer);
				this.EarthEnergy.consumeEnergy(transfer);
				write(tag);
				markDirty();
			}
			if(this.DarkEnergy.getEnergyStored() > 0){
				int transfer = this.DarkEnergy.getEnergyStored();
				this.MCMEnergy.addEnergy(transfer);
				this.DarkEnergy.consumeEnergy(transfer);
				write(tag);
				markDirty();
			}
			if(this.LightEnergy.getEnergyStored() > 0){
				int transfer = this.LightEnergy.getEnergyStored();
				this.MCMEnergy.addEnergy(transfer);
				this.LightEnergy.consumeEnergy(transfer);
				write(tag);
				markDirty();
			}
		}
	}



	private void executeEnergySearch() {

		try(BlockPos.PooledMutable pooledMutable = BlockPos.PooledMutable.retain()){
			final BlockPos tilePos = this.pos;
			final int posX = tilePos.getX();
			final int posY = tilePos.getY();
			final int posZ = tilePos.getZ();

			for(int z = -5; z<= 10; ++z){
				for(int x = -5; x<=10; ++x){
					for(int y = -5; y <=10; ++y){
						final int dist = (x*x) + (y*y) + (z*z);
						if (dist > 25){
							continue;
						}

						if(dist <1){
							continue;
						}

						pooledMutable.setPos(posX +x, posY + y, posZ + z);
						final BlockState blockState = world.getBlockState(pooledMutable);
						final IFluidState fluidState = world.getFluidState(pooledMutable);
						final Block block = blockState.getBlock();

						if(block instanceof EssenceCollector){
							TileEntity tileEntity = world.getTileEntity(pooledMutable);
							EssenceCollectorTileEntity tileTarget = (EssenceCollectorTileEntity) tileEntity;

							if(tileTarget.FireEnergy.getEnergyStored() > 0 && this.FireEnergy.getEnergyStored() < 1000000){
								int transfer = tileTarget.FireEnergy.getEnergyStored();
								tileTarget.FireEnergy.consumeEnergy(transfer);
								this.FireEnergy.addEnergy(transfer);
								write(tag);
								markDirty();
							}
							if(tileTarget.WaterEnergy.getEnergyStored() > 0 && this.WaterEnergy.getEnergyStored() < 1000000){
								int transfer = tileTarget.WaterEnergy.getEnergyStored();
								tileTarget.WaterEnergy.consumeEnergy(transfer);
								this.WaterEnergy.addEnergy(transfer);
								write(tag);
								markDirty();
							}
							if(tileTarget.AirEnergy.getEnergyStored() > 0 && this.AirEnergy.getEnergyStored() < 1000000){
								int transfer = tileTarget.AirEnergy.getEnergyStored();
								tileTarget.AirEnergy.consumeEnergy(transfer);
								this.AirEnergy.addEnergy(transfer);
								write(tag);
								markDirty();
							}
							if(tileTarget.EarthEnergy.getEnergyStored() > 0 && this.EarthEnergy.getEnergyStored() < 1000000){
								int transfer = tileTarget.EarthEnergy.getEnergyStored();
								tileTarget.EarthEnergy.consumeEnergy(transfer);
								this.EarthEnergy.addEnergy(transfer);
								write(tag);
								markDirty();
							}
							if(tileTarget.DarkEnergy.getEnergyStored() > 0 && this.DarkEnergy.getEnergyStored() < 1000000){
								int transfer = tileTarget.DarkEnergy.getEnergyStored();
								tileTarget.DarkEnergy.consumeEnergy(transfer);
								this.DarkEnergy.addEnergy(transfer);
								write(tag);
								markDirty();
							}
							if(tileTarget.LightEnergy.getEnergyStored() > 0 && this.LightEnergy.getEnergyStored() < 1000000){
								int transfer = tileTarget.LightEnergy.getEnergyStored();
								tileTarget.LightEnergy.consumeEnergy(transfer);
								this.LightEnergy.addEnergy(transfer);
								write(tag);
								markDirty();
							}
						}
					}
				}
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
	public void read(CompoundNBT tag) {
		CompoundNBT invTag = tag.getCompound("inv");
		handler.ifPresent(h -> ((INBTSerializable<CompoundNBT>) inventory).deserializeNBT(invTag));
		super.read(tag);
		readRestorableNBT(tag);
	}

	@Override
	public CompoundNBT write(CompoundNBT tag) {
		super.write(tag);
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
		this.write(tag);

		// the number here is generally ignored for non-vanilla TileEntities, 0 is safest
		return new SUpdateTileEntityPacket(this.getPos(), 0 , tag);
	}

	@Override
	public CompoundNBT getUpdateTag() {
		CompoundNBT tag = new CompoundNBT();
		write(tag);
		return tag;
	}

	@Override
	public void handleUpdateTag(CompoundNBT tag) {
		this.read(tag);
	}

	// this method gets called on the client when it receives the packet that was sent in the previous method
	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		this.read(pkt.getNbtCompound());
	}

	public void readRestorableNBT(CompoundNBT tag){
		this.energyblocks = tag.get("energypos");
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
