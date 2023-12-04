package airbnb.controller;

import airbnb.exception.ExistIdException;
import airbnb.network.MyIOStream;
import airbnb.network.Protocol;
import airbnb.persistence.MyBatisConnectionFactory;
import airbnb.persistence.dao.UserDAO;
import airbnb.persistence.dto.UserDTO;

import java.io.IOException;

public class SignController {

    Protocol protocol;

    MyIOStream myIOStream;

    public SignController(Protocol protocol, MyIOStream myIOStream) {
        this.protocol = protocol;
        this.myIOStream = myIOStream;
    }

    public void sign() throws IOException {
        System.out.println("회원가입 요청");
        UserDTO userDTO = (UserDTO) protocol.getObject();
        UserDAO userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        //회원가입
        try {
            userDAO.insertUser(userDTO);
            protocol.setProtocolCode(Protocol.CODE_SUCCESS); // 회원가입 성공
            myIOStream.oos.writeObject(protocol);
            System.out.println("\t회원가입 승인");

        } catch (ExistIdException eie) {
            protocol.setProtocolCode(Protocol.CODE_ERROR);
            protocol.setObject(eie.getMessage());
            myIOStream.oos.writeObject(protocol);
            System.out.println("\t회원가입 거절");
        }
    }
}
