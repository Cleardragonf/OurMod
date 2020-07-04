package com.cleardragonf.ourmod.util;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.client.gui.*;
import com.cleardragonf.ourmod.init.BlockInitNew;
import com.cleardragonf.ourmod.init.ModContainerTypes;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = OurMod.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {


	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		ScreenManager.registerFactory(ModContainerTypes.PORTABLE_CHEST.get(), PortableChestScreen::new);
		ScreenManager.registerFactory(ModContainerTypes.FISHING_NET.get(), FishingNetScreen::new);
		ScreenManager.registerFactory(ModContainerTypes.ESSENCE_COLLECTOR.get(), EssenceCollectorScreen::new);
		ScreenManager.registerFactory(ModContainerTypes.MCM_CHEST.get(), MCMChestScreen::new);
		ScreenManager.registerFactory(ModContainerTypes.MASTER_WARD_STONE.get(), MasterWardStoneScreen::new);
		ScreenManager.registerFactory(ModContainerTypes.BOUNDARY_WARD_STONE.get(), BoundaryWardStoneScreen::new);
		RenderTypeLookup.setRenderLayer(BlockInitNew.WARDBARRIER.get(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(BlockInitNew.TOMATO_CROP.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockInitNew.RICE_CROP.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockInitNew.ONION_CROP.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockInitNew.CORN_CROP.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockInitNew.CUCUMBER_CROP.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockInitNew.BROCCOLI_CROP.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockInitNew.LETTUCE_CROP.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockInitNew.PEANUT_CROP.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockInitNew.PEPPER_CROP.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockInitNew.CAULIFLOWER_CROP.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockInitNew.EGGPLANT_CROP.get(), RenderType.getCutout());
	}
}
