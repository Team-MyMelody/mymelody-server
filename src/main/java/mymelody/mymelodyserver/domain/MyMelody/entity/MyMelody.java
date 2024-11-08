package mymelody.mymelodyserver.domain.MyMelody.entity;

import jakarta.persistence.*;
import lombok.*;
import mymelody.mymelodyserver.domain.Member.entity.Member;
import mymelody.mymelodyserver.domain.Music.entity.Music;
import mymelody.mymelodyserver.global.entity.BaseTimeEntity;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MyMelody extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "my_melody_id")
    private Long id;

    private double latitude; //위도
    private double longitude; //경도

    private String content;

    @ColumnDefault("0")
    private int totalLikes;

    @ColumnDefault("0")
    private int totalComments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "music_id")
    private Music music;

    public void increaseTotalLikes() {
        this.totalLikes++;
    }

    public void decreaseTotalLikes() {
        this.totalLikes--;
    }

    public void increaseTotalComments() {
        this.totalComments++;
    }

    public void decreaseTotalComments() {
        this.totalComments--;
    }
}
