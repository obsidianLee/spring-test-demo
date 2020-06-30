package com.test.demo;

import lombok.Data;

import java.util.regex.Pattern;

/**
 * @author Lixueyuan
 * @date 2019/9/30 18:12
 */
@Data
public class Email<T> {
    /**
     * 邮箱
     */
    private static final String EMAIL_REGEX="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    /**
     * 邮箱格式校验
     * @param email
     * @return
     */
    public static boolean  checkEmail(String email){
        return Pattern.matches(EMAIL_REGEX, email.trim());
    }

    T obj;
}
