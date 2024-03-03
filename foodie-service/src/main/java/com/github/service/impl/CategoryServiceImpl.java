package com.github.service.impl;

import com.github.enums.CategoryType;
import com.github.enums.YesOrNo;
import com.github.mapper.CategoryMapper;
import com.github.pojo.Category;
import com.github.service.CategoryService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Dooby Kim
 * @Date 2024/3/3 下午6:57
 * @Version 1.0
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 查询一级分类
     *
     * @return
     */
    @Override
    public List<Category> queryAllRootLevelCat() {
        Example example = new Example(Category.class);
        final Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", CategoryType.LEVEL1.type);
        return categoryMapper.selectByExample(example);
    }
}
