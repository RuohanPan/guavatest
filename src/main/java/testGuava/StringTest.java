package testGuava;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by ruohanpan on 2017/5/15.
 */
public class StringTest
{
    @Test
    public void joinerTest() {
        /*使用;连接字符串并忽略null
        * Joiner返回新的对象，线程安全*/
        Joiner joiner = Joiner.on("; ").skipNulls();
        System.out.println(joiner.join("Harry", null, "Ron", "Hermione"));
        Joiner joiner2 = Joiner.on("; ").useForNull("||");
        System.out.println(joiner2.join("Harry", null, "Ron", "Hermione"));
        /*Joiner也可以连接对象类型，将对象的toString()值连接起来*/
        System.out.println(Joiner.on(",").join(Arrays.asList(1, 5, 7)));
    }

    @Test
    /*Splitter,String.split丢弃了尾部的分隔符*/
    public void splitterTest() {
         Iterable<String> result = Splitter.on(",")
                .trimResults()
                .omitEmptyStrings()
                .split("foo,bar,,   qux, ");
         for (String s : result) {
             System.out.println(s);
         }
    }

    @Test
    public void charMatcherTest() {
        /*测试是否字符序列中的所有字符都匹配*/
        System.out.println(CharMatcher.anyOf("aeiou").matchesAllOf("binarytree"));
        /*给定字符范围匹配*/
        System.out.println(CharMatcher.inRange('a', 'c').removeFrom("abcdefghigk"));
        String s = CharMatcher.DIGIT.replaceFrom("su12edefrgvrvgtgttrrrr4r55556", "_");
        System.out.println(s);
    }
}
