package mymelody.mymelodyserver.global.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
// 에러 별 코드와 에러 메세지 정의
public enum ErrorCode {
    // 400 BAD REQUEST
    INVALID_AUTHORIZATION_CODE(HttpStatus.BAD_REQUEST, "유효한 코드가 아닙니다"),
    INVALID_COMMENT_DELETE_REQUEST(HttpStatus.BAD_REQUEST, "삭제를 요청한 사용자가 작성한 댓글이 아닙니다."),

    // 401 UNAUTHORIZED

    // 403 FORBIDDEN

    // 404 NOT FOUND
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."),
    SPOTIFY_PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "스포티파이 계정 정보를 찾을 수 없습니다."),
    MYMELODY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 마이멜로디를 찾을 수 없습니다."),
    LIKES_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 마이멜로디에 대한 좋아요를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 댓글을 찾을 수 없습니다.");

    // 409 CONFLICT

    private final HttpStatus httpStatus;
    private final String message;
}
