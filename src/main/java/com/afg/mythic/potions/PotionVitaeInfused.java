package com.afg.mythic.potions;

import com.afg.mythic.Mythic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

/**
 * Created by AFlyingGrayson on 8/21/17
 */
public class PotionVitaeInfused extends Potion
{
	public static PotionVitaeInfused POTION = new PotionVitaeInfused();

	public boolean shouldRender(PotionEffect effect) {
		return true;
	}

	public boolean hasStatusIcon() {
		return false;
	}

	protected PotionVitaeInfused()
	{
		super(false, 660000);
		this.setPotionName("potion.vitaeInfused");
		this.setRegistryName(new ResourceLocation(Mythic.MODID, "vitaeInfused"));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
		if(effect.getPotion() == this) {
			GlStateManager.pushMatrix();
			GlStateManager.enableBlend();
			GL11.glBlendFunc(770, 771);
			mc.getRenderItem().renderItemIntoGUI(Items.TOTEM_OF_UNDYING.getDefaultInstance(), x + 8, y + 8);
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
		}

	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
		if(effect.getPotion() == this) {
			GlStateManager.pushMatrix();
			GlStateManager.enableBlend();
			GL11.glBlendFunc(770, 771);
			mc.getRenderItem().renderItemIntoGUI(Items.TOTEM_OF_UNDYING.getDefaultInstance(), x + 4, y + 4);
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
		}

	}

}
