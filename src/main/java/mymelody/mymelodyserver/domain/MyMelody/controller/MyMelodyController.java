package mymelody.mymelodyserver.domain.MyMelody.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mymelody.mymelodyserver.domain.MyMelody.dto.response.GetMyMelodiesByLocation;
import mymelody.mymelodyserver.domain.MyMelody.dto.response.MyMelodyInfo;
import mymelody.mymelodyserver.domain.MyMelody.service.MyMelodyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mymelody")
@RequiredArgsConstructor
public class MyMelodyController {

    private final MyMelodyService myMelodyService;

    @Operation(summary = "반경 1km 내의 마이멜로디 목록 조회(페이지네이션)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @Parameters({
            @Parameter(name = "latitude", description = "사용자의 현재 위치 위도"),
            @Parameter(name = "longitude", description = "사용자의 현재 위치 경도"),
            @Parameter(name = "page", description = "조회할 페이지 위치, default는 1"),
            @Parameter(name = "size", description = "조회할 목록 사이즈, default는 10")
    })
    @GetMapping("/location/page")
    public ResponseEntity<GetMyMelodiesByLocation> getMyMelodiesByLocation(@RequestParam double latitude,
            @RequestParam double longitude, @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(myMelodyService.getMyMelodiesByLocationWithPagination(latitude,
                longitude, page, size));
    }

    @Operation(summary = "반경 1km 내의 마이멜로디 목록 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @Parameters({
            @Parameter(name = "latitude", description = "사용자의 현재 위치 위도"),
            @Parameter(name = "longitude", description = "사용자의 현재 위치 경도")
    })
    @GetMapping("/location")
    public ResponseEntity<List<MyMelodyInfo>> getMyMelodiesByLocation(@RequestParam double latitude,
            @RequestParam double longitude) {
        return ResponseEntity.ok(myMelodyService.getMyMelodiesByLocation(latitude, longitude));
    }
}
