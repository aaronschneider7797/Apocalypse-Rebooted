package com.toast.apocalypse.common.register;

import com.toast.apocalypse.common.core.Apocalypse;
import com.toast.apocalypse.common.item.BucketHelmItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.function.Supplier;

public class ApocalypseItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Apocalypse.MODID);


    public static final RegistryObject<Item> BUCKET_HELM = registerItem("bucket_helm", () -> new BucketHelmItem(new Item.Properties().tab(ItemGroup.TAB_COMBAT).durability(82)));
    public static final RegistryObject<SpawnEggItem> GHOST_SPAWN_EGG = registerSpawnEgg("ghost", ApocalypseEntities.GHOST_TYPE, 0xBCBCBC, 0x708899);


    private static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<T> itemSupplier) {
        return ITEMS.register(name, itemSupplier);
    }

    private static <T extends Item, E extends LivingEntity> RegistryObject<SpawnEggItem> registerSpawnEgg(String name, EntityType<E> entityType, int primaryColor, int secondaryColor) {
        return ITEMS.register(name + "_spawn_egg", () -> new SpawnEggItem(entityType, primaryColor, secondaryColor, new Item.Properties().tab(ItemGroup.TAB_MISC)));
    }
}