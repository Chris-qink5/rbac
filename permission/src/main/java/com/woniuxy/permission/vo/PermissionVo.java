package com.woniuxy.permission.vo;

import lombok.Data;

import java.util.List;

@Data
public class PermissionVo {
    private Integer operateUid;
    private List<String> checkList;
}
