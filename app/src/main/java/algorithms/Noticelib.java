package lib;

import android.util.Log;

public class Noticelib extends Thread {
	String address="http://smalldat.tistory.com/";
	String message;
	netload nl;
	public Noticelib() {

	}

	public void run(){
		message = nl.loadhtml(gen_address());

	}

	private String gen_address(){
		String f_address;
		String tokenend="[본문끝]";
		netload nl = new netload();
		String html;
		html = nl.loadhtml(address + "entry/kapp");
		f_address = html.substring(html.indexOf(address), html.indexOf(tokenend));
		Log.v("Kongjilib",html.substring(0,f_address.indexOf("\"")));
		return html.substring(0,f_address.indexOf("\""));

	}


}