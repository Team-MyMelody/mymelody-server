package mymelody.mymelodyserver.domain.Comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mymelody.mymelodyserver.domain.Comment.dto.request.CreateComment;
import mymelody.mymelodyserver.domain.Comment.dto.response.GetCommentsByMyMelody;
import mymelody.mymelodyserver.domain.Comment.service.CommentService;
import mymelody.mymelodyserver.global.auth.security.CustomUserDetails;
import mymelody.mymelodyserver.global.common.PageRequest;
import mymelody.mymelodyserver.global.entity.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
@Tag(name = "Comment", description = "코멘트 API")
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
    public ResponseEntity<Void> createComment(@RequestBody CreateComment createComment,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        commentService.createComment(createComment, customUserDetails.getMemberId());
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
            PageRequest pageRequest, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseEntity.ok(commentService.getCommentsByMyMelody(myMelodyId, pageRequest,
                customUserDetails == null ? 0 : customUserDetails.getMemberId()));
    }

    @Operation(summary = "comment 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "400", description = "삭제를 요청한 사용자가 작성한 댓글이 아님",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = """
                    1. 존재하지 않는 사용자
                    2. 존재하지 않는 comment""",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @Parameters({
            @Parameter(name = "commentId", description = "comment 아이디")
    })
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        commentService.deleteComment(commentId, customUserDetails.getMemberId());
        return ResponseEntity.ok().build();
    }
}
