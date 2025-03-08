package center.infrastructure.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;


/**
 * @projectName: api-gateway
 * @package: center.infrastructure.common
 * @className: OperationRequest
 * @author: Peregrine Calder
 * @version: 1.0
 */
@NoArgsConstructor
@Getter
@Setter
public class OperationRequest<T> {
    private int pageStart = 0;
    private int pageEnd = 0;

    private int pageIndex;
    private int pageSize;

    private T data;

    public OperationRequest(String page, String rows) {
        this.pageIndex = StringUtils.isEmpty(page) ? 1 : Integer.parseInt(page);
        this.pageSize = StringUtils.isEmpty(page) ? 10 : Integer.parseInt(rows);
        if (0 == this.pageIndex) {
            this.pageIndex = 1;
        }
        this.pageStart = (this.pageIndex - 1) * this.pageSize;
        this.pageEnd = this.pageSize;
    }

    public OperationRequest(int page, int rows) {
        this.pageIndex = 0 == page ? 1 : page;
        this.pageSize = 0 == rows ? 10 : rows;
        this.pageStart = (this.pageIndex - 1) * this.pageSize;
        this.pageEnd = this.pageSize;
    }

    public void setPage(String page, String rows) {
        this.pageIndex = StringUtils.isEmpty(page) ? 1 : Integer.parseInt(page);
        this.pageSize = StringUtils.isEmpty(page) ? 10 : Integer.parseInt(rows);
        if (0 == this.pageIndex) {
            this.pageIndex = 1;
        }
        this.pageStart = (this.pageIndex - 1) * this.pageSize;
        this.pageEnd = this.pageSize;
    }
}
