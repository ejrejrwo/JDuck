package com.example.grblu;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener {

	static final int REQUEST_ENABLE_BT = 10;
	int mPairedDeviceCount = 0;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private TextView valueView, valueView1;
	private View mTop, mBottom, mLeft, mRight;
	BluetoothAdapter mBluetoothAdapter;
	BluetoothDevice mRemoteDevice;
	BluetoothSocket mSocket = null;
	OutputStream mOutputStream = null;
	InputStream mInputStream = null;
	String mStrDelimiter = "\n";
	char mCharDelimiter = '\n';
	Thread mWorkerThread = null;
	Set<BluetoothDevice> mDevices;
	String message;
	private Button mButtonBu, mButtonLi, mButtonST;
	int F = 0;

	@Override
	protected void onDestroy() {
		try {
			mWorkerThread.interrupt(); // 데이터 수신 쓰레드 종료
			mInputStream.close(); // 입력 스트림 닫음
			mOutputStream.close(); // 출력 스트림 닫음
			mSocket.close(); // 소켓 닫음
		} catch (Exception e) {
		}
		super.onDestroy(); // 어플리케이션이 종료될 때 호출되는 함수(스트림과 소켓을 닫음)
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		valueView = (TextView) findViewById(R.id.values);
		mTop = findViewById(R.id.top);
		mBottom = findViewById(R.id.bottom);
		mLeft = findViewById(R.id.left);
		mRight = findViewById(R.id.right);
		mButtonBu = (Button) findViewById(R.id.Bubutton);
		mButtonLi = (Button) findViewById(R.id.Libutton);
		mButtonST = (Button) findViewById(R.id.STbutton);
		valueView1 = (TextView) findViewById(R.id.values1);
		mButtonBu.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// 버튼을 터치했을경우 부저가 울리도록 하는 부저 터치리스너
				switch (arg1.getAction()) {
				// 버튼을 다운(누르는동안)시키면 아두이이노에서 부저가 울리도록 구현할때 필요한 "7"이라는 문자를 생성
				case MotionEvent.ACTION_DOWN:
					message = "7";

					break;
				// 버튼을 다운(누르는동안)시키면 아두이이노에서 부저가 그만 울리도록 구현할때 필요한 "8"이라는 문자를 생성
				case MotionEvent.ACTION_UP:
					message = "8";

					break;

				}
				try {
					// 터치리스너로 발생한 메세지를 블루투스를 이용하여 전송
					mOutputStream.write(message.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
				return false;
			}
		});
		mButtonLi.setOnClickListener(new OnClickListener() {
			// 버튼을 클릭시 LED가 점등되도록 하는 클릭리스너
			@Override
			public void onClick(View view) {
				// 버틀을 클릭할 경우 아두이노에서 LED가 점등되도록 구현할때 필요한 "6"이라는 문자를 생성
				String message = "6";
				try {
					// 클릭리스너로 발생한 메세지를 블루투스를 이용하여 전송
					mOutputStream.write(message.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});

		mButtonST.setOnClickListener(new OnClickListener() {
			// 버튼을 클릭시 정지가 되도록 하는 클릭리스너
			@Override
			// 버튼을 클릭시 아두이노에서 정지하도록 구현할때 필요한 "5"라는 문자를 생성
			public void onClick(View view) {

				String message = "5";
				// 클릭리스너로 발생한 메세지를 블루투스를 이용하여 전송
				try {
					mOutputStream.write(message.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
	}

	@Override
	protected void onResume() {
		// 가속도 센서 리스너 오브젝트를 등록
		super.onResume();
		mSensorManager.registerListener(this, mAccelerometer,
				SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	protected void onPause() {
		// 가속도 센서 리스너 오브젝트를 등록해제
		super.onPause();
		mSensorManager.unregisterListener(this);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// 가속도 값이 변경될때마다 호출됨
		float[] values = event.values;
		float x = values[0];
		float y = values[1];
		int scaleFactor;

		if (x > 4.5) {
			// X값이 4.5 초과일 경우 아두이노에서 후진동작을 구현할때 필요한 "3"이라는 문자를 생성
			mBottom.setBackgroundResource(com.example.grblu.R.drawable.down);
			// 화살표 방향이 아래로 향하는 이미지를 불러옴
			valueView1.setText("3");
			String message = "3";
			try {
				// 읽어온 센서값으로 발생한 메세지를 블루투스를 이용하여 전송
				mOutputStream.write(message.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (x > 3.5 && x < 4.5) {
			// X값이 3.5초과 4.5미만 일 때 아두이노에서 정지동작을 구현할때 필요한 "5"라는 문자를 생성
			valueView1.setText("5");
			// 검정색 화면 이미지를 불러옴
			mTop.setBackgroundResource(com.example.grblu.R.drawable.black);
			mBottom.setBackgroundResource(com.example.grblu.R.drawable.black);
			mLeft.setBackgroundResource(com.example.grblu.R.drawable.black);
			mRight.setBackgroundResource(com.example.grblu.R.drawable.black);
			String message = "5";
			try {
				// 읽어온 센서값으로 발생한 메세지를 블루투스를 이용하여 전송
				mOutputStream.write(message.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else if (x < -4.5) {
			// X값이 -4.5미만일 경우 아두이노에서 전진동작을 구현할때 필요한 "1"이라는 문자를 생성
			mTop.setBackgroundResource(com.example.grblu.R.drawable.up);
			// 화살표 방향이 위로 향하는 이미지를 불러옴
			valueView1.setText("1");
			String message = "1";
			try {
				// 읽어온 센서값으로 발생한 메세지를 블루투스를 이용하여 전송
				mOutputStream.write(message.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (x < -3.5 && x > -4.5) {
			// X값이 -3.5미만 -4.5초과 일 때 아두이노에서 정지동작을 구현할때 필요한 "5"라는 문자를 생성
			valueView1.setText("5");
			// 검정색 화면 이미지를 불러옴
			mTop.setBackgroundResource(com.example.grblu.R.drawable.black);
			mBottom.setBackgroundResource(com.example.grblu.R.drawable.black);
			mLeft.setBackgroundResource(com.example.grblu.R.drawable.black);
			mRight.setBackgroundResource(com.example.grblu.R.drawable.black);
			String message = "5";
			try {
				// 읽어온 센서값으로 발생한 메세지를 블루투스를 이용하여 전송
				mOutputStream.write(message.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else if (y > 4.5) {
			// Y값이 4.5초과일 경우 아두이노에서 우회전 동작을 구현할 때 필요한 "4"라는 문자를 생성
			mRight.setBackgroundResource(com.example.grblu.R.drawable.right);
			// 화살표 방향이 오른쪽으로 향하는 이미지를 불러옴
			valueView1.setText("4");
			String message = "4";
			try {
				// 읽어온 센서값으로 발생한 메세지를 블루투스를 이용하여 전송
				mOutputStream.write(message.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (y > 3.5 && y < 4.5) {
			// Y값이 3.5초과 4.5미만일 때 아두이노에서 정지동작을 구현할때 필요한 "5"라는 문자를 생성
			valueView1.setText("5");
			// 검정색 화면 이미지를 불러옴
			mTop.setBackgroundResource(com.example.grblu.R.drawable.black);
			mBottom.setBackgroundResource(com.example.grblu.R.drawable.black);
			mLeft.setBackgroundResource(com.example.grblu.R.drawable.black);
			mRight.setBackgroundResource(com.example.grblu.R.drawable.black);
			String message = "5";
			try {
				// 읽어온 센서값으로 발생한 메세지를 블루투스를 이용하여 전송
				mOutputStream.write(message.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (y < -4.5) {
			// Y값이 -4.5미만일 경우 아두이노에서 좌회전 동작을 구현할 때 필요한 "2"라는 문자를 생성
			mLeft.setBackgroundResource(com.example.grblu.R.drawable.left);
			// 화살표 방향이 왼쪽으로 향하는 이미지를 불러옴
			valueView1.setText("2");
			String message = "2";
			try {
				// 읽어온 센서값으로 발생한 메세지를 블루투스를 이용하여 전송
				mOutputStream.write(message.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (y < -3.5 && y > -4.5) {
			// Y값이 -4.5초과 -3.5미만 일 때 아두이노에서 정지동작을 구현할때 필요한 "5"라는 문자를 생성
			valueView1.setText("5");
			// 검정색 화면 이미지를 불러옴
			mTop.setBackgroundResource(com.example.grblu.R.drawable.black);
			mBottom.setBackgroundResource(com.example.grblu.R.drawable.black);
			mLeft.setBackgroundResource(com.example.grblu.R.drawable.black);
			mRight.setBackgroundResource(com.example.grblu.R.drawable.black);
			String message = "5";
			try {
				// 읽어온 센서값으로 발생한 메세지를 블루투스를 이용하여 전송
				mOutputStream.write(message.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		valueView.setText(String.format("X: %1$1.2f, Y: %2$1.2f, Z: %3$1.2f",
				values[0], values[1], values[2]));
		// 읽어오는 센서값 X, Y, Z를 뷰화면에 표시

	}

	BluetoothDevice getDeviceFromBondedList(String name) {
		// BluetoothDevice 객체를 페어링 된 기기 목록에서 얻어옴
		BluetoothDevice selectedDevice = null;
		for (BluetoothDevice device : mDevices) {
			if (name.equals(device.getName())) {
				selectedDevice = device;
				break;
			}
		}
		return selectedDevice;
	}

	void connectToSelectedDevice(String selectedDeviceName) {
		mRemoteDevice = getDeviceFromBondedList(selectedDeviceName);
		UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
		try {
			// 소켓 생성
			mSocket = mRemoteDevice.createRfcommSocketToServiceRecord(uuid);
			// RFCOMM 채널을 통한 연결
			mSocket.connect();
			// 데이터 송수신을 위한 스트림 얻기
			mOutputStream = mSocket.getOutputStream();
			mInputStream = mSocket.getInputStream();
			// 데이터 수신 준비
		} catch (Exception e) {
			// 블루투스 연결 중 오류 발생
			// 연결 중 오류 발생 토스트텍스트 생성
			Toast.makeText(getApplicationContext(), "블루투스 연결 중 오류가 발생했습니다.",
					Toast.LENGTH_LONG).show();
			finish(); // 어플리케이션 종료
		}
	}

	void selectDevice() {
		mDevices = mBluetoothAdapter.getBondedDevices();
		mPairedDeviceCount = mDevices.size();
		if (mPairedDeviceCount == 0) {
			// 페어링 된 장치가 없는 경우
			// 페어링된 장치가 없다는 토스트텍스트 생성
			Toast.makeText(getApplicationContext(), "페어링된 장치가 없습니다.",
					Toast.LENGTH_LONG).show();
			finish(); // 어플리케이션 종료
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("블루투스 장치 선택");
		// 페어링 된 블루투스 장치의 이름 목록 작성
		List<String> listItems = new ArrayList<String>();
		for (BluetoothDevice device : mDevices) {
			listItems.add(device.getName());
		}
		listItems.add("취소"); // 취소 항목 추가
		final CharSequence[] items = listItems
				.toArray(new CharSequence[listItems.size()]);
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				if (item == mPairedDeviceCount) {
					// 연결할 장치를 선택하지 않고 '취소'를 누른 경우
					// 연결할 장치를 선택 않했다는 토스트텍스트 생성
					Toast.makeText(getApplicationContext(),
							"연결할 장치를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
					finish(); // 어플리케이션 종료
				} else {
					// 연결할 장치를 선택한 경우
					// 선택한 장치와 연결을 시도함
					connectToSelectedDevice(items[item].toString());
				}
			}
		});
		builder.setCancelable(false); // 뒤로 가기 버튼 사용 금지
		AlertDialog alert = builder.create();
		alert.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	void checkBluetooth() {
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
			// 장치가 블루투스를 지원하지 않는 경우 토스트텍스트 생성
			Toast.makeText(getApplicationContext(), "기기가 블루투스를 지원하지 않습니다.",
					Toast.LENGTH_LONG).show();
			finish(); // 어플리케이션 종료
		} else {
			// 장치가 블루투스를 지원하는 경우
			if (!mBluetoothAdapter.isEnabled()) {
				// 블루투스를 지원하지만 비활성 상태인 경우
				Toast.makeText(getApplicationContext(), "현재 블루투스가 비활성 상태입니다.",
						Toast.LENGTH_LONG).show();
				// 블루투스를 활성 상태로 바꾸기 위해 사용자 동의 요청
				Intent enableBtIntent = new Intent(
						BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
			} else
				selectDevice();
			// 블루투스를 지원하며 활성 상태인 경우
			// 페어링 된 기기 목록을 보여주고 연결할 장치를 선택
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_ENABLE_BT:
			if (resultCode == RESULT_OK) {
				// 블루투스가 활성 상태로 변경됨
				selectDevice();
			} else if (resultCode == RESULT_CANCELED) {
				// 블루투스가 비활성 상태임
				// 사용못한다는 토스트텍스트 생성
				Toast.makeText(getApplicationContext(),
						"블루투스를 사용할 수 없어 프로그램을 종료합니다.", Toast.LENGTH_LONG)
						.show();
				finish(); // 어플리케이션 종료
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
