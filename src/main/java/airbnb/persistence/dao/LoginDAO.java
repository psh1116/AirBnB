package airbnb.persistence.dao;

import airbnb.exception.InvalidIdException;
import airbnb.exception.InvalidPwdException;
import airbnb.persistence.dto.LoginDTO;
import airbnb.persistence.dto.UserDTO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class LoginDAO {
    private final SqlSessionFactory sqlSessionFactory;

    public LoginDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public UserDTO login(LoginDTO loginDTO) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserDTO loginedUserDTO = session.selectOne("mapper.LoginMapper.login", loginDTO.getId());
            if (loginedUserDTO != null) {
                if (loginDTO.getPasswd().equals(loginedUserDTO.getLoginPwd())) {
                    return loginedUserDTO;
                } else {
                    throw new InvalidPwdException("Wrong Password"); // 비밀번호 틀림
                }
            } else {
                throw new InvalidIdException("Wrong ID"); // 아이디 틀림
            }
        }
    }
}
