package com.beowulf.call;

import com.example.beowulfwebrtc.AppData.AppUri;
import com.example.beowulfwebrtc.LogUtil;
import com.example.beowulfwebrtc.SDKApplication;
import com.example.beowulfwebrtc.network.JsonAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class TestPushApp {
    String deviceTokens = "";
    String based64data = "";
    String requestId = "";

    public TestPushApp(String _deviceTokens, String _based64data, String _requestId) {
        deviceTokens = _deviceTokens;
        based64data = _based64data;
        requestId = _requestId;
        based64data=based64data.replace("\\n","");
        based64data=based64data.replace("\n","");
        based64data=based64data.replace("\\r","");
        based64data=based64data.replace("\r","");
    }

    JsonAPI.JsonCallback jsonCallback = (statusCode, str) -> {

        if (statusCode == HttpsURLConnection.HTTP_OK) {

        }

    };

    JsonAPI.JsonRequestListener jsonRequestListener = new JsonAPI.JsonRequestListener() {
        @Override
        public void onBeginRequest() {
        }

        @Override
        public void onEndRequest() {
        }
    };


    public void Execute() {


        JSONObject json = new JSONObject();
        try {
            JSONArray array=new JSONArray();
            array.put(deviceTokens);
            json.put("deviceTokens", array);
            json.put("modeProduction", true);
            json.put("notifyType", "message");
            json.put("message", based64data);
            json.put("badge", 1);

            JSONObject extrainos = new JSONObject();
            extrainos.put("qrcode", "3ng-chat");
            extrainos.put("requestId", requestId);
            json.put("extra_infos", extrainos);

        } catch (
                JSONException e) {
            e.printStackTrace();
        }

        String jsonStr = json.toString();
        HashMap<String, String> requestHeader = new HashMap<String, String>();
        requestHeader.put("Content-Type", "application/json");

        JsonAPI.getInstance().post(requestHeader, "http://35.229.222.105/kpush/api/v1/push/demo?key=AIzaSyC36tlUhXlvcb8_DvXU98qJwp1RNAir6TY", jsonStr, jsonCallback, jsonRequestListener);


    }

}
