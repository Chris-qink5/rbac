package com.woniuxy.permission.vo;

import lombok.Data;

import java.util.List;
@Data
public class RoleVo {
    private Integer operateUid;
    private List<String> checkList;
}
