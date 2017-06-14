package cn.tom.web.controller;

import cn.tom.web.model.User;
import cn.tom.web.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by tom on 17/5/14.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/post")
    public String postUser() {
        User user = new User();
        user.setAge(20);
        user.setName("张三");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -20);
        user.setBirthday(calendar.getTime());
        Integer integer = userService.insertUser(user);
        LOGGER.info("postUser, result:{}", integer > 0);
        return "success";
    }

    @RequestMapping(value = "/urlParam/{id}")
    public String urlParam(@PathVariable String id, String u) {
        System.out.println(u);
        System.out.println(id);
        return "success";
    }
}
