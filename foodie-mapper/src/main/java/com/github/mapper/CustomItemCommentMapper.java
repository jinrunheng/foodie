package com.github.mapper;

import com.github.vo.MyCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author Dooby Kim
 * @Date 2024/5/12 上午12:35
 * @Version 1.0
 */
public interface CustomItemCommentMapper {
    void saveComments(Map<String, Object> map);

    List<MyCommentVO> queryMyComments(@Param("paramsMap") Map<String, String> paramsMap);
}
