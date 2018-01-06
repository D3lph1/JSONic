package com.github.d3lph1.jsonic.lexing.tokens.brackets.curly;

import com.github.d3lph1.jsonic.lexing.tokens.Token;

/**
 * Represents a "}" lexeme.
 *
 * @author D3lph1
 */
public class Right extends Token
{
    /**
     * Length of "}".
     */
    public static final int LEXEME_LENGTH = 1;

    public Right(int position)
    {
        super(position);
    }
}
