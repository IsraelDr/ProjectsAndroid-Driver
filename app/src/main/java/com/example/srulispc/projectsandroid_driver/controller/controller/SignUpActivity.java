package com.example.srulispc.projectsandroid_driver.controller.controller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.srulispc.projectsandroid_driver.R;
import com.example.srulispc.projectsandroid_driver.controller.model.backend.BackendFactory;
import com.example.srulispc.projectsandroid_driver.controller.model.backend.Ibackend;
import com.example.srulispc.projectsandroid_driver.controller.model.entities.Driver;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A login screen that offers login via email/password.
 */
public class SignUpActivity extends AppCompatActivity{

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    FirebaseAuth firebaseAuth;
    private View mProgressView;
    private View mLoginFormView;
    private Ibackend backend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseAuth = FirebaseAuth.getInstance();
        backend= BackendFactory.getInstance();
        Button signup = (Button) findViewById(R.id.btn_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignup();
            }
        });

        mLoginFormView = findViewById(R.id.signup_form);
        mProgressView = findViewById(R.id.signup_progress);
    }

    private void attemptSignup() {

        // Reset errors.
        TextView midentitynumberView=findViewById(R.id.input_identitynumber);
        TextView mfirstnameView=findViewById(R.id.input_firstname);
        TextView mlastnameView=findViewById(R.id.input_lastname);
        TextView mpasswordView=findViewById(R.id.input_password);
        TextView mverifpasswordView=findViewById(R.id.input_verifpassword);
        TextView mcreditcardView=findViewById(R.id.input_creditcard);
        TextView memailView=findViewById(R.id.input_email);
        TextView mphonenumberView=findViewById(R.id.input_phonenumber);
        midentitynumberView.setError(null);
        mfirstnameView.setError(null);
        mlastnameView.setError(null);
        mpasswordView.setError(null);
        mverifpasswordView.setError(null);
        mcreditcardView.setError(null);
        memailView.setError(null);
        mphonenumberView.setError(null);
        // Store values at the time of the login attempt.
        final int identitynumber=Integer.parseInt(midentitynumberView.getText().toString());
        final String email = memailView.getText().toString();
        String password = mpasswordView.getText().toString();
        final long creditcard = Long.parseLong(mcreditcardView.getText().toString());
        String verifpassword = mverifpasswordView.getText().toString();
        final String firstname = mfirstnameView.getText().toString();
        final String lastname = mlastnameView.getText().toString();
        final int phonenumber=Integer.parseInt(mphonenumberView.getText().toString());

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mpasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mpasswordView;
            cancel = true;
        } else if(!password.equals(verifpassword)){
            mverifpasswordView.setError(getString(R.string.error_passwords_dont_match));
            focusView = mverifpasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            memailView.setError(getString(R.string.error_field_required));
            focusView = memailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            memailView.setError(getString(R.string.error_invalid_email));
            focusView = memailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            memailView.setError(getString(R.string.error_invalid_email));
            focusView = memailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Driver newDriver=new Driver(identitynumber,firstname,lastname,email,phonenumber,creditcard);
                                backend.addDriver(newDriver);
                                if(firebaseAuth.getCurrentUser()!=null){
                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                }
                                //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                //finish();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"E-mail or password is wrong",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }



    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */

}

