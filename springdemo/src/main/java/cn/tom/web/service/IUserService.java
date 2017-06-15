package cn.tom.web.service;

import cn.tom.web.model.User;

/**
 * Created by tom on 17/5/14.
 */
public interface IUserService {

    User selectUser(Integer id);

    Integer insertUser(User user);

    Integer updateUser(User user);
}
