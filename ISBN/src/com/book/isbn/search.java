package com.book.isbn;

import com.book.isbn.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class search extends Activity {

	private Button bt1;  // 바코드로 파싱하기 버튼
	private String info;
	
	/** Called when the activity is first created. */
	@TargetApi(Build.VERSION_CODES.DONUT) @Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    // TODO Auto-generated method stub
	    bt1=(Button)findViewById(R.id.button1);
	    
	    bt1.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				IntentIntegrator.initiateScan(search.this); // 바코드 스캔 실행 
			}
			
	    });
	    		
	}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // 바코드 리더 이후 결과 처리하는 메소드
    	//super.onActivityResult(requestCode, resultCode, data);
    	if(RESULT_OK == resultCode){
    		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
    	    if (scanResult != null) { // 결과가 있다면
    	      // handle scan result
    	    	TextView scanResultText = (TextView) findViewById(R.id.edt1); 
    	    	String text = scanResult.getContents();
    	    	scanResultText.setText(text);
    	    	// 바코드 스캔 결과를 edt1에 받아옴
    	    	Intent intent = new Intent(search.this, NaverBook.class);// 넘겨줄 Intent 생성 , 파싱을 위한 NaverBook.class로 이동
    	    	Bundle data1 = new Bundle(); // 번들도 생성
    	    	data1.putString("key", text.toString()); // key라는 키워드로 IBSN값을 넘겨줌
    	    	intent.putExtras(data1); // Intent로 값 넘겨줌
    	    	startActivity(intent); // NaverShopping 실행
    	    	
    	    }
    	}    		
    }

}
