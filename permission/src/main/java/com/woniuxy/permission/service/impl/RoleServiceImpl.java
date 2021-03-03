package com.woniuxy.permission.service.impl;

import com.woniuxy.permission.domain.Permission;
import com.woniuxy.permission.domain.Role;
import com.woniuxy.permission.domain.User;
import com.woniuxy.permission.mapper.RoleMapper;
import com.woniuxy.permission.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Resource
    private RoleMapper roleMapper;
    public List<Role> queryRoleByUid(int uid){
        List<Role> roles = roleMapper.queryRoleByUid(uid);
        return roles;
    }

    @Override
    public List<User> queryUserBypid(int pid) {
        List<User> users = roleMapper.queryUserBypid(pid);
        List<User> users1 = removeDuplicate(users);
        return users1;
    }
    /**
     * 去重
     */
    public  List<User> removeDuplicate(List<User> list){
        List<User> listTemp = new ArrayList<User>();
        for(int i=0;i<list.size();i++){
            if(!listTemp.contains(list.get(i))){
                listTemp.add(list.get(i));
            }
        }
        return listTemp;
    }
}
