package airbnb.persistence;

import airbnb.persistence.mapper.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class MyBatisConnectionFactory {
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            String resource = "config/config.xml";
            Reader reader = Resources.getResourceAsReader(resource);
            if (sqlSessionFactory == null) {
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, "development");
                Class[] mappers = {AmenitiesMapper.class, ApprovedMapper.class, HouseMapper.class, LoginMapper.class, ReservationMapper.class, UserMapper.class, WaitingMapper.class, ReviewMapper.class, FeePolicyMapper.class, DiscountPolicyMapper.class, ReplyMapper.class}; // -> 메퍼 목록들 넣으면 됨
                for (Class mapper : mappers) {
                    sqlSessionFactory.getConfiguration().addMapper(mapper);
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}

