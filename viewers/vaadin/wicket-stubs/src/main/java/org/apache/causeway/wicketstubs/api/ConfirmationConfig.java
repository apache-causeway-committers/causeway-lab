package org.apache.causeway.wicketstubs.api;

import lombok.NonNull;

public class ConfirmationConfig {
    public ConfirmationConfig withTitle(@NonNull String title) {
        return new ConfirmationConfig().withTitle(title);
    }

    public ConfirmationConfig withBtnOkLabel(@NonNull String okLabel) {
        return new ConfirmationConfig().withBtnOkLabel(okLabel);
    }

    public ConfirmationConfig withBtnCancelLabel(@NonNull String cancelLabel) {
        return new ConfirmationConfig().withBtnCancelLabel(cancelLabel);
    }

    public ConfirmationConfig withBtnOkClass(String s) {
        return new ConfirmationConfig().withBtnOkClass(s);
    }

    public TooltipConfig withBtnCancelClass(String s) {
        return null;
    }
}
