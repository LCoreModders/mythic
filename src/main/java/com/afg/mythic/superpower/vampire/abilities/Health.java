package com.afg.mythic.superpower.vampire.abilities;

import com.afg.mythic.superpower.AbilityAttributeScale;
import com.afg.mythic.superpower.vampire.VampirePlayerHandler;
import lucraft.mods.lucraftcore.superpower.SuperpowerHandler;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by AFlyingGrayson on 8/21/17
 */
public class Health extends AbilityAttributeScale
{
	public Health(EntityPlayer player, float factor)
	{
		super(player, factor);
	}

	@Override public float getFactor() {
		VampirePlayerHandler handler = SuperpowerHandler.getSpecificSuperpowerPlayerHandler(player, VampirePlayerHandler.class);
		return this.factor * handler.getLevel();
	}


	@Override public IAttribute getAttribute()
	{
		return SharedMonsterAttributes.MAX_HEALTH;
	}
}
