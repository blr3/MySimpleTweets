package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    EditText etMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        etMessage = (EditText) findViewById(R.id.etwh2);
    }


    public void createTweet(View view) {
        TwitterClient client = TwitterApp.getRestClient(this);
        client.sendTweet(String.valueOf(etMessage.getText()), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.d("TwitterClient", response.toString());
                        Tweet tweet = Tweet.fromJSON(response);

                        Intent i = new Intent();
                        i.putExtra("new tweet", Parcels.wrap(tweet));
                        setResult(RESULT_OK, i);
                        finish();
                    }
                }
        );
    }
}
