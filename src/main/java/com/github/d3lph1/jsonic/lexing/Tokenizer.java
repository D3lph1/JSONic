package com.github.d3lph1.jsonic.lexing;

import com.github.d3lph1.jsonic.lexing.readers.Reader;
import com.github.d3lph1.jsonic.lexing.tokens.*;
import com.github.d3lph1.jsonic.lexing.tokens.String;
import com.github.d3lph1.jsonic.lexing.tokens.bools.Boolean;
import com.github.d3lph1.jsonic.lexing.tokens.bools.False;
import com.github.d3lph1.jsonic.lexing.tokens.bools.True;
import com.github.d3lph1.jsonic.lexing.tokens.brackets.square.Left;
import com.github.d3lph1.jsonic.lexing.tokens.brackets.square.Right;
import com.github.d3lph1.jsonic.lexing.tokens.nums.Double;
import com.github.d3lph1.jsonic.lexing.tokens.nums.Integer;
import com.github.d3lph1.jsonic.lexing.tokens.nums.Num;

import java.io.IOException;

/**
 * Tokenizer (or lexer) class converts an incoming character stream to a set of tokens,
 * which are then used by the parser.
 * For example, incoming string:
 *      {
 *          "key": [1, 2.5]
 *      }
 * will be broken down into the following tokens:
 *      com.github.d3lph1.jsonic.lexing.tokens.brackets.curly.Left // {
 *      com.github.d3lph1.jsonic.lexing.tokens.String  // "key"
 *      com.github.d3lph1.jsonic.lexing.tokens.Colon  // :
 *      com.github.d3lph1.jsonic.lexing.tokens.brackets.square.Left  // [
 *      com.github.d3lph1.jsonic.lexing.tokens.nums.Integer  // 1
 *      com.github.d3lph1.jsonic.lexing.tokens.Comma  // ,
 *      com.github.d3lph1.jsonic.lexing.tokens.nums.Double  // 2.5
 *      com.github.d3lph1.jsonic.lexing.tokens.brackets.square.Right  // ]
 *      com.github.d3lph1.jsonic.lexing.tokens.brackets.curly.Right  // }
 *
 * @author D3lph1
 */
public class Tokenizer
{
    private Reader reader;

    /**
     * Current readable character.
     */
    private char peek = ' ';

    /**
     * Number of the current readable line.
     */
    private int line = 1;

    /**
     * Absolute position of the readable character.
     */
    private int absolutePosition = 0;

    /**
     * The position of the readable character in the string.
     */
    private int linePosition = 0;

    public Tokenizer(Reader reader)
    {
        this.reader = reader;
    }

    /**
     * @return Next token from incoming stream.
     */
    public Token next()
    {
        for (; ; read()) {
            if (peek == ' ' || peek == '\t' || peek == '\r') {
                continue;
            } else if (peek == '\n') {
                line++;
                linePosition = 0;
            } else {
                break;
            }
        }

        Num maybeNum = tryNum();
        if (maybeNum != null) return maybeNum;

        Boolean maybeBoolean = tryBoolean();
        if (maybeBoolean != null) return maybeBoolean;

        Null maybeNull = tryNull();
        if (maybeNull != null) return maybeNull;

        switch (peek) {
            // String literal.
            case '"':
                return tokenizeString();
            case ',':
                read();
                return new Comma(absolutePosition - Comma.LEXEME_LENGTH);
            case ':':
                read();
                return new Colon(absolutePosition - Colon.LEXEME_LENGTH);
            case '[':
                read();
                return new Left(absolutePosition - Left.LEXEME_LENGTH);
            case ']':
                read();
                return new Right(absolutePosition - Right.LEXEME_LENGTH);
            case '{':
                read();
                return new com.github.d3lph1.jsonic.lexing.tokens.brackets.curly.Left(
                        absolutePosition - com.github.d3lph1.jsonic.lexing.tokens.brackets.curly.Left.LEXEME_LENGTH
                );
            case '}':
                read();
                return new com.github.d3lph1.jsonic.lexing.tokens.brackets.curly.Right(
                        absolutePosition - com.github.d3lph1.jsonic.lexing.tokens.brackets.curly.Right.LEXEME_LENGTH
                );
        }

        if (peek == (char)-1) {
            return null;
        }

        throw new UnexpectedCharException(absolutePosition, line, linePosition);
    }

    private Num tryNum()
    {
        if (peek == '-') {
            read();
            if (Character.isDigit(peek)) {
                return tokenizeNum(Num.Sign.NEGATIVE);
            }

            throw new UnexpectedCharException(absolutePosition, line, linePosition, Num.class);
        } else {
            if (Character.isDigit(peek)) {
                return tokenizeNum(Num.Sign.POSITIVE);
            }
        }

        return null;
    }

    private Num tokenizeNum(int sign)
    {
        int v = 0;
        do {
            v = 10 * v + Character.digit(peek, 10);
            read();
        } while (Character.isDigit(peek));

        if (peek != '.') {
            return new Integer(v * sign, absolutePosition - java.lang.String.valueOf(v * sign).length());
        }
        float x = v;
        float d = 10;
        for (; ; ) {
            read();
            if (!Character.isDigit(peek)) {
                break;
            }
            x += Character.digit(peek, 10) / d;
            d = d * 10;
        }

        return new Double(x * sign, absolutePosition - java.lang.String.valueOf(v * sign).length());
    }

    private Boolean tryBoolean()
    {
        if (peek == 't') {
            if (read("rue")) {
                return new True(absolutePosition - True.LEXEME_LENGTH);
            }

            throw new UnexpectedCharException(absolutePosition, line, linePosition, True.class);
        } else if (peek == 'f') {
            if (read("alse")) {
                return new False(absolutePosition - False.LEXEME_LENGTH);
            }

            throw new UnexpectedCharException(absolutePosition, line, linePosition, False.class);
        }

        return null;
    }

    private Null tryNull()
    {
        if (peek == 'n') {
            if (read("ull")) {
                return new Null(absolutePosition - Null.LEXEME_LENGTH);
            }

            throw new UnexpectedCharException(absolutePosition, line, linePosition, Null.class);
        }

        return null;
    }

    private String tokenizeString()
    {
        StringBuilder sb = new StringBuilder();
        read();
        char sub = peek;
        while (!(sub != '\\' && peek == '"')) {
            sb.append(peek);
            sub = peek;
            read();
            if (peek == '\n') {
                throw new UnexpectedCharException(absolutePosition, line, linePosition, "New line character not allowed in JSON specification");
            }
            // "\\" -> "\"
            if (sub == '\\' && peek == '\\') {
                sub = '\\';
                read();
            }

            // Unicode sequence.
            if (sub == '\\' && peek == 'u') {
                // Remove '\'. Example: \u00b0.
                //                      ^---- this
                sb.deleteCharAt(sb.length() - 1);
                read();
                StringBuilder usb = new StringBuilder();
                // 4 - length of unicode sequence.
                for(int i = 0; i < 4; i++) {
                    usb.append(peek);
                    read();
                }
                // "\" -> " "
                sub = ' ';

                sb.append((char) java.lang.Integer.parseInt(usb.toString(), 16));
            }
        }
        read();

        return new String(sb.toString(), absolutePosition - sb.length());
    }

    private void read()
    {
        try {
            peek = reader.read();
            absolutePosition++;
            linePosition++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean read(java.lang.String str)
    {
        try {
            boolean f = true;
            for (int i = 0; i < str.length(); i++) {
                absolutePosition++;
                linePosition++;
                if (!reader.read(str.charAt(i))) {
                    f = false;
                }
            }
            peek = ' ';

            return f;
        } catch (IOException e) {
            e.printStackTrace();
        }
        peek = ' ';

        return false;
    }

    public char getPeek()
    {
        return peek;
    }

    public int getAbsolutePosition()
    {
        return absolutePosition;
    }

    public int getLine()
    {
        return line;
    }

    public int getLinePosition()
    {
        return linePosition;
    }
}
