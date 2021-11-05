package com.cleardragonf.ourmod.init;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.container.*;

import com.cleardragonf.ourmod.util.ClientEventBusSubscriber;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainerTypes {
	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, OurMod.MOD_ID);
	
	public static final RegistryObject<ContainerType<PortableChestContainer>> PORTABLE_CHEST = CONTAINER_TYPES.register("portablechest", () -> IForgeContainerType.create(PortableChestContainer::new ));
	public static final RegistryObject<ContainerType<FishingNetContainer>> FISHING_NET = CONTAINER_TYPES.register("fishingnet", () -> IForgeContainerType.create(FishingNetContainer::new ));
	public static final RegistryObject<ContainerType<EssenceCollectorContainer>> ESSENCE_COLLECTOR = CONTAINER_TYPES.register("essencecollector", () -> IForgeContainerType.create(EssenceCollectorContainer::new ));
	public static final RegistryObject<ContainerType<MCMChestContainer>> MCM_CHEST = CONTAINER_TYPES.register("mcmchest", () -> IForgeContainerType.create(MCMChestContainer::new ));
	public static final RegistryObject<ContainerType<ChargeStoneContainer>> CHARGE_STONE = CONTAINER_TYPES.register("chargestone", () -> IForgeContainerType.create(ChargeStoneContainer::new ));
	public static final RegistryObject<ContainerType<MasterWardStoneContainer>> MASTER_WARD_STONE = CONTAINER_TYPES.register("masterwardstone", () -> IForgeContainerType.create(MasterWardStoneContainer::new ));
	public static final RegistryObject<ContainerType<BoundaryWardStoneContainer>> BOUNDARY_WARD_STONE = CONTAINER_TYPES.register("boundarywardstone", () -> IForgeContainerType.create(BoundaryWardStoneContainer::new ));
}
