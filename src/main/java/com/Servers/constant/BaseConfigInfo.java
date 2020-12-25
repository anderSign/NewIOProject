package com.Servers.constant;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
/**
 * 基本配置信息的操作
 */
public @interface BaseConfigInfo {
    /*
    线程池的名称  最终默认打印的名称是:初始服务器1[,初始服务器n]
     */
    String poolName() default "BasePool";
    /*
    默认是10，超过抛出 @InvalidObjectException异常
     */
    int maxPoolNum() default 10;
    /*
    默认8080,可以根据自己需要自行修改
     */
    int serverHost() default 8080;
    /*
    自动配置的基本信息
     */
    String[] autoConfigBasePath() default {"com.Servers.constant","com.Servers.config"};
}
