package com.matjojo.desire_paths.wailaSupport;

import com.matjojo.desire_paths.data.Blocks.Trampleable;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;

public class WailaPlugin implements IWailaPlugin {

    @Override
    public void register(IRegistrar registrar) {
        registrar.registerComponentProvider(WailaNameComponentProvider.INSTANCE, TooltipPosition.HEAD, Trampleable.class);
        registrar.registerComponentProvider(WailaNameComponentProvider.INSTANCE, TooltipPosition.TAIL, Trampleable.class);
        registrar.registerStackProvider(WailaNameComponentProvider.INSTANCE, Trampleable.class);
    }
}
