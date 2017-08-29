package com.afg.mythic.superpower.vampire.abilities;

import lucraft.mods.lucraftcore.abilities.Ability;
import lucraft.mods.lucraftcore.abilities.AbilityConstant;
import lucraft.mods.lucraftcore.superpower.SuperpowerHandler;
import lucraft.mods.lucraftcore.superpower.SuperpowerPlayerHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by AFlyingGrayson on 8/21/17
 */
@Mod.EventBusSubscriber
public class VampireResistance extends AbilityConstant
{
	public VampireResistance(EntityPlayer player) { super(player); }

	@Override public void updateTick() {}

	@SubscribeEvent
	public static void onHurtEvent(LivingHurtEvent event)
	{
		if (event.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getEntity();

			SuperpowerPlayerHandler handler = SuperpowerHandler.getSuperpowerPlayerHandler(player);
			if (handler != null)
			{
				for (Ability ability : handler.getAbilities())
				{
					if (ability instanceof VampireResistance && ability.isUnlocked())
					{
						if (event.getSource().isFireDamage())
							return;
						if (event.getSource().isMagicDamage())
							return;

						float resistance = handler.getLevel() * 0.025f;
						event.setAmount(event.getAmount() * resistance);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onAttackedEvent(LivingAttackEvent event)
	{
		if (event.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getEntity();

			SuperpowerPlayerHandler handler = SuperpowerHandler.getSuperpowerPlayerHandler(player);
			if (handler != null)
			{
				for (Ability ability : handler.getAbilities())
				{
					if (ability instanceof VampireResistance && ability.isUnlocked())
					{
						if (event.getSource().isFireDamage())
							return;
						if (event.getSource().isMagicDamage())
							return;

						if(event.getAmount() <= handler.getLevel()/15f)
							event.setCanceled(true);
					}
				}
			}
		}
	}

	@Override public boolean showInAbilityBar() {
		return false;
	}

}
