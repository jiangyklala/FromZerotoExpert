package com.jiang.fzte.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class PasswordLimit {

    /**
     * 密码限制
     * @param password 传入的密码
     * @return 0 - 密码符合; 1 - 密码长度只能在 6 - 16 位; 2 - 密码只能包含数字和英文大小写三种字符; 3 - 密码需要至少两种字符
     */
    public static int passWordLimit(String password) {

        // 长度判断
        if (password.length() < 6 || password.length() > 16) {
            return 1;
        }

        // 强度判断: a.密码字符只能是 数字+英文大小写（算是三种字符); b.包含 2 种及其以上字符;
        Pattern pattern1 = Pattern.compile("^[a-zA-Z0-9]*$"); // 只能匹配 a
        Pattern pattern2 = Pattern.compile("^(?![a-z]+$)(?![A-Z]+$)(?![0-9]+$)[a-zA-Z0-9]*$"); // 只能匹配 b
        if (!pattern1.matcher(password).matches()) {
            return 2;
        }
        if (!pattern2.matcher(password).matches()) {
            return 3;
        }

        return 0;
    }
}
