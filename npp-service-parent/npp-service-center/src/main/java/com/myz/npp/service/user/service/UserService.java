package com.myz.npp.service.user.service;

import com.myz.common.constant.StatusVal;
import com.myz.common.util.DateUtil;
import com.myz.npp.api.user.dto.UserInsertParam;
import com.myz.npp.service.user.dao.mapper.UserMapper;
import com.myz.npp.service.user.dao.model.User;
import com.myz.npp.service.user.dao.model.UserExample;
import com.myz.npp.service.usermapping.dao.mapper.UserMappingMapper;
import com.myz.npp.service.usermapping.dao.model.UserMapping;
import com.myz.npp.service.usermapping.dao.model.UserMappingExample;
import com.myz.starters.distribute.id.SnowflakeIdWorker;
import com.myz.starters.distribute.id.SnowflakeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
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

    @Autowired
    private UserMappingMapper userMappingMapper;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

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
     * 框架有重试 一定要做幂等
     * @param user
     * @return
     */
    public int saveUser(User user){
        String nowDateStr = DateUtil.nowDateStr();
        user.setCreateTime(nowDateStr);
        user.setUpdateTime(nowDateStr);
        user.setStatus(StatusVal.DEFAULT);

        long id = snowflakeIdWorker.nextId();
        SnowflakeInfo snowflakeInfo = snowflakeIdWorker.extraId(id);
        user.setUserId(id+"");
        log.info("user save id={},extra id to snowflakeInfo={}",id,snowflakeInfo);

        int userRow = userMapper.insert(user);

        BeanCopier beanCopier = BeanCopier.create(User.class,UserMapping.class,false);
        UserMapping userMapping = new UserMapping();
        beanCopier.copy(user,userMapping,null);
        int insert = userMappingMapper.insert(userMapping);
        log.info("userInsertRow={},userMappingInsertRow={}",userRow,insert);
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
     *
     * @param mobile
     * @return
     */
    public User getByMobile(String mobile) {
        UserMappingExample userMappingExample = new UserMappingExample();
        UserMappingExample.Criteria userMappingExampleCriteria = userMappingExample.createCriteria();
        userMappingExampleCriteria.andMobileEqualTo(mobile);
        List<UserMapping> userMappings = userMappingMapper.selectByExample(userMappingExample);
        if (!CollectionUtils.isEmpty(userMappings)){
            UserMapping userMapping = userMappings.get(0);
            String userId = userMapping.getUserId();
            log.info("mobile={} -> userId={} found in userMapping then use shardColumn",mobile,userId);

            User byShardingColumn = this.getByShardingColumn(userId);
            return byShardingColumn;
        }
        log.info("mobile={} not found in userMapping do query all shard",mobile);
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andMobileEqualTo(mobile);

        List<User> users = userMapper.selectByExample(userExample);
        return CollectionUtils.isEmpty(users)?null:users.get(0);
    }


    public static void main(String[] args) {
        String userId="1928715797925888";
        System.out.println("hasCode="+userId.hashCode()+" long="+Long.parseLong(userId)+" db"+userId.hashCode() %2+" table="+userId.hashCode()%3);
    }
}
