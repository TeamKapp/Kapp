package lib;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by devnote on 15. 9. 22.
 */
public class htmlparser {
    String UrlString;
    Source sc;
    public htmlparser(String UrlString){
        this.UrlString = UrlString;

    }

    public void htmlload() throws IOException {this.sc = new Source(new URL(UrlString));}

    public String htmlcut(String tag, int num){


        String parsed; //String 형태의 output
        Element table; //파싱해온 데이터 저장 Element

        sc.fullSequentialParse(); //웹에서 모든 문자열을 뽑아옴
                table = sc.getAllElements(tag).get(num); //tag = HTMLElementName.DIV
        // num번째 <table> 태그 안의 내용을 table Element로 가져옴
        parsed = table.getTextExtractor().toString();

        try {
            parsed = new String(parsed.getBytes(sc.getEncoding()),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return parsed;
    }


}
