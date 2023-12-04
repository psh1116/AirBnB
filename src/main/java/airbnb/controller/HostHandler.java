package airbnb.controller;

import airbnb.network.MyIOStream;
import airbnb.network.Protocol;

import java.io.IOException;

public class HostHandler {

    private MyIOStream myIOStream;

    public HostHandler(MyIOStream myIOStream) {
        this.myIOStream = myIOStream;
    }

    public void receiveHouseRegistration(Protocol protocol) throws IOException {
        HouseRegistrationController houseRegistrationController = new HouseRegistrationController(protocol, myIOStream);
        switch (protocol.getProtocolCode()) {
            case Protocol.CODE_SEND_REGISTRATION_HOUSE_INFO:
                houseRegistrationController.houseRegistration();
                break;
            case Protocol.CODE_REQUEST_BASIC_FACILITIES_LIST:
                houseRegistrationController.getBasicAmenities();
                break;
            case Protocol.CODE_REQUEST_SAFETY_FACILITIES_LIST:
                houseRegistrationController.getSafetyAmenities();
                break;
            case Protocol.CODE_REQUEST_ACCESSIBILITY_FACILITIES_LIST:
                houseRegistrationController.getAccessAmenities();
                break;
            default:

                break;
        }
    }

    public void receiveViewMyHouse(Protocol protocol) throws IOException {
        SearchHostReservationController searchHostReservationController = new SearchHostReservationController(protocol, myIOStream);
        switch (protocol.getProtocolCode()) {
            case Protocol.CODE_REQUEST_MY_HOUSE_LIST:
                searchHostReservationController.sendHouseByHostId();
                break;
            case Protocol.CODE_REQUEST_RESERVATION_LIST:
                searchHostReservationController.sendReservationByHouseId();
                break;
            default:

                break;
        }
    }

    public void receiveSetCostPolicy(Protocol protocol) throws IOException {
        SetCostPolicyController setCostPolicyController = new SetCostPolicyController(protocol, myIOStream);
        switch (protocol.getProtocolCode()) {
            case Protocol.CODE_REQUEST_DIVISION_COST_POLICY_HOUSE_LIST:

                break;
            case Protocol.CODE_SEND_WEEKEND_WEEKDAYS_COST_POLICY:
                setCostPolicyController.insertCostPolicy();
                break;
            case Protocol.CODE_REQUEST_APPROVED_NOT_SET_FEE_POLICY_HOUSE_LIST:
                setCostPolicyController.sendNotSetFeePolicyHouseList();
                break;

            default:

                break;
        }
    }

    public void receiveSetDiscountPolicy(Protocol protocol) throws Exception {
        SetDiscountPolicyController setDiscountPolicyController = new SetDiscountPolicyController(protocol, myIOStream);
        switch (protocol.getProtocolCode()) {
            case Protocol.CODE_REQUEST_MY_HOUSE_LIST:
                setDiscountPolicyController.sendApprovedSetFeePolicyHouse();
                break;
            case Protocol.CODE_SEND_DISCOUNT_POLICY_ON_CONSECUTIVE_NIGHTS:
                setDiscountPolicyController.setDiscountPolicy();
                break;

            case Protocol.CODE_APPLY_DISCOUNT_TO_EXISTING_RESERVATIONS:

                break;
            case Protocol.CODE_SEND_DISCOUNT_ON_QUANTITY_OR_FLAT:

                break;


//            case Protocol.CODE_SEND_DISCOUNT_POLICY_ON_CONSECUTIVE_NIGHTS:
//                setDiscountPolicyController.setFeePolicy();
//                break;

            default:

                break;
        }
    }

//    public void receiveSetDiscountPolicyOnConsecutiveNights(Protocol protocol) {
//        switch (protocol.getProtocolCode()) {
//            case Protocol.CODE_SEND_DISCOUNT_POLICY_ON_CONSECUTIVE_NIGHTS:
//
//                break;
//            case Protocol.CODE_APPLY_DISCOUNT_TO_EXISTING_RESERVATIONS:
//
//                break;
//
//
//            default:
//
//                break;
//        }
//    }
//
//    public void receiveSetDiscountOnQuantityOrFlat(Protocol protocol) {
//        switch (protocol.getProtocolCode()) {
//            case Protocol.CODE_SEND_DISCOUNT_ON_QUANTITY_OR_FLAT:
//
//                break;
//
//
//            default:
//
//                break;
//        }
//    }

    public void receiveMyHouseInfoEdit(Protocol protocol) {
        switch (protocol.getProtocolCode()) {
            case Protocol.CODE_SEND_EDITED_HOUSE_INFO:

                break;


            default:

                break;
        }
    }

    public void receiveViewHouseReservation(Protocol protocol) {
        switch (protocol.getProtocolCode()) {


            default:

                break;
        }
    }

    public void receiveReservationAllowOrRefuse(Protocol protocol) throws IOException {
        ReservationAllowOrRefuseController reservationAllowOrRefuseController = new ReservationAllowOrRefuseController(protocol, myIOStream);
        switch (protocol.getProtocolCode()) {
            case Protocol.CODE_REQUEST_WAITING_FOR_RESERVATION_APPROVAL:
                reservationAllowOrRefuseController.sendWaitingForReservationApprovalList();
                break;
            case Protocol.CODE_REQUEST_APPROVE_OR_REFUSE_RESERVATION:
                reservationAllowOrRefuseController.setReservationStatus();
                break;
            default:

                break;
        }
    }

    public void receiveReviewManagement(Protocol protocol) throws IOException {
        RequestReplyController requestReplyController = new RequestReplyController(protocol, myIOStream);
        switch (protocol.getProtocolCode()) {
            case Protocol.CODE_REQUEST_NOT_REPLY_REVIEW:
                requestReplyController.sendReviewNotReply();
                break;
            case Protocol.CODE_SEND_REPLYING_TO_REVIEW:
                requestReplyController.requestReply();
                break;

            default:

                break;
        }
    }
}
