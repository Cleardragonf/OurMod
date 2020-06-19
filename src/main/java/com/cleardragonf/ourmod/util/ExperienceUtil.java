package com.cleardragonf.ourmod.util;

import net.minecraft.entity.player.PlayerEntity;

import java.util.stream.IntStream;

public class ExperienceUtil {
    public static int expBarCap(int level) {
        if (level >= 30)
            return 112 + (level - 30) * 9;
        return (level >= 15) ? (37 + (level - 15) * 5) : (7 + level * 2);
    }

    public static int expToLevel(int exp) {
        int level = 0;
        int _exp = exp;
        int cap = expBarCap(level);
        while (_exp >= cap) {
            _exp -= cap;
            cap = expBarCap(++level);
        }
        return level;
    }

    public static int levelToExp(int level, float expBar) {
        int sum = IntStream.range(0, level).map(ExperienceUtil::expBarCap).sum();
        return sum + Math.round(expBarCap(level) * expBar);
    }

    public static int getPlayerExp(PlayerEntity player) {
        return levelToExp(player.experienceLevel, player.experience);
    }

    public static void addExpToPlayer(PlayerEntity player, int value) {
        player.addScore(value);
        int playerExp = getPlayerExp(player);
        int limit = Integer.MAX_VALUE - playerExp;
        int _value = Math.min(value, limit);
        int exp = playerExp + _value;
        int level = expToLevel(exp);
        float rest = (exp - levelToExp(level, 0.0F));
        player.experienceTotal += _value;
        player.experienceLevel = level;
        player.experience = rest / expBarCap(level);
    }

    public static void removeExpFromPlayer(PlayerEntity player, int value) {
        int exp = getPlayerExp(player);
        player.addScore(-exp);
        player.experienceTotal -= exp;
        player.experienceLevel = 0;
        player.experience = 0.0F;
        if (exp > value)
            addExpToPlayer(player, exp - value);
    }
}
