import com.ylz.dao.SeckillMapper;
import com.ylz.service.SeckillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by liuburu on 2017/2/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml",
        "classpath:spring/spring-web.xml"
})
public class TestAutowire {
    @Autowired
    private SeckillMapper seckillMapper;

    @Autowired
    private SeckillService seckillService;



    @Test
    public void test(){
//        System.out.println(seckillMapper);
//        System.out.println(seckillService);
    }
}
