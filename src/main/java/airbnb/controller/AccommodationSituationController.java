package airbnb.controller;

import airbnb.network.MyIOStream;
import airbnb.network.Protocol;
import airbnb.persistence.MyBatisConnectionFactory;
import airbnb.persistence.dao.*;
import airbnb.persistence.dto.HouseAndHostDTO;
import airbnb.persistence.dto.HouseDTO;
import airbnb.persistence.dto.MoreHouseInfoDTO;
import airbnb.persistence.dto.ReservationDTO;

import java.io.IOException;
import java.util.List;

public class AccommodationSituationController {
    Protocol protocol;
    Protocol returnProtocol;

    MyIOStream myIOStream;

    public AccommodationSituationController(Protocol protocol, MyIOStream myIOStream) {
        this.protocol = protocol;
        this.myIOStream = myIOStream;
    }

    public void sendApprovedHouseList() throws IOException {
        System.out.println("ADMIN - 숙소 (승인&요금 설정) 목록 요청");
        List<HouseDTO> list;
        HouseDAO houseDAO = new HouseDAO(MyBatisConnectionFactory.getSqlSessionFactory());

        list = houseDAO.getApprovedHouseSetFeePolicy();
        returnProtocol = new Protocol(Protocol.TYPE_VIEW_ACCOMMODATION_REGISTRATION_LIST, Protocol.CODE_SUCCESS, list);
        myIOStream.oos.writeObject(returnProtocol);
        System.out.println("\tADMIN - 숙소 (승인&요금 설정) 목록 전송");
    }

    public void sendReservationByHouseId() throws IOException {
        System.out.println("ADMIN - 선택된 숙소 예약 목록 요청");
        MoreHouseInfoDTO moreHouseInfoDTO = new MoreHouseInfoDTO();
        HouseDTO houseDTO = (HouseDTO) protocol.getObject();

        FeePolicyDAO feePolicyDAO = new FeePolicyDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        DiscountPolicyDAO discountPolicyDAO = new DiscountPolicyDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        ReservationDAO reservationDAO = new ReservationDAO(MyBatisConnectionFactory.getSqlSessionFactory());

        moreHouseInfoDTO.setFeePolicyDTO(feePolicyDAO.getFeePolicyByHouseId(houseDTO.getHouseId()));
        moreHouseInfoDTO.setReservationDTOList(reservationDAO.getReservationByHouseIdWithNotRefuse(houseDTO.getHouseId()));
        moreHouseInfoDTO.setDiscountPolicyDTO(discountPolicyDAO.getDiscountPolicyByHouseId(houseDTO.getHouseId()));

        returnProtocol = new Protocol(Protocol.TYPE_MONTHLY_RESERVATION_STATUS_FOR_ACCOMMODATION, Protocol.CODE_SUCCESS, moreHouseInfoDTO);
        myIOStream.oos.writeObject(returnProtocol);
        System.out.println("\tADMIN - 선택된 숙소 예약 목록 전송");
    }

    public void sendAfterStayReservationByHouseId() throws IOException {
        System.out.println("ADMIN - 선택된 숙소의 완료된 예약 목록 요청");
        MoreHouseInfoDTO moreHouseInfoDTO = new MoreHouseInfoDTO();
        HouseDTO houseDTO = (HouseDTO) protocol.getObject();

        FeePolicyDAO feePolicyDAO = new FeePolicyDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        DiscountPolicyDAO discountPolicyDAO = new DiscountPolicyDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        ReservationDAO reservationDAO = new ReservationDAO(MyBatisConnectionFactory.getSqlSessionFactory());

        moreHouseInfoDTO.setFeePolicyDTO(feePolicyDAO.getFeePolicyByHouseId(houseDTO.getHouseId()));
        moreHouseInfoDTO.setReservationDTOList(reservationDAO.getReservationByHouseIdWithAfterStay(houseDTO.getHouseId()));
        moreHouseInfoDTO.setDiscountPolicyDTO(discountPolicyDAO.getDiscountPolicyByHouseId(houseDTO.getHouseId()));

        returnProtocol = new Protocol(Protocol.TYPE_MONTHLY_RESERVATION_STATUS_FOR_ACCOMMODATION, Protocol.CODE_SUCCESS, moreHouseInfoDTO);
        myIOStream.oos.writeObject(returnProtocol);
        System.out.println("\tADMIN - 선택된 숙소의 완료된 예약 목록 전송");
    }
}
