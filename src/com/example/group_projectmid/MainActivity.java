package com.example.group_projectmid;

import com.group.mid.ScanActivity;
import com.main.fragment.fragment_login;
import com.main.fragment.fragment_logout;
import com.widget.radialmenu.semicircularmenu.SemiCircularRadialMenu;
import com.widget.radialmenu.semicircularmenu.SemiCircularRadialMenuItem;
import com.widget.radialmenu.semicircularmenu.SemiCircularRadialMenuItem.OnSemiCircularRadialMenuPressed;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity{
	
	private SemiCircularRadialMenu mMenu;
	private SemiCircularRadialMenuItem mRegistration, mRollCall, mInfo, mAttendance, mBroadcast;
	private Button login_button, logout_button;
	private fragment_login frLogin;
	private fragment_logout frLogout;
	private String username,password;
	private FragmentTransaction fragmentTransaction;
	//調用seat_Listener.getIndex 可以得到座位選擇 0是一般座位 1是亂數座位
	private ChoiceOnClickListener seat_Listener= new ChoiceOnClickListener(1);
	////////////////////////////////////////////////////////////////////////////////////////
	private boolean isTeacher = true;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		

		
		
		
		iniFragmentView();
		iniSemiCircularRadialMenu();
		
	}
	
	public void iniFragmentView(){
		login_button = (Button) findViewById(R.id.button_login);
		logout_button = (Button) findViewById(R.id.button_logout);
		logout_button.setEnabled(false);
		login_button.setOnClickListener(login_listener);
		logout_button.setOnClickListener(logout_listener);
		
		frLogin = new fragment_login();
		
		fragmentTransaction = getFragmentManager().beginTransaction();
		fragmentTransaction.add(R.id.frameLay, frLogin);
		fragmentTransaction.commit();
	}
	
	public void iniSemiCircularRadialMenu() {
		mRegistration = new SemiCircularRadialMenuItem("Registration", getResources().getDrawable(R.drawable.ic_registration), "簽到");
		mRollCall = new SemiCircularRadialMenuItem("RollCall", getResources().getDrawable(R.drawable.ic_rollcall), "點名");
		mInfo = new SemiCircularRadialMenuItem("info", getResources().getDrawable(R.drawable.ic_info), "Info");
		mAttendance = new SemiCircularRadialMenuItem("Attendance", getResources().getDrawable(R.drawable.ic_attendence), "出席率");
		mBroadcast = new SemiCircularRadialMenuItem("Broadcast", getResources().getDrawable(R.drawable.ic_broadcast), "推播");
		
		mMenu = (SemiCircularRadialMenu) findViewById(R.id.radial_menu);
		mMenu.addMenuItem(mBroadcast.getMenuID(), mBroadcast);
		mMenu.addMenuItem(mInfo.getMenuID(), mInfo);
		mMenu.addMenuItem(mRegistration.getMenuID(), mRegistration);
		mMenu.addMenuItem(mRollCall.getMenuID(), mRollCall);
		mMenu.addMenuItem(mAttendance.getMenuID(), mAttendance);
		
	}
	
	public void SemiCircularRadialItem_setPressed(){//變換Activity在這邊
		
		if(isTeacher){//老師所有功能都能做
			//簽到
			mRegistration.setOnSemiCircularRadialMenuPressed(new OnSemiCircularRadialMenuPressed() {
				@Override
				public void onMenuItemPressed() {
					showDialog();
					//Toast.makeText(MainActivity.this, mRegistration.getText(), Toast.LENGTH_SHORT).show();
				}
			});
			
			mRollCall.setOnSemiCircularRadialMenuPressed(new OnSemiCircularRadialMenuPressed() {
				@Override
				public void onMenuItemPressed() {
					Toast.makeText(MainActivity.this, mRollCall.getText(), Toast.LENGTH_SHORT).show();
				}
			});
			
			mInfo.setOnSemiCircularRadialMenuPressed(new OnSemiCircularRadialMenuPressed() {
				@Override
				public void onMenuItemPressed() {
					Toast.makeText(MainActivity.this, mInfo.getText(), Toast.LENGTH_SHORT).show();
				}
			});
			
			mAttendance.setOnSemiCircularRadialMenuPressed(new OnSemiCircularRadialMenuPressed() {
				@Override
				public void onMenuItemPressed() {
					Toast.makeText(MainActivity.this, mAttendance.getText(), Toast.LENGTH_SHORT).show();
				}
			});
			
			mBroadcast.setOnSemiCircularRadialMenuPressed(new OnSemiCircularRadialMenuPressed() {
				@Override
				public void onMenuItemPressed() {
					Toast.makeText(MainActivity.this, mBroadcast.getText(), Toast.LENGTH_SHORT).show();
					mMenu.dismissMenu();
				}
			});
		}
		else{//學生只有資訊 出席率 推播
			//改Icon
			mRegistration.reset_Drawble(getResources().getDrawable(R.drawable.ic_registration_eneditabled));
			mRollCall.reset_Drawble(getResources().getDrawable(R.drawable.ic_rollcall_eneditabled));
			
			mInfo.setOnSemiCircularRadialMenuPressed(new OnSemiCircularRadialMenuPressed() {
				@Override
				public void onMenuItemPressed() {
					Toast.makeText(MainActivity.this, mInfo.getText(), Toast.LENGTH_SHORT).show();
				}
			});
			
			mAttendance.setOnSemiCircularRadialMenuPressed(new OnSemiCircularRadialMenuPressed() {
				@Override
				public void onMenuItemPressed() {
					Toast.makeText(MainActivity.this, mAttendance.getText(), Toast.LENGTH_SHORT).show();
				}
			});
			
			mBroadcast.setOnSemiCircularRadialMenuPressed(new OnSemiCircularRadialMenuPressed() {
				@Override
				public void onMenuItemPressed() {
					Toast.makeText(MainActivity.this, mBroadcast.getText(), Toast.LENGTH_SHORT).show();
					mMenu.dismissMenu();
				}
			});
		}
	}
	public boolean isTeacher(){
		return isTeacher;
	}
	
	private Button.OnClickListener login_listener = new Button.OnClickListener() {
		public void onClick(View v) {
			frLogout = new fragment_logout();
			EditText login_username = (EditText) findViewById(R.id.username_login);
			EditText login_password = (EditText) findViewById(R.id.password_login);
			username = login_username.getText().toString();
			password = login_password.getText().toString();
			//檢查資料庫 看帳密是否吻合且判斷是學生或老師
			//
			//
			//
			//符合 元件SemiCircularRadialMenu is unclocked
			mMenu.set_unLocked();
			logout_button.setEnabled(true);
			login_button.setEnabled(false);
			
			//判斷完學生 老師身份後 選擇註冊和重新修改部分itemIcon
			SemiCircularRadialItem_setPressed();
			
			
			fragmentTransaction = getFragmentManager().beginTransaction();
			Bundle args = new Bundle();
			args.putString("username", username);
			frLogout.setArguments(args);
			
			fragmentTransaction.replace(R.id.frameLay, frLogout);
			fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
		}
	};
	
	private Button.OnClickListener logout_listener = new Button.OnClickListener() {
		public void onClick(View v) {
			if(mMenu.isOpened()){
				//Toast.makeText(MainActivity.this, "不能回去", Toast.LENGTH_SHORT).show();
			}
			else {
				mMenu.set_Locked();
				logout_button.setEnabled(false);
				login_button.setEnabled(true);
				
				fragmentTransaction = getFragmentManager().beginTransaction();
				fragmentTransaction.remove(frLogout);
				fragmentTransaction.add(R.id.frameLay, frLogin);
				fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
			}		
		}
	};
	
	private void showDialog() {	
		String[] seat_kind = new String[]{"一般座位表","亂數座位表"}; 	
		AlertDialog.Builder MyAlertDialog = new AlertDialog.Builder(this);
		MyAlertDialog.setTitle(R.string.seat_string);
		MyAlertDialog.setIcon(android.R.drawable.ic_dialog_info);
		MyAlertDialog.setSingleChoiceItems(seat_kind, 0, seat_Listener);
		MyAlertDialog.setNegativeButton("確定", seat_Listener);
		MyAlertDialog.setPositiveButton("取消", seat_Listener);
		MyAlertDialog.create().show();			
	}
	
	private class ChoiceOnClickListener implements DialogInterface.OnClickListener {  
		//選擇亂數或一般座位DialogListener
		
        private int index;//選擇0是一般座位 1是亂數座位
        public ChoiceOnClickListener(int index){
        	this.index = index;
        }
        
        @Override  
        public void onClick(DialogInterface dialogInterface, int which) {  
            
        	
        	
            if(which >= 0){
            	index = which;
            }
            else {
				if(which == DialogInterface.BUTTON_NEGATIVE){
					//跳Activity
					Bundle bundle = new Bundle();
					Intent intent = new Intent();
					switch (index) {
					case 1:
						
			    		bundle.putBoolean("isRandom", false);
			    		intent.putExtras(bundle);
			    		intent.setClass(MainActivity.this, ScanActivity.class);
			    		startActivity(intent);
						break;
					case 2:
						
			    		bundle.putBoolean("isRandom", true);
			    		intent.putExtras(bundle);
			    		intent.setClass(MainActivity.this, ScanActivity.class);
			    		startActivity(intent);
						break;	

					default:
						Toast.makeText(MainActivity.this, "Impossible!", Toast.LENGTH_SHORT).show();
						break;
					}
					
				}
				else if(which == DialogInterface.BUTTON_POSITIVE){
					dialogInterface.dismiss();
				}
			}
        }  
          
        public int getIndex() {  
            return index;  
        }  
    }  

}
