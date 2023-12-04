package airbnb.controller;

import airbnb.exception.InvalidIdException;
import airbnb.exception.InvalidPwdException;
import airbnb.network.MyIOStream;
import airbnb.network.Protocol;
import airbnb.persistence.MyBatisConnectionFactory;
import airbnb.persistence.dao.LoginDAO;
import airbnb.persistence.dto.LoginDTO;
import airbnb.persistence.dto.UserDTO;

import java.io.IOException;

public class LoginController {
    Protocol protocol;

    MyIOStream myIOStream;

    public LoginController(Protocol protocol, MyIOStream myIOStream) {
        this.protocol = protocol;
        this.myIOStream = myIOStream;
    }

    public void login() throws IOException {
        System.out.println("로그인 요청");
        LoginDTO loginDTO = (LoginDTO) protocol.getObject();
        LoginDAO loginDAO = new LoginDAO(MyBatisConnectionFactory.getSqlSessionFactory());


        try {
            UserDTO userDTO = loginDAO.login(loginDTO);
            protocol.setProtocolCode(Protocol.CODE_SUCCESS);
            protocol.setObject(userDTO);
            myIOStream.oos.writeObject(protocol);
            System.out.println("로그인 승인");
        } catch (InvalidIdException iie) {
            protocol.setProtocolCode(Protocol.CODE_ERROR);
            protocol.setObject(iie.getMessage());
            myIOStream.oos.writeObject(protocol);
            System.out.println("로그인 거절");
        } catch (InvalidPwdException ipe) {
            protocol.setProtocolCode(Protocol.CODE_ERROR);
            protocol.setObject(ipe.getMessage());
            myIOStream.oos.writeObject(protocol);
            System.out.println("로그인 거절");
        }
    }
}
