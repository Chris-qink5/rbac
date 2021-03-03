package com.woniuxy.permission.controller;


import com.alibaba.druid.sql.visitor.functions.If;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.regexp.internal.RE;
import com.woniuxy.permission.domain.Permission;
import com.woniuxy.permission.domain.Query;
import com.woniuxy.permission.domain.Role;
import com.woniuxy.permission.domain.User;
import com.woniuxy.permission.dto.Result;
import com.woniuxy.permission.dto.StatusCode;
import com.woniuxy.permission.service.PermissionService;
import com.woniuxy.permission.service.RoleService;
import com.woniuxy.permission.service.UserService;
import com.woniuxy.permission.util.SaltUtil;
import com.woniuxy.permission.vo.PermissionVo;
import com.woniuxy.permission.vo.RoleVo;
import com.woniuxy.permission.vo.UserVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;
import javax.lang.model.element.VariableElement;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qinkui
 * @since 2021-02-18
 */
@Controller
@RequestMapping("/user")

public class UserController {
    private QueryWrapper<User> queryWrapper;
    private Integer size;
    @Resource
    private UserService userService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private RoleService roleService;
    @PostMapping("register")
    @ResponseBody
    public Result register(@RequestBody UserVO userVO){
        System.out.println("进入注册");
        QueryWrapper<User> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("username",userVO.getUsername());
        User userDB=userService.getOne(queryWrapper);
        if (ObjectUtils.isEmpty(userDB)) {
            String salt = new SaltUtil(8).getSalt();
            Md5Hash md5Hash = new Md5Hash(userVO.getPassword(), salt, 2048);
            User user = new User();
            user.setUsername(userVO.getUsername());
            user.setPassword(md5Hash.toHex());
            user.setSalt(salt);
            userService.save(user);
            System.out.println("数据已返回");
            return new Result(true, StatusCode.OK,"注册成功");

        }else {
            return new Result(false,StatusCode.ERROR,"该用户已存在");

        }


    }

    @RequestMapping("login")
    @ResponseBody
    public Result login(@RequestBody UserVO user, HttpSession session){
        System.out.println(user);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken authenticationToken = new UsernamePasswordToken(user.getUsername(), user.getPassword(), user.isCheack());
        subject.login(authenticationToken);

        return new Result(true, StatusCode.OK,"登陆成功");

    }
    @RequestMapping("findManue")
    @ResponseBody
    public Result findManue(){
        System.out.println("进入find");
        List<Permission> rootManue = userService.findManue();
        return new Result(true, StatusCode.OK," ",rootManue);

    }

    @RequestMapping("/devPage")
    @ResponseBody
    public IPage devPage(int current,int Csize){


        //参数1 ： 当前页码
        //参数2 ： 每页记录数
        Page<User> page = new Page<>(current,Csize);
        //参数1 ： 分页对象
        //参数2 ： 条件构造器
        userService.page(page, queryWrapper);
        page.getRecords().forEach(System.out::println);
        List<User> Users = page.getRecords();
        System.out.println("page已返回");
        return page;
    }
    @RequestMapping("/changWrapper")
    @ResponseBody
    public String changWrapper(@RequestBody Query query){
        System.out.println(query);
        queryWrapper=new QueryWrapper<User>();
        if(query.getTitle()!=null){
            queryWrapper.like("title",query.getTitle());
        }
        if(query.getLengthMin()!=null){
            queryWrapper.gt("length",query.getLengthMin());
        }
        if(query.getLengthMax()!=null){
            queryWrapper.lt("length",query.getLengthMax());
        }
        if(query.getSize()!=null){
            size=query.getSize();
        }
        return "success";
    }

    @RequestMapping("find3Manues")
    @ResponseBody
    public Result find3Manues(int pid2b){
        System.out.println("进入find3Manues"+pid2b);
        List<Permission> manueS = permissionService.find3ManueS(pid2b);
        return new Result(true, StatusCode.OK,"登陆成功",manueS);
    }


    @RequestMapping("deleteUser")
    @ResponseBody
    public Result deleteUser(int uid){
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",uid);
        boolean remove = userService.remove(wrapper);
        if(remove){
            return new Result(true, StatusCode.OK,"删除成功");
        }else {
            return new Result(true, StatusCode.ERROR,"删除失败");
        }
    }
    @RequestMapping("queryRole")
    @ResponseBody
    public Result queryRole(int uid){
        System.out.println(uid);
        List<Role> roles = roleService.queryRoleByUid(uid);
        return new Result(true, StatusCode.OK,"查询成功",roles);
    }

    @RequestMapping("allRole")
    @ResponseBody
    public Result allRole(){
        List<Role> list = roleService.list(null);
        return new Result(true, StatusCode.OK,"查询成功",list);
    }
    @RequestMapping("changeRole")
    @ResponseBody
    public Result changeRole(@RequestBody RoleVo roleVo){
        System.out.println(roleVo.getOperateUid());
        System.out.println(roleVo.getCheckList());
        userService.upRole(roleVo.getOperateUid(),roleVo.getCheckList());
        return new Result(true, StatusCode.OK,"修改成功");
    }

    @RequestMapping("allPermission")
    @ResponseBody
    public Result allPermission(){
        List<Permission> list = permissionService.list(null);
        return new Result(true, StatusCode.OK,"查询成功",list);
    }
    @RequestMapping("queryPermission")
    @ResponseBody
    public Result queryPermission(int uid){
        System.out.println(uid);
        List<Permission> permission= permissionService.queryPermissionByUid(uid);
        return new Result(true, StatusCode.OK,"查询成功",permission);
    }

    @RequestMapping("changePermission")
    @ResponseBody
    public Result changePermission(@RequestBody PermissionVo pVo){
        System.out.println(pVo.getOperateUid());
        System.out.println(pVo.getCheckList());
        return new Result(true, StatusCode.OK,"修改成功");
    }
}

