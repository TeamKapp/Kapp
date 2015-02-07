package lib;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Exception;import java.lang.String;import java.lang.StringBuffer;import java.lang.Thread;import java.net.HttpURLConnection;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class kongjilib extends Thread {
	static String mainaddress;
    static String menuaddress = "http://www.kongjugo.cnehs.kr/boardCnts/list.do?";
	static String html;
	public static String parsed[][];
    String addtoaddress;

	public kongjilib(String addr) {

		mainaddress = addr + "main.do?in=in";
        menuaddress = addr + "boardCnts/list.do?";
	}

	public void run() {
		autosetAddress();
	}

	protected String loadhtml(String Url) {
		StringBuffer Html = new StringBuffer();// 파싱 이전 Html 문서
		try {
			URL url = new URL(Url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if (conn != null) {
				conn.setConnectTimeout(5000);
				conn.setUseCaches(false);

				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					BufferedReader br = new BufferedReader(
							new InputStreamReader(conn.getInputStream()));

					for (;;) {
						String line = br.readLine();

						if (line == null) {
							break;
						}
						Html.append(line + '\n');// Html에 문서내용을 추가.
					}
					br.close();
				}
				conn.disconnect();
			}
			// Html 불러오기 완료
			return Html.toString();
		} catch (Exception 로드가앙대) {
			return null;
		}
	}

	void autosetAddress() {//사이트 주소가 바뀌었을 경우를 대비
        String source = loadhtml(mainaddress);
        StringBuffer sb = new StringBuffer();
        StringBuffer sc = new StringBuffer();
        sb.append(source.split("\'\\>\\<span\\>공지사항")[0]);
        sc.append(sb.toString().split("열린광장")[1]);
        sb.delete(0,sb.length());
        sb.append(sc.toString().split("\\<li\\>\\<a href\\=\'")[1]);
        addtoaddress = sb.toString();
	}


    void kongjimenu(){
        String source = loadhtml(menuaddress+addtoaddress+"&page=");
        StringBuffer sb = new StringBuffer();
        StringBuffer sc = new StringBuffer();
        String[][] parsed = new String[10][3];
        sb.append(source.split("title\\=\'작성일\'")[1]);
        for(int i = 1; i<=10; i++){
            sc.delete(0, sc.length());
            sc.append(sb.toString().split("title\\=\"")[i]);}
        sb.delete(0,sb.length());

    }


	/*
	 * 급식 파싱하기 일주일치 식단
	 */

	protected static void Mealparseweek() {// 입력값:html문서,
											// 요일값,
											// 출력메뉴(1:날짜,
											// 2:인원,
											// 3:급식),
											// 출력값:급식메뉴[요일값]
		String[][] parsed = new String[8][3];// 파싱 완료된 것
		String[] tokens = html.split("\\#\\$\\!");
		int max = 22;
		// 이 for문이 오류투성이다.
		for (int i = 1, j = 0; i <= max; i++) {
			parsed[j][0] = tokens[i];// i에 해당하는 토큰 받아오기
			j++;
			parsed[j][1] = tokens[i];// i에 해당하는 토큰 받아오기
			j++;
			parsed[j][2] = tokens[i];// i에 해당하는 토큰 받아오기
			j++;
		}
		kongjilib.parsed = parsed;

	}

	protected static void Mealparsemth() {// 입력값:html문서, 요일값,
											// 출력메뉴(1:날짜,
											// 2:인원, 3:급식),
											// 출력값:급식메뉴[요일값]

		StringTokenizer st = new StringTokenizer(html, "\\#\\$\\!");

		String[][] parsed = new String[3][32];
		StringBuffer sb = new StringBuffer();
		Pattern even = Pattern.compile("\\[석식\\]", Pattern.CASE_INSENSITIVE);
		Pattern afte = Pattern.compile("\\[중식\\]", Pattern.CASE_INSENSITIVE);
		Pattern morn = Pattern.compile("\\[조식\\]", Pattern.CASE_INSENSITIVE);
		Matcher regexMatcher;

		for (int i = 0; i <= 2; i++)
			st.nextToken();

		
		
		for (int i = 1; i < 32; i++) {

			sb.delete(0, sb.length());
			regexMatcher = even.matcher(sb.append(st.nextToken()).toString());
			int j = sb.length();
			
			if (regexMatcher.find()){
				
				parsed[2][i] = sb.toString().substring(regexMatcher.end(), j);
				j = regexMatcher.start();
			}
			
			else{
			parsed[2][i] = "급식이 없는 듯 합니다.";
			}

			regexMatcher.reset();
			regexMatcher = afte.matcher(sb.toString());
			if (regexMatcher.find()){
				
				parsed[1][i] = sb.toString().substring(regexMatcher.end(), j);
				j = regexMatcher.start();
				
			}
			else{
			parsed[1][i] = "급식이 없는 듯 합니다.";
			}
			
			regexMatcher.reset();
			regexMatcher = morn.matcher(sb.toString());
			if (regexMatcher.find()){
				parsed[0][i] = sb.toString().substring(regexMatcher.end(), j);
			}
			else{
			parsed[0][i] = "급식이 없는 듯 합니다.";
			}

			regexMatcher.reset();
		}
		kongjilib.parsed = parsed;
		
		
	}
}