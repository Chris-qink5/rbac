package com.woniuxy.permission.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import com.woniuxy.permission.vo.RoleVo;
import com.woniuxy.permission.vo.UserVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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
@RequestMapping("/role")
public class RoleController {
    private QueryWrapper<Permission> queryWrapper;
    private Integer size;
    @Resource
    private UserService userService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private RoleService roleService;


    @RequestMapping("findManue")
    @ResponseBody
    public Result findManue(){
        System.out.println("进入find");
        List<Permission> rootManue = userService.findManue();
        return new Result(true, StatusCode.OK," ",rootManue);

    }

    @RequestMapping("/devPage")
    @ResponseBody
    public IPage devPage(int current, int Csize){
        //参数1 ： 当前页码
        //参数2 ： 每页记录数
        Page<Permission> page = new Page<>(current,Csize);
        //参数1 ： 分页对象
        //参数2 ： 条件构造器
        permissionService.page(page, queryWrapper);
        page.getRecords().forEach(System.out::println);
        List<Permission> Users = page.getRecords();
        System.out.println("page已返回");
        return page;
    }
    @RequestMapping("/changWrapper")
    @ResponseBody
    public String changWrapper(@RequestBody Query query){
        System.out.println(query);
        queryWrapper=new QueryWrapper<Permission>();
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



    @RequestMapping("queryUser")
    @ResponseBody
    public Result queryRole(int pid){
        System.out.println(pid);
        List<User> users = roleService.queryUserBypid(pid);
        return new Result(true, StatusCode.OK,"查询成功",users);
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

}

