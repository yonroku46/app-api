package com.app.demo.webapi.service.impl;

import com.app.demo.aspect.LocaleAspect;
import com.app.demo.constants.MessageIdConst;
import com.app.demo.dao.*;
import com.app.demo.dao.entity.*;
import com.app.demo.dto.request.SocialCommentReqDto;
import com.app.demo.dto.request.SocialFilterReqDto;
import com.app.demo.dto.response.*;
import com.app.demo.dto.response.core.Information;
import com.app.demo.dto.response.core.ResponseDto;
import com.app.demo.utils.ResponseUtils;
import com.app.demo.webapi.service.SocialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ソーシャル機能サービス詳細
 *
 * @author y_ha
 */
@Service
@Slf4j
public class SocialServiceImpl implements SocialService {

    @Autowired
    MessageSource messageSource;

    @Autowired
    private MSocialDao mSocialDao;

    @Autowired
    private MSocialLikeDao mSocialLikeDao;

    @Autowired
    private MSocialCommentDao mSocialCommentDao;

    @Override
    public ResponseDto getSocialInfo(Integer userId, Integer socialId) {
        SocialInfoResDto res = mSocialDao.findSocialByPk(socialId);

        if (res != null) {
            // お気に入りカウントセット
            Map<Integer, Integer> likedCountMap = mSocialLikeDao.getLikedCount(Collections.singletonList(socialId));
            res.setLikedCount(likedCountMap.getOrDefault(res.getSocialId(), 0));
            // お気に入り判定
            if (userId != null) {
                MSocialLike liked = mSocialLikeDao.findUserLiked(userId, socialId);
                res.setLiked(liked != null);
            }
        }

        return ResponseUtils.generateDtoSuccess(new Information(MessageIdConst.I_GETTING_SUCCESS,
                messageSource.getMessage(MessageIdConst.I_GETTING_SUCCESS, new String[]{"SocialInfo"}, LocaleAspect.LOCALE)), res);
    }

    @Override
    public ResponseDto getSocialList(Integer userId, SocialFilterReqDto filter) {
        // フィルターでソーシャルリストを取得
        List<SocialInfoResDto> res = mSocialDao.findSocialByFilter(filter);
        List<Integer> socialIdList = res.stream()
                                        .map(SocialInfoResDto::getSocialId)
                                        .collect(Collectors.toList());
        // お気に入りカウントセット
        if (!CollectionUtils.isEmpty(socialIdList)) {
            Map<Integer, Integer> likedCountMap = mSocialLikeDao.getLikedCount(socialIdList);
            for (SocialInfoResDto resData : res) {
                resData.setLikedCount(likedCountMap.getOrDefault(resData.getSocialId(),0));
            }
        }

        // お気に入り判定
        if (userId != null) {
            List<MSocialLike> likedList = mSocialLikeDao.findUserLikedList(userId);
            for (SocialInfoResDto social : res) {
                boolean isLiked = false;
                for (MSocialLike liked : likedList) {
                    if (social.getSocialId() == liked.getSocialId()) {
                        isLiked = true;
                        break;
                    }
                }
                social.setLiked(isLiked);
            }
        }

        return ResponseUtils.generateDtoSuccess(new Information(MessageIdConst.I_GETTING_SUCCESS,
                messageSource.getMessage(MessageIdConst.I_GETTING_SUCCESS, new String[]{"SocialList"}, LocaleAspect.LOCALE)), new SocialInfoListResDto(res));
    }

    @Override
    public ResponseDto socialLikeInsert(Integer userId, Integer socialId) {
        FlgResDto res = new FlgResDto();

        mSocialLikeDao.insertRow(userId, socialId);
        res.setIsFlg(Boolean.TRUE);

        return ResponseUtils.generateDtoSuccess(new Information(MessageIdConst.I_GETTING_SUCCESS,
                messageSource.getMessage(MessageIdConst.I_INSERT_SUCCESS, new String[]{"SocialLike"}, LocaleAspect.LOCALE)), res);
    }

    @Override
    public ResponseDto socialLikeDelete(Integer userId, Integer socialId) {
        FlgResDto res = new FlgResDto();

        mSocialLikeDao.deleteRow(userId, socialId);
        res.setIsFlg(Boolean.TRUE);

        return ResponseUtils.generateDtoSuccess(new Information(MessageIdConst.I_GETTING_SUCCESS,
                messageSource.getMessage(MessageIdConst.I_DELETE_SUCCESS, new String[]{"SocialLike"}, LocaleAspect.LOCALE)), res);
    }

    @Override
    public ResponseDto getSocialComment(Integer socialId) {
        // ソーシャルのコメントリスト取得
        List<SocialCommentResDto> commentList = mSocialCommentDao.findCommentList(socialId);
        // コメントをグループ化するマップ生成
        Map<Integer, SocialCommentResDto> commentMap = new HashMap<>();

        // 返信されたコメントを整理
        for (SocialCommentResDto comment : commentList) {
            Integer commentId = comment.getCommentId();
            Integer reply = comment.getReply();

            // replyがNULLの場合新しいコメント
            if (reply == null) {
                commentMap.put(commentId, comment);
            } else {
                // 親コメントのreplyリストに追加
                SocialCommentResDto parentComment = commentMap.get(reply);
                if (parentComment != null) {
                    parentComment.getReplies().add(comment);
                }
            }
        }

        // 結果をもう一度リスト化
        List<SocialCommentResDto> res = new ArrayList<>(commentMap.values());

        return ResponseUtils.generateDtoSuccess(new Information(MessageIdConst.I_GETTING_SUCCESS,
                messageSource.getMessage(MessageIdConst.I_GETTING_SUCCESS, new String[]{"SocialComment"}, LocaleAspect.LOCALE)), new SocialCommentListResDto(res));
    }

    @Override
    public ResponseDto socialCommentInsert(Integer userId, SocialCommentReqDto req) {
        FlgResDto res = new FlgResDto();

        mSocialCommentDao.insertRow(userId, req);
        res.setIsFlg(Boolean.TRUE);

        return ResponseUtils.generateDtoSuccess(new Information(MessageIdConst.I_GETTING_SUCCESS,
                messageSource.getMessage(MessageIdConst.I_INSERT_SUCCESS, new String[]{"SocialComment"}, LocaleAspect.LOCALE)), res);
    }

    @Override
    public ResponseDto socialCommentDelete(Integer userId, Integer socialId, Integer commentId) {
        FlgResDto res = new FlgResDto();

        mSocialCommentDao.deleteRow(userId, socialId, commentId);
        res.setIsFlg(Boolean.TRUE);

        return ResponseUtils.generateDtoSuccess(new Information(MessageIdConst.I_GETTING_SUCCESS,
                messageSource.getMessage(MessageIdConst.I_DELETE_SUCCESS, new String[]{"SocialComment"}, LocaleAspect.LOCALE)), res);
    }
}
