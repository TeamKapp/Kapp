package lib;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Meallib extends Thread {
	static String address;
	static String html;
	public static String parsed[][];

	public Meallib(String addr) {

		address = addr;
	}

	public void run() {
        netload nl = new netload();
		html = nl.loadhtml(address);
		mealparseauto();
	}



	static void mealparseauto() {// 이거시 바로 주소만 넣으면 알아서 주간급식인지 월간급식인지 판별하는
									String[] tokens = address.split("\\_");
		String ret = tokens[2];
		if (ret.equals("md01")) {// 주간급식
			Mealparseweek();
		} else if (ret.equals("md00"))// 월간급식
		{
			Mealparsemth();
		} else {// 읎다
			
		}

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
		Meallib.parsed = parsed;

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
		Meallib.parsed = parsed;
		
		
	}
}