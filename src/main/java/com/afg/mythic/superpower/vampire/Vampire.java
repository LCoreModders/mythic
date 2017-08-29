package com.afg.mythic.superpower.vampire;

import com.afg.mythic.Mythic;
import com.afg.mythic.superpower.vampire.abilities.*;
import lucraft.mods.lucraftcore.abilities.Ability;
import lucraft.mods.lucraftcore.superpower.Superpower;
import lucraft.mods.lucraftcore.superpower.SuperpowerPlayerHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

/**
 * Created by AFlyingGrayson on 8/20/17
 */
public class Vampire extends Superpower
{
	public Vampire() {
		super("Vampire");
		this.setRegistryName(Mythic.MODID, "vampire");
	}

	@Override public SuperpowerPlayerHandler getNewSuperpowerHandler(EntityPlayer entityPlayer)
	{
		return new VampirePlayerHandler(entityPlayer, this);
	}

	@Override public void renderIcon(Minecraft minecraft, int i, int i1) {}

	@Override public int getMaxLevel() { return 30; }

	@Override public boolean canLevelUp() { return true; }

	@Override protected List<Ability> addDefaultAbilities(EntityPlayer player, List<Ability> list) {
		list.add(new SunlightWeakness(player).setUnlocked(true));
		list.add(new LimitedImmortality(player).setUnlocked(true));
		list.add(new DrainBlood(player).setUnlocked(true));
		list.add(new Health(player, 1.5f).setUnlocked(true).setRequiredLevel(2));
		list.add(new Strength(player, 0.5f).setUnlocked(true).setRequiredLevel(2));
		list.add(new Speed(player, 0.1f).setUnlocked(true).setRequiredLevel(2));
		list.add(new Jump(player, 0.1f).setUnlocked(true).setRequiredLevel(2));
		list.add(new VampireResistance(player).setUnlocked(true).setRequiredLevel(2));
		list.add(new Embrace(player).setUnlocked(true).setRequiredLevel(10));
		return list;
	}
}
