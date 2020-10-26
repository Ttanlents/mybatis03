package com.yjf.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * @author 余俊锋
 * @date 2020/10/12 18:46
 * @Description
 */
public class MybatisUtils {
    private final static ThreadLocal<SqlSession> session=new ThreadLocal<>();

    private static SqlSessionFactory getFactory(){
        try {
            Reader reader= Resources.getResourceAsReader("mybatis.xml");
            SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
            return builder.build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static SqlSession getSession(){
        if (session.get()!=null){
            return session.get();
        }
        //当前线程set值一次永久使用，其他线程不共享
        session.set(getFactory().openSession());
        return session.get();
    }

    public static void main(String[] args) {
        System.out.println(getSession());
        System.out.println(getSession());

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(getSession());
            }
        }).start();
    }

}
