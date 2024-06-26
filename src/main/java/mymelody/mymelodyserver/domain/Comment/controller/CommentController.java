package mymelody.mymelodyserver.domain.Comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import mymelody.mymelodyserver.domain.Comment.dto.request.CreateComment;
import mymelody.mymelodyserver.domain.Comment.dto.response.GetCommentsByMyMelody;
import mymelody.mymelodyserver.domain.Comment.service.CommentService;
import mymelody.mymelodyserver.global.common.PageRequest;
import mymelody.mymelodyserver.global.entity.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "comment 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "생성 성공"),
            @ApiResponse(responseCode = "404", description = """
                    1. 존재하지 않는 사용자
                    2. 존재하지 않는 마이멜로디""",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CreateComment createComment) {
        // TODO spring security 적용 후 authenticationPrincipal로 사용자 정보 받아오는 코드 추가
        commentService.createComment(createComment, 1L);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "특정 마이멜로디에 대한 댓글 목록 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @Parameters({
            @Parameter(name = "myMelodyId", description = "마이멜로디 아이디"),
            @Parameter(name = "page", description = "조회할 페이지 위치, default는 1"),
            @Parameter(name = "size", description = "조회할 목록 사이즈, default는 10")
    })
    @GetMapping("/mymelody/{myMelodyId}")
    public ResponseEntity<GetCommentsByMyMelody> getCommentsByMyMelody(@PathVariable Long myMelodyId,
            PageRequest pageRequest) {
        return ResponseEntity.ok(commentService.getCommentsByMyMelody(myMelodyId, pageRequest));
    }
}
