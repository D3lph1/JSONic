package com.github.d3lph1.jsonic.lexing.readers;

import java.io.IOException;
import java.io.InputStream;

/**
 * Performs a one-character read from the stream.
 *
 * @author D3lph1
 */
public class StreamReader implements Reader
{
    /**
     * The stream from which the reading occurs.
     */
    private InputStream stream;

    /**
     * @param stream {@link #stream}
     */
    public StreamReader(InputStream stream)
    {
        this.stream = stream;
    }

    @Override
    public char read() throws IOException
    {
        return (char) stream.read();
    }
}
