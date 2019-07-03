package com.matjojo.desire_paths.core;

import net.minecraft.state.property.IntProperty;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class Util {
    public static final IntProperty DESIRE_PATH_PROPERTY;

    public static final TranslatableText DESIRE_PATHS_MOD_NAME;

    static {
        DESIRE_PATH_PROPERTY = IntProperty.of("desiretramples", 0, TrampleUtil.MAX_TRAMPLE);

        DESIRE_PATHS_MOD_NAME = new TranslatableText("desirepaths.hwylamodname");
    }

    public static Identifier getIdentifier(String whatFor) {
        return new Identifier("desire-paths", whatFor);
    }

}
