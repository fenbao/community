package com.zjq.community.service;

import com.zjq.community.mapper.UserMapper;
import com.zjq.community.model.User;
import com.zjq.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @date 2022-02-07 21:48
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size() == 0){
            // 创建
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtModified());
            // userMapper.insert(dbUser); 这里写错了导致一直user写不到数据库中
            userMapper.insert(user);
        }else {
            // 变更
            // 重新设置这些值是因为这些值是可能变化的，accountId是不变的
            User dbUser = users.get(0);
            User updateUser = new User();
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());
            UserExample example = new UserExample();
            example.createCriteria().
                    andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser,example);
        }

    }
}
