package com.github.d3lph1.jsonic.parsing;

import com.github.d3lph1.jsonic.*;
import com.github.d3lph1.jsonic.lexing.Tokenizer;
import com.github.d3lph1.jsonic.lexing.UnexpectedCharException;
import com.github.d3lph1.jsonic.lexing.tokens.*;
import com.github.d3lph1.jsonic.lexing.tokens.String;
import com.github.d3lph1.jsonic.lexing.tokens.bools.False;
import com.github.d3lph1.jsonic.lexing.tokens.bools.True;
import com.github.d3lph1.jsonic.lexing.tokens.brackets.square.Left;
import com.github.d3lph1.jsonic.lexing.tokens.brackets.square.Right;
import com.github.d3lph1.jsonic.lexing.tokens.nums.Double;
import com.github.d3lph1.jsonic.lexing.tokens.nums.Integer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The parser performs the syntactic analysis. Unlike the lexer, it works with tokens and not symbols.
 * Converts an incoming set of tokens into a json representation of a construct in the form of
 * program objects.
 *
 * @author D3lph1
 */
public class Parser
{
    private Tokenizer tokenizer;

    /**
     * Previous token.
     */
    private Token previous;

    /**
     * Current token.
     */
    private Token peek;

    /**
     * The level of nesting on which is located the currently read token.
     *
     * For example:
     *      <- Nesting level: 0
     *      {
     *          <- Nesting level: 1
     *          "key": {
     *              <- Nesting level: 2
     *              "one": [
     *                  <- Nesting level: 3
     *                  1,
     *                  2,
     *                  3
     *              ],
     *              <- Nesting level: 2
     *              "two": [
     *                  <- Nesting level: 3
     *                  1,
     *                  2,
     *                  3
     *              ]
     *              <- Nesting level: 2
     *          }
     *          <- Nesting level: 1
     *      }
     *      <- Nesting level: 0
     */
    private int nesting = 0;

    public Parser(Tokenizer tokenizer)
    {
        this.tokenizer = tokenizer;
    }

    /**
     * Produces parsing JSON from the incoming stream to JsonElement representation.
     */
    public JsonElement parse()
    {
        next();

        // If [] then return null.
        if (previous instanceof Left && peek instanceof Right) {
            return null;
        }

        // Start of array
        if (peek instanceof Left) {
            int curNesting = ++nesting;
            ArrayList<JsonElement> elements = new ArrayList<>();
            do {
                JsonElement el = parse();
                // For support empty array. If is empty array.
                if (el == null) {
                    break;
                }
                elements.add(el);
                next();

                // Detect comma after array item     [1, 2, 3, 4]
                //                               ------^--^--^
                if (!(peek instanceof Comma) && !(peek instanceof Right)) {
                    throw parseException("Expected comma after array item, but token " + peek.getClass().getName() + " given");
                }
            } while (!(peek instanceof Right) || curNesting != nesting);
            nesting--;
            checkRoot();

            return new JsonArray(elements);
        }

        // Start of object.
        if (peek instanceof com.github.d3lph1.jsonic.lexing.tokens.brackets.curly.Left) {
            int curNesting = ++nesting;
            HashMap<java.lang.String, JsonElement> elements = new HashMap<>();
            do {
                next();
                // If } token then is empty object.
                if (peek instanceof com.github.d3lph1.jsonic.lexing.tokens.brackets.curly.Right) {
                    break;
                }

                // Check key on exists and valid type.
                if (!(peek instanceof String)) {
                    throw parseException("Invalid object syntax. Expected key, but token " + peek.getClass().getName() + " given");
                }

                String key = (String) peek;
                next();
                // Check colon (:) on exists    {"key1": "value1", "key2": "value2"}
                //                              -------^-----------------^
                if (!(peek instanceof Colon)) {
                    throw parseException("Invalid object syntax. Expected colon, but token " + peek.getClass().getName() + " given");
                }

                elements.put(key.getValue(), parse());
                next();

                // Detect comma after object item    {"key1": "value1", "key2": "value2"}
                //                                   -----------------^
                if (!(peek instanceof Comma) && !(peek instanceof com.github.d3lph1.jsonic.lexing.tokens.brackets.curly.Right)) {
                    throw parseException("Expected comma after object item, but token " + peek.getClass().getName() + " given");
                }
            } while (!(peek instanceof com.github.d3lph1.jsonic.lexing.tokens.brackets.curly.Right) || curNesting != nesting);
            nesting--;
            checkRoot();

            return new JsonObject(elements);
        }

        // Primitives

        if (peek instanceof String) {
            String token = ((String) peek).deepCopy();
            checkRoot();
            return new JsonPrimitive(token.getValue());
        }
        if (peek instanceof Integer) {
            Integer token = ((Integer) peek).deepCopy();
            checkRoot();
            return new JsonPrimitive(token.getValue());
        }
        if (peek instanceof Double) {
            Double token = ((Double) peek).deepCopy();
            checkRoot();
            return new JsonPrimitive(token.getValue());
        }
        if (peek instanceof True) {
            checkRoot();
            return new JsonPrimitive(true);
        }
        if (peek instanceof False) {
            checkRoot();
            return new JsonPrimitive(false);
        }
        if (peek instanceof Null) {
            checkRoot();
            return JsonNull.getInstance();
        }

        throw parseException("Parse error");
    }

    /**
     * Put in this.peek next token.
     */
    private void next()
    {
        try {
            previous = peek;
            peek = tokenizer.next();
        } catch (UnexpectedCharException e) {
            throw new ParseException(e);
        }
    }

    /**
     * It checks that the current root is the only root.
     */
    private void checkRoot()
    {
        if (nesting == 0) {
            next();
            if (peek != null) {
                throw parseException("JSON must contains only one root element. Second start");
            }
        }
    }

    private ParseException parseException(java.lang.String msg)
    {
        return new ParseException(msg, peek.getPosition());
    }
}
