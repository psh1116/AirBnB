package airbnb.persistence.dao;

import airbnb.exception.ExistHouseException;
import airbnb.persistence.dto.*;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.ArrayList;
import java.util.List;

public class HouseDAO {
    private final SqlSessionFactory sqlSessionFactory;

    public HouseDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }


    // 관리자 -> 모든 숙소 조회
    public List<HouseDTO> getAll() {
        List<HouseDTO> list;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.HouseMapper.getAll");
        }

        return list;
    }

    public List<HouseDTO> getApprovedHouse() {
        List<HouseDTO> list;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.HouseMapper.getApprovedHouse");
        }

        return list;
    }

    public List<HouseDTO> getApprovedHouseSetFeePolicyByHostId(int hostId) {
        List<HouseDTO> list;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.HouseMapper.getApprovedHouseSetFeePolicyByHostId", hostId);
        }

        return list;
    }

    public List<HouseAndDiscountDTO> getApprovedHouseSetFeePolicyAndDiscountInfoByHostId(int hostId) {
        List<HouseAndDiscountDTO> list;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.HouseMapper.getApprovedHouseSetFeePolicyAndDiscountInfoByHostId", hostId);
        }

        return list;
    }

    public List<HouseAndFeeDTO> getApprovedSetFeePolicy() {
        List<HouseAndFeeDTO> list;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.HouseMapper.getApprovedSetFeePolicy");
        }
        return list;
    }

    public List<HouseAndFeeDTO> getApprovedSetFeePolicyASC() {
        List<HouseAndFeeDTO> list;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.HouseMapper.getApprovedSetFeePolicyASC");
        }
        return list;
    }

    public List<HouseAndFeeDTO> getApprovedSetFeePolicyDESC() {
        List<HouseAndFeeDTO> list;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.HouseMapper.getApprovedSetFeePolicyDESC");
        }
        return list;
    }

    // 게스트 -> 승인된 것만 조회 가능
    public List<HouseDTO> getApprovedHouseSetFeePolicy() {
        List<HouseDTO> list;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.HouseMapper.getApprovedHouseSetFeePolicy");
        }
        return list;
    }

    // HOST -> 예약현황 조회하려면 일단 getHouseByHostId 받고 -> reservation 조회 getHouseByHostId
    public List<HouseDTO> getHouseByHostId(int hostId) {
        List<HouseDTO> list;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.HouseMapper.getHouseByHostId", hostId);
        }

        return list;
    }

    // 숙소 등록 -> Waiting 에 넣어줘야함
    public void insertHouse(HouseDTO insertHouseDTO) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            HouseDTO houseDTO = session.selectOne("mapper.HouseMapper.getHouseByHouseName", insertHouseDTO.getHouseName());
            if (houseDTO == null) {
                WaitingDAO waitingDAO = new WaitingDAO(sqlSessionFactory);
                session.insert("mapper.HouseMapper.insertHouse", insertHouseDTO);
                session.commit();
                // 숙소 등록하면 숙소 승인 큐에도 넣어줘야 한다.
                waitingDAO.insertWaitingRecentHouse();
            } else
                throw new ExistHouseException("Exist Accommodation Name !");
        }
    }

    // 숙소 이름으로 조회
    public HouseDTO getHouseByName(String name) {
        HouseDTO houseDTO;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            houseDTO = session.selectOne("mapper.HouseMapper.getHouseByHouseName", name);
        }
        return houseDTO;
    }

    public List<HouseAndFeeDTO> getHouseAndFeePolicyWithFilter(FilterDTO filterDTO) {
        List<HouseAndFeeDTO> list;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.HouseMapper.getFilteredHouses", filterDTO);
        }

        return list;
    }

    // not set fee_policy
    public List<HouseDTO> getApprovedHouseNotSetFeePolicyByHostId(int hostId) {
        List<HouseDTO> list;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.HouseMapper.getApprovedHouseNotSetFeePolicyByHostId", hostId);
        }

        return list;
    }

    // 호스트 -> 등록한 거에 대한거 승인된지 안된지 확인해야함
    public List<HouseDTO> getNotApprovedHouseByHostId(int hostId) {
        List<HouseDTO> list;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.HouseMapper.getNotApprovedHouseByHostId", hostId);
        }
        return list;
    }

    // 관리자 -> 승인할지말지 선택해야하니 조회해야함
    public List<HouseDTO> getNotApprovedHouse() {
        List<HouseDTO> list;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.HouseMapper.getNotApprovedHouse");
        }

        return list;
    }

    public List<HouseAndHostDTO> getWaitingHouse() {
        List<HouseAndHostDTO> list;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.HouseMapper.getWaitingWithHostAndHouse");
        }

        return list;
    }
}
