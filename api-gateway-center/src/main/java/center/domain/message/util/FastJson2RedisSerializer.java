package center.domain.message.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.util.AbstractMap;
import java.util.Map;


/**
 * @projectName: api-gateway
 * @package: center.domain.message.util
 * @className: FastJson2RedisSerializer
 * @author: Peregrine Calder
 * @version: 1.0
 */

public class FastJson2RedisSerializer<T> implements RedisSerializer<T> {

    private final Class<T> clazz;

    @Override
    public byte[] serialize(T t) throws SerializationException {
        Map.Entry<String, T> entity = new AbstractMap.SimpleEntry<>(t.getClass().getName(), t);
        return JSON.toJSONString(entity, JSONWriter.Feature.WriteClassName).getBytes(Charset.defaultCharset());
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        String str = new String(bytes, Charset.defaultCharset());
        int index = str.indexOf(":");
        String cls = str.substring(2, index - 1);
        String obj = str.substring(index + 1, str.length() - 1);
        return JSON.parseObject(
                obj,
                clazz,
                JSONReader.autoTypeFilter(
                        cls
                ),
                JSONReader.Feature.SupportClassForName);
    }

    public FastJson2RedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }
}

