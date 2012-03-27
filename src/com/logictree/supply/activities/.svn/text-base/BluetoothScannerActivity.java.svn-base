package com.logictree.supply.activities;

import java.util.Set;

import com.logictree.supply.R;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class BluetoothScannerActivity extends Activity implements OnItemClickListener {

	private static final int REQUEST_ENABLE_BT = 0;

	private static final String TAG = "BluetoothScannerActivity";

	private BluetoothAdapter bluetoothAdapter = null;
	private ArrayAdapter<String> mPairedDevicesArrayAdapter;
	private ArrayAdapter<String> mNewDevicesArrayAdapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.device_list);
		setTitle(R.string.select_device);
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		Button scanButton = (Button) findViewById(R.id.button_scan);
		scanButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				doDiscovery();
				v.setVisibility(View.GONE);
			}
		});

		mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);
		mNewDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);

		ListView pairedListView = (ListView)findViewById(R.id.paired_devices);
		pairedListView.setAdapter(mPairedDevicesArrayAdapter);
		pairedListView.setOnItemClickListener(this);

		ListView newListView = (ListView)findViewById(R.id.new_devices);
		newListView.setAdapter(mNewDevicesArrayAdapter);
		newListView.setOnItemClickListener(this);

		if(bluetoothAdapter == null) {
			Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
			finish();
			return;
		}

		Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

		if(pairedDevices.size() > 0) {
			findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
			for(BluetoothDevice device: pairedDevices) {
				mPairedDevicesArrayAdapter.add(device.getName() + "\n" +device.getAddress());
			}

		} else {
			findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
			String noDevices = getResources().getText(R.string.none_paired).toString();
			mPairedDevicesArrayAdapter.add(noDevices);
		}

		mPairedDevicesArrayAdapter.notifyDataSetChanged();

		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		this.registerReceiver(receiver, filter);

		filter =  new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		this.registerReceiver(receiver, filter);

	}

	@Override
	protected void onStart() {
		super.onStart();

		if(!bluetoothAdapter.isEnabled()) {
			Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(intent, REQUEST_ENABLE_BT);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case REQUEST_ENABLE_BT:
			if(resultCode == RESULT_OK) {
				//				doDiscovery();
				Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

				if(pairedDevices.size() > 0) {
					findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
					for(BluetoothDevice device: pairedDevices) {
						mPairedDevicesArrayAdapter.add(device.getName() + "\n" +device.getAddress());
					}
				} else {
					findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
					String noDevices = getResources().getText(R.string.none_paired).toString();
					mPairedDevicesArrayAdapter.add(noDevices);
				}

			} else {
				Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
				finish();
			}

			break;
		default:
			break;
		}
	}

	private void doDiscovery() {
		setProgressBarIndeterminateVisibility(true);
		setTitle(R.string.scanning);
		findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);

		if (bluetoothAdapter.isDiscovering()) {
			bluetoothAdapter.cancelDiscovery();
		}
		bluetoothAdapter.startDiscovery();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(bluetoothAdapter != null) {
			bluetoothAdapter.cancelDiscovery();
		}
		this.unregisterReceiver(receiver);
	}

	public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {
		bluetoothAdapter.cancelDiscovery();
		finish();
		/*String info = ((TextView)v).getText().toString();
		String address = info.substring(info.length() - 17);
		Intent intent = new Intent(BluetoothScannerActivity.this, BluetoothChatActivity.class);
		intent.putExtra("address", address);
		Log.v(TAG,address);
		startActivity(intent);*/
	}

	private final BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			if(BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				if(device.getBondState() != BluetoothDevice.BOND_BONDED) {
					mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
				}
			} else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
				setProgressBarIndeterminateVisibility(false);
				setTitle(R.string.select_device);
				if (mNewDevicesArrayAdapter.getCount() == 0) {
					String noDevices = getResources().getText(R.string.none_found).toString();
					mNewDevicesArrayAdapter.add(noDevices);		
				}
			}
		}
	};

	private void ensureDiscoverable() {
		Log.v(TAG, "ensure discoverable");
		if (bluetoothAdapter.getScanMode() !=
			BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
			Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
			startActivity(discoverableIntent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.bluetooth_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.discoverable:
			ensureDiscoverable();
			return true;
		}
		return false;
	}
}