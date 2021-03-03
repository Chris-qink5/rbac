package com.woniuxy.permission.service.impl;

import com.woniuxy.permission.domain.Permission;
import com.woniuxy.permission.domain.User;
import com.woniuxy.permission.mapper.PermissionMapper;
import com.woniuxy.permission.mapper.UserMapper;
import com.woniuxy.permission.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qinkui
 * @since 2021-02-18
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Resource
    private PermissionMapper permissionMapper;
    @Override
    public List<Permission> find3ManueS( int pid) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<Permission> manue3 = permissionMapper.find3Manue(user.getId(),pid);
        return manue3;
    }

    @Override
    public List<Permission> queryPermissionByUid(int uid) {
        List<Permission> permissions = permissionMapper.queryPermissionByUid(uid);
        return permissions;
    }
}
