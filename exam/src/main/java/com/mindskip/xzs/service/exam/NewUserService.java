package com.mindskip.xzs.service.exam;

import com.mindskip.xzs.domain.Position;
import com.mindskip.xzs.domain.User;
import com.mindskip.xzs.domain.UserOrPosition;
import com.mindskip.xzs.domain.main.Org;
import com.mindskip.xzs.service.BaseService;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;


public interface NewUserService extends BaseService<User> {

    Org queryOrgList (String orgNo);

    Org queryOrgInfo (String orgNo);

    List<User> getUserListByOrgNo (String orgNo,String raalName);

    public User getUserById(Integer id) ;
    public User getUserByUserName(String username);

    public int updatePwd(Map upMap);

    /**
     * 岗位列表（新）
     * @param user
     * @return
     */
    LinkedHashSet<Position> getPositionList(User user);
    LinkedHashSet<Position> getPositionListByUserNo(User user);

    /**
     * 保存岗位信息
     * @return
     */
    int insertPosition(Position position);

    /**
     * 修改岗位信息
     * @param position
     * @return
     */
    int updatePosition(Position position);

    /**
     * 删除岗位信息
     * @param position
     * @return
     */
    int deletePosition(Position position);

    /**
     * 保存岗位用户关联
     * @param uos
     * @return
     */
    int insertUserOrPosition(UserOrPosition uos);

    /**
     * 查询岗位用户关联数据
     * @param uos
     * @return
     */
    UserOrPosition getUserOrPosition(UserOrPosition uos);

    /**
     * 修改岗位用户关联
     * @param uos
     * @return
     */
    int updateUserOrPosition(UserOrPosition uos);
    /**
     * 删除岗位用户关联
     * @return
     */
    int deleteUserOrPosition(UserOrPosition uos);
}
