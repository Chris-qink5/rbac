package com.woniuxy.permission.service;

import com.woniuxy.permission.domain.Permission;
import com.woniuxy.permission.domain.User;
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
public interface UserService extends IService<User> {
    public List<Permission> findManue();
    public void upRole(Integer uid,List<String> rolenames);

}
