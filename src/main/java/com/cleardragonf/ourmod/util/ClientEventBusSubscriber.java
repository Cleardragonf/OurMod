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
		ScreenManager.register(ModContainerTypes.PORTABLE_CHEST.get(), PortableChestScreen::new);
		ScreenManager.register(ModContainerTypes.FISHING_NET.get(), FishingNetScreen::new);
		ScreenManager.register(ModContainerTypes.ESSENCE_COLLECTOR.get(), EssenceCollectorScreen::new);
		ScreenManager.register(ModContainerTypes.MCM_CHEST.get(), MCMChestScreen::new);
		ScreenManager.register(ModContainerTypes.CHARGE_STONE.get(), ChargeStoneScreen::new);
		ScreenManager.register(ModContainerTypes.MASTER_WARD_STONE.get(), MasterWardStoneScreen::new);
		ScreenManager.register(ModContainerTypes.BOUNDARY_WARD_STONE.get(), BoundaryWardStoneScreen::new);
		RenderTypeLookup.setRenderLayer(BlockInitNew.WARDBARRIER.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(BlockInitNew.WARDINSIDE.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(BlockInitNew.TOMATO_CROP.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInitNew.RICE_CROP.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInitNew.ONION_CROP.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInitNew.CORN_CROP.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInitNew.CUCUMBER_CROP.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInitNew.BROCCOLI_CROP.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInitNew.LETTUCE_CROP.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInitNew.PEANUT_CROP.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInitNew.PEPPER_CROP.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInitNew.CAULIFLOWER_CROP.get(), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(BlockInitNew.EGGPLANT_CROP.get(), RenderType.cutout());
	}
}
