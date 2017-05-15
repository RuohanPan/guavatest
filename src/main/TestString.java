import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import org.junit.Test;



import static org.junit.Assert.*;

/**
 * Created by ruohanpan on 2017/5/12.
 */
public class TestString {
    @Test
    public void test() {
        testNullValue();
        testForString();
    }

    public void testForString() {
        Joiner joiner = Joiner.on("|").useForNull("");//把null替换为指定字符串;
        //id||startdate|payFlowNo
        System.out.println(joiner.join("id", null, "startdate", "payFlowNo"));
    }

    private void testNullValue() {
        Optional<Integer> absent = Optional.fromNullable(null);
        assertFalse(absent.isPresent());
        try {
            absent.get();
            fail();
        } catch (IllegalStateException e) {
            assertTrue(true);
        }
        assertEquals(1, absent.or(1).intValue());
        assertNull(absent.orNull());
    }
}
