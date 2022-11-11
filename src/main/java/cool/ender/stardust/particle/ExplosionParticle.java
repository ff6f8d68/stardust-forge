package cool.ender.stardust.particle;

import cool.ender.stardust.registry.ParticleRegistry;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExplosionParticle extends TextureSheetParticle {

    int frame = 6;
    int tickPerFrame = 2;

    protected ExplosionParticle(ClientLevel p_108328_, double p_108329_, double p_108330_, double p_108331_, SpriteSet spriteSet, double p_108332_, double p_108333_, double p_108334_) {
        super(p_108328_, p_108329_, p_108330_, p_108331_, p_108332_, p_108333_, p_108334_);
        this.setSpriteFromAge(spriteSet);
        this.friction = 0.8f;
        this.quadSize = 0.8f;
        this.lifetime = frame * tickPerFrame;
        this.alpha = 0;
        this.hasPhysics = false;
    }

    @Override
    public void tick() {
        super.tick();
        if (age == 1) {
            this.level.addParticle((ParticleOptions) ParticleRegistry.PLASMA_EXPLOSION_1.get(), this.x, this.y, this.z, 0, 0, 0);
        }
        if (age == 3) {
            this.level.addParticle((ParticleOptions) ParticleRegistry.PLASMA_EXPLOSION_2.get(), this.x, this.y, this.z, 0, 0, 0);
        }
        if (age == 5) {
            this.level.addParticle((ParticleOptions) ParticleRegistry.PLASMA_EXPLOSION_3.get(), this.x, this.y, this.z, 0, 0, 0);
        }
        if (age == 7) {
            this.level.addParticle((ParticleOptions) ParticleRegistry.PLASMA_EXPLOSION_4.get(), this.x, this.y, this.z, 0, 0, 0);
        }
        if (age == 9) {
            this.level.addParticle((ParticleOptions) ParticleRegistry.PLASMA_EXPLOSION_5.get(), this.x, this.y, this.z, 0, 0, 0);
        }
        if (age == 11) {
            this.level.addParticle((ParticleOptions) ParticleRegistry.PLASMA_EXPLOSION_6.get(), this.x, this.y, this.z, 0, 0, 0);
        }
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
            return new ExplosionParticle(level, x, y, z, this.sprites, t_x, t_y, t_z);
        }
    }
}
