package com.afg.mythic.superpower;

import lucraft.mods.lucraftcore.abilities.AbilityAttributeModifier;
import lucraft.mods.lucraftcore.superpower.SuperpowerHandler;
import lucraft.mods.lucraftcore.superpower.SuperpowerPlayerHandler;
import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;

/**
 * Created by AFlyingGrayson on 8/21/17
 */
public abstract class AbilityAttributeScale extends AbilityAttributeModifier
{
	public float baseAmount = 0;

	public AbilityAttributeScale(EntityPlayer player, float factor)
	{
		super(player, UUID.fromString("c5020091-737f-48e8-82d4-ad9e8d0773d8"), factor, 0);
	}

	public AbilityAttributeScale(EntityPlayer player, float factor, float baseAmount)
	{
		super(player, UUID.fromString("c5020091-737f-48e8-82d4-ad9e8d0773d8"), factor, 0);
		this.baseAmount = baseAmount;
	}

	@Override public float getFactor() {
		SuperpowerPlayerHandler handler = SuperpowerHandler.getSuperpowerPlayerHandler(player);
		return this.factor * handler.getLevel() + baseAmount;
	}
}
