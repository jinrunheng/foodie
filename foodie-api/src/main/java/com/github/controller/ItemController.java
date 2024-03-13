package com.github.controller;

import com.github.pojo.Item;
import com.github.pojo.ItemImg;
import com.github.pojo.ItemParam;
import com.github.pojo.ItemSpec;
import com.github.service.ItemService;
import com.github.utils.CustomJSONResult;
import com.github.vo.ItemInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Dooby Kim
 * @Date 2024/3/13 下午10:16
 * @Version 1.0
 * @Desc 商品详情
 */
@Api(tags = {"用于商品详情展示的相关接口"})
@RestController
@RequestMapping("items")
public class ItemController {

    @Resource
    private ItemService itemService;

    @GetMapping("/info/{itemId}")
    @ApiOperation(value = "查询商品详情")
    public CustomJSONResult info(
            @PathVariable
            @ApiParam(name = "itemId", value = "商品 ID", required = true)
                    String itemId
    ) {
        if (StringUtils.isBlank(itemId)) {
            return CustomJSONResult.errorMsg("商品 ID 不能为空!");
        }
        final Item item = itemService.queryItemById(itemId);
        final List<ItemImg> itemImgs = itemService.queryItemImgList(itemId);
        final List<ItemSpec> itemSpecs = itemService.queryItemSpecList(itemId);
        final ItemParam itemParam = itemService.queryItemParam(itemId);
        ItemInfoVO itemInfoVO = new ItemInfoVO();
        itemInfoVO.setItem(item);
        itemInfoVO.setItemImgList(itemImgs);
        itemInfoVO.setItemSpecList(itemSpecs);
        itemInfoVO.setItemParams(itemParam);
        return CustomJSONResult.ok(itemInfoVO);
    }
}
