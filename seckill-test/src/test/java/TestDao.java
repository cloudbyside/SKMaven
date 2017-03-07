import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by liuburu on 2017/3/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml"
})
public class TestDao {

    @Autowired(required = false)
    private DataSource dataSource;

    @Test
    public void testQuerySeckill() throws SQLException {
        System.out.println(dataSource.getConnection());
    }
}
