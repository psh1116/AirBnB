package airbnb.persistence.dao;

import airbnb.persistence.MyBatisConnectionFactory;
import airbnb.persistence.dto.DiscountPolicyDTO;
import airbnb.persistence.dto.FeePolicyDTO;
import airbnb.persistence.dto.ReservationDTO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import airbnb.persistence.SaleCalculator;
import java.util.List;

public class DiscountPolicyDAO {
    private final SqlSessionFactory sqlSessionFactory;

    public DiscountPolicyDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public synchronized void insert(DiscountPolicyDTO discountPolicyDTO) throws Exception {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            DiscountPolicyDTO check = session.selectOne("mapper.DiscountPolicyMapper.getDiscountByHouseId", discountPolicyDTO.getHouseId());

            if (check == null) {    // 없으면 새로 넣음
                session.insert("mapper.DiscountPolicyMapper.insert", discountPolicyDTO);
                synchronize(discountPolicyDTO);
                session.commit();
            } else {        // 있으면 바꿈
                session.update("mapper.DiscountPolicyMapper.update", discountPolicyDTO);
                synchronize(discountPolicyDTO);
                session.commit();
            }
        }
    }

    public void synchronize(DiscountPolicyDTO discountPolicyDTO) throws Exception {
        System.out.println("할인 정책 동기화 요청");
        ReservationDAO reservationDAO = new ReservationDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        FeePolicyDAO feePolicyDAO = new FeePolicyDAO(MyBatisConnectionFactory.getSqlSessionFactory());

        List<ReservationDTO> reservationDTOS = reservationDAO.getReservationByHouseId(discountPolicyDTO.getHouseId());
        FeePolicyDTO feePolicyDTO = feePolicyDAO.getFeePolicyByHouseId(discountPolicyDTO.getHouseId());

        if (reservationDTOS != null) {
            for (ReservationDTO reservationDTO : reservationDTOS) {
                if (discountPolicyDTO.getDiscount_rate() != 0)
                    reservationDTO.setCost(SaleCalculator.CalculateRate(String.valueOf(reservationDTO.getCheckIn()), String.valueOf(reservationDTO.getCheckOut()), discountPolicyDTO, feePolicyDTO, reservationDTO.getGuestNum()));
                else
                    reservationDTO.setCost(SaleCalculator.CalculateAmount(String.valueOf(reservationDTO.getCheckIn()), String.valueOf(reservationDTO.getCheckOut()), discountPolicyDTO, feePolicyDTO, reservationDTO.getGuestNum()));
                reservationDAO.updateCost(reservationDTO);
            }
        }
        System.out.println("\t할인 정책 동기화 완료");
    }

    public DiscountPolicyDTO getDiscountPolicyByHouseId(int houseId) {
        DiscountPolicyDTO discountPolicyDTO;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            discountPolicyDTO = session.selectOne("mapper.DiscountPolicyMapper.getDiscountPolicyByHouseId", houseId);
        }
        return discountPolicyDTO;
    }
}