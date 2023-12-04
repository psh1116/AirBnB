package airbnb.controller;

import airbnb.network.MyIOStream;
import airbnb.network.Protocol;

import java.io.IOException;

public class AdminHandler {

    private MyIOStream myIOStream;

    public AdminHandler(MyIOStream myIOStream) {
        this.myIOStream = myIOStream;
    }

    public void receiveViewAccommodationRegistrationList(Protocol protocol) throws IOException { // 숙소 등록 신청 목록보기
        RequestHouseRegistrationController requestHouseRegistrationController = new RequestHouseRegistrationController(protocol, myIOStream);
        switch (protocol.getProtocolCode()) {
            case Protocol.CODE_REQUEST_ACCOMMODATION_REGISTRATION_LIST:
                requestHouseRegistrationController.sendWaitHouse();
                break;
            case Protocol.CODE_REQUEST_MORE_INFO:
                requestHouseRegistrationController.sendMoreInfo();
                break;
            case Protocol.CODE_SEND_REJECT_INFORMATION:
                requestHouseRegistrationController.refuseRegisterHouse();
                break;
            case Protocol.CODE_SEND_APPROVAL_FORMATION:
                requestHouseRegistrationController.approvedRegisterHouse();
                break;
            default:
                break;
        }
    }
}
