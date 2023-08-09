package com.atguigu.auth.controller;

import com.atguigu.auth.service.SysUserService;
import com.atguigu.common.execption.GuiguException;
import com.atguigu.common.jwt.JwtHelper;
import com.atguigu.common.result.R;
import com.atguigu.model.system.SysUser;
import com.atguigu.vo.system.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * @Auther: 茶凡
 * @ClassName IndexController
 * @date 2023/8/3 12:29
 * @Description 后台登录管理接口
 */
@Api(tags = "后台登录管理")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 登录
     * @return
     */
    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo) {

        SysUser sysUser = sysUserService.getByUsername(loginVo.getUsername());

        if(null == sysUser){
            throw  new GuiguException(201,"用户不存在");
        }
        if(!com.atguigu.common.util.MD5.encrypt(loginVo.getPassword()).equals(sysUser.getPassword())) {
            throw new GuiguException(201,"密码错误");
        }
        if(sysUser.getStatus().intValue() == 0) {
            throw new GuiguException(201,"用户被禁用");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("token", JwtHelper.createToken(sysUser.getId(), sysUser.getUsername()));
        return R.ok(map);
    }

    protected final Log logger = LogFactory.getLog(getClass());
    /**
     * 获取用户信息
     * @return
     */
    @ApiOperation(value = "获取用户信息")
    @GetMapping("info")
    public R info(HttpServletRequest request) {

        String username = JwtHelper.getUsername(request.getHeader("token"));

        logger.info("==================== username: " + username);
        Map<String, Object> map = sysUserService.getUserInfo(username);

        return R.ok(map);
    }
    /**
     * 退出
     * @return
     */
    @PostMapping("logout")
    public R logout(){
        return R.ok();
    }

//    public static void main(String[] args) {
//        String password = "111111";
//        System.out.println(DigestUtils.md5DigestAsHex(password.getBytes()));
//    }

}

