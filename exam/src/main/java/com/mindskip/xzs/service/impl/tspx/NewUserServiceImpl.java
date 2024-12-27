package com.mindskip.xzs.service.impl.tspx;

import com.mindskip.xzs.domain.Position;
import com.mindskip.xzs.domain.User;
import com.mindskip.xzs.domain.UserOrPosition;
import com.mindskip.xzs.domain.tspx.Org;
import com.mindskip.xzs.repository.NewUserMapper;
import com.mindskip.xzs.service.impl.BaseServiceImpl;
import com.mindskip.xzs.service.tspx.NewUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;


@Service
public class NewUserServiceImpl extends BaseServiceImpl<User> implements NewUserService {

    private final NewUserMapper userMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public NewUserServiceImpl(NewUserMapper userMapper, ApplicationEventPublisher eventPublisher) {
        super(userMapper);
        this.userMapper = userMapper;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Org queryOrgList(String orgNo) {
        Org org = userMapper.queryOrgInfo(orgNo);
        List<Org> retOrgs03 = new ArrayList<>();
        List<Org> retOrgs04 = new ArrayList<>();
        List<Org> retOrgs06 = new ArrayList<>();
        if(org !=null){
            org=  buidOrgTree(org);
        }
        return org;
    }

    Org buidOrgTree(Org org){
        if(org !=null){
            String  orgType = org.getOrgType();
            String orgNo=org.getOrgNo();
                org.setIsLeaf("0");
                List<Org> orgs = userMapper.getOrgList(orgNo);
                org.setChildren(orgs);
                if(orgs !=null && orgs.size()>0 ){
                    org.setIsLeaf("1");
                    for (Org org1:orgs) {
                        buidOrgTree(org1);
                    }
                }
                org.setChildren(orgs);
        }
     return org;

    }

    @Override
    public Org queryOrgInfo(String orgNo) {
        return userMapper.queryOrgInfo(orgNo);
    }

    @Override
    public List<User> getUserListByOrgNo(String orgNo,String userName) {
        List<User> list = userMapper.getUserListByOrgNo(orgNo, userName);
        return list;
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }
    @Override
    public User getUserByUserName(String username) {
        return userMapper.getUserByUserName(username);
    }

    @Override
    public int updatePwd(Map upMap){
        return userMapper.updatePwd(upMap);
    }

    @Override
    public LinkedHashSet<Position> getPositionList(User user) {
        return userMapper.getPositionList(user);
    }

    @Override
    public LinkedHashSet<Position> getPositionListByUserNo(User userNo) {
        return userMapper.getPositionListByUserNo(userNo);
    }

    @Override
    public int insertPosition(Position position) {
        return userMapper.insertPosition(position);
    }

    @Override
    public int updatePosition(Position position) {
        return userMapper.updatePosition(position);
    }

    @Override
    public int deletePosition(Position position) {
        return userMapper.deletePosition(position);
    }

    @Override
    public int insertUserOrPosition(UserOrPosition uos) {
        return userMapper.insertUserOrPosition(uos);
    }

    @Override
    public UserOrPosition getUserOrPosition(UserOrPosition uos) {
        return userMapper.getUserOrPosition(uos);
    }

    @Override
    public int updateUserOrPosition(UserOrPosition uos) {
        return userMapper.updateUserOrPosition(uos);
    }

    @Override
    public int deleteUserOrPosition(UserOrPosition uos) {
        return userMapper.deleteUserOrPosition(uos);
    }
}
