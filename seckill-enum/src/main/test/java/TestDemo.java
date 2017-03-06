import com.alibaba.fastjson.JSON;
import com.ylz.enums.QueryTypeEnum;
import com.ylz.enums.SeckillEnums;
import org.junit.Test;

/**
 * Created by liuburu on 2017/2/24.
 */
public class TestDemo {

    @Test
    public void testEnum(){
        //System.out.println(SeckillEnums.stateOf(1001));
        System.out.println(QueryTypeEnum.TYPE_0.getQueryType());
    }
}
