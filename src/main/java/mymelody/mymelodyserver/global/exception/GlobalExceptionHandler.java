package mymelody.mymelodyserver.global.exception;

import mymelody.mymelodyserver.global.entity.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
// 전역 예외 처리를 담당하는 Class
public class GlobalExceptionHandler {

    // 비즈니스 로직
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(new ErrorResponse(e.getErrorCode()));
    }
}
