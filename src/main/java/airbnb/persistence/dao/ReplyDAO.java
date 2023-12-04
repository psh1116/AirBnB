package airbnb.persistence.dao;

import airbnb.exception.ExistReivewException;
import airbnb.persistence.dto.ReplyDTO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class ReplyDAO {
    private final SqlSessionFactory sqlSessionFactory;

    public ReplyDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<ReplyDTO> getAll() {
        List<ReplyDTO> list;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.ReplyMapper.getAll");
        }

        return list;
    }

    // 예약 번호에 따른 대댓글
    public ReplyDTO getReplyWithReservationId(int reservationId) {
        ReplyDTO replyDTO;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            replyDTO = session.selectOne("mapper.ReplyMapper.getReplyWithReservationId", reservationId);
        }

        return replyDTO;
    }

    // 대댓글 중복 확인 후, 대댓글 작성
    public void insert(ReplyDTO replyDTO) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            ReplyDTO check = session.selectOne("mapper.ReplyMapper.getReplyWithReservationId", replyDTO.getReservationId());
            if (check == null) {
                session.insert("mapper.ReplyMapper.insert", replyDTO);
                session.commit();
            } else {
                throw new ExistReivewException("Exist Reply !");
            }
        }
    }
}
