package org.example;

import java.util.Calendar;

public class HostHandler {
    public void run() {

    }

    void all() {
        System.out.println("1. 숙박 등록"); ////////
        System.out.println("숙소 이름 작성 : ");
        System.out.println("숙소 소개 작성(500자 이내) : ");
        System.out.println("객실 타입 선택");
        System.out.println("1. 개인실\n" +
                           "2. 집 전체");
        System.out.println("수용 정보 작성(최대 인원 설정) : ");
        System.out.println("----편의시설 선택----\n" +
                "1.무선 인터넷 2.주방 3.세탁기 4.건조기 5.에어컨난방\n" +
                "6.업무전용 공간 7.TV 8.헤어드라이어 9.다리미 10.수영장\n" +
                "11.온수 욕조 12.무료 주차 공간 13.아기 침대 14.킹사이즈 침대 15.헬스장\n" +
                "16.바비큐 그릴 17.아침식사 18.실내 벽난로 19.흡연 가능 20.화재경보기\n" +
                "21.일산화탄소 경보기");




        System.out.println("2. 요금 정책 설정");
        System.out.println("##### 주말 / 평일 요금 설정 #####");
        System.out.println("주말 요금 : (원)");
        System.out.println("평일 요금 : (원)");



        System.out.println("3. 할인 정책 설정");

        System.out.println("1. 연박 할인 적용 기간 설정");
        System.out.println("[추가 1박당 할인 설정]\n" +
                            "1. 정액 할인\n" +
                            "2. 정률 할인");
        System.out.println("1박 당 할인 금액 설정(원) : "); // 정액 할인 시
        System.out.println("1박 당 할인 백분율 설정(%) : "); // 정률 할인 시

        System.out.println("2. 정량 / 정률 할인 설정");
        System.out.println("[평일, 주말 할인 설정]\n" +
                            "1. 정량 할인\n" +
                            "2. 정률 할인");
        System.out.println("평일 할인 금액 설정(원) : "); // 정액 할인 시
        System.out.println("주말 할인 금액 설정(원) : ");
        System.out.println("평일 할인 백분율 설정(%) : ");// 정률 할인 시
        System.out.println("주말 할인 백분율 설정(%) : ");



        System.out.println("4. 숙박 예약 현황");
        // 숙박 예약 현황 클릭시 달력 출력 되어야함. 년도와 월을 입력받는 것과 기호로 예약 유뮤 표시하는건 차후 진행하고 달력 출력은 대충 짜봄
        //calender() 실행 예시
        //Sun Mon Tue Wed Thu Fri Sat
        //             1   2   3   4
        //
        // 5   6   7   8   9  10  11
        //
        //12  13  14  15  16  17  18
        //
        //19  20  21  22  23  24  25
        //
        //26  27  28  29  30


        System.out.println("5. 예약 승인 / 거절");
        System.out.println("1. 예약 승인 대기 리스트 확인");
        System.out.println("2. 예약 거절 기록 확인");

        System.out.println("6. 리뷰 관리");
        System.out.println("1. 최근 리뷰 확인");
        System.out.println("1. 리뷰 답글 작성");
    }

    void calender(){
        // 현재 달력 인스턴스 가져옴
        Calendar calendar = Calendar.getInstance();
        // 현재 달의 첫 날 설정
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int month = calendar.get(Calendar.MONTH);
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // 달력 요일 출력
        System.out.println("Sun Mon Tue Wed Thu Fri Sat");

        // 첫 번째 날짜가 시작될 때까지 공백으로
        for (int i = 1; i < firstDayOfWeek; i++) {
            System.out.print("    ");
        }

        // 날짜 출력
        for (int i = 1; i <= daysInMonth; i++) {
            System.out.printf("%2d  ", i);
            if ((i + firstDayOfWeek - 1) % 7 == 0 || i == daysInMonth) {
                System.out.println();
                // 날짜 아래 공간 추가
                for (int j = 0; j < 7 && (i - j) > 0; j++) {
                    System.out.print("    "); // 날짜 밑의 공간
                }
                System.out.println(); // 다음 주로 이동
            }
        }
    }
}
