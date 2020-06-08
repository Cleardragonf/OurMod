package com.cleardragonf.ourmod.init;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.container.EssenceCollectorContainer;
import com.cleardragonf.ourmod.container.FishingNetContainer;
import com.cleardragonf.ourmod.container.MCMChestContainer;
import com.cleardragonf.ourmod.container.PortableChestContainer;

import com.cleardragonf.ourmod.util.ClientEventBusSubscriber;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainerTypes {
	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = new DeferredRegister<ContainerType<?>>(ForgeRegistries.CONTAINERS, OurMod.MOD_ID);
	
	public static final RegistryObject<ContainerType<PortableChestContainer>> PORTABLE_CHEST = CONTAINER_TYPES.register("portablechest", () -> IForgeContainerType.create(PortableChestContainer::new ));
	public static final RegistryObject<ContainerType<FishingNetContainer>> FISHING_NET = CONTAINER_TYPES.register("fishingnet", () -> IForgeContainerType.create(FishingNetContainer::new ));
	public static final RegistryObject<ContainerType<EssenceCollectorContainer>> ESSENCE_COLLECTOR = CONTAINER_TYPES.register("essencecollector", () -> IForgeContainerType.create(EssenceCollectorContainer::new ));
	public static final RegistryObject<ContainerType<MCMChestContainer>> MCM_CHEST = CONTAINER_TYPES.register("mcmchest", () -> IForgeContainerType.create(MCMChestContainer::new ));
}
