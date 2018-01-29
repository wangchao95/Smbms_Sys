package cn.jbit.smbms.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MybatisUtil {
    private static  SqlSessionFactory factory = null;

    static {
        String configPath = "mybatis.xml";
        //加载配置文件的流
        InputStream is = null;
        try {
            is = Resources.getResourceAsStream(configPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactoryBuilder builderFactory = new SqlSessionFactoryBuilder();
        factory = builderFactory.build(is);
    }

    /**
     * 打开会话
     * @return
     */
    public static SqlSession openSession(){
        return factory.openSession(false);   //不自动提交
    }

    /**
     * 关闭会话
     */
    public static void closeSession(SqlSession sqlSession){
        if(sqlSession!=null){
            sqlSession.close();
        }
    }
}