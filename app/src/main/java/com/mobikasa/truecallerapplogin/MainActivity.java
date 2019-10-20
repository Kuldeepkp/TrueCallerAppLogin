package com.mobikasa.truecallerapplogin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.truecaller.android.sdk.ITrueCallback;
import com.truecaller.android.sdk.TrueError;
import com.truecaller.android.sdk.TrueProfile;
import com.truecaller.android.sdk.TrueSDK;
import com.truecaller.android.sdk.TrueSdkScope;

public class MainActivity extends AppCompatActivity implements ITrueCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TrueSdkScope trueScope = new TrueSdkScope.Builder(this, this)
                .consentMode(TrueSdkScope.CONSENT_MODE_POPUP )
                .consentTitleOption( TrueSdkScope.SDK_CONSENT_TITLE_VERIFY )
                .footerType( TrueSdkScope.FOOTER_TYPE_SKIP )
                .build();
        TrueSDK.init(trueScope);

        findViewById(R.id.chck_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TrueSDK.getInstance().isUsable()){
                    TrueSDK.getInstance().getUserProfile(MainActivity.this);
                }else {

                }
            }
        });

    }

    @Override
    public void onSuccessProfileShared(@NonNull TrueProfile trueProfile) {

        Toast.makeText(this, ""+trueProfile.firstName, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFailureProfileShared(@NonNull TrueError trueError) {
        Toast.makeText(this, ""+trueError.getErrorType(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onVerificationRequired() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TrueSDK.getInstance().onActivityResultObtained( this,resultCode, data);
    }
}
