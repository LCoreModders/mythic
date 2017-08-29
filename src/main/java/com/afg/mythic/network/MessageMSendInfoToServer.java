package com.afg.mythic.network;

import com.afg.mythic.superpower.wendigo.abilities.AbilityWendigoJump;
import io.netty.buffer.ByteBuf;
import lucraft.mods.lucraftcore.abilities.Ability;
import lucraft.mods.lucraftcore.network.AbstractServerMessageHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.HashMap;

/**
 * Created by AFlyingGrayson on 8/25/17
 */

public class MessageMSendInfoToServer implements IMessage
{

	public MServerInfoType type;
	public int info;

	public MessageMSendInfoToServer() {}

	public MessageMSendInfoToServer(MServerInfoType type) {
		this.type = type;
		this.info = 0;
	}

	public MessageMSendInfoToServer(MServerInfoType type, int i) {
		this.type = type;
		this.info = i;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		type = MServerInfoType.getInfoTypeFromId(buf.readInt());
		info = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(type.ordinal());
		buf.writeInt(info);
	}

	public static class Handler extends AbstractServerMessageHandler<MessageMSendInfoToServer>
	{

		@Override
		public IMessage handleServerMessage(EntityPlayer player, MessageMSendInfoToServer message, MessageContext ctx) {
			MServerInfoType type = message.type;

			switch (type) {
			case SUPER_JUMP_KEY:
				AbilityWendigoJump ab = Ability.getAbilityFromClass(Ability.getCurrentPlayerAbilities(player), AbilityWendigoJump.class);

				if(ab != null && ab.isUnlocked() && ab.isEnabled()) {
					ab.setKeyPressed(message.info == 1);
				}

				break;
			default:
				break;
			}

			return null;
		}
	}

	public static HashMap<Integer, MServerInfoType> ids = new HashMap<>();

	public enum MServerInfoType
	{
		SUPER_JUMP_KEY;

		MServerInfoType() {
			MessageMSendInfoToServer.ids.put(this.ordinal(), this);
		}

		public static MServerInfoType getInfoTypeFromId(int id) {
			return MessageMSendInfoToServer.ids.get(id);
		}

	}

}
