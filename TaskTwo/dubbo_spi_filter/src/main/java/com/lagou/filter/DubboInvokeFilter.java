package com.lagou.filter;

import com.lagou.bean.CountTimeModel;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.rpc.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


@Activate(group = {CommonConstants.CONSUMER})
public class DubboInvokeFilter   implements Filter {

    private static Logger log = LoggerFactory.getLogger(DubboInvokeFilter.class);

    private ConcurrentHashMap responseTimeMap = new ConcurrentHashMap();

    // 开始打印时间
    private long startPrintTime = 0L;

    // 开始请求时间

    private static long startRequestTime = System.currentTimeMillis();

    List<CountTimeModel>  modelList = new ArrayList();



        @Override
        public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
            long start = System.currentTimeMillis();
            Result result = invoker.invoke(invocation);
            long elapsed = System.currentTimeMillis() - start;

            CountTimeModel countTimeModel=new CountTimeModel() ;
            countTimeModel.setElapsed(elapsed);
            countTimeModel.setTime(System.currentTimeMillis());

            modelList.add(countTimeModel) ;

            if (System.currentTimeMillis() - startPrintTime >= 5 * 1000) {
                System.out.println("Method:"+ invocation.getMethodName() +"========>TP90耗时:" + tpCount(modelList,90) + "ms;======>TP99耗时:"
                        +tpCount(modelList,99));
                // 开始打印时间重新赋值
                startPrintTime = System.currentTimeMillis();
            }

            return result;
    }



    /**
     * 计算TP90，TP99等
     * @param list
     * @param percent
     * @return
     */
    private static int tpCount(List<CountTimeModel> list, int percent) {
           List<Integer> timeList = new ArrayList();
           for(CountTimeModel c :list){
               long time=c.getTime();
               if((System.currentTimeMillis()-time)/(1000*60)<=1){
                   timeList.add((int)c.getElapsed()) ;
               }
           }
           return tp(timeList,percent) ;
    }

    private static int tp(List<Integer> times, int percent) {
        float percentF = (float)percent/100;
        int index = (int)(percentF * times.size() - 1);
        Collections.sort(times);
        return times.get(index);
    }
}
