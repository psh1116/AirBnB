package airbnb.controller;

import airbnb.network.MyIOStream;
import airbnb.network.Protocol;
import airbnb.persistence.MyBatisConnectionFactory;
import airbnb.persistence.dao.*;
import airbnb.persistence.dto.HouseAndFeeDTO;

import java.io.IOException;
import java.util.List;

public class SearchAllHouseController {
    Protocol protocol;
    Protocol returnProtocol;

    MyIOStream myIOStream;

    public SearchAllHouseController(Protocol protocol, MyIOStream myIOStream) {
        this.protocol = protocol;
        this.myIOStream = myIOStream;
    }

    public void sendAllHouseList() throws IOException { // 모든 숙소 목록을 보내는 메소드
        System.out.println("GUEST - 숙소 목록 요청");
        List<HouseAndFeeDTO> list;
        HouseDAO houseDAO = new HouseDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        Protocol returnProtocol;

        list = houseDAO.getApprovedSetFeePolicy();
        returnProtocol = new Protocol(Protocol.TYPE_SEARCH_ALL_HOUSE, Protocol.CODE_SUCCESS, list);
        myIOStream.oos.writeObject(returnProtocol);
        System.out.println("\tGUEST - 숙소 목록 전달");
    }

    public void sendAllHouseListASC() throws IOException {
        System.out.println("GUEST - 숙소 목록 요청 (오름차순)");
        List<HouseAndFeeDTO> list;
        HouseDAO houseDAO = new HouseDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        Protocol returnProtocol;

        list = houseDAO.getApprovedSetFeePolicyASC();
        returnProtocol = new Protocol(Protocol.TYPE_SEARCH_ALL_HOUSE, Protocol.CODE_SUCCESS, list);
        myIOStream.oos.writeObject(returnProtocol);
        System.out.println("\tGUEST - 숙소 목록 전달 (오름차순)");
    }

        public void sendAllHouseListDESC() throws IOException {
        System.out.println("GUEST - 숙소 목록 요청 (내림차순)");
        List<HouseAndFeeDTO> list;
        HouseDAO houseDAO = new HouseDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        Protocol returnProtocol;

        list = houseDAO.getApprovedSetFeePolicyDESC();
        returnProtocol = new Protocol(Protocol.TYPE_SEARCH_ALL_HOUSE, Protocol.CODE_SUCCESS, list);
        myIOStream.oos.writeObject(returnProtocol);
        System.out.println("\tGUEST - 숙소 목록 전달 (내림차순)");
    }
}

