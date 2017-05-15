package testGuava;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import model.Person;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.*;

/**
 * Created by ruohanpan on 2017/5/12.
 */
public class FunctionTest {
    @Test public void test() {
        filterWithIteratorTest();
        filterWithCollections2Test();
        getOldPersonTest();
        testTransformTest();
        filterUsingMultiPredicatesTest();
    }

    @Test public void filterWithIteratorTest() {
        List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        Iterable<String> result = Iterables.filter(names, Predicates.containsPattern("a"));
        for (String name : result) {
            System.out.println(name);
        }
    }

    @Test public void filterWithCollections2Test() {
        List<String> names = Lists.newArrayList("john", "Jane", "Adam", "Tom");
        Collection<String> result = Collections2.filter(names, Predicates.containsPattern("a"));
        assertEquals(2, result.size());
        for (String name : result) {
            System.out.println(name);
        }
        result.add("Anna");
        assertEquals(5, names.size());
    }

    @Test public void getOldPersonTest() {
        final List<Person> people = newArrayList(new Person("pan", 10), new Person("ruo", 21), new Person("han", 22),
                new Person("ss", 30));
        List<Person> oldPerson = newArrayList(Collections2.filter(people, new Predicate<Person>() {
            public boolean apply(Person person) {
                return person.getAge() >= 20;
            }
        }));
        for (Person person : oldPerson) {
            System.out.println("name: " + person.getName() + " age: " + person.getAge());
        }

    }

    @Test
    /*将List<Integer>中的integer类型转换为String并添加test作为后缀字符
    * 每次返回的都是新的对象并且操作过程是线程不安全的*/ public void testTransformTest() {
        List<Integer> intList = Lists.newArrayList(1, 2, 3, 4, 5);
        List<String> c1 = Lists.transform(intList, new Function<Integer, String>() {
            public String apply(Integer input) {
                return String.valueOf(input) + "test";
            }
        });
        for (String s : c1) {
            System.out.println(s);
        }
    }

    @Test
    /*多个predicate组合*/ public void filterUsingMultiPredicatesTest() {
        List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        Collection<String> result = Collections2.filter(names,
                Predicates.or(Predicates.containsPattern("J"), Predicates.not(Predicates.containsPattern("a"))));
        assertEquals(3, result.size());
        for (String s : result) {
            System.out.println("name: " + s);
        }
    }

    @Test public void removeNullTest() {
        List<String> names = Lists.newArrayList("John", null, "Jane", null, "Adam", "Tom");
        System.out.println("list size: " + names.size());
        Collection<String> result = Collections2.filter(names, Predicates.notNull());
        System.out.println("list size: " + result.size());
        for (String s : result) {
            System.out.println("name: " + s);
        }
    }

    @Test
    /*查看一个Collection中的所有元素是否符合某个条件*/ public void checkIsMatchTest() {
        List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        boolean result = Iterables.all(names, Predicates.containsPattern("n|m"));
        //        assertTrue(result);
        System.out.println(result);
        result = Iterables.all(names, Predicates.containsPattern("a"));
        //        assertFalse(result);
        System.out.println(result);
    }

    @Test
    /*将一个list中的每一个元素使用Predicates.containsPattern，判断是否包含m，返回的是boolean，然后再得到的boolean值一起转换为collection */ public void transformNameToPredicateTest() {
        List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        Collection<Boolean> result = Collections2.transform(names, Functions.forPredicate(Predicates.containsPattern("m")));//boolean类型collection
        assertEquals(4, result.size());
        for (Boolean rst : result) {
            System.out.println(rst);
        }
    }

    @Test
    public void usingComposedFunctionTest() {
        Function<String, Integer> f1 = new Function<String, Integer>() {
            public Integer apply(String input) {
                return input.length();
            }
        };
        Function<Integer, Boolean> f2 = new Function<Integer, Boolean>() {
            public Boolean apply(Integer input) {
                return input % 2 == 0;
            }
        };
        List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        Collection<Boolean> result = Collections2.transform(names, Functions.compose(f2, f1));
        for (Boolean rst : result) {
            System.out.println(rst);
        }
    }

    @Test
    /*输出以A或T开头的名字的长度*/
    public void filteringAndTransformTest() {
        Predicate<String> predicate = new Predicate<String>() {
            public boolean apply(String input) {
                return input.startsWith("A") || input.startsWith("T");
            }
        };
        Function<String, Integer> func = new Function<String, Integer>() {
            public Integer apply(String input) {
                return input.length();
            }
        };
        List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        Collection<Integer> result = FluentIterable.from(names).filter(predicate).transform(func).toList();
        for (Integer i : result) {
            System.out.println(i);
        }
    }
}
