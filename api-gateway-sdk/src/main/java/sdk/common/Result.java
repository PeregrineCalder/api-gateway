package sdk.common;

import lombok.Getter;
import lombok.Setter;

/**
 * @projectName: api-gateway
 * @package: sdk.common
 * @className: Result
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Setter
@Getter
public class Result<T> {
    private String code;
    private String info;
    private T data;
}
