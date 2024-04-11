package com.github.controller.center;

import com.github.bo.center.CenterUserBO;
import com.github.dto.FoodieUserDTO;
import com.github.pojo.FoodieUser;
import com.github.service.center.CenterUserService;
import com.github.utils.CookieUtils;
import com.github.utils.CustomJSONResult;
import com.github.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
