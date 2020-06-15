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

	public LazyOptional<IItemHandler> handler = LazyOptional.of(this::createHandler);
	//public LazyOptional<IEnergyStorage> energy = LazyOptional.of(this::createEnergy);
	public final CustomEnergyStorage FireEnergy = new CustomEnergyStorage(1000000,0);

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
		if (world.isRemote) {
			return;
		}

		if (counter > 0) {
			counter--;

			markDirty();
		}

		if (counter <= 0) {
			executeEnergySearch();
			ItemStack stack = new ItemStack(inventory.getStackInSlot(0).getStack().getItem());
			Item mcmValueItem = stack.getItem();
				//Takes the Clone slot and Existing MCM value and Begins Duplicating the Item.
					inventory.getStackInSlot(0).getStack().getCapability(MCMValueProvider.MCMValue).ifPresent(a -> {
							if(this.FireEnergy.getEnergyStored() >= MCMREader(mcmValueItem, a)){
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

								this.FireEnergy.consumeEnergy(MCMREader(mcmValueItem, a));
							}


					});


				//Removes inputs and Adds MCM Value to the Chest
				for (int i = 1; i < 5; i++) {
					markDirty();
					if(!inventory.getStackInSlot(i).isEmpty()){

						markDirty();
						Item inputstack = inventory.getStackInSlot(i).getStack().getItem();
						inventory.getStackInSlot(i).getStack().getCapability(MCMValueProvider.MCMValue).ifPresent(a ->{
							this.FireEnergy.addEnergy(MCMREader(inputstack, a));
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

							if(tileTarget.FireEnergy.getEnergyStored() > 0 && this.FireEnergy.getEnergyStored() < 100000){

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
		tag.putInt("fireenergy", this.FireEnergy.getEnergyStored());
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
		this.inventory.deserializeNBT(tag.getCompound("inv"));
		this.FireEnergy.setEnergy(tag.getInt("fireenergy"));
	}
}
