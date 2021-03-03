package com.woniuxy.permission.mapper;

import com.woniuxy.permission.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniuxy.permission.domain.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qinkui
 * @since 2021-02-18
 */
public interface RoleMapper extends BaseMapper<Role> {
    @Select("SELECT r.* FROM role r INNER JOIN user_role ur ON r.`id`=ur.`rid` INNER JOIN `user` u ON u.`id`=ur.`uid` WHERE u.`id`=#{uid}")
    public List<Role> queryRoleByUid(int uid);
    @Select("SELECT u.* FROM permission p INNER JOIN" +
            "    role_permission rp ON p.`id`=rp.`pid`INNER JOIN" +
            "    role r ON rp.`rid`=r.`id` INNER JOIN" +
            "    user_role ur ON r.`id`=ur.`rid`INNER JOIN " +
            "`user` u ON ur.`uid`=u.`id` WHERE  p.`pid`=#{pid}")
    public List<User> queryUserBypid(int pid);

}
