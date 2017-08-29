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
public class PotionAnemic extends Potion
{
	public static PotionAnemic POTION = new PotionAnemic();

	public boolean shouldRender(PotionEffect effect) {
		return true;
	}

	public boolean hasStatusIcon() {
		return false;
	}

	protected PotionAnemic()
	{
		super(true, 660000);
		this.setPotionName("potion.anemic");
		this.setRegistryName(new ResourceLocation(Mythic.MODID, "anemic"));

	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
		if(effect.getPotion() == this) {
			GlStateManager.pushMatrix();
			GlStateManager.enableBlend();
			GL11.glBlendFunc(770, 771);
			mc.getRenderItem().renderItemIntoGUI(Items.FEATHER.getDefaultInstance(), x + 8, y + 8);
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
			mc.getRenderItem().renderItemIntoGUI(Items.FEATHER.getDefaultInstance(), x + 4, y + 4);
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
		}

	}
}
