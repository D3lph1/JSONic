package com.github.d3lph1.jsonic;

import com.github.d3lph1.jsonic.building.BuilderOptions;
import com.github.d3lph1.jsonic.building.JsonBuilder;
import com.github.d3lph1.jsonic.lexing.Tokenizer;
import com.github.d3lph1.jsonic.lexing.readers.Reader;
import com.github.d3lph1.jsonic.lexing.readers.StreamReader;
import com.github.d3lph1.jsonic.lexing.readers.StringReader;
import com.github.d3lph1.jsonic.naming.UnderCaseNamingStrategy;
import com.github.d3lph1.jsonic.parsing.Parser;
import com.github.d3lph1.jsonic.typing.TypeConverter;

import java.io.InputStream;

/**
 * Class-wrapper for JSONic functionality.
 *
 * @author D3lph1
 */
public final class Jsonic
{
    private TypeConverter typeConverter;

    private JsonBuilder builder;

    public Jsonic()
    {
        typeConverter = new TypeConverter(new UnderCaseNamingStrategy());
        builder = new JsonBuilder(BuilderOptions.INLINE);
    }

    public Jsonic(JsonBuilder builder)
    {
        typeConverter = new TypeConverter(new UnderCaseNamingStrategy());
        this.builder = builder;
    }

    public Jsonic(TypeConverter typeConverter, JsonBuilder builder)
    {
        this.typeConverter = typeConverter;
        this.builder = builder;
    }

    public String toJson(Object encoded)
    {
        return builder.build(encoded);
    }

    public <T> T fromJson(String json, Class<T> template)
    {
        Parser parser = createParser(new StringReader(json));

        return convert(parser.parse(), template);
    }

    public <T> T fromJson(InputStream stream, Class<T> template)
    {
        Parser parser = createParser(new StreamReader(stream));

        return convert(parser.parse(), template);
    }

    public <T> T fromJson(Reader reader, Class<T> template)
    {
        Parser parser = createParser(reader);

        return convert(parser.parse(), template);
    }

    public JsonElement fromJson(String json)
    {
        Parser parser = createParser(new StringReader(json));

        return parser.parse();
    }

    public JsonElement fromJson(InputStream stream)
    {
        Parser parser = createParser(new StreamReader(stream));

        return parser.parse();
    }

    public JsonElement fromJson(Reader reader)
    {
        Parser parser = createParser(reader);

        return parser.parse();
    }

    private <T> T convert(JsonElement element, Class<T> template)
    {
        try {
            return typeConverter.toType(element, template);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Parser createParser(Reader reader)
    {
        return new Parser(new Tokenizer(reader));
    }

    public void setTypeConverter(TypeConverter converter)
    {
        typeConverter = converter;
    }

    public void setBuilder(JsonBuilder builder)
    {
        this.builder = builder;
    }

    public SerializersPool getSerializersPool()
    {
        return builder.getSerializersPool();
    }

    public void setSerializersPool(SerializersPool serializersPool)
    {
        builder.setSerializersPool(serializersPool);
    }

    public DeserializersPool getDeserializersPool()
    {
        return typeConverter.getDeserializersPool();
    }

    public void setDeserializersPool(DeserializersPool deserializersPool)
    {
        typeConverter.setDeserializersPool(deserializersPool);
    }
}
