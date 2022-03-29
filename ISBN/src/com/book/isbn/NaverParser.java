package com.book.isbn;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Intent;
import android.util.Log;


public class NaverParser {
	private String key1;
	ArrayList<ShopData> data;
	
	NaverParser(String key){
		this.key1=key; // NaverShopping에서 Key값
	}
	public ArrayList<ShopData> getBookData(final String info,final int count,final int start){
		data=new ArrayList<ShopData>();
		
		ShopData item=null;
		
		String m_searchinfo=""; // 검색할 정보 = IBSN
		
		try{
			m_searchinfo=URLEncoder.encode(info, "UTF8"); // 검색정보 인코딩
		} catch(UnsupportedEncodingException e1){
			e1.printStackTrace();
		}
		
		try{
			URL text= new URL(
					"http://openapi.naver.com/search?key=" + key1 + "&query="
					+ m_searchinfo + "&display=" + count 
					+ "&start=" + start + "&target=book"); //네이버 api URL 사용
			                                               //개발자 센터 참조
			XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
			XmlPullParser parser=parserCreator.newPullParser();
			
			parser.setInput(text.openStream(),null);
			
			Log.i("NET", "Parsing...");
			
			// 읽어온 정보를 파싱하여 데이터를 만든다.
			int parseEvent = parser.getEventType();
			while(parseEvent != XmlPullParser.END_DOCUMENT){
				Intent intent1 = new Intent();
				switch(parseEvent){
				
				case XmlPullParser.START_TAG:
					String tag=parser.getName();
					// XML파싱 이용해서 각각 태그에 맞는것을 Shopdata array에 저장
					if(tag.compareTo("title")==0)
					{
						item=new ShopData();
						String titlesrc=parser.nextText();
						item.title=titlesrc;
						Log.i("NET","START...");
					}
					if(tag.compareTo("image")==0)
					{
						String imagesrc=parser.nextText();
						item.image=imagesrc;
					}
					if(tag.compareTo("author")==0)
					{
						String authorsrc=parser.nextText();
						item.author=authorsrc;
						data.add(item);
					}
					if(tag.compareTo("price")==0)
					{
						String pricesrc=parser.nextText();
						item.price=pricesrc;
					}
					if(tag.compareTo("publisher")==0)
					{
						String publishersrc=parser.nextText();
						item.publisher=publishersrc;
						data.add(item);
					}
					
					break;
				}
				parseEvent=parser.next();
				// 다음 데이터로 넘어간다.. END_DOCUMENT일때까지..
			}
			Log.i("NET","End...");
		} catch(Exception e)
		{
			Log.i("NET","Parsing fail");
		}
		
		return data;
	}
}
