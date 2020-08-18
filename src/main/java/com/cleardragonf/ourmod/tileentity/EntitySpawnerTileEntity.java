package com.cleardragonf.ourmod.tileentity;

import com.cleardragonf.ourmod.MCM.IMCMValueCapability;
import com.cleardragonf.ourmod.MCM.MCMValueProvider;
import com.cleardragonf.ourmod.MCM.MCMValues;
import com.cleardragonf.ourmod.container.EntitySpawnerContainer;
import com.cleardragonf.ourmod.container.MCMChestContainer;
import com.cleardragonf.ourmod.essence.CustomEnergyStorage;
import com.cleardragonf.ourmod.init.ModTileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
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


public class EntitySpawnerTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

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
			EntitySpawnerTileEntity.this.markDirty();
		}
	};


	private int counter;

	public EntitySpawnerTileEntity() {
		super(ModTileEntityTypes.ENTITY_SPAWNER.get());
	}

	public void updateBlock(){
		world.notifyBlockUpdate(pos, this.getBlockState(), this.getBlockState(), 1);
	}

	@Override
	public void tick() {
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
			for (int i = 0; i < 4; i++) {
				if(inventory.getStackInSlot(i).isEmpty()){

				}else{

					if(this.MCMEnergy.getEnergyStored() > 50){
						if(inventory.getStackInSlot(i).getItem().equals(Items.ZOMBIE_SPAWN_EGG)){

						}
					}
				}
			}
			markDirty();
		}

		BlockState blockState = world.getBlockState(pos);
		if (blockState.get(BlockStateProperties.POWERED) != counter > 0) {
			world.setBlockState(pos, blockState.with(BlockStateProperties.POWERED, counter > 0), 3);
		}
	}

	private void executeMCMEnergyConvert() {
		if(this.MCMEnergy.getEnergyStored() < 6000000){
			if(this.FireEnergy.getEnergyStored() > 0){
				int transfer = this.FireEnergy.getEnergyStored();
				this.MCMEnergy.addEnergy(transfer/1000);
				this.FireEnergy.consumeEnergy(transfer);
				write(tag);
				markDirty();
			}
			if(this.WaterEnergy.getEnergyStored() > 0){
				int transfer = this.WaterEnergy.getEnergyStored();
				this.MCMEnergy.addEnergy(transfer/1000);
				this.WaterEnergy.consumeEnergy(transfer);
				write(tag);
				markDirty();
			}
			if(this.AirEnergy.getEnergyStored() > 0){
				int transfer = this.AirEnergy.getEnergyStored();
				this.MCMEnergy.addEnergy(transfer/1000);
				this.AirEnergy.consumeEnergy(transfer);
				write(tag);
				markDirty();
			}
			if(this.EarthEnergy.getEnergyStored() > 0){
				int transfer = this.EarthEnergy.getEnergyStored();
				this.MCMEnergy.addEnergy(transfer/1000);
				this.EarthEnergy.consumeEnergy(transfer);
				write(tag);
				markDirty();
			}
			if(this.DarkEnergy.getEnergyStored() > 0){
				int transfer = this.DarkEnergy.getEnergyStored();
				this.MCMEnergy.addEnergy(transfer/1000);
				this.DarkEnergy.consumeEnergy(transfer);
				write(tag);
				markDirty();
			}
			if(this.LightEnergy.getEnergyStored() > 0){
				int transfer = this.LightEnergy.getEnergyStored();
				this.MCMEnergy.addEnergy(transfer/1000);
				this.LightEnergy.consumeEnergy(transfer);
				write(tag);
				markDirty();
			}
		}
	}



	private void executeEnergySearch() {
		if(energyblocks!= null){

			CompoundNBT tagger = (CompoundNBT) energyblocks;

				BlockPos pos = new BlockPos(tagger.getInt("x"),tagger.getInt("y"),tagger.getInt("z"));
				TileEntity tileEntity = world.getTileEntity(pos);
				if(tileEntity instanceof EssenceCollectorTileEntity){
					EssenceCollectorTileEntity tileTarget = (EssenceCollectorTileEntity) tileEntity;


					if(tileTarget.FireEnergy.getEnergyStored() > 100 && this.FireEnergy.getEnergyStored() < 1000000){
						int transfer = 100;
						tileTarget.FireEnergy.consumeEnergy(transfer);
						this.FireEnergy.addEnergy(transfer);
						write(tag);
						markDirty();
					}
					if(tileTarget.WaterEnergy.getEnergyStored() > 100 && this.WaterEnergy.getEnergyStored() < 1000000){
						int transfer = 100;
						tileTarget.WaterEnergy.consumeEnergy(transfer);
						this.WaterEnergy.addEnergy(transfer);
						write(tag);
						markDirty();
					}
					if(tileTarget.AirEnergy.getEnergyStored() > 100 && this.AirEnergy.getEnergyStored() < 1000000){
						int transfer = 100;
						tileTarget.AirEnergy.consumeEnergy(transfer);
						this.AirEnergy.addEnergy(transfer);
						write(tag);
						markDirty();
					}
					if(tileTarget.EarthEnergy.getEnergyStored() > 100 && this.EarthEnergy.getEnergyStored() < 1000000){
						int transfer = 100;
						tileTarget.EarthEnergy.consumeEnergy(transfer);
						this.EarthEnergy.addEnergy(transfer);
						write(tag);
						markDirty();
					}
					if(tileTarget.DarkEnergy.getEnergyStored() > 100 && this.DarkEnergy.getEnergyStored() < 1000000){
						int transfer = 100;
						tileTarget.DarkEnergy.consumeEnergy(transfer);
						this.DarkEnergy.addEnergy(transfer);
						write(tag);
						markDirty();
					}
					if(tileTarget.LightEnergy.getEnergyStored() > 100 && this.LightEnergy.getEnergyStored() < 1000000){
						int transfer = 100;
						tileTarget.LightEnergy.consumeEnergy(transfer);
						this.LightEnergy.addEnergy(transfer);
						write(tag);
						markDirty();
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
		return new EntitySpawnerContainer(i, playerInventory, this);
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
