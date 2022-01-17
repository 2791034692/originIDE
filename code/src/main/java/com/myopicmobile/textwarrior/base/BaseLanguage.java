/*
 * Copyright (c) 2013 Tah Wei Hoon.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0,
 * with full text available at http://www.apache.org/licenses/LICENSE-2.0.html
 *
 * This software is provided "as is". Use at your own risk.
 */
package com.myopicmobile.textwarrior.base;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Base class for programming language syntax.
 * By default, C-like symbols and operators are included, but not keywords.
 */
public abstract class BaseLanguage {

    public final static char EOF = '\uFFFF';
    public final static char NULL_CHAR = '\u0000';
    public final static char NEWLINE = '\n';
    public final static char BACKSPACE = '\b';
    public final static char TAB = '\t';
    public final static String GLYPH_NEWLINE = "\u21b5";
    public final static String GLYPH_SPACE = "\u00b7";
    public final static String GLYPH_TAB = "\u00bb";

    private final static char[] BASIC_OPERATORS = {
            '(', ')', '{', '}', '.', ',', ';', '=', '+', '-',
            '/', '*', '&', '!', '|', ':', '[', ']', '<', '>',
            '?', '~', '%', '^'
    };

    protected HashMap<String, String> mVarMap = new HashMap<>();
    protected HashMap<String, Integer> mKeywordMap = new HashMap<>(0);
    protected HashMap<String, Integer> mClassMap = new HashMap<>(0);
    protected HashMap<String, String[]> mMethodMap = new HashMap<>(0);
    protected HashMap<String, String[]> mStaticMethodMap = new HashMap<>(0);
    protected HashMap<String, Integer> mUserMap = new HashMap<>(0);
    protected HashMap<Character, Integer> mOperatorMap = generateOperators(BASIC_OPERATORS);
    private ArrayList<String> _userCache = new ArrayList<>(0);
    private String[] userWords = new String[0];
    private String[] keywords;
    private String[] classes;
    private BasePanel mCodePanel;
    private BaseLexer mLexer;
    private BaseCodeTree mCodeTree;

    public BaseLanguage() {
    }

    public BasePanel getCodePanel() {
        return mCodePanel;
    }

    public void setCodePanel(BasePanel mCodePanel) {
        this.mCodePanel = mCodePanel;
        mCodePanel.setLanguage(this);
    }

    public BaseLexer getLexer() {
        return this.mLexer;
    }

    public void setLexer(BaseLexer lexer) {
        this.mLexer = lexer;
        mLexer.setLanguage(this);
    }

    public BaseCodeTree getCodeTree() {
        return this.mCodeTree == null ? new BaseCodeTree() : this.mCodeTree;
    }

    public void setCodeTree(BaseCodeTree tree) {
        this.mCodeTree = tree;
    }

    public void updateUserWord() {
        String[] uw = new String[_userCache.size()];
        userWords = _userCache.toArray(uw);
    }

    public String[] getUserWord() {
        return userWords;
    }

    public String[] getNames() {
        return classes;
    }

    public void setNames(String[] names) {
        classes = names;
        ArrayList<String> buf = new ArrayList<>();
        mClassMap = new HashMap<>(names.length);
        for (String name : names) {
            if (!buf.contains(name))
                buf.add(name);
            mClassMap.put(name, BaseLexer.CLASS);
        }
        classes = new String[buf.size()];
        buf.toArray(classes);
    }

    public String[] getBasePackage(String name) {
        if (mMethodMap.containsKey(name.toUpperCase())) {
            return mMethodMap.get(name.toUpperCase());
        }
        return mMethodMap.get(name);
    }

    public CharSequence format(CharSequence sequence) {
        return sequence;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keywords) {
        keywords = new String[keywords.length];
        for (int i = 0; i < keywords.length; i++) {
            keywords[i] = keywords[i] + "[keyword]";
        }

        mKeywordMap = new HashMap<>(keywords.length);
        for (int i = 0; i < keywords.length; ++i) {
            mKeywordMap.put(keywords[i], BaseLexer.KEYWORD);
        }
    }

    public void addNames(String[] names) {
        String[] old = getNames();
        String[] news = new String[old.length + names.length];
        System.arraycopy(names, 0, news, old.length, names.length);
        System.arraycopy(old, 0, news, 0, old.length);
        setNames(news);
    }

    public void addName(String name) {
        if (name != null) {
            if (!mClassMap.containsKey(name) && !name.equals("")) {
                String[] newArray = new String[classes.length + 1];
                newArray[newArray.length - 1] = name;
                classes = newArray;
                mClassMap.put(name, BaseLexer.CLASS);
            }
        }
    }

    public void addBasePackage(String name, String[] names) {
        addUserWord(name);
        updateUserWord();
        if (!mMethodMap.containsKey(name)) {
            mMethodMap.put(name, names);
        }
    }

    public void clearUserWord() {
        _userCache.clear();
        mUserMap.clear();
    }

    public void addUserWord(String name) {
        if (!_userCache.contains(name) && !mClassMap.containsKey(name))
            _userCache.add(name);
        mUserMap.put(name, BaseLexer.NORMAL);
    }

    protected void setOperators(char[] operators) {
        mOperatorMap = generateOperators(operators);
    }

    private HashMap<Character, Integer> generateOperators(char[] operators) {
        HashMap<Character, Integer> operatorsMap = new HashMap<>(operators.length);
        for (int i = 0; i < operators.length; ++i) {
            operatorsMap.put(operators[i], BaseLexer.OPERATOR);
        }
        return operatorsMap;
    }

    public final boolean isOperator(char c) {
        return mOperatorMap.containsKey(Character.valueOf(c));
    }

    public final boolean isKeyword(String s) {
        return mKeywordMap.containsKey(s);
    }

    public final boolean isName(String s) {
        return mClassMap.containsKey(s);
    }

    public final boolean isBasePackage(String s) {
        return mMethodMap.containsKey(s) || mMethodMap.containsKey(s.toUpperCase());
    }

    public final boolean isBaseWord(String p, String s) {
        String[] pkg = mMethodMap.get(p);
        for (String n : pkg) {
            if (n.equals(s))
                return true;
        }
        return false;
    }

    public final boolean isUserWord(String s) {
        return mUserMap.containsKey(s);
    }

    private boolean contains(String[] a, String s) {
        for (String n : a) {
            if (n.equals(s))
                return true;
        }
        return false;
    }

    private boolean contains(ArrayList<String> a, String s) {
        for (String n : a) {
            if (n.equals(s))
                return true;
        }
        return false;
    }

    /**
     * 空白符
     *
     * @param c
     * @return
     */
    public boolean isWhitespace(char c) {
        return (c == ' ' || c == '\n' || c == '\t' ||
                c == '\r' || c == '\f' || c == EOF);
    }

    /**
     * 点运算符
     *
     * @param c
     * @return
     */
    public boolean isSentenceTerminator(char c) {
        return (c == '.');
    }

    /**
     * 斜杠
     *
     * @param c
     * @return
     */
    public boolean isEscapeChar(char c) {
        return (c == '\\');
    }

    /**
     * Derived classes that do not do represent C-like programming languages
     * should return false; otherwise return true
     */
    public boolean isProgLang() {
        return true;
    }

    /**
     * Whether the word after c is a token
     */
    public boolean isWordStart(char c) {
        return false;
    }

    /**
     * Whether cSc is a token, where S is a sequence of characters that are on the same line
     * 字符串引号
     */
    public boolean isDelimiterA(char c) {
        return (c == '"');
    }

    /**
     * Same concept as isDelimiterA(char), but Language and its subclasses can
     * specify a second type of symbol to use here
     * 单个字符引号
     */
    public boolean isDelimiterB(char c) {
        return (c == '\'');
    }

    /**
     * Whether cL is a token, where L is a sequence of characters until the end of the line
     * 宏定义
     */
    public boolean isLineAStart(char c) {
        return (c == '#');
    }

    /**
     * Same concept as isLineAStart(char), but Language and its subclasses can
     * specify a second type of symbol to use here
     */
    public boolean isLineBStart(char c) {
        return false;
    }

    /**
     * Whether c0c1L is a token, where L is a sequence of characters until the end of the line
     * 单行注释
     */
    public boolean isLineStart(char c0, char c1) {
        return (c0 == '/' && c1 == '/');
    }

    /**
     * Whether c0c1 signifies the start of a multi-line token
     * 多行注释开始
     */
    public boolean isMultilineStartDelimiter(char c0, char c1) {
        return (c0 == '/' && c1 == '*');
    }

    /**
     * Whether c0c1 signifies the end of a multi-line token
     * 多行注释结束
     */
    public boolean isMultilineEndDelimiter(char c0, char c1) {
        return (c0 == '*' && c1 == '/');
    }
}
