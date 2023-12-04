package airbnb.persistence.dao;

import airbnb.exception.ExistIdException;
import airbnb.exception.ExistReivewException;
import airbnb.persistence.dto.ReplyDTO;
import airbnb.persistence.dto.ReviewCheckDTO;
import airbnb.persistence.dto.ReviewDTO;
import airbnb.persistence.dto.UserReviewDTO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import java.util.List;

public class ReviewDAO {
    private final SqlSessionFactory sqlSessionFactory;
    public ReviewDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void insertReview(ReviewDTO reviewDTO) {
        try(SqlSession session = sqlSessionFactory.openSession()) {
            ReviewDTO check = session.selectOne("mapper.ReviewMapper.getReviewByReservationId", reviewDTO.getReservationId());
            if (check == null) {
                int houseId = session.selectOne("mapper.ReservationMapper.getHouseIdByReservationId", reviewDTO.getReservationId());
                reviewDTO.setHouseId(houseId);
                session.insert("mapper.ReviewMapper.insert", reviewDTO);
                session.commit();
            } else {
                throw new ExistReivewException("Already exist !");
            }
        }
    }

    // 최근 리뷰 확인 -> 7일 이내 & 대댓글 작성 안한거
    public List<ReviewCheckDTO> getReviewNotReply(int hostId) {
        List<ReviewCheckDTO> list;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.ReviewMapper.getReviewNotReply", hostId);
        }

        return list;
    }
    public List<UserReviewDTO> getReviewByHouseId(int houseId) {
        List<UserReviewDTO> list;

        try(SqlSession session = sqlSessionFactory.openSession()) {
            list = session.selectList("mapper.ReviewMapper.getReviewByHouseId", houseId);
        }

        return list;
    }
}
