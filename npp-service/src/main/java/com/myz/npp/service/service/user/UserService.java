package com.myz.npp.service.service.user;

import com.myz.common.util.DateUtil;
import com.myz.npp.service.dao.user.mapper.UserMapper;
import com.myz.npp.service.dao.user.model.User;
import com.myz.npp.service.dao.user.model.UserExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author yzMa
 * @desc
 * @date 2020/10/31 17:58
 * @email 2641007740@qq.com
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    /**
     * 根据id查询User，
     * 使用shardingJdbc没有使用分片键所有库所有表查询
     * @param id
     * @return
     */
    public User getById(Long id){
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 使用shardingJdbc根据分片键 路由到指定库指定表
     * @param userId
     * @return
     */
    public User getByShardingColumn(String userId){
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);

        List<User> users = userMapper.selectByExample(example);

        return CollectionUtils.isEmpty(users)?null:users.get(0);
    }

    /**
     *
     * @param platformId
     * @return
     */
    public User getByPlatformId(String platformId){
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andPlatformIdEqualTo(platformId);

        List<User> users = userMapper.selectByExample(example);

        return CollectionUtils.isEmpty(users)?null:users.get(0);
    }

    /**
     *
     * @param user
     * @return
     */
    public int saveUser(User user){
        String nowDateStr = DateUtil.nowDateStr();
        user.setCreateTime(nowDateStr);
        user.setUpdateTime(nowDateStr);
        int userRow = userMapper.insert(user);
        return userRow;
    }

    /**
     *
     * @param user
     * @return
     */
    public int updateByUserId(User user){
        UserExample userExample =  new UserExample();
        userExample.createCriteria().andUserIdEqualTo(user.getUserId());

        String nowDateStr = DateUtil.nowDateStr();
        user.setUpdateTime(nowDateStr);
        return userMapper.updateByExampleSelective(user,userExample);
    }


    /**
     * 【跟使用sharding-jdbc类似的分库分表中间件的区别之一】
     * @param mobile
     * @return
     */
    public User getByMobile(String mobile) {
//        UserMappingExample userMappingExample = new UserMappingExample();
//        UserMappingExample.Criteria userMappingExampleCriteria = userMappingExample.createCriteria();
//        userMappingExampleCriteria.andMobileEqualTo(mobile);
//        List<UserMapping> userMappings = userMappingMapper.selectByExample(userMappingExample);
//        if (!CollectionUtils.isEmpty(userMappings)){
//            UserMapping userMapping = userMappings.get(0);
//            String userId = userMapping.getUserId();
//            log.info("mobile={} -> userId={} found in userMapping then use shardColumn",mobile,userId);
//
//            User byShardingColumn = this.getByShardingColumn(userId);
//            return byShardingColumn;
//        }
//        log.info("mobile={} not found in userMapping do query all shard",mobile);
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andMobileEqualTo(mobile);

        List<User> users = userMapper.selectByExample(userExample);
        return CollectionUtils.isEmpty(users)?null:users.get(0);

    }

}
