package indi.shenqqq.bbs.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author Shen Qi
 * @Date 2022/4/8 18:01
 * @Description XX
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

    private static ApplicationContext context = null;

    public SpringContextUtils() {
        super();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        return context.getBean(beanName, clazz);
    }

    public static <T> T getBean(Class<T> clazz) {
        T t = null;
        Map<String, T> map = context.getBeansOfType(clazz);
        for (Map.Entry<String, T> entry : map.entrySet()) {
            t = entry.getValue();
        }
        return t;
    }

    public static boolean containsBean(String beanName) {
        return context.containsBean(beanName);
    }

    public static boolean isSingleton(String beanName) {
        return context.isSingleton(beanName);
    }

    public static Class getType(String beanName) {
        return context.getType(beanName);
    }

}
