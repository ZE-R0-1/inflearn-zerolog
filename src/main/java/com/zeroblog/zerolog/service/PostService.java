package com.zeroblog.zerolog.service;


import com.zeroblog.zerolog.domain.Post;
import com.zeroblog.zerolog.domain.PostEditor;
import com.zeroblog.zerolog.exception.PostNodFound;
import com.zeroblog.zerolog.repository.PostRepository;
import com.zeroblog.zerolog.request.PostCreate;
import com.zeroblog.zerolog.request.PostEdit;
import com.zeroblog.zerolog.request.PostResponse;
import com.zeroblog.zerolog.request.PostSearch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate) {
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.save(post);
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNodFound::new);

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();

        /**
         *  PostController -> PostWebService -> Repository
         *                    PostService
         */
    }

    public List<PostResponse> getList(PostSearch postSearch) {
        return postRepository.getList(postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    // 글이 너무 많은 경우 -> 비용이 너무 많이 든다
    // 글 -> 1억개 -> DB 뻗음
    // DB -> 어플 서버로 전달하는 시간, 트래픽비용가능성

    @Transactional
    public void edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNodFound::new);

        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();

        PostEditor postEditor = editorBuilder.title(postEdit.getTitle())
                .content(postEdit.getContent())
                .build();

        post.edit(postEditor);
    }

    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNodFound::new);

        postRepository.delete(post);
    }
}
