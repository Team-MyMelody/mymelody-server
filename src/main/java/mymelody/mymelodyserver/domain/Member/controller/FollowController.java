package mymelody.mymelodyserver.domain.Member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mymelody.mymelodyserver.domain.Member.dto.response.GetFollowPage;
import mymelody.mymelodyserver.domain.Member.service.FollowService;
import mymelody.mymelodyserver.global.auth.security.CustomUserDetails;
import mymelody.mymelodyserver.global.common.PageRequest;
import mymelody.mymelodyserver.global.entity.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/follow")
@RequiredArgsConstructor
@Tag(name = "Follow", description = "팔로우 API")
@Slf4j
public class FollowController {
    private final FollowService followService;

    @Operation(summary = "팔로우/취소하기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "팔로우/취소 성공"),
            @ApiResponse(responseCode = "404", description = "팔로워/팔로잉 사용자 찾을 수 없음",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/{followingId}")
    public ResponseEntity<?> follow(@PathVariable Long followingId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("[FollowController] follow");
        return followService.follow(followingId, Long.valueOf(userDetails.getUsername()));
    }

    @Operation(summary = "팔로워 조회",
            description = "page default 1, size default 10")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "팔로워 목록 불러오기 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @Parameter(name = "memberId", description = "팔로워 목록을 알고 싶은 사용자의 id")
    @GetMapping("/{memberId}/follower")
    public GetFollowPage getFollowerPage(@PathVariable Long memberId, PageRequest pageRequest) {
        log.info("[FollowController] getFollowerPage");
        return followService.getFollowerPage(memberId, pageRequest);
    }

    @Operation(summary = "팔로잉 조회",
            description = "page default 1, size default 10")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "팔로잉 목록 불러오기 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @Parameter(name = "memberId", description = "팔로잉 목록을 알고 싶은 사용자의 id")
    @GetMapping("/{memberId}/following")
    public GetFollowPage getFollowingPage(@PathVariable Long memberId, PageRequest pageRequest) {
        log.info("[FollowController] getFollowingPage");
        return followService.getFollowingPage(memberId, pageRequest);
    }


}
