package lib;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class wordlib extends Thread {// 사용법: word[]와 mean[]에 각각 값을 세팅한 후
    // turnout()메소드를 실행하면 알아서 이차원배열으로 출력됨.

    protected String url = "http://m.wordbook.naver.com/endic/today/words.nhn";
    protected String[] word;
    protected String[] mean;

    public wordlib() {

    }

    public String[][] turnout() {
        String[][] outlet = new String[2][5];
        for (int i = 0; i <= 4; i++) {
            outlet[0][i] = word[i];
            outlet[1][i] = mean[i];
        }
        return outlet;

    }

    protected String[] wordparse(String mHtml) {// 입력값:html문서, 요일값, 출력메뉴(1:날짜,
        // 2:인원, 3:급식), 출력값:급식메뉴[요일값]

        StringBuffer parsing = new StringBuffer();
        int whatwhich;
        String[] tokens1 = mHtml.split("\\<em class\\=");

        String[] parsed = new String[5];

        int max = 5;
        // 이 for문이 오류투성이다.
        for (int i = 1; i <= max; i++) {

            parsing.delete(0, parsing.length());

            parsing.append(tokens1[(2*i)-1]);// i에 해당하는 토큰 받아오기

            String[] tokens2 = parsing.toString()
                    .split("\\<\\/em\\>\\<\\/a\\>");
            parsing.delete(0, parsing.length());
            parsing.append(tokens2[0]);

            whatwhich = parsing.toString().indexOf('>');
            parsing.delete(0, whatwhich + 1);
            parsed[i - 1] = parsing.toString();
        }

        return parsed;

    }

    protected String[] meanparse(String mhtml) {
        StringBuffer parsing = new StringBuffer();
        String[] tokens1 = mhtml.split("<p class=\"txt_ct2\">");

        String[] parsed = new String[5];

        int max = 5;

        for (int i = 1; i <= max; i++) {

            parsing.delete(0, parsing.length());

            parsing.append(tokens1[i]);

            String[] tokens2 = parsing.toString().split(" \\<\\/p\\>");
            parsing.delete(0, parsing.length());
            parsing.append(tokens2[0]);

			/*
			 * StringTokenizer stoken;// StringTokenizer이용
			 *
			 * stoken = new StringTokenizer(parsing.toString(), "</span>");
			 * parsing.delete(0, parsing.length());
			 *
			 * for (;;) { if (stoken.hasMoreTokens()) {
			 * parsing.append(stoken.nextToken()); } else break; } stoken = new
			 * StringTokenizer(parsing.toString(), "<span class=\"txt_col\">");
			 * parsing.delete(0, parsing.length());
			 *
			 * for (;;) { if (stoken.hasMoreTokens()) {
			 * parsing.append(stoken.nextToken()); } else break; }
			 */
            parsed[i - 1] = parsing.toString().replaceAll("</span>", "")
                    .replaceAll("<span class=\"txt_col\">", "")
                    .replaceAll("\t", "").replaceAll("\n    ", "").replaceAll("\n", "");

        }

        return parsed;
    }


}
