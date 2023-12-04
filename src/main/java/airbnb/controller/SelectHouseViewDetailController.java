package airbnb.controller;

import airbnb.network.MyIOStream;
import airbnb.network.Protocol;
import airbnb.persistence.MyBatisConnectionFactory;
import airbnb.persistence.dao.*;
import airbnb.persistence.dto.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class SelectHouseViewDetailController {
    Protocol protocol;

MyIOStream myIOStream;

    public SelectHouseViewDetailController(Protocol protocol, MyIOStream myIOStream) {
        this.protocol = protocol;
        this.myIOStream = myIOStream;
    }

    public void sendMoreHouseInfo() throws IOException {
        System.out.println("GUEST - 숙소 상세 정보 요청");
        MoreHouseInfoDTO moreHouseInfoDTO = new MoreHouseInfoDTO();
        HouseDTO houseDTO = (HouseDTO) protocol.getObject();

        AmenitiesDAO amenitiesDAO = new AmenitiesDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        FeePolicyDAO feePolicyDAO = new FeePolicyDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        ReservationDAO reservationDAO = new ReservationDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        ReviewDAO reviewDAO = new ReviewDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        DiscountPolicyDAO discountPolicyDAO = new DiscountPolicyDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        ReplyDAO replyDAO = new ReplyDAO(MyBatisConnectionFactory.getSqlSessionFactory());

        moreHouseInfoDTO.setDiscountPolicyDTO(discountPolicyDAO.getDiscountPolicyByHouseId(houseDTO.getHouseId()));
        moreHouseInfoDTO.setAmenitiesDTOList(amenitiesDAO.getAmenitiesByHouseId(houseDTO.getHouseId()));
        FeePolicyDTO temp = feePolicyDAO.getFeePolicyByHouseId(houseDTO.getHouseId());
        moreHouseInfoDTO.setFeePolicyDTO(temp);
        moreHouseInfoDTO.setReservationDTOList(reservationDAO.getReservationByHouseId(houseDTO.getHouseId()));
        moreHouseInfoDTO.setReviewDTOList(reviewDAO.getReviewByHouseId(houseDTO.getHouseId()));

        List<ReplyDTO> replyDTOList = new ArrayList<>();

        for (ReservationDTO reservationDTO : moreHouseInfoDTO.getReservationDTOList()) {
            ReplyDTO replyDTO =  replyDAO.getReplyWithReservationId(reservationDTO.getReservationId());
            if (replyDTO != null)
                replyDTOList.add(replyDTO);
        }

        moreHouseInfoDTO.setReplyDTOList(replyDTOList);
        Protocol returnProtocol = new Protocol(Protocol.TYPE_SELECT_HOUSE_VIEW_DETAIL, Protocol.CODE_SEND_SELECT_HOUSE_INFO, moreHouseInfoDTO);

        returnProtocol.setObject(moreHouseInfoDTO);
        myIOStream.oos.writeObject(returnProtocol);
        System.out.println("\tGUEST - 숙소 상세 정보 전달");
    }

    public void receiveReservationRequest() {

    }
}
