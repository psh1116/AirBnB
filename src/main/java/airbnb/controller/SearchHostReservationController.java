package airbnb.controller;

import airbnb.network.MyIOStream;
import airbnb.network.Protocol;
import airbnb.persistence.MyBatisConnectionFactory;
import airbnb.persistence.dao.HouseDAO;
import airbnb.persistence.dao.ReservationDAO;
import airbnb.persistence.dto.HouseDTO;
import airbnb.persistence.dto.ReservationDTO;
import airbnb.persistence.dto.UserDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchHostReservationController {
    Protocol protocol;
    Protocol returnProtocol;

    MyIOStream myIOStream;

    public SearchHostReservationController(Protocol protocol, MyIOStream myIOStream) {
        this.protocol = protocol;
        this.myIOStream = myIOStream;
    }

    public void sendHouseByHostId() throws IOException {
        System.out.println("HOST - 등록한 숙소 목록 요청");
        List<HouseDTO> list;
        HouseDAO houseDAO = new HouseDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        UserDTO userDTO = (UserDTO) protocol.getObject();

        list = houseDAO.getApprovedHouseSetFeePolicyByHostId(userDTO.getUserId());

        returnProtocol = new Protocol(Protocol.TYPE_VIEW_MY_HOUSE, Protocol.CODE_SUCCESS, list);
        myIOStream.oos.writeObject(returnProtocol);

        System.out.println("\tHOST - 등록한 숙소 목록 전달");
    }

    public void sendReservationByHouseId() throws IOException {
        System.out.println("HOST - 숙소 예약 목록 요청");

        List<ReservationDTO> list;
        ReservationDAO reservationDAO = new ReservationDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        HouseDTO houseDTO = (HouseDTO) protocol.getObject();

        list = reservationDAO.getReservationByHouseId(houseDTO.getHouseId());
        returnProtocol = new Protocol(Protocol.TYPE_VIEW_MY_HOUSE, Protocol.CODE_SUCCESS, list);
        myIOStream.oos.writeObject(returnProtocol);
        System.out.println("\tHOST - 숙소 예약 목록 전달");
    }
}
