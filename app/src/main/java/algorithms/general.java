package algorithms;

import java.util.Calendar;

/**
 * Created by FullOfOrange on 2015. 7. 13..
 */
public class general {
    static Calendar cal = Calendar.getInstance();

    public static int yoon(int month, int thisyear) {//어느 달을 입력하면 그 달이 몇일까지 있는지 출력하는 함수

        int feb;
        if ((thisyear % 4 == 0) && (thisyear % 100 != 0)
                || (thisyear % 400 == 0)) {
            feb = 29;
        } else
            feb = 28;
        switch (month) {
            case 2:
                return feb;
            case 4:
                return 30;
            case 6:
                return 30;
            case 9:
                return 30;
            case 11:
                return 30;
            default:
                return 31;
        }
    }
}
