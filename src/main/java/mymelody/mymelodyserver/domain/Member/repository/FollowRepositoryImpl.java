package mymelody.mymelodyserver.domain.Member.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import mymelody.mymelodyserver.domain.Member.dto.response.FollowInfo;
import mymelody.mymelodyserver.domain.Member.dto.response.QFollowInfo;
import mymelody.mymelodyserver.domain.Member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static mymelody.mymelodyserver.domain.Member.entity.QFollow.*;
import static mymelody.mymelodyserver.domain.Member.entity.QMember.member;

@Repository
public class FollowRepositoryImpl implements FollowRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public FollowRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<FollowInfo> getFollowerInfoPage(Member following, Pageable pageable) {
        List<FollowInfo> contents = queryFactory
                .select(new QFollowInfo(
                        follow.follower.id,
                        follow.follower.nickname
                ))
                .from(follow)
                .join(follow.following, member)
                .where(follow.following.eq(following))
                .orderBy(follow.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(follow.count())
                .from(follow)
                .where(follow.following.eq(following));

        return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<FollowInfo> getFollowingInfoPage(Member follower, Pageable pageable) {
        List<FollowInfo> contents = queryFactory
                .select(new QFollowInfo(
                        follow.following.id,
                        follow.following.nickname
                ))
                .from(follow)
                .join(follow.follower, member)
                .where(follow.follower.eq(follower))
                .orderBy(follow.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(follow.count())
                .from(follow)
                .where(follow.follower.eq(follower));

        return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchOne);

    }

}
