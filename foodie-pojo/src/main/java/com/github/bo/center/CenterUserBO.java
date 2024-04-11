package com.github.bo.center;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
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

    @NotBlank(message = "用户昵称不能为空")
    @Length(max = 12, message = "用户昵称不能超过 12 位")
    private String nickname;

    @Length(max = 12, message = "用户真实姓名不能超过 12 位")
    private String realname;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String mobile;

    @Email
    private String email;

    @Min(value = 0, message = "性别选择不正确")
    @Max(value = 2, message = "性别选择不正确")
    private Integer sex;
    private Date birthday;
}
