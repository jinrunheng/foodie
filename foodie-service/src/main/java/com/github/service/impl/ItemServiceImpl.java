package com.github.service.impl;

import com.github.enums.CommentLevel;
import com.github.mapper.*;
import com.github.pojo.*;
import com.github.service.ItemService;
import com.github.vo.CommentLevelCountsVO;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

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


}
