package com.iccm.zmmd.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.code.kaptcha.Constants;
import com.iccm.zmmd.common.BaseController;
import com.iccm.zmmd.common.JsonResult;
import com.iccm.zmmd.system.model.User;
import com.iccm.zmmd.system.service.AuthoritiesService;
import com.iccm.zmmd.system.service.MenuService;
import com.iccm.zmmd.system.service.UserRoleService;
import com.iccm.zmmd.system.service.UserService;
import com.wangfan.endecrypt.utils.EndecryptUtils;
import com.iccm.zmmd.common.utils.StringUtil;
import com.iccm.zmmd.system.model.LoginParams;
import com.iccm.zmmd.system.model.Menu;
import com.iccm.zmmd.system.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.provider.Token;
import org.wf.jwtp.provider.TokenStore;

import javax.servlet.http.HttpSession;
import java.util.*;

@Api(value = "个人信息", tags = "main")
@RequestMapping("/main")
@Controller
public class MainController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private AuthoritiesService authoritiesService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private UserRoleService userRoleService;

    /*@ApiIgnore
    @RequestMapping({"/", "/index"})
    public String index() {
        return "redirect:index.html";
    }*/

    @ResponseBody
    @ApiOperation(value = "获取个人信息")
    @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    @GetMapping("user/info")
    public JsonResult userInfo(HttpServletRequest request) {
//        User user = userService.selectById(getLoginUserId(request));
//        List<Authorities> auths = new ArrayList<>();
//        for (String auth : getLoginToken(request).getPermissions()) {
//            Authorities t = new Authorities();
//            t.setAuthority(auth);
//            auths.add(t);
//        }
//        user.setAuthorities(auths);
//        return JsonResult.ok().put("user", user);
        String[] array = {"admin","super_admin"};
        return JsonResult.ok().put("access",array);
    }

    @ResponseBody
    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "账号", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "form")
    })
    @PostMapping("user/login")
    public JsonResult login(@RequestBody LoginParams loginParams, HttpSession session) {
        if(!session.getAttribute(Constants.KAPTCHA_SESSION_KEY).equals(loginParams.getValidateCode())){
            return JsonResult.error("验证码错误");
        }
        User user = userService.getByUsername(loginParams.getUsername());
        if (user == null) {
            return JsonResult.error("账号不存在");
        } else if (!user.getPassword().equals(EndecryptUtils.encrytMd5(loginParams.getPassword()))) {
            return JsonResult.error("密码错误");
        } else if (user.getState() != 0) {
            return JsonResult.error("账号被锁定");
        }
        String[] roles = arrayToString(userRoleService.getRoleIds(user.getUserId()));
        String[] permissions = listToArray(authoritiesService.listByUserId(user.getUserId()));
        Token token = tokenStore.createNewToken(String.valueOf(user.getUserId()), permissions, roles);
        return JsonResult.ok("登录成功").put("access_token", token.getAccessToken());
    }

    @ResponseBody
    @ApiOperation(value = "获取所有菜单")
    @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    @GetMapping("user/menu")
    public JsonResult userMenu(HttpServletRequest request) {
        // 获取当前用户的权限
        Token token = getLoginToken(request);
        String[] auths = token.getPermissions();
        // 查询所有的菜单
        List<Menu> menus = menuService.selectList(new EntityWrapper<Menu>().orderBy("sort_number", true));
        // 移除没有权限的菜单
        Iterator<Menu> iterator = menus.iterator();
        while (iterator.hasNext()) {
            Menu next = iterator.next();
            boolean haveAuth = false;
            for (String auth : auths) {
                if (StringUtil.isBlank(next.getAuthority()) || next.getAuthority().equals(auth)) {
                    haveAuth = true;
                }
            }
            if (!haveAuth) {
                iterator.remove();
            }
        }
        // 去除空的目录
        iterator = menus.iterator();
        while (iterator.hasNext()) {
            Menu next = iterator.next();
            if (StringUtil.isBlank(next.getMenuUrl())) {
                boolean haveSub = false;
                for (Menu t : menus) {
                    if (t.getParentId() == next.getMenuId()) {
                        haveSub = true;
                        break;
                    }
                }
                if (!haveSub) {
                    iterator.remove();
                }
            }
        }
        return JsonResult.ok().put("data", getMenuTree(menus, -1));
    }

    // 递归转化树形菜单
    private List<Map<String, Object>> getMenuTree(List<Menu> menus, Integer parentId) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < menus.size(); i++) {
            Menu temp = menus.get(i);
            if (parentId.intValue() == temp.getParentId().intValue()) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", temp.getMenuName());
                map.put("icon", temp.getMenuIcon());
                map.put("url", StringUtil.isBlank(temp.getMenuUrl()) ? "javascript:;" : temp.getMenuUrl());
                map.put("subMenus", getMenuTree(menus, menus.get(i).getMenuId()));
                list.add(map);
            }
        }
        return list;
    }

    private String[] listToArray(List<String> list) {
        String[] strs = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            strs[i] = list.get(i);
        }
        return strs;
    }

    private String[] arrayToString(Object[] objs) {
        String[] strs = new String[objs.length];
        for (int i = 0; i < objs.length; i++) {
            strs[i] = String.valueOf(objs[i]);
        }
        return strs;
    }

}
