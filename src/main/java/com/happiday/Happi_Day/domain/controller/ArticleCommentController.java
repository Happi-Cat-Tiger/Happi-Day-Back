package com.happiday.Happi_Day.domain.controller;

import com.happiday.Happi_Day.domain.entity.article.dto.ReadCommentDto;
import com.happiday.Happi_Day.domain.entity.article.dto.WriteCommentDto;
import com.happiday.Happi_Day.domain.service.ArticleCommentService;
import com.happiday.Happi_Day.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/articles/{articleId}/comments")
@RequiredArgsConstructor
public class ArticleCommentController {
    private final ArticleCommentService articleCommentService;

    @PostMapping
    public ResponseEntity<ReadCommentDto> writeComment(
            @PathVariable("articleId") Long articleId,
            @RequestBody WriteCommentDto requestDto) {
        String username = SecurityUtils.getCurrentUsername();
        ReadCommentDto responseComment = articleCommentService.writeComment(articleId, requestDto, username);
        return new ResponseEntity<>(responseComment, HttpStatus.CREATED);
    }

    // 댓글 조회
    @GetMapping()
    public ResponseEntity<Page<ReadCommentDto>> readComment(
            @PathVariable("articleId") Long articleId,
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ReadCommentDto> responseComments = articleCommentService.readComment(articleId, pageable);
        return new ResponseEntity<>(responseComments, HttpStatus.OK);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<ReadCommentDto> updateComment(
            @PathVariable("articleId") Long articleId,
            @PathVariable("commentId") Long commentId,
            @RequestBody WriteCommentDto requestDto) {
        ReadCommentDto responseComment = articleCommentService.updateComment(articleId, commentId, requestDto);
        return new ResponseEntity<>(responseComment, HttpStatus.OK);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable("articleId") Long articleId,
            @PathVariable("commentId") Long commentId) {
        articleCommentService.deleteComment(articleId, commentId);
        return new ResponseEntity<>("삭제 성공", HttpStatus.OK);
    }


}
