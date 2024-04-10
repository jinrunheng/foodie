package com.github.bo.center;

import lombok.*;

import java.util.Date;

/**
 * @Author Dooby Kim
 * @Date 2024/4/10 下午11:59
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CenterUserBO {
    private String username;
    private String password;
    private String confirmPassword;
    private String nickname;
    private String realname;
    private String mobile;
    private String email;
    private Integer sex;
    private Date birthday;
}
