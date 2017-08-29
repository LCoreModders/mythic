package com.afg.mythic;

import com.afg.mythic.client.ClientEventHandler;
import com.afg.mythic.network.NetworkHandler;
import com.afg.mythic.superpower.vampire.Vampire;
import com.afg.mythic.superpower.vampire.abilities.*;
import com.afg.mythic.superpower.wendigo.Wendigo;
import com.afg.mythic.superpower.wendigo.abilities.AbilityWendigoDevour;
import com.afg.mythic.superpower.wendigo.abilities.AbilityWendigoJump;
import com.afg.mythic.superpower.wendigo.abilities.AbilityWendigoSpeed;
import com.afg.mythic.superpower.wendigo.abilities.AbilityWendigoStrength;
import lucraft.mods.lucraftcore.abilities.Ability;
import lucraft.mods.lucraftcore.superpower.Superpower;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by AFlyingGrayson on 8/20/17
 */

@Mod(modid = Mythic.MODID, name = "Mythic", version = Mythic.VERSION)
@Mod.EventBusSubscriber
public class Mythic
{
	public static final String MODID = "mythic";
	public static final String VERSION = "0.1";

	public static Vampire vampire = new Vampire();
	public static Wendigo wendigo = new Wendigo();

	@Mod.EventHandler
	public void init(FMLInitializationEvent event){
		if(event.getSide().equals(Side.CLIENT))
			MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
		NetworkHandler.register();
	}

	@SubscribeEvent
	public static void onRegisterSuperpower(RegistryEvent.Register<Superpower> e)
	{
		e.getRegistry().register(vampire);
		e.getRegistry().register(wendigo);
	}

	@SubscribeEvent
	public static void onRegisterAbility(RegistryEvent.Register<Ability.AbilityEntry> e)
	{
		e.getRegistry().register(new Ability.AbilityEntry(SunlightWeakness.class, new ResourceLocation(MODID, "sunlightWeakness")));
		e.getRegistry().register(new Ability.AbilityEntry(LimitedImmortality.class, new ResourceLocation(MODID, "limitedImmortality")));
		e.getRegistry().register(new Ability.AbilityEntry(DrainBlood.class, new ResourceLocation(MODID, "drainBlood")));
		e.getRegistry().register(new Ability.AbilityEntry(Health.class, new ResourceLocation(MODID, "vampireHealth")));
		e.getRegistry().register(new Ability.AbilityEntry(VampireResistance.class, new ResourceLocation(MODID, "vampireResistance")));
		e.getRegistry().register(new Ability.AbilityEntry(Speed.class, new ResourceLocation(MODID, "vampireSpeed")));
		e.getRegistry().register(new Ability.AbilityEntry(Strength.class, new ResourceLocation(MODID, "vampireStrength")));
		e.getRegistry().register(new Ability.AbilityEntry(Jump.class, new ResourceLocation(MODID, "vampireJump")));
		e.getRegistry().register(new Ability.AbilityEntry(Embrace.class, new ResourceLocation(MODID, "embrace")));

		e.getRegistry().register(new Ability.AbilityEntry(AbilityWendigoJump.class, new ResourceLocation(MODID, "wendigoJump")));
		e.getRegistry().register(new Ability.AbilityEntry(AbilityWendigoSpeed.class, new ResourceLocation(MODID, "wendigoSpeed")));
		e.getRegistry().register(new Ability.AbilityEntry(AbilityWendigoStrength.class, new ResourceLocation(MODID, "wendigoStrength")));
		e.getRegistry().register(new Ability.AbilityEntry(AbilityWendigoDevour.class, new ResourceLocation(MODID, "wendigoDevour")));
	}
}
