package airbnb.controller;

import airbnb.network.MyIOStream;
import airbnb.network.Protocol;
import airbnb.persistence.MyBatisConnectionFactory;
import airbnb.persistence.dao.AmenitiesDAO;
import airbnb.persistence.dao.HouseDAO;
import airbnb.persistence.dto.AmenitiesDTO;
import airbnb.persistence.dto.FilterDTO;
import airbnb.persistence.dto.HouseAndFeeDTO;

import java.io.IOException;
import java.util.List;

public class SearchHouseController {
    Protocol protocol;
    Protocol returnProtocol;

    MyIOStream myIOStream;

    public SearchHouseController(Protocol protocol, MyIOStream myIOStream) {
        this.protocol = protocol;
        this.myIOStream = myIOStream;
    }

    public void sendAmenities() throws IOException {
        System.out.println("GUEST - 숙소 검색 요청 (편의 시설 목록 전송)");
        List<AmenitiesDTO> list;

        AmenitiesDAO amenitiesDAO = new AmenitiesDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        list = amenitiesDAO.getOption();
        returnProtocol = new Protocol(Protocol.TYPE_REQUEST_SEARCH, Protocol.CODE_SUCCESS, list);
        myIOStream.oos.writeObject(returnProtocol);

        System.out.println("\tGUEST - 숙소 검색 승인 (편의 시설 목록 전송)");
    }

    public void sendHouseAndFeePolicyWithFilter() throws IOException{
        System.out.println("GUEST - 숙소 필터링 검색 요청");

        List<HouseAndFeeDTO> list;
        FilterDTO filterDTO = (FilterDTO) protocol.getObject();
        HouseDAO houseDAO = new HouseDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        list = houseDAO.getHouseAndFeePolicyWithFilter(filterDTO);

        returnProtocol = new Protocol(Protocol.TYPE_REQUEST_SEARCH, Protocol.CODE_SUCCESS, list);
        myIOStream.oos.writeObject(returnProtocol);

        System.out.println("GUEST - 숙소 필터링 검색 전송");
    }
}
