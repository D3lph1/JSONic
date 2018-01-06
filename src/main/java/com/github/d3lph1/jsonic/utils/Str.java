package com.github.d3lph1.jsonic.utils;

/**
 * Contains utility methods for working with strings.
 *
 * @author D3lph1
 */
public class Str
{
    /**
     * Repeat the next string the specified number of times.
     * Example:
     *  <code>
     *      String result = Str.repeat("lorem_", 3);
     *      System.out.println(result); // lorem_lorem_lorem_
     *  </code>
     *
     * @param str Following string.
     * @param count How many times do need to repeat.
     * @return String with repetitions.
     */
    public static String repeat(String str, int count)
    {
        return new String(new char[count]).replace("\0", str);
    }
}
