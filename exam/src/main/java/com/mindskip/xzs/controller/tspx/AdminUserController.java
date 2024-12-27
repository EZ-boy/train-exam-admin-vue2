package com.mindskip.xzs.controller.tspx;

import com.mindskip.xzs.base.BaseApiController;
import com.mindskip.xzs.base.RestResponse;
import com.mindskip.xzs.domain.Position;
import com.mindskip.xzs.domain.User;
import com.mindskip.xzs.domain.UserOrPosition;
import com.mindskip.xzs.domain.tspx.Org;
import com.mindskip.xzs.service.AuthenticationService;
import com.mindskip.xzs.service.UserEventLogService;
import com.mindskip.xzs.service.tspx.NewUserService;
import com.mindskip.xzs.viewmodel.admin.user.UserCreateVM;
import com.mindskip.xzs.viewmodel.admin.user.UserResponseVM;
import com.mindskip.xzs.viewmodel.admin.user.UserUpdateVM;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;


@RestController("TspxAdminUserController")
@RequestMapping(value = "/api/tspx/user")
public class AdminUserController extends BaseApiController {

    private final NewUserService userService;
    private final UserEventLogService userEventLogService;
    private final AuthenticationService authenticationService;

    @Autowired
    public AdminUserController(NewUserService userService, UserEventLogService userEventLogService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.userEventLogService = userEventLogService;
        this.authenticationService = authenticationService;
    }

    @RequestMapping(value = "/queryOrgList", method = RequestMethod.POST)
    public RestResponse<List<Org>> queryOrgList(@RequestBody Map<String, Object> map) {
        String orgNo = String.valueOf(map.get("orgNo"));
        List<Org> orgs = new ArrayList<>();
        orgs.add(userService.queryOrgList(orgNo));
        return RestResponse.ok(orgs);
    }

    @RequestMapping(value = "/queryOrgInfo", method = RequestMethod.POST)
    public RestResponse<Org> queryOrgInfo(@RequestBody Map<String, Object> map) {
        String orgNo = String.valueOf(map.get("orgNo"));
        Org orgInfo = userService.queryOrgInfo(orgNo);
        return RestResponse.ok(orgInfo);
    }

    @RequestMapping(value = "/getUserListByOrgNo", method = RequestMethod.POST)
    public RestResponse<List<User>> getUserListByOrgNo(@RequestBody Map<String, Object> map) {
        String orgNo = String.valueOf(map.get("orgNo"));
        String raalName = map.get("realName") != null?  map.get("realName").toString():"";
        List<User> orgList = userService.getUserListByOrgNo(orgNo,raalName);
        return RestResponse.ok(orgList);
    }

    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<UserResponseVM> select(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        UserResponseVM userVm = UserResponseVM.from(user);
        return RestResponse.ok(userVm);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RestResponse<User> edit(@RequestBody @Valid UserCreateVM model) {
        if (model.getId() == null) {  //create
            User existUser = userService.getUserByUserName(model.getUserName());
            if (null != existUser) {
                return new RestResponse<>(2, "用户已存在");
            }


        }
        model.setBirthDay(null);
        User user = modelMapper.map(model, User.class);

        if (model.getId() == null) {
            String encodePwd = authenticationService.pwdEncode("123456");
            user.setPassword(encodePwd);
            user.setUserUuid(UUID.randomUUID().toString());
            user.setCreateTime(new Date());
            user.setLastActiveTime(new Date());
            user.setDeleted(false);
            int result = userService.insertByFilter(user);
            if(result>0){
                //新增岗位关联关系
                if (StringUtils.isNotBlank(user.getPositionNo())) {
                    String[] positionNo=user.getPositionNo().split(",");
                    for (String s : positionNo) {
                        UserOrPosition uos = new UserOrPosition();
                        uos.setOrgId(user.getOrgNo());
                        uos.setPositionNo(s);
                        uos.setUserId(user.getUserName());
                        uos.setUserName(user.getUserName());
                        userService.insertUserOrPosition(uos);
                    }
                }
            }
        } else {
            if (!StringUtils.isBlank(model.getPassword())) {
                String encodePwd = authenticationService.pwdEncode(model.getPassword());
                user.setPassword(encodePwd);
            }
            user.setModifyTime(new Date());
            int result = userService.updateByIdFilter(user);
            if (result > 0) {
                if (StringUtils.isNotBlank(user.getPositionNo())) {
                    String[] positionNo = user.getPositionNo().split(",");
                    UserOrPosition u = new UserOrPosition();
                    u.setUserId(user.getUserName());
                    userService.deleteUserOrPosition(u);
                    for (String s : positionNo) {
                        UserOrPosition uos = new UserOrPosition();
                        uos.setOrgId(user.getOrgNo());
                        uos.setPositionNo(s);
                        uos.setUserId(user.getUserName());
                        uos.setUserName(user.getUserName());
                        userService.insertUserOrPosition(uos);
                    }
                }
            }
        }
        return RestResponse.ok(user);
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RestResponse update(@RequestBody @Valid UserUpdateVM model) {
        User user = userService.selectById(getCurrentUser().getId());
        modelMapper.map(model, user);
        user.setModifyTime(new Date());
        userService.updateByIdFilter(user);
        return RestResponse.ok();
    }
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public RestResponse delete(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        user.setDeleted(true);
        int i = userService.updateByIdFilter(user);
        if (user != null) {
            if (i > 0) {
                //删除用户与岗位关联关系
                UserOrPosition uos = new UserOrPosition();
                uos.setUserId(user.getUserName());
                userService.deleteUserOrPosition(uos);
            }
        }
        return RestResponse.ok();
    }

    /**
     * 根据用户编号或单位编号查询岗位集合
     * @param user
     * @return
     */
    @RequestMapping(value = "/getPositionListByUserNo", method = RequestMethod.POST)
    public RestResponse<LinkedHashSet<Position>> getPositionListByUserNo(@RequestBody User user) {
        LinkedHashSet<Position> orgList = userService.getPositionListByUserNo(user);
        return RestResponse.ok(orgList);
    }

    /**
     *  根据orgNo查询岗位集合（新）
     * @param user
     * @return
     */
    @RequestMapping(value = "/getPositionList", method = RequestMethod.POST)
    public RestResponse<LinkedHashSet<Position>> getPositionList(@RequestBody User user) {
        LinkedHashSet<Position> orgList = userService.getPositionList(user);
        return RestResponse.ok(orgList);
    }
//    @RequestMapping(value = "/selectByUserName", method = RequestMethod.POST)
//    public RestResponse<List<KeyValue>> selectByUserName(@RequestBody String userName) {
//        List<KeyValue> keyValues = userService.selectByUserName(userName);
//        return RestResponse.ok(keyValues);
//    }
}
