# JSONic
### JSON in Java is convenient!

<p align="center">
<img src ="https://preview.ibb.co/i8xd9w/logo.png">
</p>

## What is JSONic?
JSONic - is a open-sourced Java library for convenient work with json. 
It allows you to convert a JSON string to program objects and vice versa. You can format the output json the way you want, for example, to make it easy for a person to read it.


## Overview
Deserialize JSON:
```java
Jsonic jsonic = new Jsonic();

// {
//     "java": {
//         "json": "convenient"
//     }   
// }
JsonObject obj = jsonic.fromJson(json).getAsObject();
// convenient
String result = obj.get("java").getAsObject().get("json").getAsPrimitive().getAsString();
```

Serialize JSON:
```java
class Response
{
    public int code;
    
    public String status;
    
    @Serialize(false)
    public String secret;
}

// ...

Response response = new Response();
response.code = 0;
response.status = "success";
response.secret = "This string will not be serialized";
Jsonic jsonic = new Jonic(new JsonBuilder(BuilderOptions.PRETTY));

// {
//     "code": 0,
//     "status": "success"
// }
String json = jsonic.toJson(response);
```

## Documentation
[Read on GitHub wiki](https://github.com/D3lph1/jsonic/wiki)

## License
The JSONic is open-sourced software licensed under the [MIT license](https://opensource.org/licenses/MIT).
