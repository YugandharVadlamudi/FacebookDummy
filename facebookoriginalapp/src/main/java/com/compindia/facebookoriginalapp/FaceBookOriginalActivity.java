package com.compindia.facebookoriginalapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class FaceBookOriginalActivity extends AppCompatActivity {

    private Button facebook;
    private ProgressDialog progress;
    private CallbackManager callbackManager;
    private String TAG=FaceBookOriginalActivity.class.getSimpleName();
    private LoginButton fbLoginButton;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode->"+requestCode
                +"\n resultCode->"+resultCode
                +"\n data->"+data);
//        if()
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        generateKeyHash();
        setContentView(R.layout.activity_face_book_original);
        fbLoginButton = (LoginButton) findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        List<String> permissions=new ArrayList<>();
        permissions.add("email");
        permissions.add("user_birthday");
        permissions.add("user_managed_groups");
//        permissions.add("user_groups");
        permissions.add("public_profile");
        permissions.add("user_friends");
        permissions.add("read_custom_friendlists");
        fbLoginButton.setReadPermissions(permissions);

        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess: loginResult->" + loginResult);
                AccessToken token =AccessToken.getCurrentAccessToken();
                Log.d(TAG, "onSuccess: token->" + token);
                GraphRequest graphRequest = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d(TAG, "onCompleted: JSONObject->"+object+"\n GraphResponse->"+response);
                        try {
                            Log.d(TAG, "onCompleted: id->"+object.get("id"));
                            Bundle paramsGroup = new Bundle();
                            paramsGroup.putString("fields", "id,cover,description,email,icon" +
                                    ",name" +
                                    ",owner"+
                                    "privacy"
                            );
                           GraphRequest grGroup= new GraphRequest(AccessToken.getCurrentAccessToken()
                                    ,"/"+ object.get("id")+"/groups"
//                                    ,"/me/groups"
                                    , null
                                    , HttpMethod.GET
                                    , new GraphRequest.Callback() {
                                @Override
                                public void onCompleted(GraphResponse response) {
                                    Log.d(TAG, "onCompleted: groups response->"+response);
                                    try {
                                        Log.d(TAG, "onCompleted: getId->" + response.getJSONObject().getJSONArray("data").getJSONObject(0).get("name"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                            }
                            );

//                            grGroup.setParameters(paramsGroup);
                            grGroup.executeAsync();
                          /*  new GraphRequest(AccessToken.getCurrentAccessToken()
                                    ,)
                          */  Bundle params = new Bundle();
                            params.putString("name", "Test Group Name");
                            new GraphRequest(AccessToken.getCurrentAccessToken()
                                    , "/v2.2/" + object.get("id") + "/groups"
                                    , params
                                    , HttpMethod.POST

                                    , new GraphRequest.Callback() {
                                @Override
                                public void onCompleted(GraphResponse response) {
                                    Log.d(TAG, "onCompleted: publish response->"+response);
                                }
                            }
                            ).executeAsync();
                            /*
                            * Friends list of facebook
                            * */
                            new GraphRequest(
                                    AccessToken.getCurrentAccessToken(),
                                    "/me/permissions",
                                    null,
                                    HttpMethod.GET,
                                    new GraphRequest.Callback() {
                                        public void onCompleted(GraphResponse response) {
                                         /* handle the result */
                                            Log.d(TAG, "onCompleted: response->"+response);
                                        }
                                    }
                            ).executeAsync();

                            /*
                            * groups with limit 2
                            * */
                            GraphRequest request = GraphRequest.newMeRequest(
                                    AccessToken.getCurrentAccessToken(),
                                    new GraphRequest.GraphJSONObjectCallback() {
                                        @Override
                                        public void onCompleted(JSONObject object, GraphResponse response) {
                                            // Insert your code here
                                            Log.d(TAG, "onCompleted: groupsResponse->"+response);
                                        }
                                    });

                            Bundle parameters = new Bundle();
                            parameters.putString("fields", "id,name,groups.limit(2)");
                            request.setParameters(parameters);
                            request.executeAsync();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,birthday,gender");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();

                /*VoiceInteractor.Request request = new DownloadManager.Request(session, "me/events?fields=cover,name,start_time");
                request.executeAsync();*/

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });



    }

    private void generateKeyHash() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo("com.compindia.facebookoriginalapp"
                    , PackageManager.GET_SIGNATURES);
            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
