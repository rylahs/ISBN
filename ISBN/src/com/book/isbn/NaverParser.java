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
		this.key1=key; // NaverShopping���� Key��
	}
	public ArrayList<ShopData> getBookData(final String info,final int count,final int start){
		data=new ArrayList<ShopData>();
		
		ShopData item=null;
		
		String m_searchinfo=""; // �˻��� ���� = IBSN
		
		try{
			m_searchinfo=URLEncoder.encode(info, "UTF8"); // �˻����� ���ڵ�
		} catch(UnsupportedEncodingException e1){
			e1.printStackTrace();
		}
		
		try{
			URL text= new URL(
					"http://openapi.naver.com/search?key=" + key1 + "&query="
					+ m_searchinfo + "&display=" + count 
					+ "&start=" + start + "&target=book"); //���̹� api URL ���
			                                               //������ ���� ����
			XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
			XmlPullParser parser=parserCreator.newPullParser();
			
			parser.setInput(text.openStream(),null);
			
			Log.i("NET", "Parsing...");
			
			// �о�� ������ �Ľ��Ͽ� �����͸� �����.
			int parseEvent = parser.getEventType();
			while(parseEvent != XmlPullParser.END_DOCUMENT){
				Intent intent1 = new Intent();
				switch(parseEvent){
				
				case XmlPullParser.START_TAG:
					String tag=parser.getName();
					// XML�Ľ� �̿��ؼ� ���� �±׿� �´°��� Shopdata array�� ����
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
				// ���� �����ͷ� �Ѿ��.. END_DOCUMENT�϶�����..
			}
			Log.i("NET","End...");
		} catch(Exception e)
		{
			Log.i("NET","Parsing fail");
		}
		
		return data;
	}
}
