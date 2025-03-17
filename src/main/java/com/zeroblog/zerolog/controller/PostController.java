package com.zeroblog.zerolog.controller;

import com.zeroblog.zerolog.domain.Post;
import com.zeroblog.zerolog.exception.InvalidRequest;
import com.zeroblog.zerolog.request.PostCreate;
import com.zeroblog.zerolog.request.PostEdit;
import com.zeroblog.zerolog.request.PostResponse;
import com.zeroblog.zerolog.request.PostSearch;
import com.zeroblog.zerolog.service.PostService;
import jakarta.persistence.PostUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {
    // SSR -> jsp, thymeleaf, mustache, freemarker
            // -> html rendering
    // SPA -> vue
            // javascript + <-> API
    //  vue -> vue + SSR -> nuxt.js
    //  react -> react + SSR - next.js


    // HTTP Method
    // GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
    // 글 등록
    // POST Method

    private final PostService postService;

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) {
        // Case1. 저장한 데이터 Entity -> response로 응답하기
        // Case2. 저장한 데이터의 primary_id -> response 응답하기
        //          Client에서는 수신한 id를 글 조회 API를 통해서 데이터를 수신받음
        // Case3. 응답 필요 없음 -> 클라이언트에서 모든 POST(글) 데이터 context를 잘 관리함
        // Bad Case: 서버에서 -> 반드시 이렇게 할껍니다! fix
        //          -> 서버에서 차라리 유연하게 대응하는게 좋음
        //          -> 한 번에 일괄적으로 잘 처리되는 케이스는 없음, 잘 관리하는 형태가 중요
        request.validate();
        postService.write(request);
    }

    // 단건 조회 API
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId) {
        return postService.get(postId);
    }

    /**
     *  /posts -> 글 전체 조회(검색 + 페이징)
     *  /posts/{postId} -> 글 한개만 조회
     */

    // 조회 API
    // 여러 개의 글 조회 API
    // /posts
    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch) {
        return postService.getList(postSearch);
    }

    @PatchMapping("/posts/{postId}")
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit request) {
        postService.edit(postId, request);
    }

    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }
}
