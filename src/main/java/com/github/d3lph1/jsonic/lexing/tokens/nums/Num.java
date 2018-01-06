package com.github.d3lph1.jsonic.lexing.tokens.nums;

import com.github.d3lph1.jsonic.lexing.tokens.Token;

/**
 * Represents a numeric lexemes.
 *
 * @author D3lph1
 */
abstract public class Num extends Token
{
    public Num(int position)
    {
        super(position);
    }

    abstract Number getValue();

    public final class Sign {
        public static final int POSITIVE = 1;
        public static final int NEGATIVE = -1;
    }
}
