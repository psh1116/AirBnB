package airbnb.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class MoreHouseInfoDTO implements Serializable {
    private List<AmenitiesDTO> amenitiesDTOList;
    private FeePolicyDTO feePolicyDTO;                  // 1
    private DiscountPolicyDTO discountPolicyDTO;        // 1
    private List<ReservationDTO> reservationDTOList;    // 1
    private List<UserReviewDTO> reviewDTOList;
    private List<ReplyDTO> replyDTOList;
}
