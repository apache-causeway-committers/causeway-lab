package org.apache.causeway.wicketstubs.api;

import java.time.Duration;

public class TooltipConfig
        //extends AbstractConfig
{
    public TooltipConfig withDelay(Duration zero) {
        return new TooltipConfig().withDelay(zero);
    }

    public TooltipConfig withAnimation(boolean b) {
        return new TooltipConfig().withAnimation(b);
    }

    public TooltipConfig withHtml(boolean html) {
        return new TooltipConfig().withHtml(html);
    }

    public PopoverConfig withSanitizer(boolean b) {
        return new TooltipConfig().withSanitizer(b);
    }
}
