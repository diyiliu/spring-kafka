package cn.diyiliu.kafka.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * SpringContextHolder
 *
 * @author: DIYILIU
 * @date: 2022/03/03
 */
public class SpringContextHolder implements ApplicationContextAware {

    /**
     * Spring应用上下文环境
     */
    private static ApplicationContext applicationContext;

    /**
     * 实现了ApplicationContextAware 接口，必须实现该方法；
     * <p>
     * 通过传递applicationContext参数初始化成员变量applicationContext
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    /**
     * 获取spring上下文
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {

        return applicationContext;
    }

    /**
     * 实例化bean
     *
     * @param name
     * @return
     * @throws BeansException
     */
    public static <T> T getBean(String name) throws BeansException {

        return (T) applicationContext.getBean(name);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> type) {

        return applicationContext.getBeansOfType(type);
    }

    public static void init() {
        new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    }

    public static void init(String filename) {
        new ClassPathXmlApplicationContext("classpath:" + filename);
    }

    public static void init(Class clazz) {
        new AnnotationConfigApplicationContext(clazz);
    }

    public static ApplicationContext run(Class clazz) {
        return new AnnotationConfigApplicationContext(clazz);
    }
}
