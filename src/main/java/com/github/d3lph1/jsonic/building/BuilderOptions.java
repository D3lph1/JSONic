package com.github.d3lph1.jsonic.building;

import com.github.d3lph1.jsonic.naming.NamingStrategy;
import com.github.d3lph1.jsonic.naming.UnderCaseNamingStrategy;

/**
 * Stores the configuration of the json builder.
 *
 * @author D3lph1
 */
public class BuilderOptions
{
    /**
     * Preset for minimized JSON.
     */
    public static final BuilderOptions INLINE =
            new BuilderOptions()
                    .indent(0)
                    .spacesBeforeArrayComma(0)
                    .spacesAfterArrayComma(0)
                    .newLineAfterLeftArrayBracket(false)
                    .newLineAfterArrayItem(false)
                    .newLineBeforeRightArrayBracket(false)
                    .newLineAfterLeftObjectBracket(false)
                    .newLineAfterObjectItem(false)
                    .spacesAfterObjectColon(0);

    /**
     * Preset for pretty json, convenient for human perception.
     */
    public static final BuilderOptions PRETTY =
            new BuilderOptions()
                    .indent(4)
                    .spacesBeforeArrayComma(0)
                    .spacesAfterArrayComma(1)
                    .newLineAfterLeftArrayBracket(true)
                    .newLineAfterArrayItem(true)
                    .newLineBeforeRightArrayBracket(true)
                    .newLineAfterLeftObjectBracket(true)
                    .newLineAfterObjectItem(true)
                    .spacesAfterObjectColon(1);

    private NamingStrategy namingStrategy = new UnderCaseNamingStrategy();

    /**
     * Tab size.
     * Example:
     * {
     *     "key": "value"
     * ^^^^--- Count of this spaces.
     * }
     */
    private int indent;

    /**
     * [
     *     1, 2, 3
     *     ^--^
     * ]
     */
    private int spacesBeforeArrayComma;

    /**
     * [
     *     1, 2, 3
     *     -^^-^^----
     * ]
     */
    private int spacesAfterArrayComma;

    private boolean newLineAfterArrayItem;

    private boolean newLineAfterLeftArrayBracket;

    private boolean newLineBeforeRightArrayBracket;

    private boolean newLineAfterLeftObjectBracket;

    private boolean newLineAfterObjectItem;

    /**
     * {
     *     "key": "value"
     *     ----^----
     * }
     */
    private int spacesBeforeObjectColon;

    /**
     * {
     *     "key": "value"
     *     -----^^--
     * }
     */
    private int spacesAfterObjectColon;

    public NamingStrategy getNamingStrategy()
    {
        return namingStrategy;
    }

    public void setNamingStrategy(NamingStrategy namingStrategy)
    {
        this.namingStrategy = namingStrategy;
    }

    public BuilderOptions indent(int size)
    {
        posCheck(size, "size");
        indent = size;

        return this;
    }

    public BuilderOptions spacesBeforeArrayComma(int count)
    {
        posCheck(count, "count");
        spacesBeforeArrayComma = count;

        return this;
    }

    public BuilderOptions spacesAfterArrayComma(int count)
    {
        posCheck(count, "count");
        spacesAfterArrayComma = count;

        return this;
    }

    public BuilderOptions newLineAfterLeftArrayBracket(boolean val)
    {
        newLineAfterLeftArrayBracket = val;

        return this;
    }

    public BuilderOptions newLineAfterArrayItem(boolean val)
    {
        newLineAfterArrayItem = val;

        return this;
    }

    public BuilderOptions newLineBeforeRightArrayBracket(boolean val)
    {
        newLineBeforeRightArrayBracket = val;

        return this;
    }

    public BuilderOptions spacesBeforeObjectColon(int count)
    {
        posCheck(count, "count");
        spacesBeforeObjectColon = count;

        return this;
    }

    public BuilderOptions spacesAfterObjectColon(int count)
    {
        posCheck(count, "count");
        spacesAfterObjectColon = count;

        return this;
    }

    private void posCheck(int v, String arg)
    {
        if (v < 0) {
            throw new IllegalArgumentException("Argument " + arg + " must be above 0");
        }
    }

    public int getIndent()
    {
        return indent;
    }

    public int getSpacesBeforeArrayComma()
    {
        return spacesBeforeArrayComma;
    }

    public int getSpacesAfterArrayComma()
    {
        return spacesAfterArrayComma;
    }

    public boolean isNewLineAfterArrayItem()
    {
        return newLineAfterArrayItem;
    }

    public int getSpacesBeforeObjectColon()
    {
        return spacesBeforeObjectColon;
    }

    public int getSpacesAfterObjectColon()
    {
        return spacesAfterObjectColon;
    }

    public boolean isNewLineAfterLeftArrayBracket()
    {
        return newLineAfterLeftArrayBracket;
    }

    public boolean isNewLineBeforeRightArrayBracket()
    {
        return newLineBeforeRightArrayBracket;
    }

    public boolean isNewLineAfterLeftObjectBracket()
    {
        return newLineAfterLeftObjectBracket;
    }

    public BuilderOptions newLineAfterLeftObjectBracket(boolean newLineAfterLeftObjectBracket)
    {
        this.newLineAfterLeftObjectBracket = newLineAfterLeftObjectBracket;

        return this;
    }

    public boolean isNewLineAfterObjectItem()
    {
        return newLineAfterObjectItem;
    }

    public BuilderOptions newLineAfterObjectItem(boolean newLineAfterObjectItem)
    {
        this.newLineAfterObjectItem = newLineAfterObjectItem;

        return this;
    }
}
