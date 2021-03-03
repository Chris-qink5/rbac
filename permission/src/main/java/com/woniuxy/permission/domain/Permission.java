package com.woniuxy.permission.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author qinkui
 * @since 2021-02-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Permission对象", description="")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

        @ApiModelProperty(value = "权限id")
        @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

        @ApiModelProperty(value = "权限名称")
        private String element;

        @ApiModelProperty(value = "跳转路径")
        private String url;

        @ApiModelProperty(value = "权限菜单等级")
        private Integer level;

        @ApiModelProperty(value = "父级菜单名称")
        private Integer pid;

        private List<Permission> secendManue;


}
