package com.zeroblog.zerolog.repository;

import com.zeroblog.zerolog.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {



}
