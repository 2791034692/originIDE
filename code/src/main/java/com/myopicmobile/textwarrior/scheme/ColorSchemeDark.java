package com.myopicmobile.textwarrior.scheme;


public class ColorSchemeDark extends ColorScheme {

    public ColorSchemeDark() {
        setColor(Colorable.FOREGROUND, 0xFFD0D2D3);
        //setColor(Colorable.BACKGROUND, 0xFF293134);
        setColor(Colorable.BACKGROUND, 0xff262626);
        setColor(Colorable.NON_PRINTING_GLYPH, 0xFF606060);
        setColor(Colorable.SELECTION_BACKGROUND, -1268224);
        setColor(Colorable.KEYWORD, 0xff569cd6);
        //setColor(Colorable.KEYWORD, -7092381);
        //setColor(Colorable.NAME, -6257987);
        //setColor(Colorable.NAME, 0xFF678CB1);
        setColor(Colorable.CLASS, 0xff4ec9b0);
        setColor(Colorable.COMMENT, 0xff60ae6f);
        setColor(Colorable.NUMBER, 0xffd69d85);
        setColor(Colorable.VARIANT, 0xff9b9bc8);
        //setColor(Colorable.STRING, -1280512);
        setColor(Colorable.STRING, 0xffbd63c5);
    }

    @Override
    public boolean isDark() {
        return true;
    }
}
