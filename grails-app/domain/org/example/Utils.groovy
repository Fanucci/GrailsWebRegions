package org.example;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;




public class Utils {

	
	public static String timeZone(String region) {
	      if(region.contains("Калининг")||region.contains("Крым"))
          {
             return "-3";
          }
	      if(region.contains("дмурт")||region.contains("Самар")){
	    	  return "-1";
	      }
	      if(region.contains("ашкорт")||region.contains("Перм")||region.contains("Курган")||region.contains("Орен")||region.contains("Тюмен")||region.contains("Ямал")||region.contains("Хант")||region.contains("Челяб")||region.contains("Екат")||region.contains("Свердл")){
	    	  return "0";
	      }
	      if(region.contains("Алтай")||region.contains("Новосиб")||region.contains("Омск")||region.contains("Томск")){
	    	  return "1";
	      }
	      if(region.contains("Тыва")||region.contains("Хакас")||region.contains("Краснояр")||region.contains("Кемер")){
	    	  return "2";
	      }
	      if(region.contains("Бурят")||region.contains("Забай")||region.contains("Иркут")){
	    	  return "3";
	      }
	      if(region.contains("Амур")||region.contains("Чит")){
	    	  return "4";
	      }
	      if(region.contains("Примор")||region.contains("Хабар")||region.contains("Магад")||region.contains("Еврей")){
	    	  return "5";
	      }
	      if(region.contains("Саха")||region.contains("Якут")||region.contains("Сахалин")){
	    	  return "6";
	      }
	      if(region.contains("Камчат")||region.contains("Чукот")){
	    	  return "7";
	      }
	      else return "-2";
	}
	public String getSearchString(String num) {
		String f3 = num.substring(1,4);
		String l7 = num.substring(4,num.length());
		String l1 = num.substring(4,7);
		String l2 = num.substring(7,9);
		String l3 = num.substring(9,11);
		
		
		String a[] = new String[9];
		a[0] = num;
		a[1] = "8"+f3+l7;
		a[2] = "7+"+f3+"+"+l7;
		a[3] = "8+"+f3+"+"+l7;
		a[4] = "7+"+f3+"+"+l1+"+"+l2+"+"+l3;
		a[5] = "8+"+f3+"+"+l1+"+"+l2+"+"+l3;
		a[6] = "7."+f3+l7;
		a[7] = "8("+f3+")"+l7;
		a[8] = "("+f3+")"+l7;
		
		
		String ya = "";
		String nig = "";
		for(int i=0;i<a.length;i++) {
			if(ya == "") ya += "%22"+a[i]+"%22"; else ya += "+%7C+%22"+a[i]+"%22";
//			if(google == "") google += "\""+a[i]+"\""; else google += " OR \""+a[i]+"\"";
			if(nig == "") nig += "%22"+a[i]+"%22"; else nig += "+%7C+%22"+a[i]+"%22";
		}
		return ya;
	}
		public static String numbers(String num) {
			String ref1 = "";//"http://yandex.ru/yandsearch?text="+getSearchString(num);
		//	String ref2 = "http://www.nigma.ru/index.php?s="+nig;
		return ref1;
		
	}
		public static String callHistory(String num){
		
			return "http://192.168.67.25/cc-line24/reports.php/calls/out?date[start]=31.12.2013+00%3A00&date[end]=31.12.2017+23%3A50&phone=&calleeid="+num+"&callstatus=&uniqueid=&paginator[page]=0+%D1%81%D1%82%D1%80.&sort[by]=date&sort[dir]=asc";
		
		}
		
		private static String readUrl(String urlString) throws Exception {
		    BufferedReader reader = null;
		    
		    try {
		        URL url = new URL(urlString);
		        reader = new BufferedReader(new InputStreamReader(url.openStream()));
		        StringBuffer buffer = new StringBuffer();
		        int read;
		        char[] chars = new char[1024];
		        while ((read = reader.read(chars)) != -1)
		            buffer.append(chars, 0, read); 

		        return buffer.toString();
		    } finally {
		        if (reader != null)
		            reader.close();
		    }
		}
		
		public static String checkIP(String num){
			String title = null;
			    try {
			        JSONObject json = new JSONObject(readUrl("http://ipinfo.io/"+num+"/json"));
			        
			        title= (String) json.get("org");
			        title= title.substring(title.indexOf(" "),title.length() - 1);
			        title = title + " - " + (String) json.get("city");
			    } catch (Exception e) {
			        e.printStackTrace();
			    }
			
			    return title;
		}		
		
		public static  Map<String, String> googleNumber(String num) throws UnsupportedEncodingException, IOException{
			String search = num;
			String google = "http://www.google.com/search?q=";
			String charset = "UTF-8";
			String userAgent = "ExampleBot 1.0 (+http://example.com/bot)"; // Change this to your company's name and bot homepage!
			 Map<String, String> hashmap = new HashMap<String, String>();
			// System.out.println(search);

			Elements links = Jsoup.connect(google + URLEncoder.encode(search, charset)).userAgent(userAgent).get().select("h3>a");

			for (Element link : links) {
			    String title = link.text();
			    String url = link.absUrl("href"); // Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
			  //  System.out.println("in");
			    
			    url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");
			 //   System.out.println("out");
			    if (!url.startsWith("http")) {
			        continue; // Ads/news/etc.
			    }
			    if (!checkSpam(url)){
			    System.out.println("Title: " + title);
			    System.out.println("URL: " + url);
			    hashmap.put(title, url);
			    }
			    
			 if (hashmap.size()>5)break;
			}
			return hashmap;
		}
		
		public static boolean checkSpam(String url){
			ArrayList<String> list = new ArrayList<String>();
			list.add("kodtelefona");
			list.add("roum");
			list.add("wiki-numbers");
			list.add("phonenum.ru");
			list.add("ktozvonit.ru");
			list.add("base4all.ru");
			list.add("wzcorp.ru");
			list.add("vtbase.ru");
			list.add("numberconnection");
			list.add("tel-base.ru");
			list.add("fdzp.cn");
			list.add("zvonil.ru");
			list.add("moperator.ru");
			list.add("mobile09.ru");
			list.add("chi-chiama.it");
			list.add("numberphone.ru");
			list.add("phone8.ru");
			list.add("mobilnomera.ru");
			list.add("numerierrati");
			list.add("bs9.eu");
			list.add("base2.ru");
			list.add("spravochnik09");
			list.add("rozshuk.ru");
			list.add("codephone.ru");
			list.add("fiber.ee");
			list.add("10c.ru");
			list.add("m09base.ru");
			list.add("vtradein.ru");
			list.add("numbersaplenty");
			list.add("nomerbeeline");
			list.add("numberabon.com");
			list.add("nomerzvonka");
			list.add("mtsnums.ru");
			list.add("ismser.ru");
			list.add("meganums.ru");
			list.add("cheynomer.ru");
			list.add("beenums");
			list.add("w-gsm.com");
			list.add("nomeru-telefona");
			list.add("telephonico");
			list.add("bases/number");
			list.add("infophon");
			list.add("pronomera");
			list.add("each-number");
			list.add("notacall");
			list.add("mightynumber");
			list.add("indiatelephone");
			list.add("waslorio");
			list.add("carleton");
			list.add("kto-mne-zvonil");
			list.add("0419job");
			list.add("prozvoni");
			list.add("poisk-mobile");
			list.add("helpalias");
			list.add("chei-nomer");
			list.add("80ajudebeif");
			list.add("whoiszone");
			list.add("tumblr");
			list.add("ktomnezvonit");
			list.add("germanytel");
			list.add("b1aga0aegq");
			list.add("numberinfo");
			list.add("w-karma");
			list.add("keyworddensitycheckertool");
			list.add("poisk356");
			list.add("tel-names");
			list.add("chinmm");
			list.add("qare");
			list.add("makeafaces");
			list.add("nomer-telefona");
			list.add("w-gsm");
			list.add("nomermegafon");
			list.add("5511qq");
			list.add("mts4u");
			list.add("xysjwj");
			list.add("nomermts");
			list.add("cejnomer");
			list.add("whophonedme");
			list.add("trickynumber");
			list.add("phone.yapl");
			list.add("womedot");
			list.add("chinwm");
			list.add("womasder");
			list.add("axprojects");
			list.add("telru2");
			list.add("kreedz");
			list.add("otkudazvonok");
			list.add("online09");
			list.add("chejnomer");
			list.add("axprojects");
			list.add("totnomer");
			list.add("wiki-karma");
			list.add("russia-phones");
			list.add("posrednikovsdes");
			list.add("elitelight");
			list.add("spravinfo");
			list.add("sync.me");
			list.add("chinnn");
			list.add("88.208.17.120");
			list.add("juchek");
			list.add("info-nomer");
			list.add("numberphone");
			list.add("naiti");
			list.add("numberphone");
			list.add("chinxx");
			list.add("drinkpunk");
			list.add("calameo");
			list.add("tel-spravka");
			list.add("comicshero");
			list.add("moneta.co.kr");
			list.add("numbers.ba-za");
			list.add("ktozvonit");
			list.add("zvonki.octo");
			list.add("allnomer");
			list.add("allnum");
			list.add("regionoperator");
			list.add("andriod");
			list.add("nomermega");
			list.add("algebra");
			list.add("mawords");
			list.add("bazatelefonov");
			list.add("nocaller");
			list.add("chinsm");
			list.add("nomermega");
			list.add("nomermega");
			
			
			
			for(String i:list) if(url.contains(i))return true;
			return false;
		}
		
		
		
		 public static void main(String[] args) throws UnsupportedEncodingException, IOException {
			 System.out.println(checkIP("46.17.201.68"));
		 }
}
	