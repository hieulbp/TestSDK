package com.beowulf.call;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.beowulfwebrtc.API.TestPush;
import com.example.beowulfwebrtc.API.startWithIdentifier;
import com.example.beowulfwebrtc.LogUtil;
import com.example.beowulfwebrtc.SDKApplication;
import com.example.beowulfwebrtc.SDKProtocol.BWF_CMM_CallManager;
import com.example.beowulfwebrtc.SDKProtocol.BWF_CMM_Error;
import com.example.beowulfwebrtc.SDKProtocol.BWF_CMM_MessageDef;
import com.example.beowulfwebrtc.SDKProtocol.BWF_CMM_Protocol;
import com.example.beowulfwebrtc.SDKProtocol.BWF_CMM_Protocol_deligate;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity implements  BWF_CMM_Protocol, LogUtil.LoggingDisplay {

    Button btnStarFrameWork, btnCall;
    EditText edtId, edtCall;
    TextView tvCallId;
    String idCall = "";
    LinearLayout ll_processing;
    ProgressBar progressBar;

    public static String KEY = "UVIxU2dMMnhBczNxNE5ndDZVbjd5VUovTkhDSG9VckZiWFZScldGd0JGLzVsU2JldFE1WlVYS25aRnA3bFRuMA";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SDKApplication.setApi_key(KEY);
        BWF_CMM_Protocol_deligate.AddListener(this);
        LogUtil.addListener(this);

        Init();

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String fireBaseToken = instanceIdResult.getToken();
                Log.d("FIREBASETOKEN", fireBaseToken);
            }
        });

        btnStarFrameWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                idCall = edtId.getText().toString();
                HandlerThread handlerThread=new HandlerThread("STRARTWID");
                handlerThread.start();
                Handler handler = new Handler(handlerThread.getLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        new startWithIdentifier(idCall).Execute();
                    }
                });
            }
        });


        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String callId = edtCall.getText().toString();
                BWF_CMM_CallManager.getInstance().voiceCallTo(callId);

            }
        });


    }

    private void Init() {
        btnStarFrameWork = findViewById(R.id.btn_start_framework);
        edtId = findViewById(R.id.edt_id);
        tvCallId = findViewById(R.id.tv_id);
        btnCall = findViewById(R.id.btn_call);
        edtCall = findViewById(R.id.edt_call);
        ll_processing = findViewById(R.id.ll_processing);
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BWF_CMM_Protocol_deligate.RemoveListener(this);
        LogUtil.removeListener(this::bwf_cmm_didStartWithIdentifier);
    }


    @Override
    public void bwf_cmm_didStartWithIdentifier(String s) {
        tvCallId.setText("Start Id "+ idCall);

        progressBar.setVisibility(View.INVISIBLE);
        btnCall.setVisibility(View.VISIBLE);

        Log.d("bwf_cmm_didStartWithIdentifier    ",  s );

    }

    @Override
    public void bwf_cmm_failedWithError(BWF_CMM_Error bwf_cmm_error) {

    }

    @Override
    public void bwf_cmm_Calling_CallDidChangeToStateOutgoing(boolean b) {

    }

    @Override
    public void bwf_cmm_Call_Denied(String s, String s1) {
        Log.d("bwf_cmm_didStartWithIdentifier    ",  "Denied " + s + " + " + s1 );
    }

    @Override
    public void bwf_cmm_generatePushContent(String s, String s1, BWF_CMM_MessageDef.bwf_cmm_push_content_type bwf_cmm_push_content_type) {

        TestPush note8 =
                new TestPush(
                        "dGCPbB0vI-I:APA91bHyFb3CSEXn2rPM7RkCMxzHysxj9jT8IyKS5oaE-7dl5zLV9JW4hJJyqPzjFGPHUTjV7E2aiKpgMD5qzeFF16iirQCxdy8mLlsTz0ZUpUoCkaRlzhIbghgBzSExNrF_HnVDebKD"
                        , s
                        , s1);// to make a call to sam sung note 8
        note8.Execute();
        Log.d("bwf_cmm_didStartWithIdentifier    ",  s );
        Log.d("bwf_cmm_didStartWithIdentifier    ",  s1 );


    }

    @Override
    public void bwf_cmm_receivePushContent(String s, String s1, BWF_CMM_MessageDef.bwf_cmm_push_content_type bwf_cmm_push_content_type) {

    }

    @Override
    public void bwf_cmm_Calling_CallDidChangeToStateReceivedIncomingCallFrom(String s, boolean b) {
        BWF_CMM_CallManager.LauchIncommingCall();
    }

    @Override
    public void bwf_cmm_Calling_CallDidChangeToStateOutgoing(String s, boolean b) {
        BWF_CMM_CallManager.LauchOutgoingCall(true);
    }

    @Override
    public void bwf_cmm_Calling_CallDidChangeToStateConnected(String s, boolean b) {

    }

    @Override
    public void bwf_cmm_Calling_CallDidChangeToStateEnd(long l, BWF_CMM_Error bwf_cmm_error) {

    }

    @Override
    public void bwf_cmm_receivePushContent(String s) {

    }

    @Override
    public void bwf_cmm_generatePushContent(String s, String s1) {

    }

    @Override
    public void DisplayString(String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView textView = new TextView(MainActivity.this);
                textView.setSingleLine(true);
                textView.setText(s);
                ll_processing.addView(textView);
            }
        });
    }
}