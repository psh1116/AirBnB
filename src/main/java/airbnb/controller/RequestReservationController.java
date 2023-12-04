package airbnb.controller;
import airbnb.exception.InvalidReservationException;
import airbnb.network.MyIOStream;
import airbnb.network.Protocol;
import airbnb.persistence.MyBatisConnectionFactory;
import airbnb.persistence.dao.ReservationDAO;
import airbnb.persistence.dto.ReservationDTO;

import java.io.IOException;

public class RequestReservationController {
    Protocol protocol;
    Protocol returnProtocol;

    MyIOStream myIOStream;

    public RequestReservationController(Protocol protocol, MyIOStream myIOStream) {
        this.protocol = protocol;
        this.myIOStream = myIOStream;
    }

    public void insertReservation() throws IOException {//클라이언트로 부터 예약 정보를 받아 예약하는 메소드
        System.out.println("GUEST - 예약 신청");
        ReservationDAO reservationDAO = new ReservationDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        ReservationDTO reservationDTO = (ReservationDTO) protocol.getObject();

        try {
            reservationDAO.insert(reservationDTO);
            returnProtocol = new Protocol(Protocol.TYPE_REQUEST_RESERVATION, Protocol.CODE_SUCCESS);
            myIOStream.oos.writeObject(returnProtocol);
            System.out.println("\tGUEST - 예약 승인");
        } catch (InvalidReservationException e) {
            returnProtocol = new Protocol(Protocol.TYPE_REQUEST_RESERVATION, Protocol.CODE_ERROR, e.getMessage());
            myIOStream.oos.writeObject(returnProtocol);
            System.out.println("\tGUEST - 예약 거절");
        }
    }
}
