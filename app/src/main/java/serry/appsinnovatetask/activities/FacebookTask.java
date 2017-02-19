package serry.appsinnovatetask.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import serry.appsinnovatetask.R;
import serry.appsinnovatetask.adapters.FriendsAdapter;
import serry.appsinnovatetask.models.Friend;
import serry.appsinnovatetask.utils.Constants;

public class FacebookTask extends AppCompatActivity {
    private CallbackManager callbackManager;
    private List<Friend> friendsList;
    private FriendsAdapter friendsAdapter;
    private boolean isChecked;

    @BindView(R.id.rv_friends_list)
    RecyclerView rvFriendList;

    @BindView(R.id.loginButton)
    LoginButton loginButton;

    @BindView(R.id.cb_remember_me)
    CheckBox cbRemember;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    @BindView(R.id.pbFriendlist)
    ProgressBar pbFriendList;
    @OnClick(R.id.btn_facebook_friends)
    public void getFBFriends() {
            pbFriendList.setVisibility(View.VISIBLE);
        Bundle required = new Bundle();
        required.putString("fields",
                "uid, name, picture.width(200).height(200)");
        required.putString("limit", "5000");
        AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
        new GraphRequest(currentAccessToken, "/me/taggable_friends", required, HttpMethod.GET, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {

                JSONObject jsonObject = response.getJSONObject();
                try {

                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonFriend = jsonArray.getJSONObject(i);
                        Friend friend = new Friend(jsonFriend.getString("name"), jsonFriend.getJSONObject("picture").getJSONObject("data").getString("url"));
                        friendsList.add(friend);
                    }
                    friendsAdapter = new FriendsAdapter(friendsList, getApplicationContext());
                    rvFriendList.setAdapter(friendsAdapter);
                    pbFriendList.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NullPointerException nullPointerE) {
                    Toast.makeText(getApplicationContext(), "You must log in facebook first", Toast.LENGTH_LONG).show();

                }
            }
        }).executeAsync();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_task);
        ButterKnife.bind(this);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        friendsList = new ArrayList<>();
        rvFriendList.setLayoutManager(new LinearLayoutManager(this));
        rvFriendList.hasFixedSize();
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
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
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
