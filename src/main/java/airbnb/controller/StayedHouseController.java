package airbnb.controller;

import airbnb.network.MyIOStream;
import airbnb.network.Protocol;
import airbnb.persistence.MyBatisConnectionFactory;
import airbnb.persistence.dao.ReservationDAO;
import airbnb.persistence.dto.CompletedStayDTO;
import airbnb.persistence.dto.UserDTO;

import java.io.IOException;
import java.util.List;

public class StayedHouseController {

    Protocol protocol;
    Protocol returnProtocol;

    MyIOStream myIOStream;

    public StayedHouseController(Protocol protocol, MyIOStream myIOStream) {
        this.protocol = protocol;
        this.myIOStream = myIOStream;
    }

    public void sendStayedHouseList() throws IOException {//클라이언트로 묵었던 숙소리스트 보내는 메소드
        System.out.println("GUEST - 이용한 숙소 리스트 요청");
        List<CompletedStayDTO> list;
        ReservationDAO reservationDAO = new ReservationDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        UserDTO userDTO = (UserDTO) protocol.getObject();
        list = reservationDAO.getCompletedStayReservationByUserId(userDTO.getUserId());
        returnProtocol = new Protocol(Protocol.TYPE_STAYED_HOUSE, Protocol.CODE_SUCCESS);
        returnProtocol.setObject(list);
        myIOStream.oos.writeObject(returnProtocol);
        System.out.println("\tGUEST - 이용한 숙소 리스트 전달");
    }
}
