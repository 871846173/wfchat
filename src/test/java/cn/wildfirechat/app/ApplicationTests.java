package cn.wildfirechat.app;

import cn.wildfirechat.app.pojo.User;
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

    @Test
    public void contextLoads() {
    }

    @Test
    public void login() {
		User user=userService.getUser("12345","12345");
//		Assert.assertEquals(new Integer(21),user.getAge());

		if(user!=null){
			System.out.println("user" + " " + user.getMobile());
		}else {
			System.out.println(0);
		}
	}

	@Test
	public void updatePassword(){
		RestResult x=userService.updatePassword("admin11","12345");

    }

	@Test
	public void selectPassword(){
//	userService.addCollection("2","2");
userService.deleteCollection("2");
	}
}
