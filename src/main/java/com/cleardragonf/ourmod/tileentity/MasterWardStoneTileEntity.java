package com.cleardragonf.ourmod.tileentity;

import com.cleardragonf.ourmod.Data.Wards;
import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.container.FishingNetContainer;
import com.cleardragonf.ourmod.container.MasterWardStoneContainer;
import com.cleardragonf.ourmod.entity.EntityEffects;
import com.cleardragonf.ourmod.essence.CustomEnergyStorage;
import com.cleardragonf.ourmod.init.BlockInitNew;
import com.cleardragonf.ourmod.init.ItemInitNew;
import com.cleardragonf.ourmod.init.ModTileEntityTypes;
import com.cleardragonf.ourmod.objects.blocks.BoundaryWardStoneBlock;
import com.cleardragonf.ourmod.objects.blocks.MasterWardStoneBlock;
import com.cleardragonf.ourmod.objects.items.wards.HealingWardTablet;
import com.cleardragonf.ourmod.objects.items.wards.HungerWardTablet;
import com.cleardragonf.ourmod.objects.items.wards.TemperatureWardTablet;
import com.cleardragonf.ourmod.objects.items.wards.ThirstWardTablet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class MasterWardStoneTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

	public INBT wardStone;
	public ListNBT boundaryWardStones = new ListNBT();
	public List<Wards> activeWardList;
	public int wardHeight = 5;
	private ListNBT wards = new ListNBT();
    public INBT energyblocks;

    public LazyOptional<IItemHandler> handler = LazyOptional.of(this::createHandler);

	public void addWardStone(INBT target){
		boundaryWardStones.add(target);
	}

	protected int numPlayersUsing;
	private boolean initialized = false;
	private CompoundNBT tag = new CompoundNBT();
	public int tick, y;
	public boolean healTeam = false;
	public boolean antiThirst = false;
	public boolean antiHunger = false;
	public int healLevel = 0;
	public int antiThirstLevel = 0;
	public int antiHungerLevel = 0;
    public int entitiesThatRequireEnergy = 0;
    public int blocksNeedingEnergy = 0;

    public boolean enoughFire = true;
    public boolean enoughWater = true;
    public boolean enoughEarth = true;
    public boolean enoughAir = true;
    public boolean enoughLight = true;
    public boolean enoughDark = true;


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

	//Inventory Portion of MasterWardStone
	public final ItemStackHandler inventory = new ItemStackHandler(5){

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
			MasterWardStoneTileEntity.this.markDirty();
		}
	};

    private IItemHandler createHandler() {
        return inventory;
    }


	/*
	public CompoundNBT serializeWard(){
		ListNBT nbtTagList = new ListNBT();
		for (int i = 0; i < stacks.size(); i++)
		{
			if (!stacks.get(i).isEmpty())
			{
				CompoundNBT itemTag = new CompoundNBT();
				itemTag.putInt("Slot", i);
				stacks.get(i).write(itemTag);
				nbtTagList.add(itemTag);
			}
		}
		CompoundNBT nbt = new CompoundNBT();
		nbt.put("Items", nbtTagList);
		nbt.putInt("Size", stacks.size());
		return nbt;
	}

	 */

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
                executeEnergyUsage();
				execute();
		}


	}

	//TODO: beginning work on Energy Finding, Consumtion and Requirements.
    private void executeEnergyUsage() {
		if(energyblocks != null){
			CompoundNBT tagger = (CompoundNBT) energyblocks;

			BlockPos pos = new BlockPos(tagger.getInt("x"),tagger.getInt("y"),tagger.getInt("z"));
			TileEntity tileEntity = world.getTileEntity(pos);
			if(tileEntity instanceof EssenceCollectorTileEntity){
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
			}else{
				energyblocks = null;
			}

		}
    }

    private void execute() {
		List<BlockPos> boundaryList = new LinkedList<>();
		boolean needsSave = false;
		if(boundaryWardStones != null){
			TileEntity tileEntity = world.getTileEntity(pos);
			int size = boundaryWardStones.size();
			for (int i = 0; i < size; i++) {
				CompoundNBT tagger = (CompoundNBT)boundaryWardStones.get(i);
				BlockPos pos = new BlockPos(tagger.getInt("x"), tagger.getInt("y"), tagger.getInt("z"));
				boundaryList.add(pos);
			}
				if(boundaryWardStones.size() == 4){

					//4 * pie * r * r
					boolean stonePlacementAccurate = false;
					//sqrt((x1-x2)^2 + (y1-y2) ^2+( z1 - z1)^2)
					int x1 = tileEntity.getPos().getX();
					int y1 = tileEntity.getPos().getY();
					int z1 = tileEntity.getPos().getZ();
					//
					int radius1 = (int) Math.sqrt(Math.pow(x1 - boundaryList.get(0).getX() , 2) + Math.pow(y1 - boundaryList.get(0).getY() , 2) + Math.pow(z1 - boundaryList.get(0).getZ() , 2));

					int radius2 = (int) Math.sqrt(Math.pow(x1 - boundaryList.get(1).getX(),2) + Math.pow(y1 - boundaryList.get(1).getY(),2)  + Math.pow(z1 - boundaryList.get(1).getZ(),2));

					int radius3 = (int) Math.sqrt(Math.pow(x1 - boundaryList.get(2).getX(),2) + Math.pow(y1 - boundaryList.get(2).getY(),2)  + Math.pow(z1 - boundaryList.get(2).getZ(),2));

					int radius4 = (int) Math.sqrt(Math.pow(x1 - boundaryList.get(3).getX(),2) + Math.pow(y1 - boundaryList.get(3).getY(),2)  + Math.pow(z1 - boundaryList.get(3).getZ(),2));

					if(radius1 == radius2 && radius1 == radius3 && radius1 == radius4) {

						if(world.getTileEntity(boundaryList.get(0)) instanceof BoundaryWardStoneTileEntity && world.getTileEntity(boundaryList.get(1)) instanceof BoundaryWardStoneTileEntity
								&&world.getTileEntity(boundaryList.get(2)) instanceof BoundaryWardStoneTileEntity&&world.getTileEntity(boundaryList.get(3)) instanceof BoundaryWardStoneTileEntity) {
							stonePlacementAccurate = true;

						}else{
							stonePlacementAccurate = false;
						}
					}
					//TODO: start adding a list view here that'll cycle through all wards attatched to this TE
					if(stonePlacementAccurate == true){
						//double d0 = (double)(1 * 10 + 10);

						AxisAlignedBB axisalignedbb = (new AxisAlignedBB(this.pos)).grow(radius1).expand(0.0D, 0.0D, 0.0D);
						List<PlayerEntity> list = this.world.getEntitiesWithinAABB(PlayerEntity.class, axisalignedbb);
						if(this.AirEnergy.getEnergyStored() >= (10*list.size())){
							for(PlayerEntity playerentity : list) {
									handler.ifPresent(e ->{
										for (int i = 0; i < 5; i++) {
											ItemStack item = e.getStackInSlot(i);
											int level = e.getStackInSlot(i).getCount();
											switch (item.getItem().getClass().getName()){
												case "HungerWardTablet":
													checkWardRequirements("Hunger", level, playerentity);
													break;
												case "ThirstWardTablet":
													checkWardRequirements("Thirst", level, playerentity);
													break;
												case "HealingWardTablet":
													checkWardRequirements("Healing", level, playerentity);
													break;
												case "TemperatureWardTablet":
													checkWardRequirements("Temperature", level, playerentity);
													break;
												default:
													checkWardRequirements("None", level, playerentity);
													break;
											}
										}
									});
									/*
									if(this.chestContents.contains(ItemInitNew.WARD_STONES_HUNGER)){
										playerentity.addPotionEffect(new EffectInstance(EntityEffects.HUNGER_WARD, 20, 1, true, true));
										EarthEnergy.consumeEnergy(10);
										WaterEnergy.consumeEnergy(10);
									}

									 */
								AirEnergy.consumeEnergy(10);
							}
						}else{
							System.out.println("Not activiating Effect...because Air Storage only at " + AirEnergy.getEnergyStored());
						}

						//set with a border right now it works for replacing air with glass
						int wardBarrier = 0;
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
											if(EarthEnergy.getEnergyStored() >= 6){
												world.setBlockState(pooledMutable, BlockInitNew.WARDBARRIER.get().getDefaultState(), 2);
												EarthEnergy.consumeEnergy(6);
											}
										}
										if(block == BlockInitNew.WARDBARRIER.get()){
											if(EarthEnergy.getEnergyStored() >= 1){
												EarthEnergy.consumeEnergy(1);
											}else{
												world.setBlockState(pooledMutable,Blocks.AIR.getDefaultState(), 1);
											}
										}

									}
								}
							}
							markDirty();
						}

					}


				else{

				}
			}
		}else{

			}

		if(needsSave){
			this.markDirty();
		}
	}

	private void checkWardRequirements(String ward, int level, PlayerEntity player) {
		int waterReq = 0;
		int fireReq = 0;
		int earthReq = 0;
		int airReq = 0;
		int lightReq = 0;
		int darkReq = 0;

		switch (ward){
			case "Hunger":
				earthReq = 10;
				waterReq = 10;
				if(EarthEnergy.getEnergyStored() >= (earthReq * level) && WaterEnergy.getEnergyStored() >= (waterReq * level)){
					player.addPotionEffect(new EffectInstance(EntityEffects.HUNGER_WARD, 30, 1,true, true));
				}
				break;
			case "Healing":
				earthReq = 10;
				fireReq = 10;
				lightReq = 10;
				if(EarthEnergy.getEnergyStored() >= (earthReq * level) && LightEnergy.getEnergyStored() >= (lightReq * level) && FireEnergy.getEnergyStored() >= (fireReq * level)){
					player.addPotionEffect(new EffectInstance(EntityEffects.HEALING_WARD, 30, 1,true, true));
					FireEnergy.consumeEnergy(fireReq * level);
					EarthEnergy.consumeEnergy(earthReq * level);
					LightEnergy.consumeEnergy(lightReq * level);

				}
				break;
			case "Thirst":
				waterReq = 20;
				if(WaterEnergy.getEnergyStored() >= (waterReq * level)){
					player.addPotionEffect(new EffectInstance(EntityEffects.THIRST_WARD, 30, 1,true, true));
					WaterEnergy.consumeEnergy(waterReq * level);
				}
				break;
			case "Temperature":
				fireReq = 10;
				lightReq = 10;
				earthReq = 10;
				if(EarthEnergy.getEnergyStored() >= (earthReq * level) && LightEnergy.getEnergyStored() >= (lightReq * level) && FireEnergy.getEnergyStored() >= (fireReq * level)){
					player.addPotionEffect(new EffectInstance(EntityEffects.TEMPERATURE_WARD, 30, 1,true, true));
					FireEnergy.consumeEnergy(fireReq * level);
					LightEnergy.consumeEnergy(lightReq * level);
					EarthEnergy.consumeEnergy(earthReq * level);
				}
				break;

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
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        //TODO:: add a write method
        handler.ifPresent(h -> {
            CompoundNBT tag2 = ((INBTSerializable<CompoundNBT>) inventory).serializeNBT();
            compound.put("inv", tag2);
        });

        compound.put("inv", inventory.serializeNBT());
        CompoundNBT wards = new CompoundNBT();
        CompoundNBT healtag = new CompoundNBT();
        tag.putBoolean("status", healTeam);
        tag.putInt("level", healLevel);
        CompoundNBT antiHungerTag = new CompoundNBT();
        antiHungerTag.putBoolean("status", antiHunger);
        antiHungerTag.putInt("level",antiHungerLevel);
        CompoundNBT antiThirstTag = new CompoundNBT();
        antiThirstTag.putBoolean("status", antiThirst);
        antiThirstTag.putInt("level", antiThirstLevel);
        wards.put("healteam",healtag);
        wards.put("antihunger",antiHungerTag);
        wards.put("antithirst",antiThirstTag);
        compound.put("wards", wards);
        compound.put("wardshape", this.boundaryWardStones);
        if (energyblocks != null){

            tag.put("energypos", this.energyblocks);
        }
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
	public void handleUpdateTag(CompoundNBT tag) {
		this.read(tag);
	}


	public void readRestorableNBT(CompoundNBT tag) {
		//TODO::Add a inv read
        CompoundNBT invTag = tag.getCompound("inv");
        handler.ifPresent(h -> ((INBTSerializable<CompoundNBT>) inventory).deserializeNBT(invTag));
		energyblocks = tag.get("energypos");
		CompoundNBT wards = (CompoundNBT) tag.getCompound("wards");
			CompoundNBT antiThirstTag = (CompoundNBT) wards.getCompound("antithirst");
				this.antiThirst = antiThirstTag.getBoolean("status");
				this.antiThirstLevel = antiThirstTag.getInt("level");
			CompoundNBT antiHungerTag = (CompoundNBT) wards.getCompound("antihunger");
				this.antiHunger = antiHungerTag.getBoolean("status");
				this.antiHungerLevel = antiHungerTag.getInt("level");
			CompoundNBT healTeamTag = (CompoundNBT) wards.getCompound("healteam");
				this.healTeam = healTeamTag.getBoolean("status");
				this.healLevel = healTeamTag.getInt("level");
		this.boundaryWardStones = tag.getList("wardshape", Constants.NBT.TAG_COMPOUND);
		this.FireEnergy.setEnergy(tag.getInt("fireenergy"));
		this.WaterEnergy.setEnergy(tag.getInt("waterenergy"));
		this.AirEnergy.setEnergy(tag.getInt("airenergy"));
		this.EarthEnergy.setEnergy(tag.getInt("earthenergy"));
		this.DarkEnergy.setEnergy(tag.getInt("darkenergy"));
		this.LightEnergy.setEnergy(tag.getInt("lightenergy"));
	}

	public void updateBlock(){
		world.notifyBlockUpdate(pos, this.getBlockState(), this.getBlockState(), 1);
	}
}
