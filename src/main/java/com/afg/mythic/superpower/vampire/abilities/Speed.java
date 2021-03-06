package com.afg.mythic.superpower.vampire.abilities;

import com.afg.mythic.superpower.AbilityAttributeScale;
import lucraft.mods.lucraftcore.attributes.LCAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by AFlyingGrayson on 8/21/17
 */
public class Speed extends AbilityAttributeScale
{

	public Speed(EntityPlayer player, float factor)
	{
		super(player, factor);
	}

	@Override public IAttribute getAttribute()
	{
		return LCAttributes.SPRINT_SPEED;
	}
}
