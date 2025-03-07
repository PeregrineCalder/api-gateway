package center.infrastructure.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @projectName: api-gateway
 * @package: center.infrastructure.common
 * @className: OperationResult
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperationResult<T> {
    private int pageTotal;
    private List<T> list;
}
