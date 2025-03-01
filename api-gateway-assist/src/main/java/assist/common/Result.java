package assist.common;

import lombok.Getter;
import lombok.Setter;

/**
 * @projectName: api-gateway
 * @package: assist.common
 * @className: Result
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Getter
@Setter
public class Result<T> {
    private String code;
    private String info;
    private T data;
}
