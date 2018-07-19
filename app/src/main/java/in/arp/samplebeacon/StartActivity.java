package in.arp.samplebeacon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    private Button btnCreateBeacon, btnSearchBeacon, btnRangeBeacon, btnScanBLE;
    //https://altbeacon.github.io/android-beacon-library/samples.html

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnSearchBeacon = (Button) findViewById(R.id.btnSearchBeacon);
        btnCreateBeacon = (Button) findViewById(R.id.btnCreateBeacon);
        btnRangeBeacon = (Button) findViewById(R.id.btnRangeBeacon);
        btnScanBLE = (Button) findViewById(R.id.btnScanBLE);

        btnSearchBeacon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, SearchBeaconActivity.class));
            }
        });

        btnCreateBeacon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, MainActivity.class));
            }
        });

        btnRangeBeacon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, RangeActivity.class));
            }
        });

        btnScanBLE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, ScanBLEActivity.class));
            }
        });
    }
}
