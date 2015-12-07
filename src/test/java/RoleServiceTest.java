import com.gs.authority.bean.Role;
import com.gs.authority.bean.User;
import com.gs.authority.dao.RoleDAO;
import com.gs.authority.service.RoleService;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

/**
 * Created by WangGenshen on 12/2/15.
 */
public class RoleServiceTest extends TestCase {

    private RoleService roleService;

    @Override
    protected void setUp() throws Exception {
        roleService = new RoleService();
    }

    @Test
    public void testAdd() {

        Role role = new Role();
        role.setId(UUID.randomUUID().toString());
        role.setName("normal");
        roleService.add(role);
    }

    @Test
    public void testQueryByIdWithAllUsers() {
        Role role = roleService.queryByIdWithUsers("7d07f8ed-b49c-40c8-97c3-aa3116bfc7d7");
        if(role != null) {
            List<User> users = role.getUsers();
            System.out.println(users.size());
            for(int i = 0, len = users.size(); i < len; i++)
            System.out.println(users.get(i).getId() + ": " + users.get(i).getName());
        }
    }

}
