package serry.appsinnovatetask.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import serry.appsinnovatetask.R;

public class MainActivity extends AppCompatActivity {
    private Intent intent;
    private CallbackManager callbackManager;
    private LoginManager loginManager;


    @OnClick(R.id.btn_facebook_task)
    public void facebookTask() {
        intent = new Intent(MainActivity.this, FacebookTask.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_get_contacts)
    public void getContacts() {
        intent = new Intent(MainActivity.this, PhoneContactsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_get_countries)
    public void getCountries() {
        intent = new Intent(MainActivity.this, CountriesTaskActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_integrate_calendar)
    public void addEvent() {
        Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", cal.getTimeInMillis());
        intent.putExtra("allDay", true);
        intent.putExtra("rule", "FREQ=YEARLY");
        intent.putExtra("endTime", cal.getTimeInMillis() + 60 * 60 * 1000);
        intent.putExtra("title", "Apps Innovate Task");
        startActivity(intent);
    }

    @OnClick(R.id.btn_share_photo)
    public void sharePhoto() {
        callbackManager = CallbackManager.Factory.create();

        List<String> permissionNeeds = Arrays.asList("publish_actions");

        //this loginManager helps you eliminate adding a LoginButton to your UI
        loginManager = LoginManager.getInstance();

        loginManager.logInWithPublishPermissions(this, permissionNeeds);

        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                sharePhotoToFacebook();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "serry.appsinnovatetask",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    private void sharePhotoToFacebook() {
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .setCaption("Appsinnovate Task ....")
                .build();

        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

        ShareApi.share(content, null);

    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        super.onActivityResult(requestCode, responseCode, data);
        callbackManager.onActivityResult(requestCode, responseCode, data);
        Toast.makeText(getApplicationContext(), "process succeeded", Toast.LENGTH_LONG).show();

    }


}
