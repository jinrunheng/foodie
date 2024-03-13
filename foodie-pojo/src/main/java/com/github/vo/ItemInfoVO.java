package com.github.vo;

import com.github.pojo.Item;
import com.github.pojo.ItemImg;
import com.github.pojo.ItemParam;
import com.github.pojo.ItemSpec;
import lombok.*;

import java.util.List;

/**
 * @Author Dooby Kim
 * @Date 2024/3/13 下午10:23
 * @Version 1.0
 * @Desc 商品详情 VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemInfoVO {
    private Item item;
    private List<ItemImg> itemImgList;
    private List<ItemSpec> itemSpecList;
    private ItemParam itemParams;
}
