package com.github.controller;

import com.github.pojo.Item;
import com.github.pojo.ItemImg;
import com.github.pojo.ItemParam;
import com.github.pojo.ItemSpec;
import com.github.service.ItemService;
import com.github.utils.CustomJSONResult;
import com.github.utils.PagedGridResult;
import com.github.vo.CommentLevelCountsVO;
import com.github.vo.ItemInfoVO;
import com.github.vo.ShopCartVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static final Integer DEFAULT_PAGE = 1;
    private static final Integer DEFAULT_PAGE_SIZE = 10;
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

    @GetMapping("/commentLevel")
    @ApiOperation(value = "查询商品评价数量")
    public CustomJSONResult commentCounts(
            @ApiParam(name = "itemId", value = "商品 ID", required = true)
            @RequestParam String itemId
    ) {
        if (StringUtils.isBlank(itemId)) {
            return CustomJSONResult.errorMsg("商品 ID 不能为空!");
        }
        final CommentLevelCountsVO commentLevelCountsVO = itemService.queryCommentCounts(itemId);
        return CustomJSONResult.ok(commentLevelCountsVO);
    }

    @GetMapping("/comments")
    @ApiOperation(value = "查询商品评价")
    public CustomJSONResult comments(
            @ApiParam(name = "itemId", value = "商品 ID", required = true)
            @RequestParam String itemId,
            @ApiParam(name = "level", value = "评论，好/中/差 评分级")
            @RequestParam Integer level,
            @ApiParam(name = "page", value = "第几页")
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "每页几条数据")
            @RequestParam Integer pageSize

    ) {
        if (StringUtils.isBlank(itemId)) {
            return CustomJSONResult.errorMsg("商品 ID 不能为空!");
        }
        if (page == null) {
            page = DEFAULT_PAGE;
        }
        if (pageSize == null) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("itemId", itemId);
        map.put("level", level);
        final PagedGridResult pagedGridResult = itemService.queryPagedComments(map, page, pageSize);
        return CustomJSONResult.ok(pagedGridResult);
    }

    @ApiOperation(value = "搜索商品列表")
    @GetMapping("/search")
    public CustomJSONResult search(
            @ApiParam(name = "keywords", value = "搜索关键字", required = true)
            @RequestParam String keywords,
            @ApiParam(name = "sort", value = "排序类型，默认排序、按照销量排序、按照价格排序")
            @RequestParam String sort,
            @ApiParam(name = "page", value = "第几页")
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "每页几条数据")
            @RequestParam Integer pageSize
    ) {
        if (StringUtils.isBlank(keywords)) {
            return CustomJSONResult.errorMsg("搜索关键字不能为空!");
        }
        if (page == null) {
            page = DEFAULT_PAGE;
        }
        if (pageSize == null) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("keywords", keywords);
        map.put("sort", sort);
        final PagedGridResult pagedGridResult = itemService.searchItems(map, page, pageSize);
        return CustomJSONResult.ok(pagedGridResult);
    }

    @ApiOperation(value = "根据三级分类查询商品列表")
    @GetMapping("/catItems")
    public CustomJSONResult catItems(
            @ApiParam(name = "catId", value = "三级分类 ID", required = true)
            @RequestParam Integer catId,
            @ApiParam(name = "sort", value = "排序类型，默认排序、按照销量排序、按照价格排序")
            @RequestParam String sort,
            @ApiParam(name = "page", value = "第几页")
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "每页几条数据")
            @RequestParam Integer pageSize
    ) {
        if (catId == null) {
            return CustomJSONResult.errorMsg("三级分类ID不能为空");
        }
        if (page == null) {
            page = DEFAULT_PAGE;
        }
        if (pageSize == null) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("catId", catId);
        map.put("sort", sort);
        final PagedGridResult pagedGridResult = itemService.searchItemsByThirdCat(map, page, pageSize);
        return CustomJSONResult.ok(pagedGridResult);
    }

    /**
     * 用于刷新购物车中的数据
     *
     * @return
     */
    @GetMapping("/refresh")
    @ApiOperation(value = "根据商品规格 ids 查找最新的商品数据")
    public CustomJSONResult refresh(
            @ApiParam(name = "itemSpecIds", value = "商品规格 ids，逗号拼接", required = true, example = "1001,1002,1003")
            @RequestParam String itemSpecIds
    ) {
        if (StringUtils.isBlank(itemSpecIds)) {
            return CustomJSONResult.ok();
        }
        List<ShopCartVO> shopCartVOS = itemService.queryItemsBySpecIds(itemSpecIds);
        return CustomJSONResult.ok(shopCartVOS);
    }
}
