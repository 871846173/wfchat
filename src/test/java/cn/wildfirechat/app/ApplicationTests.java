//package cn.wildfirechat.app;
//
//import cn.wildfirechat.app.dao.MessageDao;
//import cn.wildfirechat.app.dao.SelectTypeDao;
//import cn.wildfirechat.app.dao.UserDao;
//import cn.wildfirechat.app.pojo.SelectType;
//import cn.wildfirechat.app.pojo.User;
//import cn.wildfirechat.app.service.MessageService;
//import cn.wildfirechat.app.service.SelectTypeService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
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
//    @Autowired
//    private SelectTypeService selectTypeService;
//    @Autowired
//    private SelectTypeDao selectTypeDao;
//
//    @Autowired
//    private MessageService messageService;
//
//    @Autowired
//    private MessageDao dao;
//
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
//    public void forgetPassword() {
//        int status = userDao.updatePasswordByMobile("156060445981", "12345");
//        System.out.println(status);
//    }
//
//    @Test
//    public void insertSelectType() {
//
//        selectTypeService.createSelectType("6");
//    }
//
//    @Test
//    public void selectType() {
//
//        RestResult restResult = selectTypeService.selectType("2");
//        System.out.println(restResult);
//    }
//
//    @Test
//    public void checkUserOnline() {
//
////        RestResult restResult = serviceImpl.checkUserOnline("admin");
////        System.out.println(restResult);
////        messageService.updateMessageRead("aca4a4oo","rsrTrTee");
////        System.out.println(dao.findNoReadCount("aca4a4oo","rsrTrTee"));
////            dao.deleteMessage("102869680737255426");
////      System.out.println(messageService.selectMessageRead("102306352275161089"));
////        messageService.deleteMessage("102995878091980806",10);
//        messageService.updateMessageRead("aca4a4oo","rsrTrTee");
//    }
//}
