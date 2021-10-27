package com.cleardragonf.ourmod.setup;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class ClientProxy implements IProxy{
	@Override
	public World getClientWorld() {
		return Minecraft.getInstance().level;
	}

	@Override
	public void init() {
	}
}
