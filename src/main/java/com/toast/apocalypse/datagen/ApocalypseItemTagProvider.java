package com.toast.apocalypse.datagen;

import com.toast.apocalypse.common.core.Apocalypse;
import com.toast.apocalypse.common.core.register.ApocalypseItems;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ApocalypseItemTagProvider extends ItemTagsProvider {

    public ApocalypseItemTagProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagsProvider, Apocalypse.MODID, existingFileHelper);
    }

    protected void addTags() {
        tag(Tags.Items.INGOTS)
                .add(ApocalypseItems.LUNARIUM_INGOT.get());

        tag(ItemTags.BEACON_PAYMENT_ITEMS)
                .add(ApocalypseItems.LUNARIUM_INGOT.get());
    }
}
