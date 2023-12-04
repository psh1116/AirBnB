package airbnb.persistence.dao;

import airbnb.persistence.dto.FeePolicyDTO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class FeePolicyDAO {
    private final SqlSessionFactory sqlSessionFactory;

    public FeePolicyDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public FeePolicyDTO getFeePolicyByHouseId(int houseId) {
        FeePolicyDTO feePolicyDTO;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            feePolicyDTO = session.selectOne("mapper.FeePolicyMapper.getFeePolicyByHouseId", houseId);
        }

        return feePolicyDTO;
    }

    public void insertFeePolicy(FeePolicyDTO feePolicyDTO) {
        try(SqlSession session = sqlSessionFactory.openSession()) {
            session.insert("mapper.FeePolicyMapper.insertFeePolicy", feePolicyDTO);
            session.commit();
        }
    }
}
