package com.beowulf.call;

import android.provider.Settings;
import android.util.Log;

import com.example.beowulfwebrtc.LogUtil;
import com.example.beowulfwebrtc.SDKApplication;
import com.example.beowulfwebrtc.SDKProtocol.BWF_CMM_CallManager;
import com.example.beowulfwebrtc.SDKProtocol.BWF_CMM_Error;
import com.example.beowulfwebrtc.SDKProtocol.BWF_CMM_MessageDef;
import com.example.beowulfwebrtc.SDKProtocol.BWF_CMM_Protocol;
import com.example.beowulfwebrtc.SDKProtocol.BWF_CMM_Protocol_deligate;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MyFirebaseMessagingService   extends FirebaseMessagingService  implements BWF_CMM_Protocol {
    public MyFirebaseMessagingService() {
//        BWF_CMM_Protocol_deligate.AddListener(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage message) {
        super.onMessageReceived(message);


        BWF_CMM_Protocol_deligate.AddListener(this);

        String body = message.getData().get("body");

        Log.d("FIREBASE_BODY", "body-----" + body);

        BWF_CMM_Protocol_deligate.nofityOnReceivePushContent(body);


    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d("androidID", s);

    }

    public static String recurseKeys(JSONObject jObj, String findKey) throws JSONException {
        String finalValue = "";
        if (jObj == null) {
            return "";
        }

        Iterator<String> keyItr = jObj.keys();
        Map<String, String> map = new HashMap<>();

        while (keyItr.hasNext()) {
            String key = keyItr.next();
            map.put(key, jObj.getString(key));
        }

        for (Map.Entry<String, String> e : (map).entrySet()) {
            String key = e.getKey();
            if (key.equalsIgnoreCase(findKey)) {
                return jObj.getString(key);
            }

            // read value
            Object value = jObj.get(key);

            if (value instanceof JSONObject) {
                finalValue = recurseKeys((JSONObject) value, findKey);
            }
        }

        // key is not found
        return finalValue;

    }

    @Override
    public void bwf_cmm_didStartWithIdentifier(String s) {

    }

    @Override
    public void bwf_cmm_failedWithError(BWF_CMM_Error bwf_cmm_error) {

    }

    @Override
    public void bwf_cmm_Calling_CallDidChangeToStateOutgoing(boolean b) {

    }

    @Override
    public void bwf_cmm_Call_Denied(String s, String s1) {

    }

    @Override
    public void bwf_cmm_generatePushContent(String s, String s1, BWF_CMM_MessageDef.bwf_cmm_push_content_type bwf_cmm_push_content_type) {

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
    public void onDestroy() {
        super.onDestroy();
        BWF_CMM_Protocol_deligate.RemoveListener(this);
    }
}
