package com.afg.mythic.client;

import com.afg.mythic.network.MessageMSendInfoToServer;
import com.afg.mythic.network.NetworkHandler;
import com.afg.mythic.superpower.vampire.VampirePlayerHandler;
import com.afg.mythic.superpower.vampire.abilities.Embrace;
import com.afg.mythic.superpower.wendigo.WendigoPlayerHandler;
import com.afg.mythic.superpower.wendigo.abilities.AbilityWendigoJump;
import com.afg.mythic.util.MonsterUtil;
import lucraft.mods.lucraftcore.abilities.Ability;
import lucraft.mods.lucraftcore.superpower.SuperpowerHandler;
import lucraft.mods.lucraftcore.superpower.SuperpowerPlayerHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.opengl.GL11;

/**
 * Created by AFlyingGrayson on 8/21/17
 */
public class ClientEventHandler
{
	@SubscribeEvent
	public void renderWorldLast(RenderWorldLastEvent event)
	{
		Minecraft mc = Minecraft.getMinecraft();
		Tessellator t = Tessellator.getInstance();
		BufferBuilder b = t.getBuffer();
		GlStateManager.pushMatrix();
		GlStateManager.translate(-mc.player.posX, -mc.player.posY, -mc.player.posZ);
		GlStateManager.disableTexture2D();
		for (EntityPlayer p : mc.world.playerEntities)
		{
			SuperpowerPlayerHandler playerHandler = SuperpowerHandler.getSuperpowerPlayerHandler(p);
			if (playerHandler instanceof VampirePlayerHandler)
			{
				if (Ability.getAbilityFromClass(playerHandler.getAbilities(), Embrace.class).isEnabled())
				{
					RayTraceResult r = MonsterUtil.rayTrace(p, 3, event.getPartialTicks());
					if (r.entityHit != null)
					{
						Vec3d newVec = r.hitVec.addVector(0, 1, 0);

						Vec3d segDist = newVec.subtract(new Vec3d(p.posX, p.posY + 0.75, p.posZ)).normalize();

						b.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
						for (double i = 0; i < newVec.distanceTo(new Vec3d(p.posX, p.posY + 0.75, p.posZ)); i += 0.1)
						{
							double y = 0.25 * Math.sin((i - p.ticksExisted / 10.0 - event.getPartialTicks()) * Math.PI);
							b.pos(p.posX + segDist.x * i, p.posY + segDist.y * i + y, p.posZ + segDist.z * i).color(200, 0, 0, 125)
									.endVertex();
						}
						b.pos(newVec.x, newVec.y, newVec.z).color(200, 0, 0, 125).endVertex();
						t.draw();

						b.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
						for (double i = 0; i < newVec.distanceTo(new Vec3d(p.posX, p.posY + 0.75, p.posZ)); i += 0.1)
						{
							double y = 0.25 * Math.sin((i - p.ticksExisted / 10.0 - event.getPartialTicks()) * Math.PI);
							b.pos(p.posX + segDist.x * i + y, p.posY + segDist.y * i, p.posZ + segDist.z * i).color(200, 0, 0, 125)
									.endVertex();
						}
						b.pos(newVec.x, newVec.y, newVec.z).color(200, 0, 0, 125).endVertex();
						t.draw();

						b.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
						for (double i = 0; i < newVec.distanceTo(new Vec3d(p.posX, p.posY + 0.75, p.posZ)); i += 0.1)
						{
							double y = 0.25 * Math.sin((i - p.ticksExisted / 10.0 - event.getPartialTicks()) * Math.PI);
							b.pos(p.posX + segDist.x * i, p.posY + segDist.y * i, p.posZ + segDist.z * i + y).color(200, 0, 0, 125)
									.endVertex();
						}
						b.pos(newVec.x, newVec.y, newVec.z).color(200, 0, 0, 125).endVertex();
						t.draw();

					}
				}
			}
		}

		GlStateManager.enableTexture2D();
		GlStateManager.popMatrix();
	}

	public boolean isJumpKeyPressed = false;

	@SubscribeEvent
	public void onKey(InputEvent.KeyInputEvent e) {
		if(Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown() != isJumpKeyPressed) {
			isJumpKeyPressed = !isJumpKeyPressed;

			Ability ab = Ability.getAbilityFromClass(Ability.getCurrentPlayerAbilities(Minecraft.getMinecraft().player), AbilityWendigoJump.class);

			if(ab != null && ab.isUnlocked() && ab.isEnabled()) {
				NetworkHandler.simpleNetworkWrapper.sendToServer(new MessageMSendInfoToServer(MessageMSendInfoToServer.MServerInfoType.SUPER_JUMP_KEY, isJumpKeyPressed ? 1 : 0));
			}
		}
	}
}
