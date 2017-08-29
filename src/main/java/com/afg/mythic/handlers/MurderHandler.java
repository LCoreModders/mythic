package com.afg.mythic.handlers;

import com.afg.mythic.Mythic;
import com.afg.mythic.superpower.vampire.VampirePlayerHandler;
import com.afg.mythic.util.MonsterUtil;
import lucraft.mods.lucraftcore.superpower.SuperpowerHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by AFlyingGrayson on 8/21/17
 */
@Mod.EventBusSubscriber
public class MurderHandler
{
	public static class MurderData extends WorldSavedData
	{
		private static final String DATA_NAME = Mythic.MODID + "_MurderData";
		public boolean cainCurse = false;

		public MurderData()
		{
			super(DATA_NAME);
		}

		public MurderData(String s)
		{
			super(s);
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt)
		{
			this.cainCurse = nbt.getBoolean("cainCurse");
		}

		@Override
		public NBTTagCompound writeToNBT(NBTTagCompound compound)
		{
			compound.setBoolean("cainCurse", cainCurse);
			return compound;
		}

		public static MurderData get(World world)
		{
			MapStorage storage = world.getMapStorage();
			MurderData instance = (MurderData) storage.getOrLoadData(MurderData.class, DATA_NAME);

			if (instance == null)
			{
				instance = new MurderData();
				storage.setData(DATA_NAME, instance);
			}
			return instance;
		}
	}

	@SubscribeEvent
	public static void onKillEntity(LivingDeathEvent event){
		if(event.getSource().getTrueSource() instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
			if(MonsterUtil.isSentient(event.getEntityLiving())){
				if(!SuperpowerHandler.hasSuperpower(player)){
					SuperpowerHandler.setSuperpower(player, Mythic.vampire);
					SuperpowerHandler.getSpecificSuperpowerPlayerHandler(player, VampirePlayerHandler.class).generation = 1;
					MurderData.get(event.getEntity().world).cainCurse = true;
					player.sendStatusMessage(new TextComponentString("You've been cursed by God for your evil actions."), true);
				}
			}
		}
	}
}
