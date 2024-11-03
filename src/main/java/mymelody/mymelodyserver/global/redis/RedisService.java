package mymelody.mymelodyserver.global.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, String> redisBlackListTemplate;

    //redis에 <key 액세스 토큰, value 로그인Id> 삽입, timeout 초 동안 유지
    public void setBlackList(String token, String value, long timeout) {
        redisBlackListTemplate.opsForValue()
                .set(token, value, Duration.ofSeconds(timeout));
    }

    // key를 value로 반환
    public String getBlackList(String token){
        return redisBlackListTemplate.opsForValue().get(token);
    }

    public Boolean hasTokenInBlackList(String token) {
        return Boolean.TRUE.equals(redisBlackListTemplate.hasKey(token));
    }

    // key를 이용해 <key, value> 삭제
    public void deleteBlackList(String token){
        redisBlackListTemplate.delete(token);
    }
}
