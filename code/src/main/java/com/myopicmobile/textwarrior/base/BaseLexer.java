/*
 * Copyright (c) 2013 Tah Wei Hoon.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0,
 * with full text available at http://www.apache.org/licenses/LICENSE-2.0.html
 *
 * This software is provided "as is". Use at your own risk.
 */
package com.myopicmobile.textwarrior.base;

import com.myopicmobile.textwarrior.bean.BlockLine;
import com.myopicmobile.textwarrior.bean.ColorLine;
import com.myopicmobile.textwarrior.bean.DeviderLine;
import com.myopicmobile.textwarrior.common.DocumentProvider;
import com.myopicmobile.textwarrior.common.Flag;
import com.myopicmobile.textwarrior.common.Pair;
import com.myopicmobile.textwarrior.language.java.JavaType;
import com.myopicmobile.textwarrior.language.java.LexerJava;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Does lexical analysis of a text for C-like languages.
 * The programming language syntax used is set as a static class variable.
 */
public abstract class BaseLexer {
    @SuppressWarnings("unused")
    public final static int UNKNOWN = -1;
    public final static int NORMAL = 0;
    public final static int KEYWORD = 1;
    public final static int OPERATOR = 2;
    public final static int CLASS = 3;
    public final static int NUMBER = 4;
    public final static int FUNCTION = 5;
    public final static int VARIANT = 6;
    public final static int SINGLE_SYMBOL_WORD = 10;
    public final static int SINGLE_SYMBOL_LINE_A = 20;
    public final static int SINGLE_SYMBOL_LINE_B = 21;
    public final static int DOUBLE_SYMBOL_LINE = 30;
    public final static int DOUBLE_SYMBOL_DELIMITED_MULTILINE = 40;
    public final static int SINGLE_SYMBOL_DELIMITED_A = 50;
    public final static int SINGLE_SYMBOL_DELIMITED_B = 51;
    @SuppressWarnings("unused")
    private final static int MAX_KEYWORD_LENGTH = 127;
    private static Class<? extends BaseLexer> _implementation;

    static {
        setDefaultLexerImpl(null);
    }

    private ArrayList<BlockLine> mLines = new ArrayList<>();
    private ArrayList<ColorLine> mColorLines = new ArrayList<>();
    private ArrayList<DeviderLine> mDeviderLines = new ArrayList<>();
    //private ArrayList<LinkLine> mLinkLines = new ArrayList<>();
    private DocumentProvider _hDoc = null;
    private LexThread _workerThread = null;
    private LexCallback _callback = null;
    private BaseLanguage language;

    public BaseLexer() {
        super();
    }

    public synchronized static void setDefaultLexerImpl(Class<? extends BaseLexer> implClass) {
        //不接受不能实例化的Class
        if (implClass == null || (implClass.getModifiers() & Modifier.ABSTRACT) != 0 || implClass.isInterface()) {
            if (_implementation == null) {
                _implementation = LexerJava.class;
            }
            return;
        }
        _implementation = implClass;
    }

    //TextEditor默认从这里创建Lexer
    public static BaseLexer newInstance(LexCallback callback) {
        try {
            Constructor<? extends BaseLexer> cons = _implementation.getConstructor();
            cons.setAccessible(true);
            BaseLexer lexer = cons.newInstance();
            lexer.setCallback(callback);
            return lexer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        LexerJava defLexer = new LexerJava();
        defLexer.setCallback(callback);
        return defLexer;
    }

    public static BaseLexer newInstanceOf(LexCallback callback, Class<? extends BaseLexer> impl) {
        if (impl == null || impl.isInterface() || (impl.getModifiers() & Modifier.ABSTRACT) != 0) {
            return newInstance(callback);
        }
        try {
            Constructor<? extends BaseLexer> cons = impl.getConstructor();
            cons.setAccessible(true);
            BaseLexer lexer = cons.newInstance();
            lexer.setCallback(callback);
            return lexer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newInstance(callback);
    }

    protected static void addPairIfNeeded(Pair pair, List<Pair> pairs) {
        int size = pairs.size();
        if (size <= 0) {
            pairs.add(pair);
        } else {
            Pair last = pairs.get(size - 1);
            if (last.getSecond() != pair.getSecond()) {
                pairs.add(pair);
            }
        }
    }

    public BaseLanguage getLanguage() {
        return this.language;
    }

    public void setLanguage(BaseLanguage language) {
        this.language = language;
    }

    public void tokenize(DocumentProvider hDoc) {
        setDocument(new DocumentProvider(hDoc));
        if (_workerThread == null) {
            _workerThread = new LexThread(this);
            _workerThread.start();
        } else {
            _workerThread.restart();
        }
    }

    void tokenizeDone(List<Pair> result) {
        if (_callback != null) {
            _callback.lexDone(result);
        }
        _workerThread = null;
    }

    public void cancelTokenize() {
        if (_workerThread != null) {
            _workerThread.abort();
            _workerThread = null;
        }
    }

    public void setCallback(LexCallback cb) {
        this._callback = cb;
    }

    protected synchronized DocumentProvider getDocument() {
        return _hDoc;
    }

    public synchronized void setDocument(DocumentProvider hDoc) {
        _hDoc = hDoc;
    }

    protected void setBlockLines(ArrayList<BlockLine> lines) {
        if (lines == null) {
            mLines = new ArrayList<>();
            return;
        }
        this.mLines = lines;
    }

    public ArrayList<BlockLine> getLines() {
        return mLines;
    }

   /* protected void setLinkLines(ArrayList<LinkLine> lines){
        if(lines == null){
            mLinkLines = new ArrayList<>();
            return;
        }
        this.mLinkLines = lines;
    }

    public ArrayList<LinkLine> getLinkLines() {
        return mLinkLines;
    }*/

    public ArrayList<ColorLine> getColorLines() {
        return mColorLines;
    }

    protected void setColorLines(ArrayList<ColorLine> lines) {
        if (lines == null) {
            mColorLines = new ArrayList<>();
            return;
        }
        this.mColorLines = lines;
    }

    public ArrayList<DeviderLine> getDeviderLines() {
        return mDeviderLines;
    }

    protected void setDeviderLines(ArrayList<DeviderLine> lines) {
        if (lines == null) {
            mDeviderLines = new ArrayList<>();
            return;
        }
        this.mDeviderLines = lines;
    }

    protected abstract List<Pair> requestTokenize();

    public abstract int autoIndent(CharSequence text);

    public abstract int autoIndent(JavaType type);

    public void onEnabled() {
        //For child
    }

    public void onDisabled() {
        //For child
    }

    public interface LexCallback {
        void lexDone(List<Pair> results);
    }

    private class LexThread extends Thread {
        private final BaseLexer _lexManager;
        private final Flag _abort;
        private boolean rescan = false;
        private List<Pair> _tokens;

        public LexThread(BaseLexer p) {
            _lexManager = p;
            _abort = new Flag();
        }

        @Override
        public void run() {
            do {
                rescan = false;
                _abort.clear();
                tokenize();
            }
            while (rescan);

            if (!_abort.isSet()) {
                _lexManager.tokenizeDone(_tokens);
            }
        }

        public void restart() {
            rescan = true;
            _abort.set();
        }

        public void abort() {
            _abort.set();
        }

        private void tokenize() {
            List<Pair> pairs = _lexManager.requestTokenize();
            if (pairs == null) {
                pairs = new ArrayList<>(1);
            }
            if (pairs.isEmpty()) {
                pairs.add(new Pair(0, NORMAL));
            }
            _tokens = pairs;
        }

    }
}
