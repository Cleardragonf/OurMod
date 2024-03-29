package com.cleardragonf.ourmod.tileentity;

import com.cleardragonf.ourmod.Data.Wards;
import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.container.FishingNetContainer;
import com.cleardragonf.ourmod.container.MasterWardStoneContainer;
import com.cleardragonf.ourmod.entity.EntityEffects;
import com.cleardragonf.ourmod.essence.CustomEnergyStorage;
import com.cleardragonf.ourmod.init.BlockInitNew;
import com.cleardragonf.ourmod.init.ModTileEntityTypes;
import com.cleardragonf.ourmod.objects.blocks.MasterWardStoneBlock;
import com.cleardragonf.ourmod.objects.blocks.WardInside;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.potion.EffectInstance;
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
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;


public class MasterWardStoneTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

	public ListNBT boundaryWardStones = new ListNBT();
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
			MasterWardStoneTileEntity.this.setChanged();
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

		return new StringTextComponent("Master Ward Stone");

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
		if (block instanceof MasterWardStoneBlock) {
			this.level.blockEvent(this.worldPosition, block, 1, this.numPlayersUsing);
			this.level.updateNeighborsAt(this.worldPosition, block);
		}
	}

	public static int getPlayersUsing(IBlockReader reader, BlockPos pos) {
		BlockState blockstate = reader.getBlockState(pos);
		if (blockstate.hasTileEntity()) {
			TileEntity tileentity = reader.getBlockEntity(pos);
			if (tileentity instanceof MasterWardStoneTileEntity) {
				return ((MasterWardStoneTileEntity) tileentity).numPlayersUsing;
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
                executeEnergyUsage();
				execute();
		}


	}

	//TODO: beginning work on Energy Finding, Consumtion and Requirements.
    private void executeEnergyUsage() {
		if(energyblocks != null){
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
	public static List<BlockPos> wardLoc = new LinkedList<>();

    public void execute() {
		List<BlockPos> boundaryList = new LinkedList<>();

		boolean needsSave = false;
		if(boundaryWardStones != null){
			TileEntity tileEntity = level.getBlockEntity(worldPosition);
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
					int x1 = tileEntity.getBlockPos().getX();
					int y1 = tileEntity.getBlockPos().getY();
					int z1 = tileEntity.getBlockPos().getZ();
					//
					int radius1 = (int) Math.sqrt(Math.pow(x1 - boundaryList.get(0).getX() , 2) + Math.pow(y1 - boundaryList.get(0).getY() , 2) + Math.pow(z1 - boundaryList.get(0).getZ() , 2));

					int radius2 = (int) Math.sqrt(Math.pow(x1 - boundaryList.get(1).getX(),2) + Math.pow(y1 - boundaryList.get(1).getY(),2)  + Math.pow(z1 - boundaryList.get(1).getZ(),2));

					int radius3 = (int) Math.sqrt(Math.pow(x1 - boundaryList.get(2).getX(),2) + Math.pow(y1 - boundaryList.get(2).getY(),2)  + Math.pow(z1 - boundaryList.get(2).getZ(),2));

					int radius4 = (int) Math.sqrt(Math.pow(x1 - boundaryList.get(3).getX(),2) + Math.pow(y1 - boundaryList.get(3).getY(),2)  + Math.pow(z1 - boundaryList.get(3).getZ(),2));

					if(radius1 == radius2 && radius1 == radius3 && radius1 == radius4) {

						if(level.getBlockEntity(boundaryList.get(0)) instanceof BoundaryWardStoneTileEntity && level.getBlockEntity(boundaryList.get(1)) instanceof BoundaryWardStoneTileEntity
								&&level.getBlockEntity(boundaryList.get(2)) instanceof BoundaryWardStoneTileEntity&&level.getBlockEntity(boundaryList.get(3)) instanceof BoundaryWardStoneTileEntity) {
							stonePlacementAccurate = true;

						}else{
							stonePlacementAccurate = false;
						}
					}
					//TODO: start adding a list view here that'll cycle through all wards attatched to this TE
					if(stonePlacementAccurate == true){
						//double d0 = (double)(1 * 10 + 10);

						AxisAlignedBB axisalignedbb = (new AxisAlignedBB(this.worldPosition)).inflate(radius1).expandTowards(0.0D, 0.0D, 0.0D);
						List<PlayerEntity> list = this.level.getEntitiesOfClass(PlayerEntity.class, axisalignedbb);
						List<MonsterEntity> monsterList = this.level.getEntitiesOfClass(MonsterEntity.class, axisalignedbb);
						List<AnimalEntity> animalList = this.level.getEntitiesOfClass(AnimalEntity.class, axisalignedbb);
						if(this.AirEnergy.getEnergyStored() >= (10*list.size())){
							for(PlayerEntity playerentity : list) {
									handler.ifPresent(e ->{
										for (int i = 0; i < 5; i++) {
											ItemStack item = e.getStackInSlot(i);
											int level = e.getStackInSlot(i).getCount();
											switch (item.getItem().getClass().getName()){
												case "com.cleardragonf.ourmod.objects.items.wards.HungerWardTablet":
													checkWardRequirements("Hunger", level, playerentity, radius1);
													System.out.println(level + " " + "Hunger");
													break;
												case "com.cleardragonf.ourmod.objects.items.wards.ThirstWardTablet":
													checkWardRequirements("Thirst", level, playerentity, radius1);
													break;
												case "com.cleardragonf.ourmod.objects.items.wards.HealingWardTablet":
													checkWardRequirements("Healing", level, playerentity, radius1);
													break;
												case "com.cleardragonf.ourmod.objects.items.wards.TemperatureWardTablet":
													checkWardRequirements("Temperature", level, playerentity, radius1);
													break;
												case "com.cleardragonf.ourmod.objects.items.wards.AntiGravityWard":
													checkWardRequirements("Anti-Ward", level, playerentity, radius1);
													break;

												case "com.cleardragonf.ourmod.objects.items.wards.DaytimeWard":
													checkWardRequirements("Daytime", level, playerentity,radius1);
													break;
												default:
													checkWardRequirements("None", level, playerentity, radius1);
													System.out.println(item.getItem().getClass().getName());
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
							for(MonsterEntity monster : monsterList){
								handler.ifPresent(e ->{
									for (int i = 0; i < 5; i++) {
										ItemStack item = e.getStackInSlot(i);
										int level = e.getStackInSlot(i).getCount();
										switch (item.getItem().getClass().getName()){
											case "com.cleardragonf.ourmod.objects.items.wards.AntiHostileWard":
												checkMonsterWardRequirements("Anti-Hostile", level, monster);
												break;
											default:
												checkMonsterWardRequirements("None", level, monster);
												System.out.println(item.getItem().getClass().getName());
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
							for(AnimalEntity animal : animalList){
								handler.ifPresent(e ->{
									for (int i = 0; i < 5; i++) {
										ItemStack item = e.getStackInSlot(i);
										int level = e.getStackInSlot(i).getCount();
										switch (item.getItem().getClass().getName()){
											case "com.cleardragonf.ourmod.objects.items.wards.AntiPassiveWard":
												checkAnimalWardRequirements("Anti-Passive", level, animal);
												break;
											default:
												checkAnimalWardRequirements("None", level, animal);
												System.out.println(item.getItem().getClass().getName());
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
						Iterable<BlockPos> mutable = BlockPos.betweenClosed(-50,-50,-50,50,50,50);

						for (BlockPos block : mutable) {
							final int dist = (block.getX()*block.getX()) + (block.getY()*block.getY()) + (block.getZ() * block.getZ());
							if(dist>25){
								continue;
							}
							if(dist<1){
								continue;
							}
							final BlockState blockState = level.getBlockState(block);
							final FluidState fluidState = level.getFluidState(block);
							final Block targetblock = blockState.getBlock();
							if(targetblock == Blocks.AIR){

								if(EarthEnergy.getEnergyStored() >= 6){
									level.setBlock(block, BlockInitNew.WARDBARRIER.get().defaultBlockState(), 2);
									EarthEnergy.consumeEnergy(6);
								}
							}
							if(targetblock == BlockInitNew.WARDBARRIER.get()){
								wardLoc.add(block);
							}

						}setChanged();
					}
				else{

				}
			}
		}else{

			}

		if(needsSave){
			this.setChanged();
		}
	}

	private void checkAnimalWardRequirements(String ward, int level, AnimalEntity animal) {
		int waterReq = 0;
		int fireReq = 0;
		int earthReq = 0;
		int airReq = 0;
		int lightReq = 0;
		int darkReq = 0;

		switch (ward){
			case "Anti-Passive":
				fireReq = 10;
				lightReq = 10;
				if(LightEnergy.getEnergyStored() >= (lightReq * level) && FireEnergy.getEnergyStored() >= (fireReq * level)){
					animal.addEffect(new EffectInstance(EntityEffects.ANTI_PASSIVE_WARD, 5000, 1,true, true));
					FireEnergy.consumeEnergy(fireReq * level);
					LightEnergy.consumeEnergy(darkReq * level);
				}
				break;
		}
	}

	private void checkMonsterWardRequirements(String ward, int level, MonsterEntity monster) {
		int waterReq = 0;
		int fireReq = 0;
		int earthReq = 0;
		int airReq = 0;
		int lightReq = 0;
		int darkReq = 0;

		switch (ward){
			case "Anti-Hostile":
				fireReq = 10;
				darkReq = 10;
				if(DarkEnergy.getEnergyStored() >= (darkReq * level) && FireEnergy.getEnergyStored() >= (fireReq * level)){
					monster.addEffect(new EffectInstance(EntityEffects.ANTI_HOSTILE_WARD, 5000, 1,true, true));
					FireEnergy.consumeEnergy(fireReq * level);
					DarkEnergy.consumeEnergy(darkReq * level);
				}
				break;
		}
	}


	public void rescendWard() {
		List<BlockPos> boundaryList = new LinkedList<>();
		List<BlockPos> listToDelete = new LinkedList<>();

		boolean needsSave = false;
		if(boundaryWardStones != null){
			TileEntity tileEntity = level.getBlockEntity(worldPosition);
			int size = boundaryWardStones.size();
			for (int i = 0; i < size; i++) {
				CompoundNBT tagger = (CompoundNBT)boundaryWardStones.get(i);
				BlockPos pos = new BlockPos(tagger.getInt("x"), tagger.getInt("y"), tagger.getInt("z"));
				boundaryList.add(pos);
			}
			if(boundaryWardStones.size() > 0){

				//4 * pie * r * r
				boolean stonePlacementAccurate = true;
				//sqrt((x1-x2)^2 + (y1-y2) ^2+( z1 - z1)^2)
				int x1 = tileEntity.getBlockPos().getX();
				int y1 = tileEntity.getBlockPos().getY();
				int z1 = tileEntity.getBlockPos().getZ();
				//
				int radius1 = (int) Math.sqrt(Math.pow(x1 - boundaryList.get(0).getX() , 2) + Math.pow(y1 - boundaryList.get(0).getY() , 2) + Math.pow(z1 - boundaryList.get(0).getZ() , 2));

				//TODO: start adding a list view here that'll cycle through all wards attatched to this TE
				if(stonePlacementAccurate == true){
					int wardBarrier = 0;
					//TODO: set to WardBarrier

					Iterable<BlockPos> mutable = BlockPos.betweenClosed(worldPosition.getX()-5,worldPosition.getY()-5,worldPosition.getZ()-5,worldPosition.getX()+5,worldPosition.getY()+5,worldPosition.getZ()+5);

					for (BlockPos block : mutable) {
						final int dist = (block.getX()*block.getX()) + (block.getY()*block.getY()) + (block.getZ() * block.getZ());
						if(dist>25){
							continue;
						}
						if(dist<1){
							continue;
						}
						final BlockState blockState = level.getBlockState(block);
						final FluidState fluidState = level.getFluidState(block);
						final Block targetblock = blockState.getBlock();


						if(targetblock == Blocks.AIR){

						}
						else if(targetblock == BlockInitNew.WARDBARRIER.get()){
							level.setBlock(block, Blocks.AIR.defaultBlockState(), 3);
						}else{
							System.out.println(block);
						}
						setChanged();
					}
				}


				else{

				}
			}
		}else{

		}

		if(needsSave){
			this.setChanged();
		}
	}

	private void

	checkWardRequirements(String ward, int level, PlayerEntity player, int radius1) {
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
				System.out.println("made it to this point");
				if(EarthEnergy.getEnergyStored() >= (earthReq * level) && WaterEnergy.getEnergyStored() >= (waterReq * level)){
					player.addEffect(new EffectInstance(EntityEffects.HUNGER_WARD, 30, level,true, true));
					EarthEnergy.consumeEnergy(earthReq * level);
					WaterEnergy.consumeEnergy(waterReq * level);
				}
				break;
			case "Healing":
				earthReq = 10;
				fireReq = 10;
				lightReq = 10;
				if(EarthEnergy.getEnergyStored() >= (earthReq * level) && LightEnergy.getEnergyStored() >= (lightReq * level) && FireEnergy.getEnergyStored() >= (fireReq * level)){
					player.addEffect(new EffectInstance(EntityEffects.HEALING_WARD, 30, level,true, true));
					FireEnergy.consumeEnergy(fireReq * level);
					EarthEnergy.consumeEnergy(earthReq * level);
					LightEnergy.consumeEnergy(lightReq * level);

				}
				break;
			case "Thirst":
				waterReq = 20;
				if(WaterEnergy.getEnergyStored() >= (waterReq * level)){
					player.addEffect(new EffectInstance(EntityEffects.THIRST_WARD, 30, 1,true, true));
					WaterEnergy.consumeEnergy(waterReq * level);
				}
				break;
			case "Temperature":
				fireReq = 10;
				waterReq = 10;
				if(WaterEnergy.getEnergyStored() >= (waterReq * level) && FireEnergy.getEnergyStored() >= (fireReq * level)){
					player.addEffect(new EffectInstance(EntityEffects.TEMPERATURE_WARD, 30, 1,true, true));
					FireEnergy.consumeEnergy(fireReq * level);
					WaterEnergy.consumeEnergy(waterReq * level);
				}
				break;
			case "Anti-Passive":
				fireReq = 10;
				lightReq = 10;
				if(LightEnergy.getEnergyStored() >= (lightReq * level) && FireEnergy.getEnergyStored() >= (fireReq * level)){
					player.addEffect(new EffectInstance(EntityEffects.ANTI_PASSIVE_WARD, 30, 1,true, true));
					FireEnergy.consumeEnergy(fireReq * level);
					LightEnergy.consumeEnergy(lightReq * level);
				}
				break;
			case "Anti-Gravity":
				airReq = 30;
				if(AirEnergy.getEnergyStored() >= (airReq * level)){
					player.addEffect(new EffectInstance(EntityEffects.ANTI_GRAVITY_WARD, 30, 1,true, true));
					AirEnergy.consumeEnergy(airReq * level);
				}
				break;
			case "Daytime":
				lightReq = 20;
				if(level > 14){
					level = 14;
				}
				if(LightEnergy.getEnergyStored() >= (lightReq * level)){
					Iterable<BlockPos> mutable = BlockPos.betweenClosed(-5,-5,-5,5,5,5);

					for (BlockPos block : mutable) {
						final int dist = (block.getX()*block.getX()) + (block.getY()*block.getY()) + (block.getZ() * block.getZ());
						if(dist>25){
							continue;
						}
						if(dist<1){
							continue;
						}
						final BlockState blockState = this.level.getBlockState(block);
						final FluidState fluidState = this.level.getFluidState(block);
						final Block targetblock = blockState.getBlock();

						if(targetblock instanceof AirBlock){
							this.level.setBlock(block, BlockInitNew.WARDINSIDE.get().defaultBlockState().setValue(WardInside.LIGHT, level) ,3);

						}else if(targetblock instanceof WardInside){
							this.level.setBlock(block, BlockInitNew.WARDINSIDE.get().defaultBlockState().setValue(WardInside.LIGHT, level) ,3);
						}

					}
					LightEnergy.consumeEnergy(lightReq * level);
					setChanged();
				}else{
					Iterable<BlockPos> mutable = BlockPos.betweenClosed(-5,-5,-5,5,5,5);

					for (BlockPos block : mutable) {
						final int dist = (block.getX()*block.getX()) + (block.getY()*block.getY()) + (block.getZ() * block.getZ());
						if(dist>25){
							continue;
						}
						if(dist<1){
							continue;
						}
						final BlockState blockState = this.level.getBlockState(block);
						final FluidState fluidState = this.level.getFluidState(block);
						final Block targetblock = blockState.getBlock();

						if(targetblock instanceof WardInside){
							this.level.setBlock(block, Blocks.AIR.defaultBlockState(),3);

						}

					}
					setChanged();
				}
				break;

		}
	}

	private boolean destroyBlock(BlockPos pos, boolean dropBlock, @Nullable Entity entity) {
		BlockState blockstate = level.getBlockState(pos);
		if(blockstate.isAir(level, pos))return false;
		else {
			FluidState ifluidstate = level.getFluidState(pos);
			level.levelEvent(2001, pos, Block.getId(blockstate));
			if(dropBlock) {
				TileEntity tileentity= blockstate.hasTileEntity() ? level.getBlockEntity(pos) : null;
				Block.dropResources(blockstate, level, this.worldPosition.offset(0, 1.5, 0), tileentity, entity, ItemStack.EMPTY);
			}
			return level.setBlock(pos, ifluidstate.getFluidState().createLegacyBlock(), 3);
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
		this.save(tag);
		return super.getUpdatePacket();
	}

	@Override
	public CompoundNBT getUpdateTag() {
		save(tag);
		return tag;
	}


    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);
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

            compound.put("energypos", this.energyblocks);
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
    public void load(BlockState blockState,CompoundNBT compound) {
        super.load(blockState,compound);
        readRestorableNBT(compound);
    }


    @Override
	public void handleUpdateTag(BlockState blockState,CompoundNBT tag) {
		this.load(blockState,tag);
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
		level.sendBlockUpdated(worldPosition, this.getBlockState(), this.getBlockState(), 1);
	}


}
