package serry.appsinnovatetask.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import serry.appsinnovatetask.R;

public class MainActivity extends AppCompatActivity {
    private Intent intent;

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

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

}
