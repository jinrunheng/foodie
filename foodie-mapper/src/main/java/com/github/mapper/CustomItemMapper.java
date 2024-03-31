package com.github.mapper;

import com.github.vo.ItemCommentVO;
import com.github.vo.SearchItemsVO;
import com.github.vo.ShopCartVO;
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

    List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String, Object> paramsMap);

    List<SearchItemsVO> searchItemsByThirdCat(@Param("paramsMap") Map<String, Object> paramsMap);

    List<ShopCartVO> queryItemsBySpecIds(@Param("paramsList") List<String> specIds);

    int decreaseItemSpecStock(@Param("specId") String specId, @Param("buyCounts") int buyCounts);
}
