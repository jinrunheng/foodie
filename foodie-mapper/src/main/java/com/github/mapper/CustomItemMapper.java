package com.github.mapper;

import com.github.vo.ItemCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author Dooby Kim
 * @Date 2024/3/15 下午9:51
 * @Version 1.0
 */
public interface CustomItemMapper {
    List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String, Object> paramsMap);
}
