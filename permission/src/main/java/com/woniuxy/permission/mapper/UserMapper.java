package com.woniuxy.permission.mapper;

import com.woniuxy.permission.domain.Permission;
import com.woniuxy.permission.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qinkui
 * @since 2021-02-18
 */
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT p.* FROM permission p INNER JOIN role_permission rp ON p.`id`=rp.`pid`" +
            "INNER JOIN role r ON rp.`rid`=r.`id` " +
            "INNER JOIN user_role ur ON r.`id`=ur.`rid`" +
            "INNER JOIN `user` u ON ur.`uid`=u.`id` WHERE u.`id`=#{uid}")
    public List<Permission> findManue(int uid);

    @Delete("delete from user_role where uid=#{uid}")
    public void deleteByuid(Integer uid);
    @Insert("insert into user_role(uid,rid) values(#{uid},#{rid})")
    public void insertRoleByUidAndRid(@RequestParam Integer uid, @RequestParam Integer rid);
}
