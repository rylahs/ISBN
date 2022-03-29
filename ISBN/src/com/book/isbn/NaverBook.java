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
	// ��������� ��� �Ľ��ϴµ� ���� ����
	//private CustomAdapter adapter;
	
	ArrayList<ShopData> data; // Shopdata Array
	private String info;
	
	final int count=1; // nparser�� ���̴� count
	int start=1; // nparser�� ���̴� start
	
	private final Handler handler=new Handler(){
    	public void handleMessage(final Message msg){
    		dialog.dismiss();
    	}
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        Intent intent=getIntent(); // �˻�â�� �Է��� ���� = ISBN
	    Bundle myBundle = intent.getExtras(); // ������  �̿��Ͽ� ����
		info = myBundle.getString("key"); //search.java�� edittext�� �Էµ� �˻�� �޾ƿ� 
		
		Nparser=new NaverParser(key1); // �Ľ��ϴµ� key1�� openapiŰ��
		
		getNewList(info,count,start);
		
		
    }
    
	
    public void getNewList(final String inform,final int count,final int starts){
		dialog=ProgressDialog.show(this, "Loading..", "å����Ʈ�� �ҷ��ɴϴ�",true,false);
		new Thread(){
			public void run(){
				data=Nparser.getBookData(inform, count,starts); // ���̹� api�� �̿��� ������ ����
				Intent intent1 = new Intent(NaverBook.this, ZxingTest.class);//Intent�� �̿��� ZxingTest�� ����
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