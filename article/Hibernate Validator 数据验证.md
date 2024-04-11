## Hibernate Validator 内置注解
- `@Null` 被注释的元素必须为 `null`
- `@NotNull` 被注释的元素必须不为 `null`
- `@AssertTrue` 被注释的元素必须为 `true`
- `@AssertFalse` 被注释的元素必须为 `false`
- `@Min(value)` 被注释的元素必须是一个数字，其值必须大于等于指定的最小值
- `@Max(value)` 被注释的元素必须是一个数字，其值必须小于等于指定的最大值
- `@DecimalMin(value)` 被注释的元素必须是一个数字，其值必须大于等于指定的最小值
- `@DecimalMax(value)` 被注释的元素必须是一个数字，其值必须小于等于指定的最大值
- `@Size(max=, min=)` 被注释的元素的大小必须在指定的范围内
- `@Digits (integer, fraction)` 被注释的元素必须是一个数字，其值必须在可接受的范围内
- `@Past` 被注释的元素必须是一个过去的日期
- `@Future` 被注释的元素必须是一个将来的日期
- `@Pattern(regex=,flag=)` 被注释的元素必须符合指定的正则表达式
- `@NotBlank(message =)` 验证字符串非 `null`，且长度必须大于0
- `@Email` 被注释的元素必须是电子邮箱地址
- `@Length(min=,max=)` 被注释的字符串的大小必须在指定的范围内
- `@NotEmpty`被注释的字符串的必须非空
- `@Range(min=,max=,message=)` 被注释的元素必须在合适的范围内

### 验证 Bean 示例：
```java
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
```

### 验证 Controller 示例
```java
/**
 * @Author Dooby Kim
 * @Date 2024/4/10 下午11:55
 * @Version 1.0
 */
@Api(tags = {"用户中心-用户信息相关接口"})
@RestController
@RequestMapping("userInfo")
public class CenterUserController {

    private static final String COOKIE_NAME = "user";

    @Resource
    private CenterUserService centerUserService;

    @ApiOperation(value = "根据用户 ID，修改用户信息")
    @PostMapping("update")
    public CustomJSONResult update(@RequestParam String userId, @RequestBody @Valid CenterUserBO userBO,
                                   BindingResult bindingResult,
                                   HttpServletRequest request, HttpServletResponse response) {
        // 判断 BindingResult 是否包含错误的验证信息，如果有则直接 return
        if (bindingResult.hasErrors()) {
            final Map<String, String> errors = getErrors(bindingResult);
            return CustomJSONResult.errorMap(errors);
        }
        FoodieUser foodieUser = centerUserService.updateUserInfo(userId, userBO);
        // 用户更新后，也要更新 Cookie
        FoodieUserDTO foodieUserDTO = new FoodieUserDTO();
        BeanUtils.copyProperties(foodieUser, foodieUserDTO);
        final String cookie = JsonUtils.objectToJson(foodieUserDTO);
        CookieUtils.setCookie(request, response, COOKIE_NAME, cookie);
        return CustomJSONResult.ok(foodieUser);
    }

    private Map<String, String> getErrors(BindingResult bindingResult) {
        Map<String, String> map = new HashMap<>();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError error : fieldErrors) {
            // 发生验证错误对应的某一属性
            String errorField = error.getField();
            // 验证错误的信息
            final String errorMsg = error.getDefaultMessage();
            map.put(errorField, errorMsg);
        }
        return map;
    }
}
``` 