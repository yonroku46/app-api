package com.app.demo.webapi.service;

import com.app.demo.dto.request.SocialCommentReqDto;
import com.app.demo.dto.request.SocialFilterReqDto;
import com.app.demo.dto.response.core.ResponseDto;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ソーシャル機能サービス
 *
 * @author y_ha
 */
public interface SocialService {

    public ResponseDto getSocialInfo(Integer userId, Integer socialId);

    public ResponseDto getSocialList(Integer userId, SocialFilterReqDto req);

    public ResponseDto socialLikeInsert(Integer userId, Integer socialId);

    public ResponseDto socialLikeDelete(Integer userId, Integer socialId);

    public ResponseDto getSocialComment(Integer socialId);

    public ResponseDto socialCommentInsert(Integer userId, SocialCommentReqDto req);

    public ResponseDto socialCommentDelete(Integer userId, Integer socialId, Integer commentId);
}
