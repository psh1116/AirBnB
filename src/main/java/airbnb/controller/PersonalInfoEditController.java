package airbnb.controller;

import airbnb.exception.WrongBirthdayException;
import airbnb.exception.WrongPhoneNumberException;
import airbnb.exception.WrongUserNameException;
import airbnb.network.MyIOStream;
import airbnb.network.Protocol;
import airbnb.persistence.MyBatisConnectionFactory;
import airbnb.persistence.dao.UserDAO;
import airbnb.persistence.dto.ModifyBirthdayDTO;
import airbnb.persistence.dto.ModifyPhoneNumberDTO;
import airbnb.persistence.dto.ModifyUserNameDTO;

import java.io.IOException;

public class PersonalInfoEditController {
    Protocol protocol;
    Protocol returnProtocol;

    MyIOStream myIOStream;

    public PersonalInfoEditController(Protocol protocol, MyIOStream myIOStream) {
        this.protocol = protocol;
        this.myIOStream = myIOStream;
    }

    public void modifyUserNameInfo() throws IOException {//유저이름을 수정하는 메소드
        System.out.println("GUEST - 이름 수정 요청");
        ModifyUserNameDTO modifyUserNameDTO = (ModifyUserNameDTO) protocol.getObject();
        UserDAO userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        try {
            userDAO.updateUserName(modifyUserNameDTO);
            returnProtocol = new Protocol(Protocol.TYPE_PERSONAL_INFO_EDIT, Protocol.CODE_SUCCESS);
            myIOStream.oos.writeObject(returnProtocol);
            System.out.println("\tGUEST - 이름 수정 승인");
        } catch (WrongUserNameException une) {
            returnProtocol = new Protocol(Protocol.TYPE_PERSONAL_INFO_EDIT, Protocol.CODE_ERROR, une.getMessage());
            myIOStream.oos.writeObject(returnProtocol);
            System.out.println("\tGUEST - 이름 수정 거절");
        }
    }

    public void modifyUserPhoneNumberInfo() throws IOException {//유저전화번호를 수정하는 메소드
        System.out.println("GUEST - 전화 번호 수정 요청");
        ModifyPhoneNumberDTO modifyPhoneNumberDTO = (ModifyPhoneNumberDTO) protocol.getObject();
        if (modifyPhoneNumberDTO.getUserPhone().length() != 11) {
            returnProtocol = new Protocol(Protocol.TYPE_PERSONAL_INFO_EDIT, Protocol.CODE_ERROR, new WrongPhoneNumberException("Wrong Phone Number").getMessage());
            myIOStream.oos.writeObject(returnProtocol);
            System.out.println("\tGUEST - 전화 번호 수정 거절 -> 잘못된 전화 번호 입력");
        } else {
            UserDAO userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
            userDAO.updateUserPhone(modifyPhoneNumberDTO);
            returnProtocol = new Protocol(Protocol.TYPE_PERSONAL_INFO_EDIT, Protocol.CODE_SUCCESS);
            myIOStream.oos.writeObject(returnProtocol);
            System.out.println("\tGUEST - 전화 번호 수정 승인");
        }
    }

    public void modifyUserBirthdayInfo() throws IOException {//유저 생년월일을 수정하는 메소드
        System.out.println("GUEST - 생년 월일 수정 요청");
        ModifyBirthdayDTO modifyBirthdayDTO = (ModifyBirthdayDTO) protocol.getObject();
        UserDAO userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());

        userDAO.updateUserBirthday(modifyBirthdayDTO);
        returnProtocol = new Protocol(Protocol.TYPE_PERSONAL_INFO_EDIT, Protocol.CODE_SUCCESS);
        myIOStream.oos.writeObject(returnProtocol);
        System.out.println("\tGUEST - 생년 월일 수정 승인");
    }

}
