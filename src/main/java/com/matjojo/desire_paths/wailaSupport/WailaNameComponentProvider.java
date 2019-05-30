package com.matjojo.desire_paths.wailaSupport;

import com.matjojo.desire_paths.core.Util;
import mcp.mobius.waila.Waila;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.ITaggableList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Identifier;

import java.util.List;

public class WailaNameComponentProvider implements IComponentProvider {

    private static final Identifier MOD_NAME_TAG = new Identifier(Waila.MODID, "mod_name");
    private static final Identifier OBJECT_NAME_TAG = new Identifier(Waila.MODID, "object_name");
    static IComponentProvider INSTANCE = new WailaNameComponentProvider();

    @Override
    public void appendHead(List<Component> tooltip, IDataAccessor accessor, IPluginConfig config) {

        String translatedName = accessor.getBlock().getTextComponent().getFormattedText();

        String blockNameFormatted = String.format(Waila.CONFIG.get().getFormatting().getBlockName(), translatedName);
        ((ITaggableList<Identifier, Component>) tooltip).setTag(OBJECT_NAME_TAG, new TextComponent(blockNameFormatted));
    }

    @Override
    public void appendTail(List<Component> tooltip, IDataAccessor accessor, IPluginConfig config) {

        String modNameFormatted = String.format(Waila.CONFIG.get().getFormatting().getModName(), Util.DESIRE_PATHS_MOD_NAME.getText());

        ((ITaggableList<Identifier, Component>) tooltip).setTag(MOD_NAME_TAG, new TextComponent(modNameFormatted));
    }
}
