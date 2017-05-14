package cn.tom.web.service.impl;

import cn.tom.web.dao.UserMapper;
import cn.tom.web.model.User;
import cn.tom.web.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tom on 17/5/14.
 */
@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public Integer insertUser(User user) {
        int insert = userMapper.insert(user);
        return insert;
    }
}
