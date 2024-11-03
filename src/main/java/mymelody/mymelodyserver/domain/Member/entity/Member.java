package mymelody.mymelodyserver.domain.Member.entity;

import jakarta.persistence.*;
import lombok.*;
import mymelody.mymelodyserver.global.entity.BaseTimeEntity;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String spotifyId;
    private String name;
    private String nickname;
    private String email;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("\"USER\"")
    private Role role;

    private String refreshToken;

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
