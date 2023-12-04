package airbnb.controller;

import airbnb.network.MyIOStream;
import airbnb.network.Protocol;

import java.io.IOException;

public class GuestHandler {

    private MyIOStream myIOStream;

    public GuestHandler(MyIOStream myIOStream) {
        this.myIOStream = myIOStream;
    }

    public void receiveLoginRequestType(Protocol protocol) throws IOException, ClassNotFoundException { // 로그인 request 를 받았을 때 실행
        LoginController loginController = new LoginController(protocol, myIOStream);
        switch (protocol.getProtocolCode()) {
            case Protocol.CODE_LOGIN_REQUEST:
                loginController.login();
                break;
            default:
                System.out.println("알 수 없는 코드: ");
                // 예외 담아서 보내?
                break;
        }
    }

    public void receiveSignType(Protocol protocol) throws IOException {
        SignController signController = new SignController(protocol, myIOStream);

        switch (protocol.getProtocolCode()) {
            case Protocol.CODE_SEND_SIGN_UP_INFO:
                signController.sign();
                break;
            default:
                System.out.println("알수 없는 코드");
                // 예외 담아서 보내?
                break;
        }
    }

    public void receivePersonalInfoEditType(Protocol protocol) throws IOException {
        PersonalInfoEditController personalInfoEditController = new PersonalInfoEditController(protocol, myIOStream);
        switch (protocol.getProtocolCode()) {
            case Protocol.CODE_SEND_MODIFY_NAME_INFO:
                personalInfoEditController.modifyUserNameInfo();
                break;
            case Protocol.CODE_SEND_MODIFY_PHONENUMBER_INFO:
                personalInfoEditController.modifyUserPhoneNumberInfo();
                break;
            case Protocol.CODE_SEND_MODIFY_BIRTHDAY_INFO:
                personalInfoEditController.modifyUserBirthdayInfo();
                break;
            default:

                break;
        }
    }

    public void receiveSearchReservationType(Protocol protocol) throws IOException {
        SearchGuestReservationController searchReservationController = new SearchGuestReservationController(protocol, myIOStream);
        switch (protocol.getProtocolCode()) {
            case Protocol.CODE_MY_RESERVATION_REQUEST:
                searchReservationController.sendReservationList();
                break;
            case Protocol.CODE_REQUEST_RESERVATION_CANCELLATION:
                searchReservationController.requestReservationCancel();
                break;
            default:

                break;
        }
    }

    public void receiveWrittenReviewType(Protocol protocol) throws IOException {
        WrittenReviewController writtenReviewController = new WrittenReviewController(protocol);
        switch (protocol.getProtocolCode()) {
            case Protocol.CODE_WRITTEN_REVIEW_REQUEST:
                writtenReviewController.sendWrittenReviewList();

                break;

            case Protocol.CODE_SEND_MODIFY_REVIEW:
                //writtenReviewController.modifyReview();
                break;
            default:

                break;
        }
    }

    public void receiveStayedHouseType(Protocol protocol) throws IOException {
        StayedHouseController stayedHouseController = new StayedHouseController(protocol, myIOStream);
        switch (protocol.getProtocolCode()) {
            case Protocol.CODE_STAYED_HOUSE_LIST_REQUEST:
                stayedHouseController.sendStayedHouseList();
                break;

            default:

                break;
        }
    }

    public void receiveSendReviewType(Protocol protocol) throws IOException {
        SendReviewController sendReviewController = new SendReviewController(protocol, myIOStream);
        switch (protocol.getProtocolCode()) {
            case Protocol.CODE_SEND_REVIEW:
                sendReviewController.insertReview();
                break;

            default:

                break;
        }
    }

    public void receiveSearchAllHouseType(Protocol protocol) throws IOException {
        SearchAllHouseController searchAllHouseController = new SearchAllHouseController(protocol, myIOStream);
        switch (protocol.getProtocolCode()) {
            case Protocol.CODE_SEARCH_ALL_HOUSE_REQUEST:
                searchAllHouseController.sendAllHouseList();
                break;

            case Protocol.CODE_SEARCH_ALL_HOUSE_REQUEST_ASC:    // 오름차순
                searchAllHouseController.sendAllHouseListASC();
                break;

            case Protocol.CODE_SEARCH_ALL_HOUSE_REQUEST_DESC:// 내림차순
                searchAllHouseController.sendAllHouseListDESC();
                break;

            default:

                break;
        }
    }

    public void receiveSelectHouseViewDetailType(Protocol protocol) throws IOException {
        SelectHouseViewDetailController selectHouseViewDetailController = new SelectHouseViewDetailController(protocol, myIOStream);
        switch (protocol.getProtocolCode()) {
            case Protocol.CODE_SELECT_HOUSE_INFO_REQUEST:
                selectHouseViewDetailController.sendMoreHouseInfo();
                break;
            default:
                break;
        }
    }

    public void receiveRequestReservationType(Protocol protocol) throws IOException {
        RequestReservationController requestReservationController = new RequestReservationController(protocol, myIOStream);
        switch (protocol.getProtocolCode()) {
            case Protocol.CODE_SEND_RESERVATION_INFO:
                requestReservationController.insertReservation();
                break;
            default:

                break;
        }
    }

    public void receiveFilterType(Protocol protocol) throws IOException {
        FilterController filterController = new FilterController();
        switch (protocol.getProtocolCode()) {
            case Protocol.CODE_SEND_SELECT_FILTER:
                filterController.sendFilteredHouseList();
                break;

            default:

                break;
        }
    }

    public void receiveSearchHouse(Protocol protocol) throws IOException {
        SearchHouseController searchHouseController = new SearchHouseController(protocol, myIOStream);
        switch (protocol.getProtocolCode()) {
            case Protocol.CODE_REQUEST_SEARCH_WITH_FILTER:
                searchHouseController.sendHouseAndFeePolicyWithFilter();
                break;
            default:

                break;
        }
    }
}
