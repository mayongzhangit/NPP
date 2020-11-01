package com.myz.npp.service.service;

import com.myz.npp.service.dao.mapper.UserMapper;
import com.myz.npp.service.dao.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * 根据id查询User
     * @param id
     * @return
     */
    public User getById(Long id){
        return userMapper.selectByPrimaryKey(id);
    }

}
