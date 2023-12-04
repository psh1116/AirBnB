package org.example;

public class AdminHandler {
    public void run(){

    }

    void all() {
        System.out.println("1. 숙소 등록 신청 목록 - 승인 / 거절");
        System.out.println("1. 미승인 숙박 시설 목록"); // 선택시 미승인 숙박 시설 리스트업
        // 리스트업 숙박 시설 중 선택하면 "승인" "거절" 중 선택하고 메시지 출력, "뒤로가기" 있어야함.
        System.out.println("~~님의 숙소 등록 신청이 승인되었습니다.");
        System.out.println("~~님의 숙소 등록 신청이 거절되었습니다.");
        System.out.println("2. 거절된 숙박 시설 목록"); // 선택시 거절된 숙박 시설 리스트업
        // 리스트업 숙박 시설 중 선택하면 "복구 및 승인", "뒤로가기"



        System.out.println("2. 숙소별 월별 예약 현황");
        // 선택하면 등록된 숙소들이 리스트업 되고 숙소를 선택하면 그 숙소에 대한 예약 현황을 달력으로 출력 calender() 참고
        System.out.println("---등록된 숙박 시설 목록---");
        // 리스트업
        System.out.println("확인할 년, 월을 입력하시오 : ");



        System.out.println("3. 숙소별 월별 총 매출");
        System.out.println("---등록된 숙박 시설 목록---");
        // 리스트업
        System.out.println("확인할 년, 월을 입력하시오 : ");
    }
}
