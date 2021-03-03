package com.woniuxy.permission.service;

import com.woniuxy.permission.domain.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.woniuxy.permission.domain.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qinkui
 * @since 2021-02-18
 */
public interface RoleService extends IService<Role> {
    public List<Role> queryRoleByUid(int uid);
    public List<User> queryUserBypid(int pid);
}
