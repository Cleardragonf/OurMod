package com.cleardragonf.ourmod.MCM;

import com.google.common.collect.ImmutableMap;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MCMValues {

    public Map<Item, Integer> mcmList = new HashMap<>();

    public Map<Item, Integer> Map(){
        mcmList.put(Items.IRON_BLOCK, 9000);
        mcmList.put(Items.IRON_ORE, 3000);
        mcmList.put(Items.IRON_NUGGET, 500);
        mcmList.put(Items.DIAMOND_ORE, 3000);
        mcmList.put(Items.COBBLESTONE, 1);
        mcmList.put(Items.STONE, 1);
        mcmList.put(Items.STONE_BRICKS, 1);
        mcmList.put(Items.END_STONE, 1);
        mcmList.put(Items.NETHERRACK, 1);
        mcmList.put(Items.DIRT, 1);
        mcmList.put(Items.SAND, 1);
        mcmList.put(Items.SNOW, 1);
        mcmList.put(Items.ICE, 1);
        mcmList.put(Items.DEAD_BUSH, 1);
        mcmList.put(Items.GRAVEL, 4);
        mcmList.put(Items.CACTUS, 8);
        mcmList.put(Items.VINE, 8);
        mcmList.put(Items.COBWEB, 12);
        mcmList.put(Items.LILY_PAD, 16);
        mcmList.put(Items.RED_MUSHROOM, 32);
        mcmList.put(Items.BROWN_MUSHROOM, 32);
        mcmList.put(Items.BAMBOO, 32);
        mcmList.put(Items.SOUL_SAND, 49);
        mcmList.put(Items.OBSIDIAN, 64);
        mcmList.put(Items.SPONGE, 128);
        mcmList.put(Items.TALL_GRASS, 1);
        mcmList.put(Items.PACKED_ICE, 4);
        mcmList.put(Items.MAGMA_BLOCK, 128);
        mcmList.put(Items.CHORUS_PLANT, 64);
        mcmList.put(Items.CHORUS_FLOWER, 96);
        mcmList.put(Items.WHEAT_SEEDS, 16);
        mcmList.put(Items.BEETROOT_SEEDS, 16);
        mcmList.put(Items.MELON, 16);
        mcmList.put(Items.WHEAT, 24);
        mcmList.put(Items.NETHER_WART, 24);
        mcmList.put(Items.APPLE, 128);
        mcmList.put(Items.PUMPKIN, 144);
        mcmList.put(Items.PORKCHOP, 64);
        mcmList.put(Items.BEEF, 64);
        mcmList.put(Items.CHICKEN, 64);
        mcmList.put(Items.RABBIT, 64);
        mcmList.put(Items.MUTTON, 64);
        mcmList.put(Items.TROPICAL_FISH, 64);
        mcmList.put(Items.PUFFERFISH, 64);
        mcmList.put(Items.CARROT, 64);
        mcmList.put(Items.BEETROOT, 64);
        mcmList.put(Items.POTATO, 64);
        mcmList.put(Items.POISONOUS_POTATO, 64);
        mcmList.put(Items.CHORUS_FRUIT, 192);
        mcmList.put(Items.ROTTEN_FLESH, 32);
        mcmList.put(Items.SLIME_BALL, 32);
        mcmList.put(Items.EGG, 32);
        mcmList.put(Items.FEATHER, 48);
        mcmList.put(Items.RABBIT_HIDE, 16);
        mcmList.put(Items.RABBIT_FOOT, 128);
        mcmList.put(Items.SPIDER_EYE, 128);
        mcmList.put(Items.GUNPOWDER, 192);
        mcmList.put(Items.ENDER_PEARL, 1024);
        mcmList.put(Items.BLAZE_ROD, 1536);
        mcmList.put(Items.SHULKER_SHELL, 2048);
        mcmList.put(Items.GHAST_TEAR, 4096);
        mcmList.put(Items.DRAGON_EGG, 262144);
        mcmList.put(Items.POTION, 0);
        mcmList.put(Items.SADDLE, 192);
        mcmList.put(Items.NAME_TAG, 192);
        mcmList.put(Items.MUSIC_DISC_11, 2048);
        mcmList.put(Items.MUSIC_DISC_13, 2048);
        mcmList.put(Items.MUSIC_DISC_BLOCKS, 2048);
        mcmList.put(Items.MUSIC_DISC_CAT, 2048);
        mcmList.put(Items.MUSIC_DISC_CHIRP, 2048);
        mcmList.put(Items.MUSIC_DISC_FAR, 2048);
        mcmList.put(Items.MUSIC_DISC_MALL, 2048);
        mcmList.put(Items.MUSIC_DISC_MELLOHI, 2048);
        mcmList.put(Items.MUSIC_DISC_STAL, 2048);
        mcmList.put(Items.MUSIC_DISC_STRAD, 2048);
        mcmList.put(Items.MUSIC_DISC_WAIT, 2048);
        mcmList.put(Items.MUSIC_DISC_WARD, 2048);
        mcmList.put(Items.DIAMOND, 8192);
        mcmList.put(Items.FLINT, 4);
        mcmList.put(Items.COAL, 128);
        mcmList.put(Items.REDSTONE, 64);
        mcmList.put(Items.GLOWSTONE, 384);
        mcmList.put(Items.QUARTZ, 256);
        mcmList.put(Items.PRISMARINE, 256);
        mcmList.put(Items.PRISMARINE_CRYSTALS, 512);
        mcmList.put(Items.BLACK_DYE, 16);
        mcmList.put(Items.ENCHANTED_BOOK, 2048);
        mcmList.put(Items.EMERALD_ORE, 16384);
        mcmList.put(Items.NETHER_STAR, 139264);
        mcmList.put(Items.CLAY_BALL, 16);
        mcmList.put(Items.BONE, 144);
        mcmList.put(Items.SNOWBALL, 1);
        return mcmList;
    }

    public Map<Item, Integer> getMap(){
        return Map();
    }
}


