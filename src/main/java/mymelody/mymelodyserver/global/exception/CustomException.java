package mymelody.mymelodyserver.global.exception;

import lombok.Getter;
import mymelody.mymelodyserver.global.entity.ErrorCode;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
