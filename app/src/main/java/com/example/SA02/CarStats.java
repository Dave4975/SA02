package com.example.SA02;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.SA02.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.ref.WeakReference;
import java.util.HashMap;

public class CarStats extends AppCompatActivity {

    private TextView mAvReturnText;
    private TextView mUrbanMpg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_stats);

        mAvReturnText = (TextView)findViewById(R.id.AvMilesPerGallon);
        mUrbanMpg = (TextView)findViewById(R.id.UrbanMpg);
    }
    public void fetchData(View view) {
        new CarAsync(mAvReturnText, mUrbanMpg).execute("");
        mAvReturnText.setText(R.string.loading);
        mUrbanMpg.setText(R.string.loading);
    }


    private class CarAsync extends AsyncTask<String, Void, String> {

        private WeakReference<TextView> mAvReturn;
        private WeakReference<TextView> mUReturn;
        CarAsync(TextView avMpg, TextView uMpg) {
            this.mAvReturn = new WeakReference<>(avMpg);
            this.mUReturn = new WeakReference<>(uMpg);
        }

        @Override
        protected String doInBackground(String... strings) {
            return com.example.SA02.NetworkUtils.getMpgInfo(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String aMpg = null;
            String uMpg = null;

            try {
                HashMap<String, String> data = parseXml(s);


                try {
                    aMpg = data.get("avgMpg");
                    uMpg = data.get("cityPercent");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(aMpg != null && uMpg != null){
                    mAvReturn.get().setText(aMpg);
                    mUReturn.get().setText(uMpg);
                } else {
                    mAvReturn.get().setText(R.string.no_results);
                    mUReturn.get().setText(R.string.no_results);
                }

            }catch (Exception e) {
                // If onPostExecute does not receive a proper JSON string,
                // update the UI to show failed results.
                mAvReturn.get().setText(R.string.no_results);
                mUReturn.get().setText(R.string.no_results);
            }

        }

        public HashMap<String, String> parseXml(String xml) {
            XmlPullParserFactory factory;
            String tagName = "";
            String text = "";
            HashMap<String, String> hm = new HashMap<String, String>();

            try {
                factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                StringReader sr = new StringReader(xml);
                xpp.setInput(sr);
                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.TEXT) {
                        text = xpp.getText(); //Pulling out node text
                    } else if (eventType == XmlPullParser.END_TAG) {
                        tagName = xpp.getName();

                        hm.put(tagName, text);

                        text = ""; //Reset text for the next node
                    }
                    eventType = xpp.next();
                }
            }
            catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception e) {
                Log.d("Exception attribute", e + "+" + tagName);
            }

            return hm;
        }


    }
}
