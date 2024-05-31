package mymelody.mymelodyserver.global.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
// 에러 응답 형태 정의
public class ErrorResponse {
    private final String code;
    private final String message;

    public ErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.name();
        this.message = errorCode.getMessage();
    }
}
