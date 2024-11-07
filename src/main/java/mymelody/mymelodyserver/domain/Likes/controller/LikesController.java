package mymelody.mymelodyserver.domain.Likes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import mymelody.mymelodyserver.domain.Likes.service.LikesService;
import mymelody.mymelodyserver.global.auth.security.CustomUserDetails;
import mymelody.mymelodyserver.global.entity.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    @Operation(summary = "likes 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "생성 성공"),
            @ApiResponse(responseCode = "404", description = """
                    1. 존재하지 않는 사용자
                    2. 존재하지 않는 마이멜로디""",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/{myMelodyId}")
    public ResponseEntity<Void> createLikes(@PathVariable Long myMelodyId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        likesService.createLikes(myMelodyId, customUserDetails.getMemberId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "likes 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = """
                    1. 존재하지 않는 사용자
                    2. 존재하지 않는 마이멜로디""",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @DeleteMapping("/{myMelodyId}")
    public ResponseEntity<Void> deleteLikes(@PathVariable Long myMelodyId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        likesService.deleteLikes(myMelodyId, customUserDetails.getMemberId());
        return ResponseEntity.ok().build();
    }
}
