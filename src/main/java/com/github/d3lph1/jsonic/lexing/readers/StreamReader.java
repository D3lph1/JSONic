package com.github.d3lph1.jsonic.lexing.readers;

import java.io.*;

/**
 * Performs a one-character read from the reader.
 *
 * @author D3lph1
 */
public class StreamReader implements Reader
{
    /**
     * The reader from which the reading occurs.
     */
    private BufferedReader reader;

    /**
     * @param stream {@link #reader}
     */
    public StreamReader(InputStream stream) throws UnsupportedEncodingException
    {
        this.reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
    }

    public StreamReader(BufferedReader reader)
    {
        this.reader = reader;
    }

    @Override
    public char read() throws IOException
    {
        return (char) reader.read();
    }
}
