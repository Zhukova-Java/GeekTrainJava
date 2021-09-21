package com.geektime.week1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 2.（必做）自定义一个 Classloader，加载一个 Hello.xlass 文件，
 * 执行 hello 方法，此文件内容是一个 Hello.class
 * 文件所有字节（x=255-x）处理后的文件。文件群里提供。
 */
public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args)  {
        HelloClassLoader helloClassLoader = new HelloClassLoader();
        try {

            Class clazz = helloClassLoader.findClass("Hello");
            // 运行
            Object object = clazz.getDeclaredConstructor().newInstance();
            Method method = clazz.getMethod("hello");
            method.invoke(object);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @param name 需要解析的class名(package.class)
     */
    @Override
    protected Class<?> findClass(String name)  {
        byte[] bytes = readXlass();
        return defineClass(name, bytes, 0, bytes.length);
    }

    private byte[] readXlass() {
        File xlassFile = new File("src\\main\\java\\com\\geektime\\week1\\Hello.xlass");
        List<Byte> resultList = new ArrayList<>();
        byte[] bytes = new byte[0];

        InputStream inputStream;
        try {
            inputStream = new FileInputStream(xlassFile);
            int data;

            while ((data = inputStream.read()) > -1){
                byte b = (byte)(255 - data);
                resultList.add(b);
            }
            inputStream.close();

            int length = resultList.size();
            bytes = new byte[length];
            for (int i = 0;i < length; i++) {
                bytes[i] = resultList.get(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;
    }

}
