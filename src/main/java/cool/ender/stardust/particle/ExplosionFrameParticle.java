package cool.ender.stardust.particle;

import cool.ender.stardust.Stardust;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExplosionFrameParticle extends TextureSheetParticle {

    public ExplosionFrameParticle(ClientLevel p_108323_, double p_108324_, double p_108325_, double p_108326_) {
        super(p_108323_, p_108324_, p_108325_, p_108326_);
    }

    protected ExplosionFrameParticle(ClientLevel p_108328_, double p_108329_, double p_108330_, double p_108331_, SpriteSet spriteSet, double p_108332_, double p_108333_, double p_108334_) {
        super(p_108328_, p_108329_, p_108330_, p_108331_, p_108332_, p_108333_, p_108334_);
        this.setSpriteFromAge(spriteSet);
        this.friction = 0.8f;
        this.quadSize = 0.8f;
        this.lifetime = 2;
        this.hasPhysics = false;
    }

    @Override
    public void tick() {
        Stardust.LOGGER.info("x");
        super.tick();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Nullable
        @Override
        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double t_x, double t_y, double t_z) {
            return new ExplosionFrameParticle(level, x, y, z, this.sprites, t_x, t_y, t_z);
        }
    }
}
