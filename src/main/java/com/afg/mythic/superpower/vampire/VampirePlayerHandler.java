package com.afg.mythic.superpower.vampire;

import com.afg.mythic.Mythic;
import com.afg.mythic.superpower.vampire.abilities.DrainBlood;
import com.afg.mythic.superpower.vampire.abilities.Embrace;
import lucraft.mods.lucraftcore.abilities.Ability;
import lucraft.mods.lucraftcore.superpower.Superpower;
import lucraft.mods.lucraftcore.superpower.SuperpowerHandler;
import lucraft.mods.lucraftcore.superpower.SuperpowerPlayerHandler;
import lucraft.mods.lucraftcore.util.LucraftKeys;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by AFlyingGrayson on 8/20/17
 */
@Mod.EventBusSubscriber
public class VampirePlayerHandler extends SuperpowerPlayerHandler
{
	public int generation = 10;

	public VampirePlayerHandler(EntityPlayer player, Superpower superpower)
	{
		super(player, superpower);
	}

	@Override public Ability getAbilityForKey(LucraftKeys key)
	{
		if (key == LucraftKeys.SUPERPOWER_1)
			return Ability.getAbilityFromClass(this.getAbilities(), DrainBlood.class);
		if(key == LucraftKeys.SUPERPOWER_2)
			return Ability.getAbilityFromClass(this.getAbilities(), Embrace.class);
		return null;
	}

	@Override public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound.setInteger("generation", generation);
		return super.writeToNBT(compound);
	}

	@Override public void readFromNBT(NBTTagCompound compound)
	{
		this.generation = compound.getInteger("generation");
		super.readFromNBT(compound);
	}

	@SubscribeEvent
	public static void onUseItem(LivingEntityUseItemEvent.Start e){
		if(e.getItem().getItem() instanceof ItemFood)
			if(e.getEntity() instanceof EntityPlayer && SuperpowerHandler.hasSuperpower((EntityPlayer) e.getEntity(), Mythic.vampire))
				e.setCanceled(true);
	}
}
