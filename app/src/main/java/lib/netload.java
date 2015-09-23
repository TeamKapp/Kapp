package lib;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kdve on 15. 1. 31.
 */
public class netload {
    public netload() {
    }

    public String loadhtml(String address) {// 주소
        // http://hes.cne.go.kr/sts_sci_md01_003.do?schulCode=N100000131&schulCrseScCode=4&schulKndScCode=04&schMmealScCode=1
        return loadweb(address, null);
    }

    public String loadhtml_tag(String address) {
        return loadweb(address, "</html>");
    }

    String loadweb(String address, String tag) {
        StringBuffer Html = new StringBuffer();// 파싱 이전 Html 문서
        try {
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn != null) {
                conn.setConnectTimeout(5000);
                conn.setUseCaches(false);

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));

                    for (; ; ) {
                        String line = br.readLine();

                        if (line == tag) {
                            break;
                        }
                        Html.append(line + '\n');// Html에 문서내용을 추가.
                    }
                    br.close();
                }
                conn.disconnect();
            }
            // Html 불러오기 완료
            Log.v("loadhtml completed", Html.toString());
            return Html.toString();
        } catch (Exception 로드가앙대) {
            return null;
        }
    }

    public Boolean Checknetwork(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = manager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = manager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo lte_4g = manager
                .getNetworkInfo(ConnectivityManager.TYPE_WIMAX);
        boolean blte_4g = false;
        if (lte_4g != null)
            blte_4g = (lte_4g.isConnected() && lte_4g.isAvailable());
        if ((mobile.isConnected() && mobile.isAvailable()) || (wifi.isConnected() && wifi.isAvailable()) || blte_4g)
            return true;
        else {
            return false;
        }
    }


}
