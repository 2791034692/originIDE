package com.myopicmobile.textwarrior.scheme;

import com.myopicmobile.textwarrior.base.BaseLexer;
import com.myopicmobile.textwarrior.common.TextWarriorException;

import java.util.HashMap;

public abstract class ColorScheme {
    // In ARGB format: 0xAARRGGBB
    private static final int BLACK = 0xFF293134;
    private static final int BLUE = 0xFF0000FF;
    private static final int DARK_RED = 0xFFA31515;
    private static final int PINK = 0xFFD040DD;
    private static final int GREY = 0xFF808080;
    private static final int ORANGE = -1280512;
    private static final int LIGHT_GREY = 0xFFAAAAAA;
    private static final int MAROON = 0xFF800000;
    private static final int INDIGO = 0xFF2A40FF;
    private static final int OLIVE_GREEN = 0xFF3F7F5F;
    private static final int PURPLE = 0xFFDD4488;
    private static final int RED = 0xFFFF0000;
    private static final int WHITE = 0xFFFFFFE0;
    private static final int LIGHT_BLUE = 0xFF6080FF;
    private static final int LIGHT_BLUE2 = 0xFF40B0FF;
    private static final int GREEN = 0xFF88AA88;
    protected HashMap<Colorable, Integer> _colors = generateDefaultColors();

    public void setColor(Colorable colorable, int color) {
        _colors.put(colorable, color);
    }

    public int getColor(Colorable colorable) {
        Integer color = _colors.get(colorable);
        if (color == null) {
            TextWarriorException.fail("Color not specified for " + colorable);
            return 0;
        }
        return color.intValue();
    }

    // Currently, color scheme is tightly coupled with semantics of the token types
    public int getTokenColor(int tokenType) {
        Colorable element;
        switch (tokenType) {
            case BaseLexer.OPERATOR:
                element = Colorable.OPERATOR;
                break;
            case BaseLexer.NORMAL:
                element = Colorable.FOREGROUND;
                break;
            case BaseLexer.KEYWORD:
                element = Colorable.KEYWORD;
                break;
            case BaseLexer.CLASS:
                element = Colorable.CLASS;
                break;
            case BaseLexer.FUNCTION:
                element = Colorable.FUNCTION;
                break;
            case BaseLexer.VARIANT:
                element = Colorable.VARIANT;
                break;
            case BaseLexer.DOUBLE_SYMBOL_LINE: //fall-through
            case BaseLexer.DOUBLE_SYMBOL_DELIMITED_MULTILINE:
                //case Lexer.SINGLE_SYMBOL_LINE_B:
                element = Colorable.COMMENT;
                break;
            case BaseLexer.SINGLE_SYMBOL_DELIMITED_A: //fall-through
            case BaseLexer.SINGLE_SYMBOL_DELIMITED_B:
                element = Colorable.STRING;
                break;
            case BaseLexer.NUMBER:
                element = Colorable.OPERATOR;
                break;
            case BaseLexer.SINGLE_SYMBOL_LINE_A: //fall-through
            case BaseLexer.SINGLE_SYMBOL_WORD:
                element = Colorable.SECONDARY;
                break;
            case BaseLexer.SINGLE_SYMBOL_LINE_B: //类型
                element = Colorable.CLASS;
                break;
            default:
                TextWarriorException.fail("Invalid token type");
                element = Colorable.FOREGROUND;
                break;
        }
        return getColor(element);
    }

    /**
     * Whether this color scheme uses a dark background, like black or dark grey.
     */
    public abstract boolean isDark();

    private HashMap<Colorable, Integer> generateDefaultColors() {
        // High-contrast, black-on-white color scheme
        HashMap<Colorable, Integer> colors = new HashMap<Colorable, Integer>(Colorable.values().length);
        colors.put(Colorable.FOREGROUND, BLACK);//前景色
        colors.put(Colorable.BACKGROUND, WHITE);
        colors.put(Colorable.SELECTION_FOREGROUND, WHITE);//选择文本的前景色
        colors.put(Colorable.SELECTION_BACKGROUND, LIGHT_GREY);//选择文本的背景色
        colors.put(Colorable.CARET_FOREGROUND, WHITE);
        colors.put(Colorable.CARET_BACKGROUND, LIGHT_BLUE2);
        colors.put(Colorable.CARET_DISABLED, GREY);
        colors.put(Colorable.LINE_HIGHLIGHT, 0x20888888);
        colors.put(Colorable.NON_PRINTING_GLYPH, 0xFF000000);//行号
        colors.put(Colorable.COMMENT, OLIVE_GREEN); //注释
        colors.put(Colorable.KEYWORD, 0XFF0000FF);//关键字
        colors.put(Colorable.CLASS, 0XFF810000); // Eclipse default color
        colors.put(Colorable.NUMBER, 0XFF148B8B); // 数字
        colors.put(Colorable.STRING, 0XFF148B8B); //字符串
        colors.put(Colorable.VARIANT, BLACK); //变量
        colors.put(Colorable.FUNCTION, 0XFF810000); //方法
        colors.put(Colorable.OPERATOR, 0XFF000081);
        return colors;
    }

    public enum Colorable {
        FOREGROUND, BACKGROUND, SELECTION_FOREGROUND, SELECTION_BACKGROUND,
        CARET_FOREGROUND, CARET_BACKGROUND, CARET_DISABLED, LINE_HIGHLIGHT,
        NON_PRINTING_GLYPH, COMMENT, KEYWORD, CLASS, FUNCTION, NUMBER, STRING,
        SECONDARY, VARIANT, OPERATOR, IDENTIFIER
    }
}
