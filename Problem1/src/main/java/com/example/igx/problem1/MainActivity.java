package com.example.igx.problem1;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener /* implements Something1, Something2 */ {
    SensorManager sensormanager;
    Sensor sensor_temp;
    Sensor sensor_gyro;
    Sensor sensor_gravity;
    Sensor sensor_light;

    float temperature;
    float gyro_x;
    float gyro_y;
    float gyro_z;
    float gravity_x;
    float gravity_y;
    float gravity_z;
    float light;

    String temp = "";
    String gy = "";
    String grav = "";
    String li = "";

    String phone = "";
    String sum = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_getLocation = (Button) findViewById(R.id.btn_getLocation);
        Button btn_getSensors = (Button) findViewById(R.id.btn_getSensors);
        Button btn_sendMessage = (Button) findViewById(R.id.btn_sendMessage);

        final TextView text_selectedData = (TextView) findViewById(R.id.text_selectedData);
        final TextView text_selectedType = (TextView) findViewById(R.id.text_selectedType);
        final EditText edit_phoneNumber = (EditText) findViewById(R.id.edit_phoneNumber);

        sensormanager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor_temp = sensormanager.getDefaultSensor(Sensor.TYPE_TEMPERATURE); // 온도 센서
        sensor_gyro = sensormanager.getDefaultSensor(Sensor.TYPE_GYROSCOPE); // 자이로 센서
        sensor_gravity = sensormanager.getDefaultSensor(Sensor.TYPE_GRAVITY); // 중력 센서
        sensor_light = sensormanager.getDefaultSensor(Sensor.TYPE_LIGHT); // 빛 센서

        phone = edit_phoneNumber.getText().toString();

        btn_getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        btn_getSensors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String introduce = "Temperature: ";
                String introduce1 = "Gyro: ";
                String introduce2 = "Gravity: ";
                String introduce3 = "Light: ";

                sum = introduce + temp +"\n"+introduce1 + gy +"\n" + introduce2 + grav +"\n" + introduce3 + li;

                text_selectedData.setText(sum);
            }
        });

        btn_sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeMmsMessage(sum, Uri.parse(phone));
            }
        });
    }

    public void onResume() {
        super.onResume();

        sensormanager.registerListener(this,sensor_temp,SensorManager.SENSOR_DELAY_NORMAL);
        sensormanager.registerListener(this,sensor_gyro,SensorManager.SENSOR_DELAY_NORMAL);
        sensormanager.registerListener(this,sensor_gravity,SensorManager.SENSOR_DELAY_NORMAL);
        sensormanager.registerListener(this,sensor_light,SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPause() {
        super.onPause();

        sensormanager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor == sensor_temp) {
            temperature = event.values[0];
            temp = toString().valueOf(temperature);
        }

        if(event.sensor == sensor_gyro) {
            gyro_x = event.values[0];
            gyro_y = event.values[1];
            gyro_z = event.values[2];

            gy = toString().valueOf(gyro_x) + ", " + toString().valueOf(gyro_y) + ", "+toString().valueOf(gyro_z);
        }

        if(event.sensor == sensor_gravity) {
            gravity_x= event.values[0];
            gravity_y = event.values[1];
            gravity_z = event.values[2];

            grav = toString().valueOf(gravity_x) + ", " + toString().valueOf(gravity_y) + ", "+toString().valueOf(gravity_z);
        }

        if(event.sensor == sensor_light) {
            light = event.values[0];
            li = toString().valueOf(light);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void composeMmsMessage(String message, Uri attachchment) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:"));
        intent.putExtra("sms_body", message);
        intent.putExtra(Intent.EXTRA_STREAM, attachchment);
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
