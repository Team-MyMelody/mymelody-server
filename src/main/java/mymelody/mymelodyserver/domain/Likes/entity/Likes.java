package mymelody.mymelodyserver.domain.Likes.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mymelody.mymelodyserver.domain.Member.entity.Member;
import mymelody.mymelodyserver.domain.MyMelody.entity.MyMelody;
import mymelody.mymelodyserver.global.entity.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Likes extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "likes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "my_melody_id")
    private MyMelody myMelody;

    @Builder
    public Likes(Member member, MyMelody myMelody) {
        this.member = member;
        this.myMelody = myMelody;
    }
}
