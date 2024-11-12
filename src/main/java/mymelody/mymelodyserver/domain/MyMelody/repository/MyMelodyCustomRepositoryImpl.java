package mymelody.mymelodyserver.domain.MyMelody.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mymelody.mymelodyserver.domain.MyMelody.entity.MyMelody;
import mymelody.mymelodyserver.domain.MyMelody.entity.QMyMelody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class MyMelodyCustomRepositoryImpl implements MyMelodyCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;
    private final QMyMelody myMelody = QMyMelody.myMelody;

    @Override
    public Page<MyMelody> findAllByRange1km(double latitude, double longitude, Pageable pageable) {
        List<MyMelody> myMelodies = jpaQueryFactory.selectFrom(myMelody)
                .where(
                        Expressions.stringTemplate("ST_Distance_Sphere({0}, {1})",
                                Expressions.stringTemplate("POINT({0}, {1})",
                                        longitude, latitude),
                                Expressions.stringTemplate("POINT({0}, {1})",
                                        myMelody.longitude, myMelody.latitude)
                        ).loe(String.valueOf(100000))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory.select(myMelody.count())
                .from(myMelody)
                .where(
                        Expressions.stringTemplate("ST_Distance_Sphere({0}, {1})",
                                Expressions.stringTemplate("POINT({0}, {1})",
                                        longitude, latitude),
                                Expressions.stringTemplate("POINT({0}, {1})",
                                        myMelody.longitude, myMelody.latitude)
                        ).loe(String.valueOf(1000))
                ).fetchOne();

        return new PageImpl<>(myMelodies, pageable, count);
    }
}
