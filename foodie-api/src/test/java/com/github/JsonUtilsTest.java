package com.github;

import cn.hutool.core.lang.Assert;
import com.github.dto.FoodieUserDTO;
import com.github.utils.JsonUtils;
import org.junit.Test;

/**
 * @Author Dooby Kim
 * @Date 2024/2/28 上午12:09
 * @Version 1.0
 */
public class JsonUtilsTest {
    @Test
    public void test() {
        String s = "{\"id\":\"1762497854579281920\",\"username\":\"test\",\"nickname\":\"test\",\"realname\":null,\"face\":\"test\",\"sex\":2}";
        final FoodieUserDTO foodieUserDTO = JsonUtils.jsonToPojo(s, FoodieUserDTO.class);
        assert foodieUserDTO != null;
        Assert.equals(foodieUserDTO.getSex(), 2);
        Assert.equals(foodieUserDTO.getUsername(), "test");

    }
}
