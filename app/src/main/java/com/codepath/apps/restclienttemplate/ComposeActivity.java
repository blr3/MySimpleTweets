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

// what is in the appCompactActivity class?

/*
This is an activity created to compose a tweet. A screen is composed of an activity and an activity is a single focused thing a user
can do on the screen. so on this particular screen we want it to display a edit text box where the user can write a tweet and we want
to send the tweet back to the timeline. so the one thing the user can do, the activity is make/write a tweet.
 */

public class ComposeActivity extends AppCompatActivity {

    // created a variable named etmessage of type edit text because we want a place where the user can enter their tweet
    EditText etMessage;

    // every activity always call the onCreate method which is what goes on before the screen is seen by the user
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        we do super.onCreate because we want to not only run our code that we create but the code from the super class
         we extend the existing code that's already in super
         */
        super.onCreate(savedInstanceState);
        /*
        R stands for resources or res for short the folder where the xml is located. layout means design it is also a folder where
        the specific xml is located. then the specified xml you are referencing is attached. overall setContentView displays the screen
         design/layout of the activity
         */
        setContentView(R.layout.activity_compose);

        /*
        view occupies a rectangular area on a screen that is responsible for drawing and event handling. id is used to find
        specific view in the view tree. findViewById is going to find the view (edit text) by the unique id etwh2. since findView
        returns a view we have to cast it so that it returns an edit text into the variable etMessage. so now inside the variable
         etMessage we has an edit text view with the hin "whats happening"
         */
        etMessage = (EditText) findViewById(R.id.etwh2);
    }

/* this method is called in order to create a tweet. above we prepared the screen to display an area where we can create a tweet
 in order to create a tweet we have to manipulate the view. so for the create tweet method we pass a view in as the argument and
 name it view. we do this because the edit text is a view and the tweet is a view right??
 */
    public void createTweet(View view) {
        // client = caller of the method/service in this case the client is calling the webservice sending a request to the website
        /*
        twitter api is a rest (representational state transfer api. get is what you use to consume data. so by calling this method we are saying
        we want to get data from the twitter app and place it in this activity.
         */
        TwitterClient client = TwitterApp.getRestClient(this);
        /*
        send tweet is a method we created and we a calling it on client. first argument of send tweet is of type string. value of returns the string representation
        of whats inside. etMessage is a view so we call get text bc get text is used to get text from the text field however the text is not a string. which
        is why we have to get the string value of it.
        json handler is used to interpret and handle responses and request made using asynchttp
         */
        client.sendTweet(String.valueOf(etMessage.getText()), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.d("TwitterClient", response.toString());
                        /*
                        if successful we want to create a tweet object named tweet and assign it to This function and its methods read content in JSON format and
                        de-serializes it into R objects.
                         */
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
