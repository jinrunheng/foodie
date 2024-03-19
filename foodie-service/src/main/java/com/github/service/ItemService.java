package com.github.service;

import com.github.pojo.Item;
import com.github.pojo.ItemImg;
import com.github.pojo.ItemParam;
import com.github.pojo.ItemSpec;
import com.github.utils.PagedGridResult;
import com.github.vo.CommentLevelCountsVO;
import com.github.vo.ShopCartVO;

import java.util.List;
import java.util.Map;

/**
 * @Author Dooby Kim
 * @Date 2024/3/13 下午9:55
 * @Version 1.0
 * @Desc 商品服务
 */
public interface ItemService {
    /**
     * 根据商品 ID 查询商品信息
     *
     * @param itemId
     * @return
     */
    Item queryItemById(String itemId);

    /**
     * 根据商品 ID 查询商品图片
     *
     * @param itemId
     * @return
     */
    List<ItemImg> queryItemImgList(String itemId);

    /**
     * 根据商品 ID 查询商品规格
     *
     * @param itemId
     * @return
     */
    List<ItemSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品 ID 查询商品参数
     *
     * @param itemId
     * @return
     */
    ItemParam queryItemParam(String itemId);

    /**
     * 根据商品 ID 查询商品评价数量（总评价数，好评数，中评数，差评数）
     *
     * @param itemId
     * @return
     */
    CommentLevelCountsVO queryCommentCounts(String itemId);

    /**
     * 查询商品评论(分页)
     *
     * @param map      key:itemId,level
     * @param page     分页，表示第几页
     * @param pageSize 分页，每页多少条数据
     * @return
     */
    PagedGridResult queryPagedComments(Map<String, Object> map, Integer page, Integer pageSize);

    /**
     * 搜索商品列表
     *
     * @param map
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult searchItems(Map<String, Object> map, Integer page, Integer pageSize);

    /**
     * 根据三级分类查询商品列表
     *
     * @param map
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult searchItemsByThirdCat(Map<String, Object> map, Integer page, Integer pageSize);

    /**
     * 用于刷新渲染购物车中的商品数据:根据规格 ID（拼接形式），查询最新的购物车中的商品列表
     *
     * @param specIds
     * @return
     */
    List<ShopCartVO> queryItemsBySpecIds(String specIds);
}
