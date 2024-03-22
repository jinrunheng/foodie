package com.github.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author Dooby Kim
 * @Date 2024/3/22 下午10:32
 * @Version 1.0
 * @Desc 用户新增或修改地址的 BO
 */
@Getter
@Setter
@ToString
public class AddressBO {
    private String addressId;
    private String userId;
    private String receiver;
    private String mobile;
    private String province;
    private String city;
    private String district;
    private String detail;
}
