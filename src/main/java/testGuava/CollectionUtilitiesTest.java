package testGuava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.*;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * Created by ruohanpan on 2017/5/15.
 */


public class CollectionUtilitiesTest {

    @Test
    public void testLists() {
        Lists.newArrayList();
        System.out.println(Lists.newArrayList(1, 2, 3));
        Lists.newArrayList(Sets.newHashSet(1, 2, 3));
        Lists.newArrayListWithCapacity(10);
        System.out.println(Lists.newArrayListWithExpectedSize(10));

        Lists.newLinkedList();
        Lists.newLinkedList(Sets.newHashSet(1, 2, 3));

        Lists.partition(Lists.newArrayList(1, 2, 3, 4, 5), 2);
        Lists.reverse(Lists.newArrayList(1, 2, 3, 4, 5));
        }

    @Test
    public void ImmutableTest() {
        ImmutableList<String> iList = ImmutableList.of("1", "2", "3");
        ImmutableSet<String> iSet = ImmutableSet.of("s1", "s2", "s3");
        ImmutableMap<String, String> iMap = ImmutableMap.of("k1", "v1", "k2", "v2"); //构造方法最多支持5对KV，再多的话可以使用builder
        ImmutableMap.Builder<Object, Object> builder = ImmutableMap.builder();
        builder.put("1", "A");
        ImmutableMap<Object, Object> immutableMap = builder.build();
        System.out.println(iList);
        System.out.println(iSet);
        System.out.println(iMap);
        /*构建Immutable对象的方法：
        1.ImmutableSet.copyOf(set)
        * 2.ImmutableSet.of("a", "b")
        * 3.Builder工具*/
        Set<String> color = new HashSet<String>();
        color.add("black");
        color.add("blue");
        ImmutableSet<String> set = ImmutableSet.copyOf(color);//底层使用System.arraycopy()实现浅copy
        System.out.println(set);
        ImmutableSet<String> goodColors = ImmutableSet.<String>builder().add("white").build();
        System.out.println(goodColors);
        /*以列表形式方便地读取集合元素，读取第k个最小元素*/
        System.out.println(set.asList().get(1));
    }

    @Test
    public void newCollectionTypeTest() {
        /*multiSet:可以多次添加相等的元素且与顺序无关
        * 1.没有元素顺序限制的ArrayList
        * 2.Map<E, Integer(计数)>
        *multiSet中元素的计数只能是正数，不能为0或负
        */
        List<String> wordList = Lists.newArrayList("the", "the", "this", "the");
        HashMultiset<String> multiSet = HashMultiset.create();
        multiSet.addAll(wordList);
        System.out.println(multiSet.count("the"));
        multiSet.setCount("this", 4);
        System.out.println(multiSet);

        /*multimap：在map的value里放多个元素*/
//        Multimap<String, String> multimap = new HashMultimap<String, String>();
        /*BitMap:实现键值对的双向映射
        * bitMsp.inverse()反转键值映射*/
        BiMap<String, String> userId = HashBiMap.create();
        userId.put("ruohanpan", "sss");
        userId.put("sss","sss");
        String userForId = userId.inverse().get("sss");
        System.out.println(userForId);
        /*Table：使用多个键做索引的时候，˙支持所有类型的键：行和列
        * HashBasedTable:HashMap<R, HashMap<C,V>>
        * immutableTable:ImmutableMap<R, ImmutableMap<C,V>>*/
        Table<String, String, Integer> table = HashBasedTable.create();
        table.put("name", "password", 1234);
        table.put("first", "list", 2333);
        System.out.println(table.row("name"));
        System.out.println(table.column("list"));
        /*RangeSet:提供了一组不相连的非空的区间*/

    }
    @Test
    public void cacheTest() {
        Cache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .build();//no cacheLoader
        cache.put("name", "pan");
        cache.put("password", "1234");
        cache.asMap().putIfAbsent("name", "pan");
        try {
            cache.get("name", new Callable<String>(){
                public String call() throws Exception {
                    return null;
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}

