package mymelody.mymelodyserver.global.auth.service;

import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import java.io.IOException;
import java.net.URI;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpotifyService {
    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    @Value("${spotify.redirectURI}")
    private URI redirectURI;

    private final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId).setClientSecret(clientSecret)
            .build();
    public String getAccessToken(String code) {
        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(clientId, clientSecret, code, redirectURI).build();
        try {
            AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();
            return authorizationCodeCredentials.getAccessToken();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            // TODO 에러 처리 추가
            e.printStackTrace();
            return null;
        }
    }

    public User getSpotifyProfile(String accessToken) {
        spotifyApi.setAccessToken(accessToken);
        GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile().build();
        try {
            User spotifyProfile = getCurrentUsersProfileRequest.execute();
            return spotifyProfile;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


}
