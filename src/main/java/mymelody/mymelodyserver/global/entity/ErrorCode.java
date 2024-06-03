package mymelody.mymelodyserver.global.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
// 에러 별 코드와 에러 메세지 정의
public enum ErrorCode {
    // 400 BAD REQUEST

    // 401 UNAUTHORIZED

    // 403 FORBIDDEN

    // 404 NOT FOUND
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."),
    MYMELODY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 마이멜로디를 찾을 수 없습니다.");

    // 409 CONFLICT

    private final HttpStatus httpStatus;
    private final String message;
}
