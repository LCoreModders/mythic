package com.afg.mythic.superpower.wendigo.abilities;

import com.afg.mythic.Mythic;
import com.afg.mythic.superpower.wendigo.WendigoPlayerHandler;
import com.afg.mythic.util.MonsterUtil;
import lucraft.mods.lucraftcore.abilities.AbilityAction;
import lucraft.mods.lucraftcore.potions.PotionKnockOut;
import lucraft.mods.lucraftcore.superpower.SuperpowerHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;

/**
 * Created by AFlyingGrayson on 8/26/17
 */
public class AbilityWendigoDevour extends AbilityAction
{
	public AbilityWendigoDevour(EntityPlayer player)
	{
		super(player);
	}

	@Override public void action()
	{
		RayTraceResult result = MonsterUtil.rayTrace(player, 5, 0.0f);
		if(result.entityHit instanceof EntityLivingBase){
			EntityLivingBase entityLivingBase = (EntityLivingBase) result.entityHit;
			WendigoPlayerHandler handler = SuperpowerHandler.getSpecificSuperpowerPlayerHandler(player, WendigoPlayerHandler.class);
			if((entityLivingBase instanceof EntityPlayer || entityLivingBase instanceof EntityVillager) && entityLivingBase.isPotionActive(PotionKnockOut.POTION)){
					if(!(entityLivingBase instanceof EntityPlayer) || !SuperpowerHandler.hasSuperpower((EntityPlayer) entityLivingBase, Mythic.wendigo))
					{
						entityLivingBase.setDead();
						player.getFoodStats().addStats(8, 2.0F);
						int xpAmount = 10;
						handler.addXP(xpAmount, true);
					}
			}
		}
	}
}
