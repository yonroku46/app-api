package com.app.demo.webapi.controller;

import com.app.demo.aspect.attribute.CheckToken;
import com.app.demo.dto.request.SocialCommentReqDto;
import com.app.demo.dto.request.SocialFilterReqDto;
import com.app.demo.dto.request.SocialLikeReqDto;
import com.app.demo.dto.response.core.ResponseDto;
import com.app.demo.webapi.service.SocialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * ソーシャル機能コントローラー
 *
 * @author y_ha
 */
@RestController
@RequestMapping("/social")
@Slf4j
public class SocialController extends BaseController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private SocialService socialService;

    @GetMapping("/info")
    public ResponseDto getSocialInfo(@RequestParam("socialId") Integer socialId) {
        Integer userId = super.getCurrentUserId();
        return socialService.getSocialInfo(userId, socialId);
    }

    @GetMapping("/filter")
    public ResponseDto getSocialList(@Validated SocialFilterReqDto req) {
        Integer userId = super.getCurrentUserId();
        return socialService.getSocialList(userId, req);
    }

    @PostMapping("/like")
    @CheckToken
    public ResponseDto socialLikeInsert(@RequestBody SocialLikeReqDto req) {
        Integer userId = super.getCurrentUserId();
        return socialService.socialLikeInsert(userId, req.getSocialId());
    }

    @DeleteMapping("/like")
    @CheckToken
    public ResponseDto socialLikeDelete(@RequestParam("socialId") Integer socialId) {
        Integer userId = super.getCurrentUserId();
        return socialService.socialLikeDelete(userId, socialId);
    }

    @GetMapping("/comment")
    public ResponseDto getSocialComment(@RequestParam("socialId") Integer socialId) {
        return socialService.getSocialComment(socialId);
    }

    @PostMapping("/comment")
    @CheckToken
    public ResponseDto socialCommentInsert(@RequestBody SocialCommentReqDto req) {
        Integer userId = super.getCurrentUserId();
        return socialService.socialCommentInsert(userId, req);
    }

    @DeleteMapping("/comment")
    @CheckToken
    public ResponseDto socialCommentDelete(@RequestParam("socialId") Integer socialId, @RequestParam("commentId") Integer commentId) {
        Integer userId = super.getCurrentUserId();
        return socialService.socialCommentDelete(userId, socialId, commentId);
    }
}
