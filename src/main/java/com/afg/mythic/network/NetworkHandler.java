package com.afg.mythic.network;

import com.afg.mythic.Mythic;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by AFlyingGrayson on 8/25/17
 */
public class NetworkHandler
{
	private static byte packetId = 0;

	public static final SimpleNetworkWrapper simpleNetworkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Mythic.MODID);

	public static void register() {
		simpleNetworkWrapper.registerMessage(MessageMSendInfoToServer.Handler.class, MessageMSendInfoToServer.class, packetId++, Side.SERVER);
	}
}
