package airbnb.controller;
import airbnb.network.MyIOStream;
import airbnb.network.Protocol;
import airbnb.persistence.MyBatisConnectionFactory;
import airbnb.persistence.dao.HouseDAO;
import airbnb.persistence.dao.ReservationDAO;
import airbnb.persistence.dto.HouseDTO;
import airbnb.persistence.dto.ReservationDTO;

import java.io.IOException;
import java.util.List;

public class MonthlyReservationStatusForAccommodationController {

    Protocol protocol;
    Protocol returnProtocol;

    MyIOStream myIOStream;

    public MonthlyReservationStatusForAccommodationController(Protocol protocol, MyIOStream myIOStream) {
        this.protocol = protocol;
        this.myIOStream = myIOStream;
    }

    // 숙소 아이디로 예약 현황 조회 및 수익 조회
    // 숙소 아이디로 완료된 예약 리스트 반환
    public void sendReservation() throws IOException {
        System.out.println("ADMIN - 숙소 예약 리스트 요청 (총 매출 계산 위함)");
        HouseDTO houseDTO = (HouseDTO) protocol.getObject();
        ReservationDAO reservationDAO = new ReservationDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        List<ReservationDTO> list;

        list = reservationDAO.getReservationWithStatusAfterStayByHouseId(houseDTO.getHouseId());

        returnProtocol = new Protocol(Protocol.TYPE_MONTHLY_RESERVATION_STATUS_FOR_ACCOMMODATION, Protocol.CODE_SUCCESS, list);
        myIOStream.oos.writeObject(returnProtocol);
        System.out.println("\tADMIN - 숙소 예약 리스트 전달 (총 매출 계산 위함)");

    }

    // 승인되었고 요금 정책 설정된 모든 숙소 목록
    public void sendAllHouse() throws IOException {
        System.out.println("ADMIN - 승인 & 요금 정책 설정된 숙소 목록 요청");
        HouseDAO houseDAO = new HouseDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        List<HouseDTO> list = houseDAO.getApprovedHouseSetFeePolicy();

        returnProtocol = new Protocol(Protocol.TYPE_MONTHLY_RESERVATION_STATUS_FOR_ACCOMMODATION, Protocol.CODE_SUCCESS, list);
        myIOStream.oos.writeObject(returnProtocol);
        System.out.println("\tADMIN - 승인 & 요금 정책 설정된 숙소 목록 전달");
    }
}
