package com.afg.mythic.superpower.wendigo;

import com.afg.mythic.Mythic;
import com.afg.mythic.superpower.wendigo.abilities.AbilityWendigoDevour;
import com.afg.mythic.superpower.wendigo.abilities.AbilityWendigoJump;
import lucraft.mods.lucraftcore.abilities.Ability;
import lucraft.mods.lucraftcore.superpower.Superpower;
import lucraft.mods.lucraftcore.superpower.SuperpowerHandler;
import lucraft.mods.lucraftcore.superpower.SuperpowerPlayerHandler;
import lucraft.mods.lucraftcore.util.LucraftKeys;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Created by AFlyingGrayson on 8/24/17
 */
@Mod.EventBusSubscriber
public class WendigoPlayerHandler extends SuperpowerPlayerHandler
{

	public WendigoPlayerHandler(EntityPlayer player, Superpower superpower)
	{
		super(player, superpower);
	}

	@Override public Ability getAbilityForKey(LucraftKeys key)
	{
		if(key == LucraftKeys.SUPERPOWER_1)
			return Ability.getAbilityFromClass(getAbilities(), AbilityWendigoJump.class);
		if(key == LucraftKeys.SUPERPOWER_2)
			return Ability.getAbilityFromClass(getAbilities(), AbilityWendigoDevour.class);
		return null;


	}

	@Override public void update(TickEvent.Phase phase)
	{
		super.update(phase);
		if(phase == TickEvent.Phase.END)
			return;

		if(player.isPotionActive(Potion.getPotionById(9)))
			player.removeActivePotionEffect(Potion.getPotionById(9));
		if(player.isPotionActive(Potion.getPotionById(17)))
			player.removeActivePotionEffect(Potion.getPotionById(17));
	}

	@SubscribeEvent
	public static void onUseItem(LivingEntityUseItemEvent.Start e){
		if(e.getItem().getItem() != Items.ROTTEN_FLESH && e.getItem().getItem() instanceof ItemFood)
			if(e.getEntity() instanceof EntityPlayer && SuperpowerHandler.hasSuperpower((EntityPlayer) e.getEntity(), Mythic.wendigo))
					e.setCanceled(true);
	}

	@SubscribeEvent
	public static void onUseItem(LivingEntityUseItemEvent.Finish e){
		if(e.getItem().getItem() == Items.ROTTEN_FLESH)
			if(e.getEntity() instanceof EntityPlayer && SuperpowerHandler.hasSuperpower((EntityPlayer) e.getEntity(), Mythic.wendigo)){
				WendigoPlayerHandler handler = SuperpowerHandler.getSpecificSuperpowerPlayerHandler((EntityPlayer) e.getEntity(), WendigoPlayerHandler.class);
				handler.addXP(5, true);
			}
	}
}
