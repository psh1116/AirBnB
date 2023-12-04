package airbnb.controller;

import airbnb.exception.ExistIdException;
import airbnb.exception.ExistReivewException;
import airbnb.network.MyIOStream;
import airbnb.network.Protocol;
import airbnb.persistence.MyBatisConnectionFactory;
import airbnb.persistence.dao.ReviewDAO;
import airbnb.persistence.dto.ReviewDTO;

import java.io.IOException;

public class SendReviewController {
    Protocol protocol;
    Protocol returnProtocol;

    MyIOStream myIOStream;

    public SendReviewController(Protocol protocol, MyIOStream myIOStream) {
        this.protocol = protocol;
        this.myIOStream = myIOStream;
    }

    public void insertReview() throws IOException {// 별점과 리뷰를 등록 하는 메소드
        System.out.println("GUEST - 리뷰 등록 요청");
        ReviewDAO reviewDAO = new ReviewDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        ReviewDTO reviewDTO = (ReviewDTO) protocol.getObject();

        try {
            reviewDAO.insertReview(reviewDTO);
            returnProtocol = new Protocol(Protocol.TYPE_SEND_REVIEW, Protocol.CODE_SUCCESS);
            myIOStream.oos.writeObject(protocol);
            System.out.println("\tGUEST - 리뷰 등록 승인");
        } catch (ExistReivewException e) {
            returnProtocol = new Protocol(Protocol.TYPE_SEND_REVIEW, Protocol.CODE_ERROR, e.getMessage());
            myIOStream.oos.writeObject(returnProtocol);
            System.out.println("\tGUEST - 리뷰 등록 거절");
        }
    }
}
