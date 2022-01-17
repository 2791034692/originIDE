package com.myopicmobile.textwarrior.language.java;


import com.myopicmobile.textwarrior.base.BaseLanguage;
import com.myopicmobile.textwarrior.base.BasePanel;

public class LanguageJava extends BaseLanguage {

    private final static String[] keywords = {
            "void", "boolean", "byte", "char", "short", "int", "long", "float", "double", "strictfp",
            "import", "package", "new", "class", "interface", "extends", "implements", "enum",
            "public", "private", "protected", "static", "abstract", "final", "native", "volatile",
            "assert", "try", "throw", "throws", "catch", "finally", "instanceof", "super", "this",
            "if", "else", "for", "do", "while", "switch", "case", "default",
            "continue", "break", "return", "synchronized", "transient",
            "true", "false", "null"
    };
    private final static String[] basicNames = {
            "String", "CharSequence", "Integer", "Float", "Long", "Byte", "Character",
            "Double"
    };
    private final static String[] exNames = {
            "S5dActivity", "getContext()", "取实例()"
    };
    private final static char[] BASIC_Java_OPERATORS = {
            '(', ')', '{', '}', '.', ',', ';', '=', '+', '-',
            '/', '*', '&', '!', '|', ':', '[', ']', '<', '>',
            '?', '~', '%', '^'
    };
    private static com.myopicmobile.textwarrior.language.java.LanguageJava mLanguage = null;
    private static LexerJava mLexer = null;

    private LanguageJava() {
        setOperators(BASIC_Java_OPERATORS);
        setKeywords(keywords);
        setNames(basicNames);//先setName才能addName
    }

    public static com.myopicmobile.textwarrior.language.java.LanguageJava getInstance() {
        if (mLanguage == null) {
            mLanguage = new com.myopicmobile.textwarrior.language.java.LanguageJava();
        }
        if (mLexer == null) {
            mLexer = new LexerJava();

        }
        mLanguage.setLexer(mLexer);
        return mLanguage;
    }

    @Override
    public String[] getKeywords() {
        return keywords;
    }

    @Override
    public void setCodePanel(BasePanel mCodePanel) {
        super.setCodePanel(mCodePanel);
        mCodePanel.setLanguage(getInstance());
    }
}
