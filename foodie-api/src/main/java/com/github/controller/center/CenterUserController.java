package com.github.controller.center;

import cn.hutool.core.date.DateUtil;
import com.github.bo.center.CenterUserBO;
import com.github.config.FileUpload;
import com.github.dto.FoodieUserDTO;
import com.github.pojo.FoodieUser;
import com.github.service.center.CenterUserService;
import com.github.utils.CookieUtils;
import com.github.utils.CustomJSONResult;
import com.github.utils.ImgSuffixCheckUtil;
import com.github.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @Author Dooby Kim
 * @Date 2024/4/10 下午11:55
 * @Version 1.0
 */
@Api(tags = {"用户中心-用户信息相关接口"})
@RestController
@RequestMapping("userInfo")
@Slf4j
public class CenterUserController {

    private static final String COOKIE_NAME = "user";

    @Resource
    private FileUpload fileUpload;

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
        // TODO 后续整改，增加 token 整合 redis 分布式会话
        return CustomJSONResult.ok(foodieUser);
    }

    @ApiOperation(value = "用户上传/修改头像")
    @PostMapping("uploadFace")
    public CustomJSONResult uploadAvatar(
            @RequestParam String userId,
            MultipartFile file,
            HttpServletRequest request, HttpServletResponse response
    ) {
        // 定义用户头像保存地址
        String uploadSpace = fileUpload.getImageUserFaceLocation();
        // 在路径上要为每一个用户增加 userId 作为路径区分，用于区分不同用户上传
        String uploadPathPrefix = userId;
        if (!Objects.isNull(file)) {
            // 开始文件上传
            final String fileName = file.getOriginalFilename();
            if (StringUtils.isNotBlank(fileName)) {
                final String[] split = fileName.split("\\.");
                // 获取文件后缀名
                String fileSuffix = split[split.length - 1];
                // 对后缀名进行判断，对图片格式进行限制
                if (!ImgSuffixCheckUtil.isIllegal(fileSuffix)) {
                    return CustomJSONResult.errorMsg("图片格式错误！");
                }
                // 文件名定义
                // face-{userId}.png
                // 覆盖式上传
                String newFileName = "face-" + userId + "." + fileSuffix;
                // 上传的头像最终保存位置
                String uploadPath = uploadSpace + uploadPathPrefix + "/" + newFileName;
                final File uploadFile = new File(uploadPath);
                // 如果父目录为空，则创建目录
                Objects.requireNonNull(uploadFile.getParentFile()).mkdirs();
                try (
                        FileOutputStream fileOutputStream = new FileOutputStream(uploadFile);
                        InputStream inputStream = file.getInputStream();
                ) {
                    IOUtils.copy(inputStream, fileOutputStream);
                } catch (IOException exception) {
                    log.error("error", exception);
                }

                // 更新用户头像 -> 数据库
                // 图片服务地址
                // 由于浏览器可能存在缓存，所以我们在后端添加时间戳保证更新后的图片可以及时刷新
                String imageServerUrl = fileUpload.getImageServerUrl() + uploadPathPrefix + "/" + newFileName;
                String imageSeverUrlWithTimeStamp = imageServerUrl + "?t=" + DateUtil.format(new Date(), "yyyyMMddHHmmss");
                final FoodieUser result = centerUserService.updateUserFace(userId, imageSeverUrlWithTimeStamp);
                // 用户更新后，也要更新 Cookie
                FoodieUserDTO foodieUserDTO = new FoodieUserDTO();
                BeanUtils.copyProperties(result, foodieUserDTO);
                final String cookie = JsonUtils.objectToJson(foodieUserDTO);
                CookieUtils.setCookie(request, response, COOKIE_NAME, cookie);
                // TODO 后续整改，增加 token 整合 redis 分布式会话
                return CustomJSONResult.ok();
            }
        } else {
            return CustomJSONResult.errorMsg("文件不能为空！");
        }
        return CustomJSONResult.ok();
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
