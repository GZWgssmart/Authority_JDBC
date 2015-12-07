import com.gs.authority.bean.Authority;
import com.gs.authority.bean.Role;
import com.gs.authority.service.AuthorityService;
import com.gs.authority.service.RoleService;
import com.gs.authority.util.AuthorityUtil;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by WangGenshen on 12/2/15.
 */
public class AuthorityServiceTest extends TestCase {

    private AuthorityService authorityService;

    @Override
    protected void setUp() throws Exception {
        authorityService = new AuthorityService();
    }

    @Test
    public void testAdd() {
        Authority authority = new Authority();
        authority.setId(UUID.randomUUID().toString());
        authority.setName("查询所有用户");
        authority.setAction("com.gs.authority.service.UserService.queryByPager");
        authorityService.add(authority);
    }

    @Test
    public void testHasAuthority() {
        Role role = new Role();
        role.setId("7d07f8ed-b49c-40c8-97c3-aa3116bfc7d7");
        role.setName("admin");
        if(authorityService.hasAuthority(role, AuthorityUtil.getAction(RoleService.class, "add"))) {
            System.out.println("权限验证通过");
        } else {
            System.out.println("没有权限");
        }
    }

}
