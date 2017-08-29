package com.afg.mythic.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import java.util.List;

/**
 * Created by AFlyingGrayson on 8/20/17
 */
public class MonsterUtil
{
	public static RayTraceResult rayTrace(EntityLivingBase entityLivingBase, double blockReachDistance, float partialTicks)
	{
		Vec3d vec3d = entityLivingBase.getPositionEyes(partialTicks);
		Vec3d vec3d1 = entityLivingBase.getLook(partialTicks);
		Vec3d vec3d2 = vec3d.addVector(vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance);
		RayTraceResult result = entityLivingBase.world.rayTraceBlocks(vec3d, vec3d2, false, false, true);
		Entity entity = findEntityOnPath(entityLivingBase, vec3d, result.hitVec);
		result = (entity == null) ? result : new RayTraceResult(entity);
		return result;
	}
	private static Entity findEntityOnPath(EntityLivingBase entityLivingBase, Vec3d start, Vec3d end)
	{
		Entity entity = null;
		List<Entity> list = entityLivingBase.world.getEntitiesWithinAABBExcludingEntity(entityLivingBase, entityLivingBase.getEntityBoundingBox().expand(end.x - start.x,
				end.y - start.y, end.z - start.z).grow(1.0D));
		double d0 = 0.0D;

		for (int i = 0; i < list.size(); ++i)
		{
			Entity entity1 = list.get(i);

				AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow(0.30000001192092896D);
				RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(start, end);

				if (raytraceresult != null)
				{
					double d1 = start.squareDistanceTo(raytraceresult.hitVec);

					if (d1 < d0 || d0 == 0.0D)
					{
						entity = entity1;
						d0 = d1;
					}
				}
		}

		return entity;
	}

	public static boolean isSentient(EntityLivingBase entityLivingBase){
		if(entityLivingBase instanceof EntityVillager)
			return true;
		if(entityLivingBase instanceof EntityPlayer)
			return true;
		return false;
	}
}
