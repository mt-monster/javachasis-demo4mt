import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.huawei.demo4mt.PojoConsumer;
import com.huawei.demo4mt.TestMgr;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:PACKAGE_NAME
 */
public class PojoIT {

    @Before
    public void setUp(){
        TestMgr.errors().clear();
    }

    @Test
    public void pojoTestEntry() throws Exception {
        PojoConsumer.main(new String[0]);
        Assert.assertThat(TestMgr.errors().isEmpty(), Is.is(true));
    }

}
