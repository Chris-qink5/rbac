package com.woniuxy.permission.service;

import com.woniuxy.permission.domain.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qinkui
 * @since 2021-02-18
 */
public interface PermissionService extends IService<Permission> {
    public List<Permission> find3ManueS( int pid);
    public List<Permission> queryPermissionByUid ( int uid);
}
