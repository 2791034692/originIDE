package com.myopicmobile.textwarrior.scheme;

public class ColorSchemeLight extends ColorScheme {

    private static final int OFF_WHITE = 0xFFF0F0ED;
    private static final int OFF_BLACK = 0xFF333333;

    public ColorSchemeLight() {
        setColor(Colorable.FOREGROUND, OFF_BLACK);
        setColor(Colorable.BACKGROUND, OFF_WHITE);
        setColor(Colorable.SELECTION_FOREGROUND, OFF_WHITE);
        setColor(Colorable.CARET_FOREGROUND, OFF_WHITE);
    }

    @Override
    public boolean isDark() {
        return false;
    }
}
