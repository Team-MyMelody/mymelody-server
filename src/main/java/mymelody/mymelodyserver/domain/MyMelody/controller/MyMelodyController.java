package mymelody.mymelodyserver.domain.MyMelody.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mymelody.mymelodyserver.domain.MyMelody.dto.request.CreateMyMelody;
import mymelody.mymelodyserver.domain.MyMelody.dto.response.GetMyMelodiesByLocation;
import mymelody.mymelodyserver.domain.MyMelody.service.MyMelodyService;
import mymelody.mymelodyserver.global.auth.security.CustomUserDetails;
import mymelody.mymelodyserver.global.common.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/mymelody")
@RequiredArgsConstructor
@Tag(name = "MyMelody", description = "마이멜로디 API")
public class MyMelodyController {

    private final MyMelodyService myMelodyService;

    @Operation(summary = "반경 1km 내의 마이멜로디 목록 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @Parameters({
            @Parameter(name = "latitude", description = "사용자의 현재 위치 위도"),
            @Parameter(name = "longitude", description = "사용자의 현재 위치 경도"),
            @Parameter(name = "page", description = "조회할 페이지 위치, default는 1"),
            @Parameter(name = "size", description = "조회할 목록 사이즈, default는 10")
    })
    @GetMapping("/location")
    public ResponseEntity<GetMyMelodiesByLocation> getMyMelodiesByLocation(@RequestParam double latitude,
            @RequestParam double longitude, PageRequest pageRequest,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseEntity.ok(myMelodyService.getMyMelodiesByLocationWithPagination(latitude,
                longitude, pageRequest, customUserDetails == null ? 0 : customUserDetails.getMemberId()));
    }

    @Operation(summary = "지도에 마이멜로디 저장")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "저장 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/create")
    public ResponseEntity<?> createMyMelody(@RequestBody CreateMyMelody createMyMelody, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return myMelodyService.createMyMelody(createMyMelody, Long.parseLong(userDetails.getUsername()));
    }
}
