package com.afg.mythic.superpower.vampire.abilities;

import com.afg.mythic.Mythic;
import com.afg.mythic.potions.PotionAnemic;
import com.afg.mythic.superpower.vampire.VampirePlayerHandler;
import com.afg.mythic.util.MonsterUtil;
import lucraft.mods.lucraftcore.abilities.AbilityAction;
import lucraft.mods.lucraftcore.potions.PotionKnockOut;
import lucraft.mods.lucraftcore.superpower.SuperpowerHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;

/**
 * Created by AFlyingGrayson on 8/20/17
 */
public class DrainBlood extends AbilityAction
{
	public DrainBlood(EntityPlayer player)
	{
		super(player);
	}

	@Override public void action()
	{
		RayTraceResult result = MonsterUtil.rayTrace(player, 5, 0.0f);
			if(result.entityHit instanceof EntityLivingBase){
				EntityLivingBase entityLivingBase = (EntityLivingBase) result.entityHit;
				VampirePlayerHandler handler = SuperpowerHandler.getSpecificSuperpowerPlayerHandler(player, VampirePlayerHandler.class);
				if(entityLivingBase instanceof EntityAnimal || entityLivingBase.isPotionActive(PotionKnockOut.POTION)){
					entityLivingBase.attackEntityFrom(DamageSource.WITHER, 5.0f);

					if(entityLivingBase.isEntityUndead()){
						player.getFoodStats().addExhaustion(1.0f);
						return;
					}

					if(MonsterUtil.isSentient(entityLivingBase))
					{
						if(!(entityLivingBase instanceof EntityPlayer) || !SuperpowerHandler.hasSuperpower((EntityPlayer) entityLivingBase, Mythic.vampire))
						{
							player.getFoodStats().addStats(8, 2.0F);
							entityLivingBase.addPotionEffect(new PotionEffect(PotionAnemic.POTION, 30));
							if (handler.getLevel() == 1)
								handler.levelUp();
							else if(handler.getLevel() < 32 - handler.generation*2)
							{
								int xpAmount = 10 - handler.generation;
								if(xpAmount < 1) xpAmount = 1;
								handler.addXP(xpAmount, true);
							}
						}
					}
					else
					{
						if(handler.getLevel() != 1) {
							player.getFoodStats().addStats(4, 1.0F);
							if(handler.getLevel() < 10)
							{
								int xpAmount = 10 - handler.generation;
								if(xpAmount < 2) xpAmount = 2;
								handler.addXP(xpAmount/2, true);
							} else if(handler.getLevel() > 10){
								handler.setXP(handler.getXP() - 10);
								if(handler.getXP() == 0)
									handler.setLevel(handler.getLevel() - 1);
							}
						}
					}
				}
			}
	}
}
