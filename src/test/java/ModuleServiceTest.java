import com.gs.authority.bean.Module;
import com.gs.authority.service.ModuleService;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by WangGenshen on 12/3/15.
 */
public class ModuleServiceTest extends TestCase {

    private ModuleService moduleService;

    @Override
    protected void setUp() throws Exception {
        moduleService = new ModuleService();
    }

    @Test
    public void testAdd() {
        Module module = new Module();
        module.setId(UUID.randomUUID().toString());
        module.setName("模块管理");
        moduleService.add(module);
    }
}
