package com.mindskip.xzs.controller.exam;

import com.mindskip.xzs.base.BaseApiController;
import com.mindskip.xzs.base.RestResponse;
import com.mindskip.xzs.domain.enums.UserStatusEnum;
import com.mindskip.xzs.service.AuthenticationService;
import com.mindskip.xzs.service.exam.NewUserService;
import com.mindskip.xzs.utility.RsaUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("PwdController")
@RequestMapping(value = "/pwd")
public class PasswordController extends BaseApiController {

    private final NewUserService userService;
    private final AuthenticationService authenticationService;

    @Autowired
    public PasswordController(NewUserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
    public RestResponse<Map> updatePwd(@RequestBody Map<String, Object> map) {
        Map retMap = new HashMap();
        String username = MapUtils.getString(map,"userName");
        String password = MapUtils.getString(map,"password");
        String newPwd = MapUtils.getString(map,"newPwd");
        try {
            password = RsaUtils.decryptByPrivateKey(password);
            newPwd = RsaUtils.decryptByPrivateKey(newPwd);
        } catch (Exception e){
            retMap.put("code","202");
            retMap.put("msg","密码解析失败");
            return RestResponse.ok(retMap);
            //throw new UsernameNotFoundException("密码解析失败");
        }
        com.mindskip.xzs.domain.User user = userService.getUserByUserName(username);
        if (user == null) {
            retMap.put("code","203");
            retMap.put("msg","用户名或密码错误");
            return RestResponse.ok(retMap);
            //throw new UsernameNotFoundException("用户名或密码错误");
        }

        boolean result = authenticationService.authUser(user, username, password);
        if (!result) {
            retMap.put("code","203");
            retMap.put("msg","用户名或密码错误");
            return RestResponse.ok(retMap);
            //throw new BadCredentialsException("用户名或密码错误");
        }
        UserStatusEnum userStatusEnum = UserStatusEnum.fromCode(user.getStatus());
        if (UserStatusEnum.Disable == userStatusEnum) {
            retMap.put("code","203");
            retMap.put("msg","用户被禁用");
            return RestResponse.ok(retMap);
            //throw new LockedException("用户被禁用");
        }
        if(password.equals(newPwd)){
            retMap.put("code","204");
            retMap.put("msg","新密码不能和原密码重复");
            return RestResponse.ok(retMap);
        }
        Map upMap = new HashMap();
        upMap.put("userName",username);
        upMap.put("password", authenticationService.pwdEncode(newPwd));
        int i = userService.updatePwd(upMap);
        if(i > 0) {
            retMap.put("code","200");
            retMap.put("msg","修改成功");
        }else{
            retMap.put("code","201");
            retMap.put("msg","修改失败");
        }
        return RestResponse.ok(retMap);
    }
}
