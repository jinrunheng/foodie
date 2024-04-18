package com.github.exception;

import com.github.utils.CustomJSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @Author Dooby Kim
 * @Date 2024/4/18 下午11:53
 * @Version 1.0
 * @Desc 自定义异常捕获处理
 * @RestControllerAdvice 注解的作用：
 * @RestControllerAdvice是Spring Framework中的一个注解，它主要用于全局异常处理以及全局数据绑定。这个注解结合了@ControllerAdvice和@ResponseBody的功能，常用于RESTful API中。
 * 具体来说，@RestControllerAdvice的作用主要包括：
 * <p>
 * 1. 全局异常处理：你可以在这个类中定义方法来处理全局异常。当在Controller层中抛出异常时，Spring会查找带有 @ExceptionHandler 注解的方法来处理这个异常。这使得你可以在一个地方集中处理所有Controller中可能抛出的异常，而无需在每个Controller中都写一遍异常处理逻辑。
 * 2. 全局数据绑定：@RestControllerAdvice还可以用于全局数据绑定，比如你可以在这个类中通过@ModelAttribute注解添加一个全局可用的数据。然而，这个功能在RESTful API中可能用得较少，因为RESTful API通常返回的是JSON数据，而不是HTML页面。
 * 3. 全局数据预处理和后处理：你可以使用@InitBinder和@ResponseBodyAdvice等注解来进行数据的预处理和后处理。例如，你可能需要对传入的数据进行某些转换或验证，或者在返回数据给客户端之前添加一些额外的信息。
 * 4. 全局响应体修改：通过实现ResponseBodyAdvice接口，你可以修改或包装Controller方法的返回值。这对于统一API的响应格式、添加额外的响应信息等场景非常有用。
 * <p>
 * 总的来说，@RestControllerAdvice是一个强大的工具，它可以帮助你实现全局的异常处理、数据绑定、数据预处理和后处理等功能，从而提高代码的可维护性和可读性
 */
@RestControllerAdvice
public class CustomExceptionHandler {
    /**
     * 上传文件超过 500 KB 捕获异常并处理
     *
     * @param exceededException
     * @return
     */
    @ExceptionHandler
    public CustomJSONResult handleMaxUploadFile(MaxUploadSizeExceededException exceededException) {
        return CustomJSONResult.errorMsg("文件上传大小不能超过 500 KB！");
    }
}
