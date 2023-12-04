package airbnb.persistence.dao;


import airbnb.persistence.dto.ApprovedDTO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class ApprovedDAO {
    private final SqlSessionFactory sqlSessionFactory;

    public ApprovedDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    // 승인시키는거.. 대기 큐에서 딜리트하고 하면 될 것 같은데?
    public void insertApproved(ApprovedDTO insertApprovedDTO) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.insert("mapper.ApprovedMapper.insertApproved", insertApprovedDTO);
            session.commit();
        }
    }

    public void deleteApprovedByHouseId(int houseId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.delete("mapper.ApprovedMapper.deleteApproved", houseId);
            session.commit();
        }
    }
}
