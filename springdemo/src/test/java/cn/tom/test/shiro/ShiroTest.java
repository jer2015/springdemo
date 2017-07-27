package cn.tom.test.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author lxl
 */
public class ShiroTest {

    @Test
    public void helloWorldTest() {
        //1.获取SecurityManager工厂
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("/Users/tom/IdeaProjects/myDev/springdemo/springdemo/src/main/resources/shiro.ini");
        //2.获取SecurityManager实例并绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //3.获取Subject及创建用户名/密码身份验证token
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        //4.登录即身份验证
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            //5.身份验证失败
        }
        System.out.println(subject.isAuthenticated());

        //6.退出
        subject.logout();
    }
}
