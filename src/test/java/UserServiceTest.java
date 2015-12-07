import com.gs.authority.bean.Pager;
import com.gs.authority.bean.Role;
import com.gs.authority.bean.User;
import com.gs.authority.service.UserService;
import com.gs.authority.util.EncryptUtil;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

/**
 * Created by WangGenshen on 12/2/15.
 */
public class UserServiceTest extends TestCase {

    private UserService userService;

    @Override
    protected void setUp() throws Exception {
        userService = new UserService();
    }

    @Test
    public void testAdd() {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName("Wgssmart");
        user.setPassword(EncryptUtil.md5Encrypt("123456"));
        userService.add(user);
    }

    @Test
    public void testQueryWithRoles() {
        User user = new User();
        user.setName("Wgssmart");
        user.setPassword("123456");
        user = userService.queryWithRoles(user);
        if(user != null) {
            System.out.println(user.getName());
        }
    }

    @Test
    public void testQueryByIdWithRoles() {
        User user = userService.queryByIdWithRoles("36755cc5-6155-45fc-a693-f28c968dfb5e");
        List<Role> roles = user.getRoles();
        for(int i = 0, len = roles.size(); i < len; i++) {
            System.out.println(roles.get(i).getId() + ": " + roles.get(i).getName());
        }
    }

    @Test
    public void testQueryByPager() {
        Pager<User> userPager = userService.queryByPager(1, 20);
        System.out.println(userPager.getObjects().get(0).getName());
    }
}
