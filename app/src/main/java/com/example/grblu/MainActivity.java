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
			mWorkerThread.interrupt(); // ������ ���� ������ ����
			mInputStream.close(); // �Է� ��Ʈ�� ����
			mOutputStream.close(); // ��� ��Ʈ�� ����
			mSocket.close(); // ���� ����
		} catch (Exception e) {
		}
		super.onDestroy(); // ���ø����̼��� ����� �� ȣ��Ǵ� �Լ�(��Ʈ���� ������ ����)
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
				// ��ư�� ��ġ������� ������ �︮���� �ϴ� ���� ��ġ������
				switch (arg1.getAction()) {
				// ��ư�� �ٿ�(�����µ���)��Ű�� �Ƶ����̳뿡�� ������ �︮���� �����Ҷ� �ʿ��� "7"�̶�� ���ڸ� ����
				case MotionEvent.ACTION_DOWN:
					message = "7";

					break;
				// ��ư�� �ٿ�(�����µ���)��Ű�� �Ƶ����̳뿡�� ������ �׸� �︮���� �����Ҷ� �ʿ��� "8"�̶�� ���ڸ� ����
				case MotionEvent.ACTION_UP:
					message = "8";

					break;

				}
				try {
					// ��ġ�����ʷ� �߻��� �޼����� ��������� �̿��Ͽ� ����
					mOutputStream.write(message.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
				return false;
			}
		});
		mButtonLi.setOnClickListener(new OnClickListener() {
			// ��ư�� Ŭ���� LED�� ����ǵ��� �ϴ� Ŭ��������
			@Override
			public void onClick(View view) {
				// ��Ʋ�� Ŭ���� ��� �Ƶ��̳뿡�� LED�� ����ǵ��� �����Ҷ� �ʿ��� "6"�̶�� ���ڸ� ����
				String message = "6";
				try {
					// Ŭ�������ʷ� �߻��� �޼����� ��������� �̿��Ͽ� ����
					mOutputStream.write(message.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});

		mButtonST.setOnClickListener(new OnClickListener() {
			// ��ư�� Ŭ���� ������ �ǵ��� �ϴ� Ŭ��������
			@Override
			// ��ư�� Ŭ���� �Ƶ��̳뿡�� �����ϵ��� �����Ҷ� �ʿ��� "5"��� ���ڸ� ����
			public void onClick(View view) {

				String message = "5";
				// Ŭ�������ʷ� �߻��� �޼����� ��������� �̿��Ͽ� ����
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
		// ���ӵ� ���� ������ ������Ʈ�� ���
		super.onResume();
		mSensorManager.registerListener(this, mAccelerometer,
				SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	protected void onPause() {
		// ���ӵ� ���� ������ ������Ʈ�� �������
		super.onPause();
		mSensorManager.unregisterListener(this);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// ���ӵ� ���� ����ɶ����� ȣ���
		float[] values = event.values;
		float x = values[0];
		float y = values[1];
		int scaleFactor;

		if (x > 4.5) {
			// X���� 4.5 �ʰ��� ��� �Ƶ��̳뿡�� ���������� �����Ҷ� �ʿ��� "3"�̶�� ���ڸ� ����
			mBottom.setBackgroundResource(com.example.grblu.R.drawable.down);
			// ȭ��ǥ ������ �Ʒ��� ���ϴ� �̹����� �ҷ���
			valueView1.setText("3");
			String message = "3";
			try {
				// �о�� ���������� �߻��� �޼����� ��������� �̿��Ͽ� ����
				mOutputStream.write(message.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (x > 3.5 && x < 4.5) {
			// X���� 3.5�ʰ� 4.5�̸� �� �� �Ƶ��̳뿡�� ���������� �����Ҷ� �ʿ��� "5"��� ���ڸ� ����
			valueView1.setText("5");
			// ������ ȭ�� �̹����� �ҷ���
			mTop.setBackgroundResource(com.example.grblu.R.drawable.black);
			mBottom.setBackgroundResource(com.example.grblu.R.drawable.black);
			mLeft.setBackgroundResource(com.example.grblu.R.drawable.black);
			mRight.setBackgroundResource(com.example.grblu.R.drawable.black);
			String message = "5";
			try {
				// �о�� ���������� �߻��� �޼����� ��������� �̿��Ͽ� ����
				mOutputStream.write(message.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else if (x < -4.5) {
			// X���� -4.5�̸��� ��� �Ƶ��̳뿡�� ���������� �����Ҷ� �ʿ��� "1"�̶�� ���ڸ� ����
			mTop.setBackgroundResource(com.example.grblu.R.drawable.up);
			// ȭ��ǥ ������ ���� ���ϴ� �̹����� �ҷ���
			valueView1.setText("1");
			String message = "1";
			try {
				// �о�� ���������� �߻��� �޼����� ��������� �̿��Ͽ� ����
				mOutputStream.write(message.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (x < -3.5 && x > -4.5) {
			// X���� -3.5�̸� -4.5�ʰ� �� �� �Ƶ��̳뿡�� ���������� �����Ҷ� �ʿ��� "5"��� ���ڸ� ����
			valueView1.setText("5");
			// ������ ȭ�� �̹����� �ҷ���
			mTop.setBackgroundResource(com.example.grblu.R.drawable.black);
			mBottom.setBackgroundResource(com.example.grblu.R.drawable.black);
			mLeft.setBackgroundResource(com.example.grblu.R.drawable.black);
			mRight.setBackgroundResource(com.example.grblu.R.drawable.black);
			String message = "5";
			try {
				// �о�� ���������� �߻��� �޼����� ��������� �̿��Ͽ� ����
				mOutputStream.write(message.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else if (y > 4.5) {
			// Y���� 4.5�ʰ��� ��� �Ƶ��̳뿡�� ��ȸ�� ������ ������ �� �ʿ��� "4"��� ���ڸ� ����
			mRight.setBackgroundResource(com.example.grblu.R.drawable.right);
			// ȭ��ǥ ������ ���������� ���ϴ� �̹����� �ҷ���
			valueView1.setText("4");
			String message = "4";
			try {
				// �о�� ���������� �߻��� �޼����� ��������� �̿��Ͽ� ����
				mOutputStream.write(message.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (y > 3.5 && y < 4.5) {
			// Y���� 3.5�ʰ� 4.5�̸��� �� �Ƶ��̳뿡�� ���������� �����Ҷ� �ʿ��� "5"��� ���ڸ� ����
			valueView1.setText("5");
			// ������ ȭ�� �̹����� �ҷ���
			mTop.setBackgroundResource(com.example.grblu.R.drawable.black);
			mBottom.setBackgroundResource(com.example.grblu.R.drawable.black);
			mLeft.setBackgroundResource(com.example.grblu.R.drawable.black);
			mRight.setBackgroundResource(com.example.grblu.R.drawable.black);
			String message = "5";
			try {
				// �о�� ���������� �߻��� �޼����� ��������� �̿��Ͽ� ����
				mOutputStream.write(message.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (y < -4.5) {
			// Y���� -4.5�̸��� ��� �Ƶ��̳뿡�� ��ȸ�� ������ ������ �� �ʿ��� "2"��� ���ڸ� ����
			mLeft.setBackgroundResource(com.example.grblu.R.drawable.left);
			// ȭ��ǥ ������ �������� ���ϴ� �̹����� �ҷ���
			valueView1.setText("2");
			String message = "2";
			try {
				// �о�� ���������� �߻��� �޼����� ��������� �̿��Ͽ� ����
				mOutputStream.write(message.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (y < -3.5 && y > -4.5) {
			// Y���� -4.5�ʰ� -3.5�̸� �� �� �Ƶ��̳뿡�� ���������� �����Ҷ� �ʿ��� "5"��� ���ڸ� ����
			valueView1.setText("5");
			// ������ ȭ�� �̹����� �ҷ���
			mTop.setBackgroundResource(com.example.grblu.R.drawable.black);
			mBottom.setBackgroundResource(com.example.grblu.R.drawable.black);
			mLeft.setBackgroundResource(com.example.grblu.R.drawable.black);
			mRight.setBackgroundResource(com.example.grblu.R.drawable.black);
			String message = "5";
			try {
				// �о�� ���������� �߻��� �޼����� ��������� �̿��Ͽ� ����
				mOutputStream.write(message.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		valueView.setText(String.format("X: %1$1.2f, Y: %2$1.2f, Z: %3$1.2f",
				values[0], values[1], values[2]));
		// �о���� ������ X, Y, Z�� ��ȭ�鿡 ǥ��

	}

	BluetoothDevice getDeviceFromBondedList(String name) {
		// BluetoothDevice ��ü�� �� �� ��� ��Ͽ��� ����
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
			// ���� ����
			mSocket = mRemoteDevice.createRfcommSocketToServiceRecord(uuid);
			// RFCOMM ä���� ���� ����
			mSocket.connect();
			// ������ �ۼ����� ���� ��Ʈ�� ���
			mOutputStream = mSocket.getOutputStream();
			mInputStream = mSocket.getInputStream();
			// ������ ���� �غ�
		} catch (Exception e) {
			// ������� ���� �� ���� �߻�
			// ���� �� ���� �߻� �佺Ʈ�ؽ�Ʈ ����
			Toast.makeText(getApplicationContext(), "������� ���� �� ������ �߻��߽��ϴ�.",
					Toast.LENGTH_LONG).show();
			finish(); // ���ø����̼� ����
		}
	}

	void selectDevice() {
		mDevices = mBluetoothAdapter.getBondedDevices();
		mPairedDeviceCount = mDevices.size();
		if (mPairedDeviceCount == 0) {
			// �� �� ��ġ�� ���� ���
			// ���� ��ġ�� ���ٴ� �佺Ʈ�ؽ�Ʈ ����
			Toast.makeText(getApplicationContext(), "���� ��ġ�� �����ϴ�.",
					Toast.LENGTH_LONG).show();
			finish(); // ���ø����̼� ����
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("������� ��ġ ����");
		// �� �� ������� ��ġ�� �̸� ��� �ۼ�
		List<String> listItems = new ArrayList<String>();
		for (BluetoothDevice device : mDevices) {
			listItems.add(device.getName());
		}
		listItems.add("���"); // ��� �׸� �߰�
		final CharSequence[] items = listItems
				.toArray(new CharSequence[listItems.size()]);
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				if (item == mPairedDeviceCount) {
					// ������ ��ġ�� �������� �ʰ� '���'�� ���� ���
					// ������ ��ġ�� ���� ���ߴٴ� �佺Ʈ�ؽ�Ʈ ����
					Toast.makeText(getApplicationContext(),
							"������ ��ġ�� �������� �ʾҽ��ϴ�.", Toast.LENGTH_LONG).show();
					finish(); // ���ø����̼� ����
				} else {
					// ������ ��ġ�� ������ ���
					// ������ ��ġ�� ������ �õ���
					connectToSelectedDevice(items[item].toString());
				}
			}
		});
		builder.setCancelable(false); // �ڷ� ���� ��ư ��� ����
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
			// ��ġ�� ��������� �������� �ʴ� ��� �佺Ʈ�ؽ�Ʈ ����
			Toast.makeText(getApplicationContext(), "��Ⱑ ��������� �������� �ʽ��ϴ�.",
					Toast.LENGTH_LONG).show();
			finish(); // ���ø����̼� ����
		} else {
			// ��ġ�� ��������� �����ϴ� ���
			if (!mBluetoothAdapter.isEnabled()) {
				// ��������� ���������� ��Ȱ�� ������ ���
				Toast.makeText(getApplicationContext(), "���� ��������� ��Ȱ�� �����Դϴ�.",
						Toast.LENGTH_LONG).show();
				// ��������� Ȱ�� ���·� �ٲٱ� ���� ����� ���� ��û
				Intent enableBtIntent = new Intent(
						BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
			} else
				selectDevice();
			// ��������� �����ϸ� Ȱ�� ������ ���
			// �� �� ��� ����� �����ְ� ������ ��ġ�� ����
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_ENABLE_BT:
			if (resultCode == RESULT_OK) {
				// ��������� Ȱ�� ���·� �����
				selectDevice();
			} else if (resultCode == RESULT_CANCELED) {
				// ��������� ��Ȱ�� ������
				// �����Ѵٴ� �佺Ʈ�ؽ�Ʈ ����
				Toast.makeText(getApplicationContext(),
						"��������� ����� �� ���� ���α׷��� �����մϴ�.", Toast.LENGTH_LONG)
						.show();
				finish(); // ���ø����̼� ����
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
