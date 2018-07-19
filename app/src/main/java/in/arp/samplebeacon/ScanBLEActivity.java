package in.arp.samplebeacon;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.arpaul.utilitieslib.StringUtils;

import java.util.ArrayList;

public class ScanBLEActivity extends AppCompatActivity {

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner mBluetoothLeScanner;
    private boolean mScanning = false;
    private Handler mHandler;
    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;

    private ScanCallback mScanCallback;
    private BluetoothAdapter.LeScanCallback mLeScanCallback;
    private Button btnScan;
    private ArrayList<String> arrDevice = new ArrayList<>();
    private final int REQUEST_ENABLE_BT = 1010;
    //https://developer.android.com/guide/topics/connectivity/bluetooth-le

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_ble);

        btnScan = findViewById(R.id.btnScan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mScanning)
                    mScanning = false;
                else
                    mScanning = true;

//                scanLeDevice(mScanning);
                startScan(mScanning);
            }
        });

        mHandler = new Handler();
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            } else
                fetchScanner();
        } else {

        }

    }

    private void fetchScanner() {
        if (mBluetoothAdapter.isEnabled()){
            mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_ENABLE_BT) {
            fetchScanner();
        }
    }

    private void showDevices() {
        if(arrDevice != null && arrDevice.size() > 0) {
            String devices = StringUtils.convertArraylistToString(arrDevice);
            Toast.makeText(ScanBLEActivity.this, devices, Toast.LENGTH_SHORT).show();
        }
    }

    private void startScan(boolean mScanning) {
        // initialize the right scan callback for the current API level
        if (Build.VERSION.SDK_INT >= 21) {
            mScanCallback = new ScanCallback() {
                @Override
                public void onScanResult(int callbackType, ScanResult result) {
                    super.onScanResult(callbackType, result);
//                    mRecyclerViewAdapter.addDevice(result.getDevice().getAddress());
                    arrDevice.add(result.getDevice().getAddress().toString());
                    showDevices();
                }
            };
        } else {
            mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
//                    mRecyclerViewAdapter.addDevice(bluetoothDevice.getAddress());
                    arrDevice.add(bluetoothDevice.getAddress().toString());
                    showDevices();
                }
            };
        }

        if (mScanning) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mBluetoothLeScanner.startScan(mScanCallback);
            } else {
                mBluetoothAdapter.startLeScan(mLeScanCallback);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mBluetoothLeScanner.stopScan(mScanCallback);
            } else {
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
            }
        }
    }

    // Implement BluetoothAdapter.LeScanCallback
//    @Override
//    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
//        arrDevice.add(device.getUuids().toString());
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                String devices = StringUtils.convertArraylistToString(arrDevice);
//                Toast.makeText(ScanBLEActivity.this, devices, Toast.LENGTH_SHORT).show();
////                mLeDeviceListAdapter.addDevice(device);
////                mLeDeviceListAdapter.notifyDataSetChanged();
//            }
//        });
//    }

//    private void scanLeDevice(final boolean enable) {
//        if (enable) {
//            // Stops scanning after a pre-defined scan period.
//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mScanning = false;
//                    mBluetoothAdapter.stopLeScan(ScanBLEActivity.this);
//                }
//            }, SCAN_PERIOD);
//
//            mScanning = true;
//            mBluetoothAdapter.startLeScan(this);
//        } else {
//            mScanning = false;
//            mBluetoothAdapter.stopLeScan(this);
//        }
//    }
}
