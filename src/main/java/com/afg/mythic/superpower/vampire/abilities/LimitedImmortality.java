package com.afg.mythic.superpower.vampire.abilities;

import lucraft.mods.lucraftcore.abilities.Ability;
import lucraft.mods.lucraftcore.abilities.AbilityConstant;
import lucraft.mods.lucraftcore.network.MessageSyncPotionEffects;
import lucraft.mods.lucraftcore.network.PacketDispatcher;
import lucraft.mods.lucraftcore.potions.PotionKnockOut;
import lucraft.mods.lucraftcore.superpower.SuperpowerHandler;
import lucraft.mods.lucraftcore.superpower.SuperpowerPlayerHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

/**
 * Created by AFlyingGrayson on 8/20/17
 */
@Mod.EventBusSubscriber
public class LimitedImmortality extends AbilityConstant
{

	public LimitedImmortality(EntityPlayer player)
	{
		super(player);
	}

	@SubscribeEvent
	public static void onDeathEvent(LivingDeathEvent event){
		if(event.getEntity() instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) event.getEntity();

			SuperpowerPlayerHandler handler = SuperpowerHandler.getSuperpowerPlayerHandler(player);
			if(handler != null){
				for (Ability ability : handler.getAbilities())
				{
					if(ability instanceof LimitedImmortality && ability.isUnlocked()){

						if(event.getSource().isFireDamage())
							return;
						if(event.getSource().isMagicDamage())
							return;

						event.setCanceled(true);
						event.getEntityLiving().setHealth(event.getEntityLiving().getMaxHealth());
						int duration = 60 * 20;
						PotionEffect blindness = new PotionEffect(MobEffects.BLINDNESS, duration, 0, false, false);
						blindness.setCurativeItems(new ArrayList<>());
						PotionEffect slowness = new PotionEffect(MobEffects.SLOWNESS, duration, 255, false, false);
						slowness.setCurativeItems(new ArrayList<>());
						PotionEffect jumpboost = new PotionEffect(MobEffects.JUMP_BOOST, duration, 128, false, false);
						jumpboost.setCurativeItems(new ArrayList<>());
						PotionEffect fatigue = new PotionEffect(MobEffects.MINING_FATIGUE, duration, 128, false, false);
						fatigue.setCurativeItems(new ArrayList<>());
						PotionEffect weakness = new PotionEffect(MobEffects.WEAKNESS, duration, 128, false, false);
						weakness.setCurativeItems(new ArrayList<>());
						PotionEffect knockout = new PotionEffect(PotionKnockOut.POTION, duration, 0, false, false);
						knockout.setCurativeItems(new ArrayList<>());

						event.getEntityLiving().addPotionEffect(blindness);
						event.getEntityLiving().addPotionEffect(slowness);
						event.getEntityLiving().addPotionEffect(jumpboost);
						event.getEntityLiving().addPotionEffect(fatigue);
						event.getEntityLiving().addPotionEffect(weakness);
						event.getEntityLiving().addPotionEffect(knockout);

						PacketDispatcher.sendToAll(new MessageSyncPotionEffects(event.getEntityLiving()));
					}
				}
			}
		}
	}

	@Override public void updateTick()
	{

	}

	@Override public boolean showInAbilityBar() {
		return false;
	}

}
