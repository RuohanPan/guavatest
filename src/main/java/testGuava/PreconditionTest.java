package testGuava;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.*;
import static com.google.common.base.Preconditions.checkPositionIndexes;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ruohanpan on 2017/5/15.
 */
public class PreconditionTest {
    @Test
    public void test() {
        testCheckArgument();
        testCheckNotNull();
        testCheckState();
        testCheckElementIndex();
        testCheckPositionIndex();
        testCheckPositionIndexs();
    }

    @Test
    public void conditionTest() {
        String phone = "130000000";
        Preconditions.checkNotNull(phone, "手机号不能为空");
        Preconditions.checkArgument(phone.length() == 11, "手机号必须为11位");
    }

    @Test
    public void testCheckArgument() {
        int i = 1;
        checkArgument(i > 0, "参数是%s, 参数必须为正整数", i);
        try {
            i = -1;
            checkArgument(-1 > 0, "参数是%s, 参数必须为正整数", -1);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    public void testCheckNotNull() {
        Object value = new Object();
        checkNotNull(value, "参数是null");
        try {
            value = null;
            checkNotNull(value, "参数是null");
            fail();
        } catch (NullPointerException e) {
            assertTrue(true);
        }
    }

    private void testCheckState() {
        ArrayList<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
        checkState(list.size() < 6, "集体长度应该小于5");

        list.add(6);
        try {
            checkState(list.size() < 6, "集体长度应该小于5");
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    private void testCheckElementIndex() {
        ArrayList<Integer> list = Lists.newArrayList(1, 2, 3);
        // [0, size)
        checkElementIndex(list.size(), 4);

        try {
            checkElementIndex(list.size(), 3);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    private void testCheckPositionIndex() {
        ArrayList<Integer> list = Lists.newArrayList(1, 2, 3);
        // [0, size]
        checkPositionIndex(list.size(), 3);

        try {
            checkPositionIndex(list.size(), 2);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    private void testCheckPositionIndexs() {
        ArrayList<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
        checkPositionIndexes(4, 5, list.size());

        try {
            checkPositionIndexes(5, 6, list.size());
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }
}
