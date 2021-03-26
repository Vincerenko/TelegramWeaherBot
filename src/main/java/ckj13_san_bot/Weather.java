package ckj13_san_bot;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

public class Weather {
	//токен подключения к сервису погоды
	private static final String API_KEY = "19f88a2991b65b36f4b237bd357f3764\r\n";
	//шаблон запроса
	private static final String REQUEST_URL = "http://api.openweathermap.org/data/2.5/weather?" +
            "lang=ru&" +
            "units=metric&" +
            "q=%s&" +
            "appid=%s";
	
	public static String getWeather(String city) throws IOException {
		//формирования строки запроса(подставляб город и токен)
		String requesUrl=String.format(REQUEST_URL,city, API_KEY);
		//извлекаю данные из ответа
		URL url = new URL(requesUrl);
		//выполняю запрос и получаю ответ
		URLConnection conn = url.openConnection();
		
		InputStream is = conn.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader reader = new BufferedReader(isr);
		
		final StringBuffer buffer = new StringBuffer();
		//строки ответа в текстовом буфере
        reader.lines().forEach(line->buffer.append(line));       
        System.out.println(buffer);
		
		System.out.println("Connect done!");
		String result = buffer.toString();
		//Десериализация из JSON -строки в обьект
		JSONObject json = new JSONObject(result);
		
		
		Map<String, Object> jsonMap = json.toMap();
		Map<String, Object> mainMap = (Map<String, Object>) jsonMap.get("main");
		ArrayList<Object> weather2 = (ArrayList<Object>) jsonMap.get("weather");
		Map<String, Object> weather3 = (Map<String, Object>) weather2.get(0);
		//Map<String, Object> weatherMap= (Map<String, Object>) jsonMap.get("weather");  
		
		//BigDecimal temp = (BigDecimal) mainMap.get("temp");
		int temp;
		if (mainMap.get("temp") instanceof BigDecimal) {
			temp = ((BigDecimal) mainMap.get("temp")).intValue();
		} else {
			temp = (Integer) mainMap.get("temp");
		}
		String plusOrMunis = (int) temp >= 1 ? "+" : "";
		
		int description1 ;
		if (mainMap.get("feels_like") instanceof BigDecimal) {
			description1 = ((BigDecimal) mainMap.get("feels_like")).intValue();
		} else {
			description1 = (Integer) mainMap.get("feels_like");
		}
		String plusOrMunis2 = (int) description1 >= 1 ? "+" : "";
		String description2;
		if (weather3.get("description") instanceof Object) {
			description2 =  (String) ((Object) weather3.get("description"));
		} else {
			description2 = (String) mainMap.get("feels_like");
		}

		city = (String) jsonMap.get("name");
		
		result = String.format("Текущая температура в городе %s: "+"%s" + " %d , \nЧувствуеться как: " + "%s , %s", city, plusOrMunis, temp, description1,description2);
		return result;
		
	}
	
}
