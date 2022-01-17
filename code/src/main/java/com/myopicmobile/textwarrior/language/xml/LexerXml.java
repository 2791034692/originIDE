package com.myopicmobile.textwarrior.language.xml;

import com.myopicmobile.textwarrior.base.BaseLexer;
import com.myopicmobile.textwarrior.bean.BlockLine;
import com.myopicmobile.textwarrior.common.DocumentProvider;
import com.myopicmobile.textwarrior.common.Pair;
import com.myopicmobile.textwarrior.language.CharSeqReader;
import com.myopicmobile.textwarrior.language.java.JavaType;

import java.util.ArrayList;
import java.util.List;

public class LexerXml extends BaseLexer {

    public LexerXml() {
        super();
    }

    @Override
    protected List<Pair> requestTokenize() {
        DocumentProvider doc = getDocument();
        LanguageXml lang = (LanguageXml) getLanguage();
        ArrayList<Pair> tokens = new ArrayList<>(8196);
        ArrayList<BlockLine> lines = new ArrayList<>(1024);
        ArrayList<BlockLine> lineStacks = new ArrayList<>(1024);
        if (!lang.isProgLang()) {
            tokens.add(new Pair(0, NORMAL));
            super.setBlockLines(lines);
            return tokens;
        }
        XmlLexer lexer = new XmlLexer(new CharSeqReader(doc));
        lang.clearUserWord();
        int idx = 0;
        XmlType markedType = null;
        XmlType type = null;
        while (true) {
            try {
                type = lexer.yylex();
                if (type == XmlType.EOF) {
                    break;
                }
                switch (type) {
                    case LT:
                        lineStacks.add(new BlockLine(lexer.yyLine(), lexer.yyLine(), lexer.yychar(), lexer.yychar()));
                        addPairIfNeeded(new Pair(idx, KEYWORD), tokens);
                        break;
                    case LTMOD:
                    case GTMOD:
                        int size = lineStacks.size();
                        if (size > 0) {
                            BlockLine line = lineStacks.remove(size - 1);
                            line.endLine = lexer.yyLine();
                            line.endColumn = lexer.yychar();
                            if (type == XmlType.GTMOD) {
                                line.endLine = lexer.yyLine() + 1;
                            }
                            if (line.endLine - line.startLine > 1)
                                lines.add(line);
                        }
                        addPairIfNeeded(new Pair(idx, KEYWORD), tokens);
                        break;
                    case STRING:
                    case CHARACTER_LITERAL:
                        addPairIfNeeded(new Pair(idx, SINGLE_SYMBOL_DELIMITED_A), tokens);
                        break;
                    case IDENTIFIER:
                        if (markedType == XmlType.LT || markedType == XmlType.LTMOD) {
                            addPairIfNeeded(new Pair(idx, KEYWORD), tokens);
                        }
                        break;
                    case GT:
                        addPairIfNeeded(new Pair(idx, KEYWORD), tokens);
                        break;
                    case EQ:
                        addPairIfNeeded(new Pair(idx, OPERATOR), tokens);
                        break;
                    default:
                        addPairIfNeeded(new Pair(idx, NORMAL), tokens);
                }
                switch (type) {
                    case WHITESPACE:
                        break;
                    default:
                        markedType = type;
                        break;
                }
                idx += lexer.yytext().length();
            } catch (Exception e) {
                e.printStackTrace();
                idx++;
            }
        }
        super.setBlockLines(lines);
        return tokens;
    }

    @Override
    public int autoIndent(JavaType type) {
        return 0;
    }

    @Override
    public int autoIndent(CharSequence text) {
        XmlLexer lexer = new XmlLexer(new CharSeqReader(text));
        int indent = 0;
        try {
            XmlType type = null;
            while (true) {
                type = lexer.yylex();
                if (type == XmlType.EOF) {
                    break;
                }
                switch (type) {
                    case LT:
                        indent++;
                        break;
                    case LTMOD:
                        indent--;
                        break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return indent * 4;
    }

    @Override
    public void onEnabled() {
        super.onEnabled();
        setLanguage(LanguageXml.getInstance());
    }
}
