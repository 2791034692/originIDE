package com.myopicmobile.textwarrior.language.java;

import com.myopicmobile.textwarrior.base.BaseLanguage;
import com.myopicmobile.textwarrior.base.BaseLexer;
import com.myopicmobile.textwarrior.bean.BlockLine;
import com.myopicmobile.textwarrior.common.DocumentProvider;
import com.myopicmobile.textwarrior.common.Pair;
import com.myopicmobile.textwarrior.language.CharSeqReader;

import java.util.ArrayList;
import java.util.List;

public class LexerJava extends BaseLexer {

    public LexerJava() {
        super();
    }

    @Override
    protected List<Pair> requestTokenize() {
        int markedEndCharOffset = -1;
        int markedEndLine = -1;
        DocumentProvider doc = getDocument();
        BaseLanguage lang = getLanguage();
        ArrayList<Pair> tokens = new ArrayList<>(8196);
        ArrayList lines = new ArrayList(8196);
        ArrayList<BlockLine> lineStacks = new ArrayList<>(8196);
        ArrayList<BlockLine> lineStacks2 = new ArrayList<>(8196);
        if (!lang.isProgLang()) {
            tokens.add(new Pair(0, NORMAL));
            super.setBlockLines(lines);
            return tokens;
        }
        JavaLexer lexer = new JavaLexer(new CharSeqReader(doc));
        lang.clearUserWord();
        int idx = 0;
        String markedText = null;
        JavaType markedType = null;
        JavaType type = null;
        while (true) {
            try {
                type = lexer.yylex();
                if (type == JavaType.EOF) {
                    break;
                }
                switch (type) {
                    case CASE:
                        int size2 = lineStacks2.size();
                        if (size2 > 0) {
                            BlockLine line = lineStacks2.remove(size2 - 1);
                            line.endLine = lexer.yyLine();
                            line.endColumn = lexer.yychar();
                            if (line.endLine - line.startLine > 1) {
                                lines.add(line);
                            }
                        }
                        addPairIfNeeded(new Pair(idx, KEYWORD), tokens);
                        break;
                    case SWITCH:
                        //前一个是END的时候的分支划线策略
                        size2 = lineStacks2.size();
                        if (size2 > 0 && markedEndCharOffset != -1) {
                            BlockLine rect = lineStacks2.remove(size2 - 1);
                            rect.endLine = markedEndLine;
                            rect.endColumn = markedEndCharOffset;
                            if (rect.endLine - rect.startLine > 1)
                                lines.add(rect);
                        }
                        addPairIfNeeded(new Pair(idx, KEYWORD), tokens);
                        break;
                    case IF:
                    case DO:
                    case TRY:
                    case NEW:
                    case INT:
                    case FOR:
                    case BYTE:
                    case TRUE:
                    case THIS:
                    case CHAR:
                    case ELSE:
                    case ENUM:
                    case LONG:
                    case NULL:
                    case GOTO:
                    case VOID:
                    case BREAK:
                    case SHORT:
                    case SUPER:
                    case THROW:
                    case CATCH:
                    case CONST:
                    case CLASS:
                    case FALSE:
                    case FLOAT:
                    case FINAL:
                    case WHILE:
                    case ASSERT:
                    case STATIC:
                    case THROWS:
                    case RETURN:
                    case NATIVE:
                    case IMPORT:
                    case DOUBLE:
                    case PUBLIC:
                    case BOOLEAN:
                    case EXTENDS:
                    case DEFAULT:
                    case FINALLY:
                    case PACKAGE:
                    case PRIVATE:
                    case ABSTRACT:
                    case STRICTFP:
                    case CONTINUE:
                    case VOLATILE:
                    case TRANSIENT:
                    case INTERFACE:
                    case PROTECTED:
                    case INSTANCEOF:
                    case IMPLEMENTS:
                    case SYNCHRONIZED:
                        addPairIfNeeded(new Pair(idx, KEYWORD), tokens);
                        break;
                    case NULL_LITERAL:
                    case BOOLEAN_LITERAL:
                        addPairIfNeeded(new Pair(idx, KEYWORD), tokens);
                        break;
                    case COMMENT:

                        addPairIfNeeded(new Pair(idx, DOUBLE_SYMBOL_DELIMITED_MULTILINE), tokens);
                        break;
                    case STRING:
                    case CHARACTER_LITERAL:
                        addPairIfNeeded(new Pair(idx, SINGLE_SYMBOL_DELIMITED_A), tokens);
                        break;
                    case MINUS:
                    case INTEGER_LITERAL:
                    case FLOATING_POINT_LITERAL:
                        addPairIfNeeded(new Pair(idx, NUMBER), tokens);
                        break;
                    case IDENTIFIER:
                        if ((markedType == JavaType.IDENTIFIER
                                && lang.isName(markedText))
                                || markedType == JavaType.BOOLEAN
                                || markedType == JavaType.VOID
                                || markedType == JavaType.CHAR
                                || markedType == JavaType.INT
                                || markedType == JavaType.BYTE
                                || markedType == JavaType.DOUBLE
                                || markedType == JavaType.FLOAT) {
                            lang.addUserWord(lexer.yytext());
                            addPairIfNeeded(new Pair(idx, VARIANT), tokens);
                        } else if (markedType == JavaType.CLASS
                                || markedType == JavaType.IMPLEMENTS) {
                            lang.addName(lexer.yytext());
                            addPairIfNeeded(new Pair(idx, CLASS), tokens);
                        } else if (lang.isName(lexer.yytext())) {
                            addPairIfNeeded(new Pair(idx, CLASS), tokens);
                        } else {
                            addPairIfNeeded(new Pair(idx, NORMAL), tokens);
                        }
                        break;
                    case LPAREN:
                        if (markedType == JavaType.IDENTIFIER) {
                            String str = doc.getLine(lexer.yyLine());
                            if (str.contains("{"))
                                lang.addUserWord(str.substring(str.indexOf(markedText), str.indexOf("{")));
                        }
                        addPairIfNeeded(new Pair(idx, OPERATOR), tokens);
                        break;
                    case LBRACE:
                        lineStacks.add(new BlockLine(lexer.yyLine(), lexer.yyLine(), lexer.yychar(), lexer.yychar()));
                        addPairIfNeeded(new Pair(idx, OPERATOR), tokens);
                        break;
                    case RBRACE:
                        int size = lineStacks.size();
                        if (size > 0) {
                            BlockLine rect = lineStacks.remove(size - 1);
                            rect.endLine = lexer.yyLine();
                            rect.endColumn = lexer.yychar();
                            if (rect.endLine - rect.startLine > 1)
                                lines.add(rect);
                            markedEndCharOffset = lexer.yychar();
                            markedEndLine = lexer.yyLine();
                        }
                        addPairIfNeeded(new Pair(idx, OPERATOR), tokens);
                        break;
                    case AT:
                    case COLON:
                    case RPAREN://右括号
                    case LBRACK://左中括号
                    case RBRACK:
                    case NOT:
                    case NOTEQ:
                    case COMMA://逗号
                    case EQ://等号
                    case EQEQ:
                    case SEMICOLON://分号
                    case PLUS:
                    case MINUSMINUS:
                    case PLUSPLUS:
                    case DIV:
                    case MULT:
                    case GT:
                    case LT:
                    case QUESTION:
                    case AND:
                    case ANDAND:
                    case OROR:
                    case OR:
                    case XOR:
                    case MOD:
                    case DOT:
                    case GTEQ:
                    case LTEQ:
                        addPairIfNeeded(new Pair(idx, OPERATOR), tokens);
                        break;
                    case WHITESPACE:
                        //假装空格和前面是同一个东西
                        break;
                    case CHAR_ERROR:
                        break;
                    default:
                        addPairIfNeeded(new Pair(idx, NORMAL), tokens);
                }
                switch (type) {
                    case WHITESPACE:
                        break;
                    case CASE:
                        markedType = type;
                        lineStacks2.add(new BlockLine(lexer.yyLine(), lexer.yyLine(), lexer.yychar(), lexer.yychar()));
                        markedText = lexer.yytext();
                        break;
                    case IDENTIFIER:
                        markedType = type;
                        markedText = lexer.yytext();
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
        lang.updateUserWord();
        super.setBlockLines(lines);
        return tokens;
    }

    @Override
    public int autoIndent(JavaType type) {
        // TODO: Implement this method
        return 0;
    }

    @Override
    public int autoIndent(CharSequence text) {
        JavaLexer lexer = new JavaLexer(new CharSeqReader(text));
        int indent = 0;
        int caseCount = 0;
        try {
            JavaType type = null;
            while (true) {
                type = lexer.yylex();
                if (type == JavaType.EOF) {
                    break;
                }
                switch (type) {
                    case LPAREN:
                    case LBRACE:
                        indent++;
                        break;
                    case RPAREN:
                    case RBRACE:
                        indent--;
                        break;
                    case CASE:
                        caseCount++;
                        break;
                    case COLON:
                        if (caseCount > 0) {
                            indent++;
                            caseCount = Integer.MIN_VALUE;
                        }
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
        setLanguage(LanguageJava.getInstance());
    }
}
