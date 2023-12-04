package airbnb.persistence.dao;

import airbnb.persistence.dto.AmenitiesDTO;
import airbnb.persistence.dto.HouseDTO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class AmenitiesDAO {
    private final SqlSessionFactory sqlSessionFactory;

    public AmenitiesDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<AmenitiesDTO> getAll() {
        List<AmenitiesDTO> list;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.AmenitiesMapper.getAll");
        }

        return list;
    }

    public List<AmenitiesDTO> getOption() {
        List<AmenitiesDTO> list;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.AmenitiesMapper.getOption");
        }

        return list;
    }
    public List<AmenitiesDTO> getBasicAmenities() {
        List<AmenitiesDTO> list;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.AmenitiesMapper.getBasicAmenities");
        }

        return list;
    }

    public List<AmenitiesDTO> getSafetyAmenities() {
        List<AmenitiesDTO> list;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.AmenitiesMapper.getSafetyAmenities");
        }

        return list;
    }

    public List<AmenitiesDTO> getAccessAmenities() {
        List<AmenitiesDTO> list;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.AmenitiesMapper.getAccessAmenities");
        }

        return list;
    }

    public List<AmenitiesDTO> getAmenitiesByHouseId(int houseId) {
        List<AmenitiesDTO> list;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.AmenitiesMapper.getAmenitiesByHouseId", houseId);
        }

        return list;
    }

    public void insertAmenities(AmenitiesDTO amenitiesDTO) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.insert("mapper.AmenitiesMapper.insertAmenities", amenitiesDTO);
            session.commit();
        }
    }

    public void incrementAmenitiesCount(List<String> amenities) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.selectList("mapper.HouseMapper.incrementAmenitiesCount", amenities);
        }
    }

    public List<AmenitiesDTO> getAmenitiesByMostCount() {
        List<AmenitiesDTO> list;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.AmenitiesMapper.getAmenitiesByMostCount");
        }
        return list;
    }

}
