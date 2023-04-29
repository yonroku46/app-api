package com.app.demo.webapi.service;

import com.app.demo.dto.response.core.ResponseDto;

public interface MUserService {

    public ResponseDto findUserById(String userId, String userMail);
}
