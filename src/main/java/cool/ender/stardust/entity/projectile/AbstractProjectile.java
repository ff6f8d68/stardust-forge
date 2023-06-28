package cool.ender.stardust.entity.projectile;

import cool.ender.stardust.Stardust;
import cool.ender.stardust.component.shield.Shield;
import cool.ender.stardust.component.turret.AbstractTurret;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;
import software.bernie.geckolib3.util.GeckoLibUtil;


abstract public class AbstractProjectile {

    public abstract static class Entity extends AbstractHurtingProjectile implements IAnimatable {
        public AnimationFactory factory = GeckoLibUtil.createFactory(this);
        public boolean avoidFlag = false;

        public AbstractTurret.Tile owner;
        public Entity(EntityType<? extends AbstractHurtingProjectile> p_36833_, Level p_36834_) {
            super(p_36833_, p_36834_);
        }

        public Entity(EntityType<? extends AbstractHurtingProjectile> p_36817_, double p_36818_, double p_36819_, double p_36820_, double p_36821_, double p_36822_, double p_36823_, Level p_36824_, AbstractTurret.Tile owner) {
            this(p_36817_, p_36824_);
            this.moveTo(p_36818_, p_36819_, p_36820_, this.getYRot(), this.getXRot());
            this.reapplyPosition();
            double d0 = Math.sqrt(p_36821_ * p_36821_ + p_36822_ * p_36822_ + p_36823_ * p_36823_);
            if (d0 != 0.0D) {
                this.xPower = p_36821_ / d0 * 0.1D;
                this.yPower = p_36822_ / d0 * 0.1D;
                this.zPower = p_36823_ / d0 * 0.1D;
            }
            this.owner = owner;
        }

        public Entity(EntityType<? extends AbstractHurtingProjectile> p_36826_, LivingEntity p_36827_, double p_36828_, double p_36829_, double p_36830_, Level p_36831_, AbstractTurret.Tile owner) {
            this(p_36826_, p_36827_.getX(), p_36827_.getY(), p_36827_.getZ(), p_36828_, p_36829_, p_36830_, p_36831_, owner);
            this.setOwner(p_36827_);
            this.setRot(p_36827_.getYRot(), p_36827_.getXRot());
            this.owner = owner;
        }

        public enum ProjectileType {
            ENERGY_LASER, ENERGY_PLASMA, ENERGY_SUBSTANCE_DECOMPOSER, KINETIC_ARMOR_PIERCING, KINETIC_HIGHLY_EXPLOSIVE, KINETIC_BULLET, KINETIC_ORDNANCE_PENETRATOR
        }

        @Override
        protected void onHit(HitResult result) {
            if (!this.level.isClientSide) {
                HitResult.Type type = result.getType();
                if (type == HitResult.Type.BLOCK) {
                    BlockHitResult blockHitResult = (BlockHitResult) result;
                    BlockEntity blockEntity = this.level.getBlockEntity(blockHitResult.getBlockPos());
                    Stardust.LOGGER.info(blockEntity);
                    if (blockEntity instanceof Shield.Tile) {
                        if (this.owner.relatedShieldGenerators.contains(((Shield.Tile) blockEntity).getOwner())) {
                            Stardust.LOGGER.info("flag_changed");
                            this.avoidFlag = true;
                            return;
                        }
                    }
                }
            }
            super.onHit(result);
        }


        @Override
        public void recreateFromPacket(@NotNull ClientboundAddEntityPacket p_150128_) {
            super.recreateFromPacket(p_150128_);
        }

        @Override
        public AnimationFactory getFactory() {
            return factory;
        }
    }

    public abstract static class Model extends AnimatedGeoModel<Entity>{

    }

    public abstract static class Renderer extends GeoProjectilesRenderer<Entity> {

        public Renderer(EntityRendererProvider.Context renderManager, AnimatedGeoModel<Entity> modelProvider) {
            super(renderManager, modelProvider);
        }
    }

    public abstract static class Listener {
        @Mod.EventBusSubscriber(modid = Stardust.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
        public abstract static class ClientListener {

        }

        public abstract static class ServerListener {

        }
    }
}
