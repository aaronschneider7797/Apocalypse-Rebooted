package com.toast.apocalypse.common.core.mod_event;

import com.google.gson.JsonIOException;
import com.toast.apocalypse.common.util.References;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;

public class ThunderstormEvent extends AbstractEvent {

    public ThunderstormEvent(EventType<?> type, ServerPlayerEntity player) {
        super(type, player);
    }

    @Override
    public void onStart(MinecraftServer server) {

    }

    @Override
    public void update() {

    }

    @Override
    public void update(ServerWorld world) {

    }

    @Override
    public void update(PlayerEntity player) {

    }

    @Override
    public void onEnd() {

    }

    @Override
    public CompoundNBT write(CompoundNBT data) {
        return null;
    }

    @Override
    public void read(CompoundNBT data) throws JsonIOException {

    }
}
