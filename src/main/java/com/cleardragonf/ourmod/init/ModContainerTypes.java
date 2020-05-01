package com.cleardragonf.ourmod.init;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.container.PortableChestContainer;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainerTypes {
	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = new DeferredRegister<ContainerType<?>>(ForgeRegistries.CONTAINERS, OurMod.MOD_ID);
	
	public static final RegistryObject<ContainerType<PortableChestContainer>> PORTABLE_CHEST = CONTAINER_TYPES.register("portablechest", () -> IForgeContainerType.create(PortableChestContainer::new ));
	
}
