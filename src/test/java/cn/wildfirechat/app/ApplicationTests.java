//package cn.wildfirechat.app;
//
//import cn.wildfirechat.app.pojo.User;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ApplicationTests {
//
//    @Autowired
//    private Service userService;
//    @Autowired
//    private UserDao userDao;
//    @Autowired
//    private ServiceImpl serviceImpl;
//
//    @Test
//    public void contextLoads() {
//    }
//
//    @Test
//    public void login() {
//        User user = userService.getUser("12345", "12345");
////		Assert.assertEquals(new Integer(21),user.getAge());
//
//        if (user != null) {
//            System.out.println("user" + " " + user.getMobile());
//        } else {
//            System.out.println(0);
//        }
//    }
//
//    @Test
//    public void updatePassword() {
//        RestResult restResult = userService.updatePassword("admin11", "12345", "12345");
//
//    }
//
//    @Test
//    public void verifyPassword() {
//        User user = userDao.verifyPassword("admin", serviceImpl.getMD5("12345"));
//        if (user == null) {
//            System.out.println("没有此用户或密码错误");
//            return;
//        }
//        System.out.println(user);
//        System.out.println(user.getId());
//
//    }
//}
