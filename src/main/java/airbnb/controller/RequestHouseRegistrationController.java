package airbnb.controller;

import airbnb.network.MyIOStream;
import airbnb.network.Protocol;
import airbnb.persistence.MyBatisConnectionFactory;
import airbnb.persistence.dao.*;
import airbnb.persistence.dto.*;

import java.io.IOException;
import java.util.List;

public class RequestHouseRegistrationController {
    Protocol protocol;
    Protocol returnProtocol;

    MyIOStream myIOStream;

    public RequestHouseRegistrationController(Protocol protocol, MyIOStream myIOStream) {
        this.protocol = protocol;
        this.myIOStream = myIOStream;
    }

    //print waiting q list
    // 숙소 등록 신청 리스트 출력
    public void sendWaitHouse() throws IOException {
        System.out.println("ADMIN - 숙소 등록 신청 목록 요청");
        List<HouseAndHostDTO> list;
        HouseDAO houseDAO = new HouseDAO(MyBatisConnectionFactory.getSqlSessionFactory());

        list = houseDAO.getWaitingHouse();
        returnProtocol = new Protocol(Protocol.TYPE_VIEW_ACCOMMODATION_REGISTRATION_LIST, Protocol.CODE_SUCCESS, list);
        myIOStream.oos.writeObject(returnProtocol);
        System.out.println("\tADMIN - 숙소 등록 신청 목록 전송");
    }

    public void sendMoreInfo() throws IOException {
        System.out.println("ADMIN - 숙소 상세 정보 요청");
        HouseDTO houseDTO = (HouseDTO) protocol.getObject();

        AmenitiesDAO amenitiesDAO = new AmenitiesDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        List<AmenitiesDTO> list = amenitiesDAO.getAmenitiesByHouseId(houseDTO.getHouseId());

        Protocol returnProtocol = new Protocol(Protocol.TYPE_VIEW_ACCOMMODATION_REGISTRATION_LIST, Protocol.CODE_SUCCESS, list);
        myIOStream.oos.writeObject(returnProtocol);

        System.out.println("\tADMIN - 숙소 상세 정보 전달");
    }

    public void refuseRegisterHouse() throws IOException {
        System.out.println("ADMIN - 숙소 거절 요청");
        HouseDTO houseDTO = (HouseDTO) protocol.getObject();
        WaitingDAO waitingDAO = new WaitingDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        ApprovedDAO approvedDAO = new ApprovedDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        waitingDAO.deleteWaitingByHouseId(houseDTO.getHouseId());
        approvedDAO.insertApproved(new ApprovedDTO(houseDTO.getHouseId(), 0));
        returnProtocol = new Protocol(Protocol.TYPE_VIEW_ACCOMMODATION_REGISTRATION_LIST, Protocol.CODE_SUCCESS);
        myIOStream.oos.writeObject(returnProtocol);
        System.out.println("\tADMIN - 숙소 거절 승인");
    }

    public void approvedRegisterHouse() throws IOException {
        System.out.println("ADMIN - 숙소 승인 요청");
        HouseDTO houseDTO = (HouseDTO) protocol.getObject();
        WaitingDAO waitingDAO = new WaitingDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        ApprovedDAO approvedDAO = new ApprovedDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        waitingDAO.deleteWaitingByHouseId(houseDTO.getHouseId());
        approvedDAO.insertApproved(new ApprovedDTO(houseDTO.getHouseId(), 1));
        returnProtocol = new Protocol(Protocol.TYPE_VIEW_ACCOMMODATION_REGISTRATION_LIST, Protocol.CODE_SUCCESS);
        myIOStream.oos.writeObject(returnProtocol);
        System.out.println("\tADMIN - 숙소 승인 승인");
    }
}
