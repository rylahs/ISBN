package com.book.isbn;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class NaverBook extends Activity {
    /** Called when the activity is first created. */
	
	private String key1="f833142c99b2a7466f5de015afad9647";
	private ProgressDialog dialog;
	
	private NaverParser Nparser;
	// 어댑터쪽은 없어도 파싱하는데 지장 없음
	//private CustomAdapter adapter;
	
	ArrayList<ShopData> data; // Shopdata Array
	private String info;
	
	final int count=1; // nparser에 쓰이는 count
	int start=1; // nparser에 쓰이는 start
	
	private final Handler handler=new Handler(){
    	public void handleMessage(final Message msg){
    		dialog.dismiss();
    	}
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        Intent intent=getIntent(); // 검색창에 입력한 정보 = ISBN
	    Bundle myBundle = intent.getExtras(); // 번들을  이용하여 얻어옴
		info = myBundle.getString("key"); //search.java에 edittext에 입력된 검색어를 받아옴 
		
		Nparser=new NaverParser(key1); // 파싱하는데 key1은 openapi키값
		
		getNewList(info,count,start);
		
		
    }
    
	
    public void getNewList(final String inform,final int count,final int starts){
		dialog=ProgressDialog.show(this, "Loading..", "책리스트를 불러옵니다",true,false);
		new Thread(){
			public void run(){
				data=Nparser.getBookData(inform, count,starts); // 네이버 api를 이용해 정보를 얻어옴
				Intent intent1 = new Intent(NaverBook.this, ZxingTest.class);//Intent를 이용해 ZxingTest에 보냄
				for(int i = 0; i < data.size(); i++) {
					intent1.putExtra("titlesrc", Nparser.data.get(i).title);
					intent1.putExtra("imagesrc", Nparser.data.get(i).image);
					intent1.putExtra("authorsrc", Nparser.data.get(i).author);
					intent1.putExtra("pricesrc", Nparser.data.get(i).price);
					intent1.putExtra("publishersrc", Nparser.data.get(i).publisher);
				}
				
				startActivity(intent1);
				handler.sendEmptyMessage(0);
			}
		}.start();
	}
    
}