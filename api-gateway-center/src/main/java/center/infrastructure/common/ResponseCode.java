package center.infrastructure.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    SUCCESS("0000", "Success"),
    UN_ERROR("0001", "Unknown Error"),
    ILLEGAL_PARAMETER("0002", "Invalid Parameter"),
    INDEX_DUP("0003", "Primary Key Error"),
    NO_UPDATE("0004", "SQL Update Error"),;

    private final String code;
    private final String info;
}
