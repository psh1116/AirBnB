package airbnb.controller;

import airbnb.network.MyIOStream;
import airbnb.network.Protocol;
import airbnb.network.Status;
import airbnb.persistence.MyBatisConnectionFactory;
import airbnb.persistence.dao.ReservationDAO;
import airbnb.persistence.dto.HouseAndReservationDTO;
import airbnb.persistence.dto.ReservationDTO;
import airbnb.persistence.dto.UserDTO;

import java.io.IOException;
import java.util.List;

public class ReservationAllowOrRefuseController {
    Protocol protocol;
    Protocol returnProtocol;

    MyIOStream myIOStream;

    public ReservationAllowOrRefuseController(Protocol protocol, MyIOStream myIOStream) {
        this.protocol = protocol;
        this.myIOStream = myIOStream;
    }

    // HOST -> 예약 승인 대기 승인 or 거절
    public void sendWaitingForReservationApprovalList() throws IOException {
        System.out.println("HOST - 예약 대기 리스트 요청");
        UserDTO userDTO = (UserDTO) protocol.getObject();

        ReservationDAO reservationDAO = new ReservationDAO(MyBatisConnectionFactory.getSqlSessionFactory());

        List<HouseAndReservationDTO> list = reservationDAO.getWaitingReservationStatusWAITINGByHostId(userDTO.getUserId());
        returnProtocol = new Protocol(Protocol.TYPE_RESERVATION_ALLOW_OR_REFUSE, Protocol.CODE_SUCCESS, list);
        myIOStream.oos.writeObject(returnProtocol);
        System.out.println("\tHOST - 예약 리스트 전송");
    }

    // HOST -> 예약 승인 or 거절 set
    public void setReservationStatus() throws IOException {
        System.out.println("HOST - 예약 승인 및 거절");
        ReservationDAO reservationDAO = new ReservationDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        ReservationDTO reservationDTO = (ReservationDTO) protocol.getObject();

        reservationDAO.updateReservationStatus(reservationDTO);
        returnProtocol = new Protocol(Protocol.TYPE_RESERVATION_ALLOW_OR_REFUSE, Protocol.CODE_SUCCESS);
        myIOStream.oos.writeObject(returnProtocol);

        if (reservationDTO.getReservationStatus() == Status.BEFORE_STAY)
            System.out.println("\tHOST - 예약 승인");
        else
            System.out.println("\tHOST - 예약 거절");
    }
}
