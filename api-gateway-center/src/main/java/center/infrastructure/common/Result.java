package center.infrastructure.common;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @projectName: api-gateway
 * @package: center.infrastructure.common
 * @className: Result
 * @author: Peregrine Calder
 * @version: 1.0
 */
@AllArgsConstructor
@Getter
public class Result<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = -3826891916021780628L;
    private String code;
    private String info;
    private T data;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
