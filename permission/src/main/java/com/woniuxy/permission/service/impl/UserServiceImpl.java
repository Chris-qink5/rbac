package com.woniuxy.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniuxy.permission.domain.Permission;
import com.woniuxy.permission.domain.Role;
import com.woniuxy.permission.domain.User;
import com.woniuxy.permission.mapper.PermissionMapper;
import com.woniuxy.permission.mapper.RoleMapper;
import com.woniuxy.permission.mapper.UserMapper;
import com.woniuxy.permission.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private PermissionMapper permissionMapper;
    @Override
    public List<Permission> findManue() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<Permission> permissions = userMapper.findManue(user.getId());
        ArrayList<Permission> rootManue = new ArrayList<>();
        permissions.forEach(permission -> {
            if(permission.getLevel()==1){
                    rootManue.add(permission);
            }
        });
        List<Permission> rootManuelist = removeDuplicate(rootManue);
        rootManuelist.forEach(root -> {
            ArrayList<Permission> son = new ArrayList<>();
            permissions.forEach(permission->{
                if(permission.getPid()==root.getId()){
                    son.add(permission);
                }
            });
            List<Permission> son1 = removeDuplicate(son);
            root.setSecendManue(son1);
        });
        System.out.println(rootManue);
        return rootManuelist;
    }



    @Override
    public void upRole(Integer uid,List<String> rolenames) {
        userMapper.deleteByuid(uid);
        rolenames.forEach(rolename->{
            QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("rolename",rolename);
            Role role = roleMapper.selectOne(queryWrapper);
            userMapper.insertRoleByUidAndRid(uid,role.getId());
        });
    }
    /**
     * 去重
     */
    public  List<Permission> removeDuplicate(List<Permission> list){
        List<Permission> listTemp = new ArrayList<Permission>();
        for(int i=0;i<list.size();i++){
            if(!listTemp.contains(list.get(i))){
                listTemp.add(list.get(i));
            }
        }
        return listTemp;
    }
}
