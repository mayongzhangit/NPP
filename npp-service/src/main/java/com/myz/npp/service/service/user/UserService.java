package com.myz.npp.service.service.user;

import com.myz.npp.service.dao.mapper.UserMapper;
import com.myz.npp.service.dao.model.User;
import com.myz.npp.service.dao.model.UserExample;
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

}
