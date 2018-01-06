package com.github.d3lph1.jsonic.lexing;

import com.github.d3lph1.jsonic.lexing.readers.StringReader;
import com.github.d3lph1.jsonic.lexing.tokens.*;
import com.github.d3lph1.jsonic.lexing.tokens.String;
import com.github.d3lph1.jsonic.lexing.tokens.bools.False;
import com.github.d3lph1.jsonic.lexing.tokens.bools.True;
import com.github.d3lph1.jsonic.lexing.tokens.brackets.square.Left;
import com.github.d3lph1.jsonic.lexing.tokens.brackets.square.Right;
import com.github.d3lph1.jsonic.lexing.tokens.nums.Double;
import com.github.d3lph1.jsonic.lexing.tokens.nums.Integer;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * @author D3lph1
 */
public class TokenizerTest
{
    private Tokenizer tokenizer(java.lang.String src)
    {
        return new Tokenizer(new StringReader(src));
    }

    @Test
    public void integerPositive()
    {
        Tokenizer tokenizer = tokenizer("12");
        Token t = tokenizer.next();
        assertThat(t, instanceOf(Integer.class));
        Integer i = (Integer) t;
        assertEquals(12, i.getValue() != null ? i.getValue() : 0);
    }

    @Test
    public void integerNegative()
    {
        Tokenizer tokenizer = tokenizer("-12");
        Token t = tokenizer.next();
        assertThat(t, instanceOf(Integer.class));
        Integer i = (Integer) t;
        assertEquals(-12, i.getValue() != null ? i.getValue() : 0);
    }

    @Test
    public void doublePositive()
    {
        Tokenizer tokenizer = tokenizer("53.7");
        Token t = tokenizer.next();
        assertThat(t, instanceOf(Double.class));
        Double d = (Double) t;
        assertEquals(53.7, d.getValue(), 0.0001);
    }

    @Test
    public void doubleNegative()
    {
        Tokenizer tokenizer = tokenizer("-53.7");
        Token t = tokenizer.next();
        assertThat(t, instanceOf(Double.class));
        Double d = (Double) t;
        assertEquals(-53.7, d.getValue(), 0.0001);
    }

    @Test(expected = UnexpectedCharException.class)
    public void unexpectedNumToken()
    {
        Tokenizer tokenizer = tokenizer("-a");
        tokenizer.next();
    }

    @Test
    public void testTrue()
    {
        Tokenizer tokenizer = tokenizer("true");
        Token t = tokenizer.next();
        assertThat(t, instanceOf(True.class));
    }

    @Test
    public void testFalse()
    {
        Tokenizer tokenizer = tokenizer("false");
        Token t = tokenizer.next();
        assertThat(t, instanceOf(False.class));
    }

    @Test(expected = UnexpectedCharException.class)
    public void unexpectedTrue()
    {
        Tokenizer tokenizer = tokenizer("tqwerty");
        tokenizer.next();
    }

    @Test(expected = UnexpectedCharException.class)
    public void unexpectedFalse()
    {
        Tokenizer tokenizer = tokenizer("fqwerty");
        tokenizer.next();
    }

    @Test
    public void testNull()
    {
        Tokenizer tokenizer = tokenizer("null");
        Token t = tokenizer.next();
        assertThat(t, instanceOf(Null.class));
    }

    @Test(expected = UnexpectedCharException.class)
    public void unexpectedNull()
    {
        Tokenizer tokenizer = tokenizer("nqwerty");
        tokenizer.next();
    }

    @Test
    public void falseIntTrueNullDouble()
    {
        Tokenizer tokenizer = tokenizer("false -34 true null 4.1");
        assertThat(tokenizer.next(), instanceOf(False.class));
        assertThat(tokenizer.next(), instanceOf(Integer.class));
        assertThat(tokenizer.next(), instanceOf(True.class));
        assertThat(tokenizer.next(), instanceOf(Null.class));
        assertThat(tokenizer.next(), instanceOf(Double.class));
    }

    @Test
    public void string()
    {
        Tokenizer tokenizer = tokenizer("\"test\"");
        Token t = tokenizer.next();
        assertThat(t, instanceOf(String.class));
        String s = (String) t;
        assertEquals("test", s.getValue());
    }

    @Test
    public void escapedString()
    {
        Tokenizer tokenizer = tokenizer("\"te\\\"st\"");
        Token t = tokenizer.next();
        assertThat(t, instanceOf(String.class));
        String s = (String) t;
        assertEquals("te\\\"st", s.getValue());
    }

    @Test
    public void unicode()
    {
        Tokenizer tokenizer = tokenizer("\"15\\u00b0\"");
        String t = (String) tokenizer.next();
        assertEquals("15\u00b0", t.getValue());
    }

    @Test
    public void unicodeWithCharAfter()
    {
        Tokenizer tokenizer = tokenizer("\"15\\u00b0C\"");
        String t = (String) tokenizer.next();
        assertEquals("15\u00b0C", t.getValue());
    }

    @Test
    public void escapedStringWithDuplicates()
    {
        Tokenizer tokenizer = tokenizer("\"te\\\\st\"");
        Token t = tokenizer.next();
        assertThat(t, instanceOf(String.class));
        String s = (String) t;
        assertEquals("te\\st", s.getValue());
    }

    @Test
    public void escapedTwoStringsWithDuplicates()
    {
        Tokenizer tokenizer = tokenizer("\"te\\\"st\" \"some st\\\\ring\"");
        Token t = tokenizer.next();
        assertThat(t, instanceOf(String.class));
        String s = (String) t;
        assertEquals("te\\\"st", s.getValue());

        t = tokenizer.next();
        assertThat(t, instanceOf(String.class));
        s = (String) t;
        assertEquals("some st\\ring", s.getValue());
    }

    @Test(expected = UnexpectedCharException.class)
    public void stringWithNewLine()
    {
        Tokenizer tokenizer = tokenizer("\"lorem\nipsum\"");
        tokenizer.next();
    }

    @Test
    public void comma()
    {
        Tokenizer tokenizer = tokenizer(",");
        Token t = tokenizer.next();
        assertThat(t, instanceOf(Comma.class));
    }

    @Test
    public void colon()
    {
        Tokenizer tokenizer = tokenizer(":");
        Token t = tokenizer.next();
        assertThat(t, instanceOf(Colon.class));
    }

    @Test
    public void rightSquareBracket()
    {
        Tokenizer tokenizer = tokenizer("]");
        Token t = tokenizer.next();
        assertThat(t, instanceOf(Right.class));
    }

    @Test
    public void leftSquareBracket()
    {
        Tokenizer tokenizer = tokenizer("[");
        Token t = tokenizer.next();
        assertThat(t, instanceOf(Left.class));
    }

    @Test
    public void rightCurlyBracket()
    {
        Tokenizer tokenizer = tokenizer("}");
        Token t = tokenizer.next();
        assertThat(t, instanceOf(com.github.d3lph1.jsonic.lexing.tokens.brackets.curly.Right.class));
    }

    @Test
    public void leftCurlyBracket()
    {
        Tokenizer tokenizer = tokenizer("{");
        Token t = tokenizer.next();
        assertThat(t, instanceOf(com.github.d3lph1.jsonic.lexing.tokens.brackets.curly.Left.class));
    }

    @Test
    public void all()
    {
        Tokenizer tokenizer = tokenizer("{\"key\": [1, -7, 5.1, -8.03, true, null, false]}");
        assertThat(tokenizer.next(), instanceOf(com.github.d3lph1.jsonic.lexing.tokens.brackets.curly.Left.class));
        assertThat(tokenizer.next(), instanceOf(String.class));
        assertThat(tokenizer.next(), instanceOf(Colon.class));
        assertThat(tokenizer.next(), instanceOf(Left.class));
        assertThat(tokenizer.next(), instanceOf(Integer.class));
        assertThat(tokenizer.next(), instanceOf(Comma.class));
        assertThat(tokenizer.next(), instanceOf(Integer.class));
        assertThat(tokenizer.next(), instanceOf(Comma.class));
        assertThat(tokenizer.next(), instanceOf(Double.class));
        assertThat(tokenizer.next(), instanceOf(Comma.class));
        assertThat(tokenizer.next(), instanceOf(Double.class));
        assertThat(tokenizer.next(), instanceOf(Comma.class));
        assertThat(tokenizer.next(), instanceOf(True.class));
        assertThat(tokenizer.next(), instanceOf(Comma.class));
        assertThat(tokenizer.next(), instanceOf(Null.class));
        assertThat(tokenizer.next(), instanceOf(Comma.class));
        assertThat(tokenizer.next(), instanceOf(False.class));
        assertThat(tokenizer.next(), instanceOf(Right.class));
        assertThat(tokenizer.next(), instanceOf(com.github.d3lph1.jsonic.lexing.tokens.brackets.curly.Right.class));
    }
}
