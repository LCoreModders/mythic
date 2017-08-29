package com.afg.mythic.superpower.wendigo.abilities;

import lucraft.mods.lucraftcore.abilities.Ability;
import lucraft.mods.lucraftcore.abilities.AbilityToggle;
import lucraft.mods.lucraftcore.client.render.abilitybar.AbilityBarMainHandler;
import lucraft.mods.lucraftcore.util.LucraftCoreUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerFlyableFallEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by AFlyingGrayson on 8/25/17
 */
@Mod.EventBusSubscriber
public class AbilityWendigoJump extends AbilityToggle
{

	private int charge;
	private boolean pressed;
	private boolean hasLanded;

	public AbilityWendigoJump(EntityPlayer player) {
		super(player);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void drawIcon(Minecraft mc, Gui gui, int x, int y) {}

	@Override
	@SideOnly(Side.CLIENT)
	public void drawCooldown(Minecraft mc, Gui gui, int x, int y) {
		mc.renderEngine.bindTexture(AbilityBarMainHandler.hudTex);
		float cooldown = (float)getCharge() / (float)getMaxCharge();
		gui.drawTexturedModalRect(x, y, 0, 33, (int) (cooldown * 18), 3);
	}

	@Override
	public void updateTick() {
		if((!isUnlocked() || !isEnabled()) && isKeyPressed())
			setKeyPressed(false);

		if(isEnabled()) {
			if(isKeyPressed()) {
				setCharge(getCharge() + 1);
				player.setSneaking(true);
			} else if(getCharge() > 0) {
				jump();
				setCharge(0);
			}
		} else if(getCharge() > 0) {
			setCharge(0);
		}
	}

	public void jump() {
		float f = getCharge() / 10F;
		Vec3d vec = player.getLookVec();
		player.addVelocity(vec.x * f * 2F, f/2F, vec.z * f * 2F);
		player.onGround = false;
		player.capabilities.allowFlying = true;
		if(player instanceof EntityPlayerMP)
			((EntityPlayerMP) player).connection.sendPacket(new SPacketEntityVelocity(player));
		this.setHasLanded(false);
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		super.deserializeNBT(nbt);
		this.charge = nbt.getInteger("Charge");
		this.pressed = nbt.getBoolean("Pressed");
		this.hasLanded = nbt.getBoolean("HasLanded");
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = super.serializeNBT();
		nbt.setInteger("Charge", charge);
		nbt.setBoolean("Pressed", pressed);
		nbt.setBoolean("HasLanded", hasLanded);
		return nbt;
	}

	public int getCharge() {
		if(!isUnlocked() || !isEnabled())
			return 0;
		return MathHelper.clamp(charge, 0, getMaxCharge());
	}

	public void setCharge(int charge) {
		this.charge = MathHelper.clamp(charge, 0, getMaxCharge());
		LucraftCoreUtil.sendSuperpowerUpdatePacket(player);
	}

	public int getMaxCharge() {
		return 3 * 20;
	}

	public boolean isKeyPressed() {
		return isUnlocked() && isEnabled() && pressed;
	}

	public void setKeyPressed(boolean pressed) {
		if(isUnlocked() && isEnabled() && this.pressed != pressed) {
			if(!pressed || player.onGround) {
				this.pressed = pressed;
				if(pressed)
					setCharge(0);
				LucraftCoreUtil.sendSuperpowerUpdatePacket(player);
			}
		}
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);

		if(isEnabled()) {
			setCharge(0);
			setKeyPressed(false);
		}
	}

	public boolean hasLanded() {
		return isUnlocked() && isEnabled() && hasLanded;
	}

	public void setHasLanded(boolean hasLanded) {
		if(isUnlocked() && this.hasLanded != hasLanded) {
			this.hasLanded = hasLanded;
			LucraftCoreUtil.sendSuperpowerUpdatePacket(player);
		}
	}

	@SubscribeEvent
	public static void onLivingJump(LivingEvent.LivingJumpEvent e) {
		if(e.getEntityLiving() instanceof EntityPlayer) {
			AbilityWendigoJump ab = Ability.getAbilityFromClass(Ability.getCurrentPlayerAbilities((EntityPlayer) e.getEntityLiving()), AbilityWendigoJump.class);

			if(ab != null && ab.isUnlocked() && ab.isEnabled() && e.getEntityLiving().onGround) {
				e.getEntityLiving().motionY = -1;
			}
		}
	}

	@SubscribeEvent
	public static void onLivingFall(LivingFallEvent e) {
		if(e.getEntityLiving() instanceof EntityPlayer)
			superJumpLanding((EntityPlayer) e.getEntityLiving());
	}

	@SubscribeEvent
	public static void onPlayerFlyableFall(PlayerFlyableFallEvent e) {
		superJumpLanding(e.getEntityPlayer());
	}

	public static void superJumpLanding(EntityPlayer player) {
		if(!player.world.isRemote) {
			AbilityWendigoJump ab = Ability.getAbilityFromClass(Ability.getCurrentPlayerAbilities(player), AbilityWendigoJump.class);

			if(ab != null && ab.isUnlocked() && ab.isEnabled() && !ab.hasLanded()) {
				ab.setHasLanded(true);
			}
		}
	}
}
