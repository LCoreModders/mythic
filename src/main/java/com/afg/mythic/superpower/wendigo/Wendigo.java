package com.afg.mythic.superpower.wendigo;

import com.afg.mythic.Mythic;
import com.afg.mythic.superpower.wendigo.abilities.AbilityWendigoDevour;
import com.afg.mythic.superpower.wendigo.abilities.AbilityWendigoJump;
import com.afg.mythic.superpower.wendigo.abilities.AbilityWendigoSpeed;
import com.afg.mythic.superpower.wendigo.abilities.AbilityWendigoStrength;
import lucraft.mods.lucraftcore.abilities.Ability;
import lucraft.mods.lucraftcore.superpower.Superpower;
import lucraft.mods.lucraftcore.superpower.SuperpowerPlayerHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

/**
 * Created by AFlyingGrayson on 8/24/17
 */
public class Wendigo extends Superpower
{
	public Wendigo() {
		super("Wendigo");
		this.setRegistryName(Mythic.MODID, "wendigo");
	}

	@Override public SuperpowerPlayerHandler getNewSuperpowerHandler(EntityPlayer entityPlayer)
	{
		return new WendigoPlayerHandler(entityPlayer, this);
	}

	@Override public void renderIcon(Minecraft minecraft, int i, int i1) {}

	@Override public int getMaxLevel() { return 10; }

	@Override public boolean canLevelUp() { return true; }

	@Override protected List<Ability> addDefaultAbilities(EntityPlayer player, List<Ability> list) {
		list.add(new AbilityWendigoJump(player).setUnlocked(true));
		list.add(new AbilityWendigoSpeed(player, 0.15f).setUnlocked(true));
		list.add(new AbilityWendigoStrength(player, 0.5F).setUnlocked(true));
		list.add(new AbilityWendigoDevour(player).setUnlocked(true));
		return list;
	}
}
