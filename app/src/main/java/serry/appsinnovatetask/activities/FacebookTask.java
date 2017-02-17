package serry.appsinnovatetask.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import serry.appsinnovatetask.R;
import serry.appsinnovatetask.utils.Constants;

public class FacebookTask extends AppCompatActivity {
    private CallbackManager callbackManager;

    private boolean isChecked;
    @BindView(R.id.loginButton)
    LoginButton loginButton;

    @BindView(R.id.cb_remember_me)
    CheckBox cbRemember;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @OnClick(R.id.btn_facebook_friends)
    public void getFBFriends() {
        Bundle requiredFields = new Bundle();
        requiredFields.putString("fields", "name,picture,width[500],height[500]");
        AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
        new GraphRequest(currentAccessToken, "/{friend-list-id", requiredFields, HttpMethod.GET, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_task);
        ButterKnife.bind(this);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        isChecked = prefs.getBoolean(Constants.isChecked, false);
        if (isChecked) {
            cbRemember.setChecked(true);
        } else
            cbRemember.setChecked(false);

        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email", "user_friends,public_profile");
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                // App code
//                GraphRequest request = GraphRequest.newMeRequest(
//                        loginResult.getAccessToken(),
//                        new GraphRequest.GraphJSONObjectCallback() {
//                            @Override
//                            public void onCompleted(
//                                    JSONObject object,
//                                    GraphResponse response) {
//                                // Application code
//
//                            }
//                        });
//                Bundle parameters = new Bundle();
//                parameters.putString("fields", "id,name,link,email,friends");
//                request.setParameters(parameters);
//                request.executeAsync();
//                new GraphRequest(
//                        AccessToken.getCurrentAccessToken(),
//                        "/{user-id}",
//                        null,
//                        HttpMethod.GET,
//                        new GraphRequest.Callback() {
//                            public void onCompleted(GraphResponse response) {
//            /* handle the result */
//                            }
//                        }
//                ).executeAsync();
            }

            @Override
            public void onCancel() {
                // App code

                Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code

                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        editor = prefs.edit();
        if (!cbRemember.isChecked()) {
            LoginManager.getInstance().logOut();
            isChecked = false;
            editor.putBoolean(Constants.isChecked, false);
            editor.commit();

        } else {
            isChecked = true;
            editor.putBoolean(Constants.isChecked, true);
            editor.commit();

        }
    }
}
