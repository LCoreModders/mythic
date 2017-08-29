package com.afg.mythic.superpower.vampire.abilities;

import lucraft.mods.lucraftcore.abilities.AbilityConstant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

/**
 * Created by AFlyingGrayson on 8/20/17
 */
public class SunlightWeakness extends AbilityConstant
{
	public SunlightWeakness(EntityPlayer player)
	{
		super(player);
	}

	@Override public void updateTick()
	{
		BlockPos pos = player.getPosition();

		if(player.ticksExisted % 20 == 0 && player.world.isDaytime())
		{
			for (int i = 200; i > pos.getY(); i--)
				if (!player.world.isAirBlock(new BlockPos(pos.getX(), i, pos.getZ())))
					return;

			player.setFire(1);
		}
	}

	@Override public boolean showInAbilityBar() {
		return false;
	}

}
