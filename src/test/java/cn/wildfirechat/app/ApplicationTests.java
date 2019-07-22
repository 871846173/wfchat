package cn.wildfirechat.app;

import cn.wildfirechat.app.pojo.User;
import cn.wildfirechat.app.service.SelectTypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private Service userService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ServiceImpl serviceImpl;
    @Autowired
    private SelectTypeService selectTypeService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void login() {
        User user = userService.getUser("12345", "12345");
//		Assert.assertEquals(new Integer(21),user.getAge());

        if (user != null) {
            System.out.println("user" + " " + user.getMobile());
        } else {
            System.out.println(0);
        }
    }

    @Test
    public void forgetPassword() {
        int status = userDao.updatePasswordByMobile("156060445981", "12345");
        System.out.println(status);
    }

    @Test
    public void insertSelectType() {
        selectTypeService.insertSelectType("2", 1, 3, 3, 1);

    }
}
