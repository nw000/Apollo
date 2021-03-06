package com.lsxiao.apollo.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.apollo.core.annotations.Receive;
import com.lsxiao.apollo.demo.base.BaseActivity;
import com.lsxiao.apollo.demo.fragment.ProducerFragment;
import com.lsxiao.apollo.demo.fragment.SubscriberFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        final Fragment subscriberFragment = SubscriberFragment.newInstance();
        final Fragment producerFragment = ProducerFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_subscriber, subscriberFragment, SubscriberFragment.TAG)
                .commit();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_producer, producerFragment, SubscriberFragment.TAG)
                .commit();
        ((TextView) findViewById(R.id.btn_start_service)).setText(String.format("启动TestService(当前pid=%s)", Process.myPid()));
        findViewById(R.id.btn_start_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TestService.class);
                startService(intent);
            }
        });
    }

    @Receive("process")
    public void onEvent(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
