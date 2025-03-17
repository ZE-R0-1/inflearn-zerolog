package com.zeroblog.zerolog.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zeroblog.zerolog.domain.Post;
import com.zeroblog.zerolog.domain.QPost;
import com.zeroblog.zerolog.request.PostSearch;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Post> getList(PostSearch postSearch) {
        return jpaQueryFactory.selectFrom(QPost.post)
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .orderBy(QPost.post.id.desc())
                .fetch();
    }
}
