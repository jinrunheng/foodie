## TODO
finished step1,week4,3-4

## 运行前端项目

- 下载 Tomcat：[下载地址](https://tomcat.apache.org/) ；我使用的版本为 apache-tomcat-8.5.69
- 解压安装包后，进入到 webapps 目录下，放入整个前端项目(拷贝项目根目录下的 foodie-shop 目录)
- 进入到 apache-tomcat 目录下的 bin 目录，并运行 startup 启动程序；使用命令：`./startup.sh`
- 我们可以通过修改 conf 目录下的 server.xml 来自定义 tomcat 访问的端口号；浏览器访问 `localhost:[port]`，如果未修改，默认端口号为 `8080`
- 页面地址：`http://localhost:8080/foodie-shop/index.html`
- 关闭 tomcat：在 apache-tomcat 目录下的 bin 目录，运行：`./shutdown.sh`

## Swagger2 
- `http://localhost:8088/doc.html`

## 核心模块
### 购物车
Cookie + Redis
## 记录
### mybatis 关联嵌套查询分页解决方案
mybatis 嵌套查询后，出现分页混乱问题，导致问题出现的原因是 mybatis 通过查询结果之后折叠了结果集把数据放在了集合里，导致了总的条数混乱。
解决方式，在 `resultMap` 的 `collection` 标签里写 `select` 属性。

`select` 属性的作用有：
1. 分离查询：通过 `select` 属性，你可以将主查询和集合查询分离开来。首先执行主查询，当需要访问集合属性时，再执行 `select` 属性指定的查询。
2. 延迟加载：结合 MyBatis 的延迟加载功能，只有当首次访问集合属性时，才会执行 `select` 属性指定的查询。这可以减少不必要的数据库访问，提高性能。

通过 `select` 属性，可以使得先查询结果集，然后再执行嵌套查询，不会导致分页错乱的发生:
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.github.mapper.CustomOrderMapper">
    <resultMap id="myOrdersVO" type="com.github.vo.MyOrderVO">
        <id column="orderId" property="orderId"/>
        <result column="createdTime" property="createdTime"/>
        <result column="payMethod" property="payMethod"/>
        <result column="realPayAmount" property="realPayAmount"/>
        <result column="postAmount" property="postAmount"/>
        <result column="isComment" property="isComment"/>
        <result column="orderStatus" property="orderStatus"/>
        <collection property="subOrderItemList"
                    select="getSubItems" column="orderId"
                    ofType="com.github.vo.MySubOrderItemVO">
            <result column="itemId" property="itemId"/>
            <result column="itemImg" property="itemImg"/>
            <result column="itemName" property="itemName"/>
            <result column="itemSpecId" property="itemSpecId"/>
            <result column="itemSpecName" property="itemSpecName"/>
            <result column="buyCounts" property="buyCounts"/>
            <result column="price" property="price"/>
        </collection>
    </resultMap>
    

    <select id="queryUserOrders" parameterType="map" resultMap="myOrdersVO">
        select od.id as orderId,
        od.created_time as createdTime,
        od.pay_method as payMethod,
        od.real_pay_amount as realPayAmount,
        od.post_amount as postAmount,
        os.order_status as orderStatus
        from `order` od
        left join order_status os
        on od.id = os.order_id
        where od.user_id = #{paramsMap.userId}
        and od.is_delete = 0
        <if test="paramsMap.orderStatus != null">
            and os.order_status = #{paramsMap.orderStatus}
        </if>
        order by od.updated_time asc
    </select>
    
    <select id="getSubItems" parameterType="string" resultType="com.github.vo.MySubOrderItemVO">
        select
            oi.item_id as itemId,
            oi.item_name as itemName,
            oi.item_img as itemImg,
            oi.item_spec_id as itemSpecId,
            oi.item_spec_name as itemSpecName,
            oi.buy_counts as buyCounts,
            oi.price as price
        from order_item oi
        where oi.order_id = #{orderId}
    </select>

</mapper>
```
     