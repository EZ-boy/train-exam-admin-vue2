package com.mindskip.xzs.repository;

import com.mindskip.xzs.domain.Position;
import com.mindskip.xzs.domain.User;
import com.mindskip.xzs.domain.UserOrPosition;
import com.mindskip.xzs.domain.main.Org;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;


@Mapper
public interface NewUserMapper extends BaseMapper<User> {

    List<Org> queryOrgList (@Param("orgNo") String orgNo);

    Org queryOrgInfo (@Param("orgNo") String orgNo);

    List<User> getUserListByOrgNo (@Param("orgNo") String orgNo,@Param("realName") String realName);
    User getUserByUserName(String username);
    User getUserById(Integer id);

    int updatePwd(Map upMap);

    List<Org> getOrgList (@Param("orgNo") String orgNo);

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

    /**
     * 根据userNo查询岗位集合
     * @param user
     * @return
     */
    LinkedHashSet<Position> getPositionListByUserNo(User user);

    /**
     * 根据orgNo查询岗位集合
     * @param user
     * @return
     */
    LinkedHashSet<Position> getPositionList(User user);
}
