package com.matjojo.desire_paths.core;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.state.property.IntegerProperty;
import net.minecraft.util.Identifier;

public class Util {
    public static final IntegerProperty DESIRE_PATH_PROPERTY;

    public static final TranslatableComponent DESIRE_PATHS_MOD_NAME;

    static {
        DESIRE_PATH_PROPERTY = IntegerProperty.create("desiretramples", 0, TrampleUtil.MAX_TRAMPLE);

        DESIRE_PATHS_MOD_NAME = new TranslatableComponent("desirepaths.hwylamodname");
    }

    public static Identifier getIdentifier(String whatFor) {
        return new Identifier("desire-paths", whatFor);
    }

}
