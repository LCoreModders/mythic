package com.afg.mythic.superpower.vampire.abilities;

import com.afg.mythic.superpower.AbilityAttributeScale;
import lucraft.mods.lucraftcore.attributes.LCAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by AFlyingGrayson on 8/21/17
 */
public class Jump extends AbilityAttributeScale
{
	public Jump(EntityPlayer player, float factor)
	{
		super(player, factor, 1.0f);
	}

	@Override public IAttribute getAttribute()
	{
		return LCAttributes.JUMP_HEIGHT;
	}
}
