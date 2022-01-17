package com.myopicmobile.textwarrior.language.xml;

import com.myopicmobile.textwarrior.base.BaseLanguage;


public class LanguageXml extends BaseLanguage {

    private final static String[] keywords = {
            "activity", "manifest", "application"
    };
    private final static String[] basicNames = {
            "intent-filter", "meta", "style"
    };
    private final static char[] BASIC_Java_OPERATORS = {
            '<', '/', '>', '=', '-'
    };
    private static LanguageXml mLanguage = null;
    private static LexerXml mLexer = null;

    private LanguageXml() {
        setOperators(BASIC_Java_OPERATORS);
        setKeywords(keywords);
        setNames(basicNames);//先setName才能addName
    }

    public static LanguageXml getInstance() {
        if (mLanguage == null) {
            mLanguage = new LanguageXml();
        }
        if (mLexer == null) {
            mLexer = new LexerXml();
        }
        mLanguage.setLexer(mLexer);
        return mLanguage;
    }
}
