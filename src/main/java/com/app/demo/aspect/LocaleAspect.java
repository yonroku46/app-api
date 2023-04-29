package com.app.demo.aspect;

import com.app.demo.constants.SecurityConst;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Aspect
@Component
public class LocaleAspect {

    @Autowired
    protected HttpServletRequest request;

    public static Locale LOCALE;

    @Value("${app.language.default}")
    private String SYSTEM_LANGUAGE_DEFAULT;

    @Before("execution(* com.app.demo.controller.*.*(..))")
    public void getLocale() {
        String language = request.getHeader(SecurityConst.LANGUAGE);
        LOCALE = SYSTEM_LANGUAGE_DEFAULT.equalsIgnoreCase(language) ? Locale.JAPAN : Locale.ENGLISH;
    }
}
