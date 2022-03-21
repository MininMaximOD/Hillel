package org.hillel.contex;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class AppContex {
    private AppContex() {}

    private static Map<String, Object> beanStorage = new HashMap<>();
    private static Properties properties = new Properties();

    public static void load(final String fileName) throws IOException {
        //проверка строки некорректная, посмотреть как правильно
        if(fileName == null) throw new IllegalArgumentException("file must be a set");
        try(InputStream is = AppContex.class.getClassLoader().getResourceAsStream(fileName)){
            properties.load(is);
        }
    }

    public static <T> T getBean(final String beanName) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (beanStorage.containsKey(beanName)) return (T) beanStorage.get(beanName);
        final String property = properties.getProperty(beanName);
        if (property == null) throw new IllegalArgumentException("bean with name " + beanName + " not found");
        final T bean = (T) Class.forName(property).newInstance();
        beanStorage.put(beanName, bean);
        return bean;
    }
}
