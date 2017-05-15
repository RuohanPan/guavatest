package testGuava;

import com.google.common.util.concurrent.*;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by ruohanpan on 2017/5/15.
 */
public class ConcurrentTest {
    ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
    final ListenableFuture<Integer> listenableFuture = executorService.submit(new Callable<Integer>() {
        public Integer call() throws Exception {
            System.out.println("call execute..");
            /*Callable接口的实现类中定义，休眠了1秒钟然后返回一个数字7*/
            TimeUnit.SECONDS.sleep(1);
            return 7;
        }
    });
    @Test
    public void futureTest() {
        listenableFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("get listenable future's result " + listenableFuture.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }, executorService);
        /*通过Futures的静态方法addCallback给ListenableFuture添加回调函数
        * 因为第二种方法可以直接得到Future的返回值，或者处理错误情况。本质上第二种方法是通过调动第一种方法实现的，做了进一步的封装*/
        Futures.addCallback(listenableFuture, new FutureCallback<Integer>() {
            public void onSuccess(Integer result) {
                System.out.println("get listenable future's result with callback " + result);
            }
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
