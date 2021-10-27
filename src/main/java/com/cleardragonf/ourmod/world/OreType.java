package com.cleardragonf.ourmod.world;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.init.BlockInitNew;
import net.minecraft.block.Block;
import net.minecraftforge.common.util.Lazy;

public enum OreType {

    EARTH_ORE(Lazy.of(BlockInitNew.EARTH_MANA), 10,1,240),
    AIR_ORE(Lazy.of(BlockInitNew.AIR_MANA), 10,60,240),
    FIRE_ORE(Lazy.of(BlockInitNew.FIRE_MANA), 10, 1, 240),
    WATER_ORE(Lazy.of(BlockInitNew.WATER_MANA), 10,1,240),
    DARK_ORE(Lazy.of(BlockInitNew.DARK_MANA), 5,1,20),
    LIGH_ORE(Lazy.of(BlockInitNew.LIGHT_MANA), 5,60,240);


    ;

    private final Lazy<Block> block;
    private final int maxVeinSize;
    private final int minHeight;
    private final int maxHeight;

    OreType(Lazy<Block> block, int maxVeinSize, int minHeight, int maxHeight) {
        this.block = block;
        this.maxVeinSize = maxVeinSize;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    public Lazy<Block> getBlock() {
        return block;
    }

    public int getMaxVeinSize() {
        return maxVeinSize;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public static OreType get(Block block){
        for(OreType ore : values()){
            if(block == ore.block){
                return ore;
            }
        }
        return null;
    }
}
