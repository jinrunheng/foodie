package com.github.service.impl;

import com.github.mapper.ItemImgMapper;
import com.github.mapper.ItemMapper;
import com.github.mapper.ItemParamMapper;
import com.github.mapper.ItemSpecMapper;
import com.github.pojo.Item;
import com.github.pojo.ItemImg;
import com.github.pojo.ItemParam;
import com.github.pojo.ItemSpec;
import com.github.service.ItemService;
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
}
