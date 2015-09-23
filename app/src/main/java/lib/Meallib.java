package lib;

import android.util.Log;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import java.io.IOException;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Meallib extends Thread {
    static String address;
    public static String html;
    public String[][] parsed;
    public static int month;
    
    public Meallib(String addr, int month) {
        this.month = month;
        address = addr;
    }

    public void run() {
        netload nl = new netload();
        html = nl.loadhtml(address);
        parsed = mealparseauto();
        Log.i("server Check","proc"+html);

        html = nl.loadhtml_tag(address);
        Log.i("server Source",""+html);

        Pattern patt = Pattern.compile("<html", Pattern.CASE_INSENSITIVE);
        Matcher regexMatcher = patt.matcher(html);

        if(html.length()==0||regexMatcher.find()){

            try {
                parsed=Mealparsemth_html(html_st_changer());
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(parsed==null){

            parsed = new String[3][36];
            Log.i("server Error","unknown or 500");
            for (int i = 1; i<=general.yoon(month); i++){
                parsed[0][i] = "Error";
                parsed[1][i] = "Error";
                parsed[2][i] = "Error";
            }}

        }
        else {
            Log.i("server NO Error", "Go On");
            parsed = mealparseauto();
        }

    }

    static String[][] mealparseauto() {// 이거시 바로 주소만 넣으면 알아숴 주간급식인쥐 월간급식인쥐 판별
        String[] tokens = address.split("_");
        String ret = tokens[2];
        Log.v("mealparseauto Started.",ret);
        if (ret.equals("md01")) {// 주간급식
            return Mealparseweek();
        } else if (ret.equals("md00"))// 월간급식
        {
            return Mealparsemth();
        } else {// 읍다
            return null;
        }

    }
	/*
	 * 급식 파싱하기 일주일치 식단
	 */
    protected static String[][] Mealparseweek() {// 입력값:html문서,
        // 요일값,
        // 출력메뉴(1:날짜,
        // 2:인원,
        // 3:급식),
        // 출력값:급식메뉴[요일값]
        String[][] parsed = new String[8][3];// 파싱 완료된 것
        String[] tokens = html.split("\\#\\$\\!");
        int max = 22;
        
        for (int i = 1, j = 0; i <= max; i++) {
            parsed[j][0] = tokens[i];// i에 해당하는 토큰 바다오기
            j++;
            parsed[j][1] = tokens[i];// i에 해당하는 토큰 바다오기
            j++;
            parsed[j][2] = tokens[i];// i에 해당하는 토큰 바다오기
            j++;
        }
        return parsed;

    }

    protected static String[][] Mealparsemth() {
        String[][] parsed = new String[3][36];// 결과물
        String token = "#$!1";
        StringTokenizer st = new StringTokenizer(html.split("#\\$! #\\$!1")[1], "#$!");

        StringBuffer sb = new StringBuffer();// 말하는대로
        Pattern even = Pattern.compile("석식]", Pattern.CASE_INSENSITIVE);// 오오오오!류
        // 방지를
        // 위해
        Pattern afte = Pattern.compile("중식]", Pattern.CASE_INSENSITIVE);
        Pattern morn = Pattern.compile("조식]", Pattern.CASE_INSENSITIVE);



        for (int i = 1; i<=general.yoon(month); i++) {
            
            Log.v("mealparsemth", i+"");
            Matcher regexMatcher;

            sb.delete(0, sb.length());
            regexMatcher = even.matcher(sb.append(" " + (st.nextToken()))
                    .toString());
            int j = sb.length();

            if (regexMatcher.find()) {// 시간 태그가 존재하는지 확인

                if (regexMatcher.end() + 1 <= j)//지정 값이 0 이상인지 확인
                    parsed[2][i] = sb.toString().substring(
                            regexMatcher.end() + 1, j);
                
                j = regexMatcher.start();
            }

            else {
                parsed[2][i] = "급식이 읍는 듯 합니다.";
            }

            regexMatcher.reset();
            regexMatcher = afte.matcher(sb.toString());
            if (regexMatcher.find()) {

                if (regexMatcher.end() + 1 <= j)
                    parsed[1][i] = sb.toString().substring(
                            regexMatcher.end() + 1, j-1);
                j = regexMatcher.start();

            } else {
                parsed[1][i] = "급식이 읍는 듯 합니다.";
            }

            regexMatcher.reset();
            regexMatcher = morn.matcher(sb.toString());
            if (regexMatcher.find()) {
                if (regexMatcher.end() + 1 <= j)
                    parsed[0][i] = sb.toString().substring(
                            regexMatcher.end() + 1, j-1);
            } else {
                parsed[0][i] = "급식이 읍는 듯 합니다.";
            }
            regexMatcher.reset();
        }
        return parsed;

    }

    protected static String html_st_changer(){
        String[] cut = address.split("_");
        String[] tokens = cut[3].split("\\.");
        String ret = tokens[0];
        Log.v("Mealparse_html",ret);
        if(ret.equals("003")){
            Log.v("address 003","Yes");
            return cut[0]+ "_"+ cut[1]+ "_"+ cut[2]+ "_"+ "001"+ "."+ tokens[1];

        }
        else if(ret.equals("001")){
            Log.v("address 001","OK");
            return address;
        }
        else{
            Log.v("address null","앙대");
            return null;
        }

    }
    protected static String[][] Mealparsemth_html(String new_address) throws IOException {

        Pattern even = Pattern.compile("석식]", Pattern.CASE_INSENSITIVE);
        Pattern afte = Pattern.compile("중식]", Pattern.CASE_INSENSITIVE);
        Pattern morn = Pattern.compile("조식]", Pattern.CASE_INSENSITIVE);
        Matcher mat;
        String[][] parsed = new String[3][36];
        StringBuffer sb = new StringBuffer();// 말하는데로


        System.out.println(new_address);
        Source sc = new Source(new URL(new_address));
        Element table;

        sc.fullSequentialParse();
        table = sc.getAllElements(HTMLElementName.TR, 2);
        hp.htmlload();
        int input=0;

        mat = morn.matcher(hp.htmlcut(HTMLElementName.DIV, input));
        for(;;input++){
            System.out.println(hp.htmlcut(HTMLElementName.DIV, input));
            Log.v("input", ""+input);

            if(mat.find()){
                break;
            }
            else if(hp.htmlcut(HTMLElementName.DIV, input).equals("1")){
                break;
            }
            hp.htmlcut(HTMLElementName.DIV, input);
            mat = morn.matcher(sb.append(hp.htmlcut(HTMLElementName.DIV, input)));
        }


        for (int i = 1; i<=general.yoon(month); i++) {
            input++;
            Matcher regexMatcher;
            Log.v("mealparsemth", i + "");

            sb.delete(0, sb.length());
            regexMatcher = even.matcher(sb.append(hp.htmlcut(HTMLElementName.DIV, input)));
            int j = sb.length();

            if (regexMatcher.find()) {// 시간 태그가 존재하는지 확인

                if (regexMatcher.end() + 1 <= j)//지정 값이 0 이상인지 확인
                    parsed[2][i] = sb.toString().substring(
                            regexMatcher.end() + 1, j);

                j = regexMatcher.start();
            }

            else {
                parsed[2][i] = "급씩씩이가 없는 듯 합니다.";
            }

            regexMatcher.reset();
            regexMatcher = afte.matcher(sb.toString());
            if (regexMatcher.find()) {

                System.out.println(i);
                if (regexMatcher.end() + 1 <= j)
                    parsed[1][i] = sb.toString().substring(
                            regexMatcher.end() + 1, j-1);
                j = regexMatcher.start();

            } else {
                parsed[1][i] = "급식이 없는 듯 합니다.";
            }

            regexMatcher.reset();
            regexMatcher = morn.matcher(sb.toString());
            if (regexMatcher.find()) {
                if (regexMatcher.end() + 1 <= j)
                    parsed[0][i] = sb.toString().substring(
                            regexMatcher.end() + 1, j-1);
            } else {
                parsed[0][i] = "급식이 없는 듯 합니다.";
            }
            regexMatcher.reset();
        }

        return parsed;
    }
}