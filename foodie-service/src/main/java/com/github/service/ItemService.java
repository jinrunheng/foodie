package com.github.service;

import com.github.pojo.Item;
import com.github.pojo.ItemImg;
import com.github.pojo.ItemParam;
import com.github.pojo.ItemSpec;

import java.util.List;

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
}
