package com.woniuxy.permission.mapper;

import com.woniuxy.permission.domain.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
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
public interface PermissionMapper extends BaseMapper<Permission> {

    @Select("SELECT p.* FROM permission p INNER JOIN" +
            "    role_permission rp ON p.`id`=rp.`pid`INNER JOIN" +
            "    role r ON rp.`rid`=r.`id` INNER JOIN" +
            "    user_role ur ON r.`id`=ur.`rid`INNER JOIN " +
            "`user` u ON ur.`uid`=u.`id` WHERE u.`id`=#{uid} AND p.`pid`=#{pid}")
    public List<Permission> find3Manue(int uid,int pid);

    @Select("SELECT p.* FROM permission p INNER JOIN" +
            "    role_permission rp ON p.`id`=rp.`pid`INNER JOIN" +
            "    role r ON rp.`rid`=r.`id` INNER JOIN" +
            "    user_role ur ON r.`id`=ur.`rid`INNER JOIN " +
            "`user` u ON ur.`uid`=u.`id` WHERE u.`id`=#{uid}")
    public List<Permission> queryPermissionByUid(int uid);

}
