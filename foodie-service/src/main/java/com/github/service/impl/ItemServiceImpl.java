package com.github.service.impl;

import com.github.enums.CommentLevel;
import com.github.enums.YesOrNo;
import com.github.mapper.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pojo.*;
import com.github.service.ItemService;
import com.github.utils.DesensitizationUtil;
import com.github.utils.PagedGridResult;
import com.github.vo.CommentLevelCountsVO;
import com.github.vo.ItemCommentVO;
import com.github.vo.SearchItemsVO;
import com.github.vo.ShopCartVO;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author Dooby Kim
 * @Date 2024/3/13 下午9:55
 * @Version 1.0
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Resource
    private ItemMapper itemMapper;

    @Resource
    private ItemImgMapper itemImgMapper;

    @Resource
    private ItemSpecMapper itemSpecMapper;

    @Resource
    private ItemParamMapper itemParamMapper;

    @Resource
    private ItemCommentMapper itemCommentMapper;

    @Resource
    private CustomItemMapper customItemMapper;


    @Override
    public Item queryItemById(String itemId) {
        return itemMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public List<ItemImg> queryItemImgList(String itemId) {
        Example itemImgExp = new Example(ItemImg.class);
        final Example.Criteria criteria = itemImgExp.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemImgMapper.selectByExample(itemImgExp);
    }

    @Override
    public List<ItemSpec> queryItemSpecList(String itemId) {
        Example itemSpecExp = new Example(ItemSpec.class);
        final Example.Criteria criteria = itemSpecExp.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemSpecMapper.selectByExample(itemSpecExp);
    }

    @Override
    public ItemParam queryItemParam(String itemId) {
        Example ItemParamExp = new Example(ItemParam.class);
        final Example.Criteria criteria = ItemParamExp.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemParamMapper.selectOneByExample(ItemParamExp);
    }

    @Override
    public CommentLevelCountsVO queryCommentCounts(String itemId) {
        CommentLevelCountsVO commentLevelCountsVO = new CommentLevelCountsVO();

        final Integer goodCounts = getCommentsCounts(itemId, CommentLevel.GOOD.type);
        final Integer normalCounts = getCommentsCounts(itemId, CommentLevel.NORMAL.type);
        final Integer badCounts = getCommentsCounts(itemId, CommentLevel.BAD.type);
        final Integer commentsCounts = goodCounts + normalCounts + badCounts;
        commentLevelCountsVO.setTotalCounts(commentsCounts);
        commentLevelCountsVO.setGoodCounts(goodCounts);
        commentLevelCountsVO.setNormalCounts(normalCounts);
        commentLevelCountsVO.setBadCounts(badCounts);
        return commentLevelCountsVO;
    }

    @Override
    public PagedGridResult queryPagedComments(Map<String, Object> map, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<ItemCommentVO> itemCommentVOList = customItemMapper.queryItemComments(map);
        for (ItemCommentVO vo : itemCommentVOList) {
            vo.setNickname(DesensitizationUtil.commonDisplay(vo.getNickname()));
        }
        return setPagedGrid(itemCommentVOList, page);
    }

    @Override
    public PagedGridResult searchItems(Map<String, Object> map, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<SearchItemsVO> searchItemsVOList = customItemMapper.searchItems(map);
        return setPagedGrid(searchItemsVOList, page);

    }

    @Override
    public PagedGridResult searchItemsByThirdCat(Map<String, Object> map, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        final List<SearchItemsVO> searchItemsVOList = customItemMapper.searchItemsByThirdCat(map);
        return setPagedGrid(searchItemsVOList, page);
    }

    @Override
    public List<ShopCartVO> queryItemsBySpecIds(String specIds) {
        String[] split = specIds.split("\\,");
        List<String> list = new ArrayList<>();
        Collections.addAll(list, split);
        return customItemMapper.queryItemsBySpecIds(list);
    }

    @Override
    public ItemSpec queryItemSpecById(String specId) {
        return itemSpecMapper.selectByPrimaryKey(specId);
    }

    @Override
    public String queryItemMainImgById(String itemId) {
        ItemImg itemImg = new ItemImg();
        itemImg.setItemId(itemId);
        itemImg.setIsMain(YesOrNo.YES.type);
        final ItemImg itemMainImg = itemImgMapper.selectOne(itemImg);
        return Objects.isNull(itemMainImg) ? null : itemMainImg.getUrl();
    }

    /**
     * 根据商品 ID，等级（好，中，差）获取评价数量
     *
     * @param itemId
     * @param level
     * @return
     */
    private Integer getCommentsCounts(String itemId, Integer level) {
        ItemComment condition = new ItemComment();
        condition.setItemId(itemId);
        if (level != null) {
            condition.setCommentLevel(level);
        }
        return itemCommentMapper.selectCount(condition);
    }

    private PagedGridResult setPagedGrid(List<?> list, Integer page) {
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult pagedGridResult = new PagedGridResult();
        pagedGridResult.setPage(page);
        pagedGridResult.setRows(list);
        pagedGridResult.setTotal(pageList.getPages());
        pagedGridResult.setRecords(pageList.getTotal());
        return pagedGridResult;
    }


}
