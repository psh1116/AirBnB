package airbnb.persistence.dao;

import airbnb.exception.ExistIdException;
import airbnb.exception.WrongUserNameException;
import airbnb.persistence.dto.ModifyBirthdayDTO;
import airbnb.persistence.dto.ModifyPhoneNumberDTO;
import airbnb.persistence.dto.ModifyUserNameDTO;
import airbnb.persistence.dto.UserDTO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class UserDAO {
    private final SqlSessionFactory sqlSessionFactory;

    public UserDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<UserDTO> getAll() {
        List<UserDTO> list;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.UserMapper.getAll");
        }
        return list;
    }

    public void updateUser(UserDTO updateDTO) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.update("mapper.UserMapper.userUpdate", updateDTO);
            session.commit();
        }
    }

    public void insertUser(UserDTO insertUserDTO) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserDTO userDTO = session.selectOne("mapper.UserMapper.searchId", insertUserDTO.getLoginId());
            int count = session.selectOne("mapper.UserMapper.getUserNumByUserPhone", insertUserDTO.getUserPhone());
            if (userDTO == null && count <= 3) {
                session.insert("mapper.UserMapper.insertUser", insertUserDTO);
                session.commit();
            } else {
                if (count > 3) {
                    throw new ExistIdException("You can create up to 3 accounts per phone number !");
                } else {
                    throw new ExistIdException("Already Exist Id !");
                }
            }
        }
    }

    // 개인정보 조회할 때 (마이페이지) 호출해서 사용하면 됨
    public UserDTO getUserByUserId(int userId) {
        UserDTO userDTO;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            userDTO = session.selectOne("mapper.UserMapper.getUserByUserId", userId);
        }
        return userDTO;
    }

    public void updateUserBirthday(ModifyBirthdayDTO modifyBirthdayDTO) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.update("mapper.UserMapper.updateUserBirthday", modifyBirthdayDTO);
            session.commit();
        }
    }


    public void updateUserPhone(ModifyPhoneNumberDTO modifyPhoneNumberDTO) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.update("mapper.UserMapper.updateUserPhone", modifyPhoneNumberDTO);
            session.commit();
        }
    }

    public void updateUserName(ModifyUserNameDTO modifyUserNameDTO) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserDTO userDTO = session.selectOne("mapper.UserMapper.getUserByUserName");
            if (userDTO == null) {
                session.update("mapper.UserMapper.updateUserName", modifyUserNameDTO);
                session.commit();
            } else {
                throw new WrongUserNameException("Wrong User Name");
            }
        }
    }
}