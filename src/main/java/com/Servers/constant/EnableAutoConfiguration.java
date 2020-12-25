package com.Servers.constant;

import com.Servers.config.BaseConfiguration;
import com.Servers.util.ClassUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *实现自动配置的类
 */
@BaseConfigInfo
public class EnableAutoConfiguration {
    private BaseConfigInfo configInfo= this.getClass().getAnnotation(BaseConfigInfo.class);
    private String[] basePath=configInfo.autoConfigBasePath();
    private BaseConfiguration baseConfiguration;
    public EnableAutoConfiguration() throws ClassNotFoundException {
        //创建并修改我们的实例
        setInstance();
    }

    private void setInstance() {
        List<BaseConfiguration> objects;
        //进行异常判断,过滤非法参数
        boolean var1=false;
        for (int i = 0; i < basePath.length; i++) {
            for (int j=0; j<2 ;j++){
                if (!var1) {
                    if (this.basePath[i].contains(ServerConstant.basePath[j])) {
                        var1=true;
                    }
                }
            }
        }
        if (!var1) throw new IllegalArgumentException("请确保你的对象输入正确:");
        List<Class<?>> classes=new ArrayList<Class<?>>();
        //给实列对象进行赋值
        for (int i = 0; i < basePath.length; i++) {
            List<Class<?>> var2 = ClassUtil.getClasses(basePath[i]);
            classes.addAll(var2);
        }
        objects=new ArrayList<>();
        //将获取到的class对象转换成实体类
        Iterator<Class<?>> iterator = classes.iterator();
        while (iterator.hasNext()){
            Class<?> oldClassObj=iterator.next();
            Class<?> classObj= null;
            try {
                //加载类对象
                classObj = Class.forName(oldClassObj.getName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            //排除接口本类对象和其他非法对象
            Constructor<?>[] declaredConstructors = null;
            try {
                if ((classObj.isAnnotation()||classObj.isEnum()||classObj.isInterface()||classObj.getName()
                        .equals("com.Servers.constant.EnableAutoConfiguration"))) continue;
                if (!classObj.newInstance().getClass().getSuperclass().getName()
                        .equals("com.Servers.config.BaseConfiguration")) continue;
                declaredConstructors = classObj.newInstance().getClass().getDeclaredConstructors();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            for (Constructor<?> declaredConstructor : declaredConstructors) {
                try {
                    BaseConfiguration var2=(BaseConfiguration)declaredConstructor.newInstance(null);
                    System.out.println("找到实例对象");
                    objects.add(var2);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            baseConfiguration=objects.get(0);
        }
    }

    public BaseConfiguration getBaseConfiguration() {
        return baseConfiguration;
    }

    public void setBaseConfiguration(BaseConfiguration baseConfiguration) {
        this.baseConfiguration = baseConfiguration;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        EnableAutoConfiguration autoConfiguration=new EnableAutoConfiguration();
    }
}
