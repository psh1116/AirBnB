package airbnb.persistence.dao;

import airbnb.exception.ImpossibleCancelException;
import airbnb.exception.InvalidReservationException;
import airbnb.network.Status;
import airbnb.persistence.dto.CompletedStayDTO;
import airbnb.persistence.dto.HouseAndReservationDTO;
import airbnb.persistence.dto.ReservationDTO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.time.LocalDate;
import java.util.List;

public class ReservationDAO {
    private final SqlSessionFactory sqlSessionFactory;

    public ReservationDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<HouseAndReservationDTO> getWaitingReservationStatusWAITINGByHostId(int hostId) {
        List<HouseAndReservationDTO> list;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.ReservationMapper.getWaitingReservationStatusWAITINGByHostId", hostId);
        }

        return list;
    }

    public void updateCost(ReservationDTO reservationDTO) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.update("mapper.ReservationMapper.updateCost", reservationDTO);
            session.commit();
        }
    }

    public List<ReservationDTO> getAll() {
        List<ReservationDTO> list;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.ReservationMapper.getAll");
        }

        return list;
    }

    // GUEST -> get reservation list
    public List<ReservationDTO> getReservationByUserId(int userId) {
        List<ReservationDTO> list;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.ReservationMapper.getReservationByUserId", userId);
        }

        return list;
    }

    public void deleteByReservationIdByHost(ReservationDTO reservationDTO) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.delete("mapper.ReservationMapper.deleteByReservationIdByHost", reservationDTO);
            session.commit();
        }
    }

    // HOST -> 예약 현황 조회
    public List<ReservationDTO> getReservationByHouseId(int houseId) {
        List<ReservationDTO> list;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.ReservationMapper.getReservationByHouseId", houseId);
        }
        return list;
    }


    public List<ReservationDTO> getReservationWithStatusAfterStayByHouseId(int houseId) {
        List<ReservationDTO> list;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.ReservationMapper.getReservationWithStatusAfterStayByHouseId", houseId);
        }

        return list;
    }

    // 숙박 가능한지 불가능한지 확인하고 reservation 테이블에 넣어야 함

    public synchronized void insert(ReservationDTO insertReservationDTO) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            int capacity = session.selectOne("mapper.HouseMapper.getCapacity", insertReservationDTO.getHouseId());
            if (capacity < insertReservationDTO.getGuestNum()) {
                throw new InvalidReservationException("Capacity exceeded !");
            }
            Boolean check = session.selectOne("mapper.ReservationMapper.checkAvailability", insertReservationDTO);
            if (check || check == null) {
                session.insert("mapper.ReservationMapper.insert", insertReservationDTO);
                session.commit();
            } else {
                throw new InvalidReservationException("This date cannot be reserved !");
            }
        }
    }


    public List<ReservationDTO> getReservationByHouseIdWithNotRefuse(int houseId) {
        List<ReservationDTO> list;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.ReservationMapper.getReservationByHouseIdWithNotRefuse", houseId);
        }

        return list;
    }

        public List<ReservationDTO> getReservationByHouseIdWithAfterStay(int houseId) {
        List<ReservationDTO> list;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.ReservationMapper.getReservationByHouseIdWithAfterStay", houseId);
        }

        return list;
    }

    // GUEST -> 숙박 완료
    public List<CompletedStayDTO> getCompletedStayReservationByUserId(int userId) {
        List<CompletedStayDTO> list;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.ReservationMapper.getCompletedStayReservationByUserId", userId);
            for (CompletedStayDTO completedStayDTO : list) {
                completedStayDTO.setHasReview(session.selectOne("mapper.ReservationMapper.hasReview", completedStayDTO.getReservationId()));
            }
        }

        return list;
    }


    // GUEST -> 숙박 완료 이전 (예약 현황)
    public List<CompletedStayDTO> getBeforeStayReservationByUserId(int userId) {
        List<CompletedStayDTO> list;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.ReservationMapper.getBeforeStayReservationByUserId", userId);
        }

        return list;
    }

    public void deleteByReservationId(ReservationDTO reservationDTO) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            if (reservationDTO.getReservationStatus() == Status.BEFORE_STAY) {
                LocalDate current = LocalDate.now();
                LocalDate checkIn = reservationDTO.getCheckIn().toLocalDate();
                if (checkIn.isBefore(current.minusDays(3))) {
                    throw new ImpossibleCancelException("Check-in is not possible more than 3 days prior to check-in !");
                } else {
                    reservationDTO.setReservationStatus(Status.CANCEL);
                    session.update("mapper.ReservationMapper.updateReservationStatus", reservationDTO);
                    session.commit();
                    System.out.println("dwedesfvkjhdewoksfpvjgneowpfvjbngeopwfjnvbg" + reservationDTO.getReservationStatus().name());
                }
            } else {
                if (reservationDTO.getReservationStatus() == Status.AFTER_STAY)
                    throw new ImpossibleCancelException("Cancellation not possible before approval");
                else if (reservationDTO.getReservationStatus() == Status.WAIT)
                    throw new ImpossibleCancelException("Cancellation is not possible while waiting for approval !");
                else if (reservationDTO.getReservationStatus() == Status.STAY)
                    throw new ImpossibleCancelException("Cancellation is not possible during your stay !");
            }
        }
    }

    public void updateReservationStatus(ReservationDTO reservationDTO) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.update("mapper.ReservationMapper.updateReservationStatus", reservationDTO);
            session.commit();
        }
    }
}