package airbnb.controller;

import airbnb.network.MyIOStream;
import airbnb.network.Protocol;
import airbnb.persistence.MyBatisConnectionFactory;
import airbnb.persistence.dao.FeePolicyDAO;
import airbnb.persistence.dao.HouseDAO;
import airbnb.persistence.dto.FeePolicyDTO;
import airbnb.persistence.dto.HouseDTO;
import airbnb.persistence.dto.UserDTO;

import java.io.IOException;
import java.util.List;

public class SetCostPolicyController {
    Protocol protocol;
    Protocol returnProtocol;

    MyIOStream myIOStream;


    public SetCostPolicyController(Protocol protocol, MyIOStream myIOStream) {
        this.protocol = protocol;
        this.myIOStream = myIOStream;
    }

    // 승인됐지만 요금 정책 설정 안된거 리스트 보내는 메소드
    public void sendNotSetFeePolicyHouseList() throws IOException {// 클라이언트로 가격정책이 적용된 숙소, 적용되지 않은 숙소를 구분하여 숙소 리스트를 전달하는 메소드
        System.out.println("HOST - 가격 정책이 정해지지 않은 숙소 목록 요청");
        HouseDAO houseDAO = new HouseDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        List<HouseDTO> list;

        UserDTO userDTO = (UserDTO) protocol.getObject();
        list = houseDAO.getApprovedHouseNotSetFeePolicyByHostId(userDTO.getUserId());

        returnProtocol = new Protocol(Protocol.TYPE_SET_COST_POLICY, Protocol.CODE_SUCCESS, list);
        myIOStream.oos.writeObject(returnProtocol);
        System.out.println("\tHOST - 가격 정책이 정해지지 않은 숙소 목록 전달");
    }

    public void insertCostPolicy() throws IOException {// 클라이언트로 부터 받은 가격정책을 설정하는 메소드
        System.out.println("HOST - 가격 정책 설정 요청");
        FeePolicyDAO feePolicyDAO = new FeePolicyDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        FeePolicyDTO feePolicyDTO = (FeePolicyDTO) protocol.getObject();

        feePolicyDAO.insertFeePolicy(feePolicyDTO);
        returnProtocol = new Protocol(Protocol.TYPE_SET_COST_POLICY, Protocol.CODE_SUCCESS);
        myIOStream.oos.writeObject(returnProtocol);
        System.out.println("\tHOST - 가격 정책 설정 승인");
    }
}
