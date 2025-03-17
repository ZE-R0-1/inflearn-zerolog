package com.zeroblog.zerolog.repository;

import com.zeroblog.zerolog.domain.Post;
import com.zeroblog.zerolog.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
