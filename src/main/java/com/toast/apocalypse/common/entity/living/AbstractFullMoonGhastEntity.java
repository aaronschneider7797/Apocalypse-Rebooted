package com.toast.apocalypse.common.entity.living;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class AbstractFullMoonGhastEntity extends GhastEntity implements IFullMoonMob {

    protected UUID playerTargetUUID;

    public AbstractFullMoonGhastEntity(EntityType<? extends GhastEntity> entityType, World world) {
        super(entityType, world);
    }

    /**
     * Used for the seeker and destroyer when shooting fireballs
     * at their target. Only X and Z distance is checked to prevent
     * players from being able to hide far underground to avoid
     * the constant barrage of explosions that is waiting for them.
     */
    public final double horizontalDistanceToSqr(Entity entity) {
        return this.horizontalDistanceToSqr(entity.position());
    }

    public final double horizontalDistanceToSqr(Vector3d vec) {
        double x = this.getX() - vec.x;
        double z = this.getZ() - vec.z;
        return x * x + z * z;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true; // Immune to drowning
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    public SoundCategory getSoundSource() {
        return SoundCategory.HOSTILE;
    }

    /**
     * Checks if the seeker actually has direct
     * line of sight to the target entity.
     */
    public boolean canSeeDirectly(Entity entity) {
        Vector3d vector3d = new Vector3d(this.getX(), this.getEyeY(), this.getZ());
        Vector3d vector3d1 = new Vector3d(entity.getX(), entity.getEyeY(), entity.getZ());
        return this.level.clip(new RayTraceContext(vector3d, vector3d1, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this)).getType() == RayTraceResult.Type.MISS;
    }

    @Nullable
    @Override
    public UUID getPlayerTargetUUID() {
        return this.playerTargetUUID;
    }

    @Override
    public void setPlayerTargetUUID(@Nullable UUID playerTargetUUID) {
        this.playerTargetUUID = playerTargetUUID;
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);

        if (this.getPlayerTargetUUID() != null) {
            compoundNBT.putUUID(PLAYER_UUID_KEY, this.getPlayerTargetUUID());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);

        if (compoundNBT.hasUUID(PLAYER_UUID_KEY)) {
            this.setPlayerTargetUUID(compoundNBT.getUUID(PLAYER_UUID_KEY));
        }
    }

    /** Slightly modified version of the ghast's movement controller */
    protected static class MoveHelperController extends MovementController {

        private final AbstractFullMoonGhastEntity ghast;
        private int floatDuration;
        private boolean canReachCurrent;

        public MoveHelperController(AbstractFullMoonGhastEntity ghast) {
            super(ghast);
            this.ghast = ghast;
        }

        @Override
        public void setWantedPosition(double x, double y, double z, double speedMod) {
            super.setWantedPosition(x, y, z, speedMod);
        }

        public void setAction(Action action) {
            this.operation = action;
        }

        @Override
        public void tick() {
            if (operation == Action.MOVE_TO) {
                if (floatDuration-- <= 0) {
                    floatDuration += ghast.getRandom().nextInt(5) + 2;
                    Vector3d vector3d = new Vector3d(wantedX - ghast.getX(), wantedY - ghast.getY(), wantedZ - ghast.getZ());
                    vector3d = vector3d.normalize();

                    canReachCurrent = canReach(vector3d);

                    if (canReachCurrent) {
                        ghast.setDeltaMovement(ghast.getDeltaMovement().add(vector3d.scale(0.1D)));
                    }
                    else {
                        operation = Action.WAIT;
                    }
                }
            }
        }

        private boolean canReach(Vector3d vec) {
            AxisAlignedBB axisalignedbb = ghast.getBoundingBox().inflate(0.5F);

            axisalignedbb = axisalignedbb.move(vec);
            return ghast.level.noCollision(ghast, axisalignedbb);
        }

        public boolean canReachCurrentWanted() {
            return canReachCurrent;
        }
    }
}
