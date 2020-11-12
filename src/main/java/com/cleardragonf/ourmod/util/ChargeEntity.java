package com.cleardragonf.ourmod.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ChargeEntity extends ProjectileItemEntity {
    public ChargeEntity(EntityType<? extends net.minecraft.entity.projectile.SnowballEntity> p_i50159_1_, World p_i50159_2_) {
        super(p_i50159_1_, p_i50159_2_);
    }

    public ChargeEntity(World worldIn, LivingEntity throwerIn) {
        super(EntityType.SNOWBALL, throwerIn, worldIn);
    }

    public ChargeEntity(World worldIn, double x, double y, double z) {
        super(EntityType.SNOWBALL, x, y, z, worldIn);
    }

    protected Item getDefaultItem() {
        return Items.SNOWBALL;
    }

    @OnlyIn(Dist.CLIENT)
    private IParticleData makeParticle() {
        ItemStack itemstack = this.func_213882_k();
        return (IParticleData)(itemstack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemParticleData(ParticleTypes.ITEM, itemstack));
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 3) {
            IParticleData iparticledata = this.makeParticle();

            for(int i = 0; i < 8; ++i) {
                this.world.addParticle(iparticledata, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    public void onImpact(RayTraceResult result) {
        if (result.getType() == RayTraceResult.Type.ENTITY) {
            Entity entity = ((EntityRayTraceResult)result).getEntity();
            int i = entity instanceof BlazeEntity ? 3 : 0;
            entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getEntity()), (float)i);
            if(entity instanceof ZombieEntity){
                ItemStack stack = Items.ZOMBIE_SPAWN_EGG.getDefaultInstance();
                world.addEntity(new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), stack));
                ((ZombieEntity)entity).remove();
            }
            else if(entity instanceof SkeletonEntity){
                ItemStack stack = Items.SKELETON_SPAWN_EGG.getDefaultInstance();
                world.addEntity(new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), stack));
                ((SkeletonEntity)entity).remove();
            }
            else if(entity instanceof SpiderEntity){
                ItemStack stack = Items.SPIDER_SPAWN_EGG.getDefaultInstance();
                world.addEntity(new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), stack));
                ((SpiderEntity)entity).remove();
            }
            else if(entity instanceof SlimeEntity){
                ItemStack stack = Items.SLIME_SPAWN_EGG.getDefaultInstance();
                world.addEntity(new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), stack));
                ((SlimeEntity)entity).remove();
            }
            else if(entity instanceof CreeperEntity){
                ItemStack stack = Items.CREEPER_SPAWN_EGG.getDefaultInstance();
                world.addEntity(new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), stack));
                ((CreeperEntity)entity).remove();
            }
            else if(entity instanceof EndermanEntity){
                ItemStack stack = Items.ENDERMAN_SPAWN_EGG.getDefaultInstance();
                world.addEntity(new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), stack));
                ((EndermanEntity)entity).remove();
            }
            else if(entity instanceof GhastEntity){
                ItemStack stack = Items.GHAST_SPAWN_EGG.getDefaultInstance();
                world.addEntity(new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), stack));
                ((GhastEntity)entity).remove();
            }
        }

        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)3);
            this.remove();
        }

    }
}