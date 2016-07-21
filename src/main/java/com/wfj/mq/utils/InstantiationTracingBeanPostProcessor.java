package com.wfj.mq.utils;

import com.wfj.mq.service.ErrorMsgInitService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by MaYong on 2015/9/16.
 */
public class InstantiationTracingBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
        if (event.getApplicationContext().getParent() == null) {//root application context 没有parent，他就是老大.
            //需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
            System.out.println("=========== spring 启动完毕 ============");
            ApplicationContext context = event.getApplicationContext();
            ErrorMsgInitService errorMsgInitService = (ErrorMsgInitService) context.getBean("errorMsgInitService");
            errorMsgInitService.init();
        }
    }
}
