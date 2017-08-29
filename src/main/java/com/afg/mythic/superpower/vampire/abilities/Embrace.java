package com.afg.mythic.superpower.vampire.abilities;

import com.afg.mythic.Mythic;
import com.afg.mythic.potions.PotionAnemic;
import com.afg.mythic.potions.PotionVitaeInfused;
import com.afg.mythic.superpower.vampire.VampirePlayerHandler;
import com.afg.mythic.util.MonsterUtil;
import lucraft.mods.lucraftcore.abilities.AbilityHeld;
import lucraft.mods.lucraftcore.superpower.SuperpowerHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by AFlyingGrayson on 8/21/17
 */
@Mod.EventBusSubscriber
public class Embrace extends AbilityHeld
{
	public Embrace(EntityPlayer player)
	{
		super(player);
	}

	@Override public void updateTick()
	{
		RayTraceResult r = MonsterUtil.rayTrace(player, 3, 0.0F);
		VampirePlayerHandler vampirePlayerHandler = SuperpowerHandler.getSpecificSuperpowerPlayerHandler(player, VampirePlayerHandler.class);
		if (r.entityHit instanceof EntityPlayer && ((EntityPlayer) r.entityHit).isPotionActive(PotionAnemic.POTION))
		{
			if(this.ticks == 0)
				r.entityHit.sendMessage(new TextComponentString("The nearby Vampire is letting his vitae flow into you."));
			else if(this.ticks == 60)
				r.entityHit.sendMessage(new TextComponentString("You're starting to feel... amazing!"));
		 	else if(this.ticks == 120){
				r.entityHit.sendMessage(new TextComponentString("Your blood is boiling with an exhilarating fire!"));
				((EntityPlayer) r.entityHit).addPotionEffect(new PotionEffect(PotionVitaeInfused.POTION, 240, vampirePlayerHandler.generation));
			}
		} else
			this.setEnabled(false);
	}

	@SubscribeEvent
	public static void onDeathEvent(LivingDeathEvent event){
		if(event.getEntity() instanceof EntityPlayer){
			if(((EntityPlayer) event.getEntity()).isPotionActive(PotionVitaeInfused.POTION)){
				SuperpowerHandler.setSuperpower((EntityPlayer) event.getEntity(), Mythic.vampire);
				SuperpowerHandler.getSpecificSuperpowerPlayerHandler((EntityPlayer) event.getEntity(), VampirePlayerHandler.class).generation = ((EntityPlayer) event.getEntity()).getActivePotionEffect(PotionVitaeInfused.POTION).getAmplifier() + 1;
				event.setCanceled(true);
			}
		}
	}
}
