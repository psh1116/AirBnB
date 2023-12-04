package airbnb.controller;

import airbnb.exception.ImpossibleCancelException;
import airbnb.network.MyIOStream;
import airbnb.network.Protocol;
import airbnb.network.Status;
import airbnb.persistence.MyBatisConnectionFactory;
import airbnb.persistence.dao.ReservationDAO;
import airbnb.persistence.dto.CompletedStayDTO;
import airbnb.persistence.dto.ReservationDTO;
import airbnb.persistence.dto.UserDTO;

import java.io.IOException;
import java.util.List;

public class SearchGuestReservationController {

    Protocol protocol;
    Protocol returnProtocol;

    MyIOStream myIOStream;

    public SearchGuestReservationController(Protocol protocol, MyIOStream myIOStream) {
        this.protocol = protocol;
        this.myIOStream = myIOStream;
    }

    public void sendReservationList() throws IOException {//클라이언트로 예약 현황 리스트 보내는 메소드
        System.out.println("GUEST - 유저 숙소 예약 현황 목록 요청");
        List<CompletedStayDTO> list;
        ReservationDAO reservationDAO = new ReservationDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        UserDTO userDTO = (UserDTO) protocol.getObject();

        list = reservationDAO.getBeforeStayReservationByUserId(userDTO.getUserId());
        returnProtocol = new Protocol(Protocol.TYPE_SEARCH_RESERVATION, Protocol.CODE_SUCCESS, list);
        myIOStream.oos.writeObject(returnProtocol);

        System.out.println("\tGUEST - 유저 숙소 예약 현황 목록 전달");
    }

    public void requestReservationCancel() throws IOException {
        System.out.println("GUEST - 숙소 예약 취소 요청");
        ReservationDTO reservationDTO = (ReservationDTO) protocol.getObject();
        ReservationDAO reservationDAO = new ReservationDAO(MyBatisConnectionFactory.getSqlSessionFactory());

        try {
            reservationDAO.deleteByReservationId(reservationDTO);
            returnProtocol = new Protocol(Protocol.TYPE_SEARCH_RESERVATION, Protocol.CODE_SUCCESS);
            myIOStream.oos.writeObject(returnProtocol);
            System.out.println("GUEST - 숙소 예약 최소 승인");
        } catch (ImpossibleCancelException e) {
            returnProtocol = new Protocol(Protocol.TYPE_SEARCH_RESERVATION, Protocol.CODE_ERROR, e.getMessage());
            myIOStream.oos.writeObject(returnProtocol);
            System.out.println("GUEST - 숙소 예약 취소 거절");
        }
    }
}
