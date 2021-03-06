package com.example.SA02;

import android.content.Context;
import android.icu.text.NumberFormat;
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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.HashMap;

public class CarStats extends AppCompatActivity {

    private TextView mAvReturnText;
    private TextView mUrbanMpg;
    private TextView mEUrbanMpg;
    private TextView mKpl;
    private TextView mMakeReturnText;
    private TextView mModelReturnText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_stats);

        mAvReturnText = (TextView)findViewById(R.id.AvMilesPerGallon);
        mUrbanMpg = (TextView)findViewById(R.id.UrbanMpg);
        mEUrbanMpg = (TextView)findViewById(R.id.ExtraUrbanMpg);
        mKpl = (TextView)findViewById(R.id.KilometresPerLitre);
        mMakeReturnText = (TextView)findViewById(R.id.CarMake);
        mModelReturnText = (TextView)findViewById(R.id.CarModel);
    }


    public void fetchData(View view) {
        new CarAsync(mAvReturnText, mUrbanMpg, mEUrbanMpg, mKpl, mMakeReturnText, mModelReturnText).execute("");
        mAvReturnText.setText(R.string.loading);
        mUrbanMpg.setText(R.string.loading);
        mEUrbanMpg.setText(R.string.loading);
        mKpl.setText(R.string.loading);
        mMakeReturnText.setText(R.string.loading);
        mModelReturnText.setText(R.string.loading);
    }


    private class CarAsync extends AsyncTask<String, Void, String[]> {

        private WeakReference<TextView> mAvReturn;
        private WeakReference<TextView> mUReturn;
        private WeakReference<TextView> mEuReturn;
        private WeakReference<TextView> mKplReturn;
        private WeakReference<TextView> mMakeReturn;
        private WeakReference<TextView> mModelReturn;
        CarAsync(TextView avMpg, TextView uMpg, TextView eUMpg, TextView kPl, TextView make, TextView model) {
            this.mAvReturn = new WeakReference<>(avMpg);
            this.mUReturn = new WeakReference<>(uMpg);
            this.mEuReturn = new WeakReference<>(eUMpg);
            this.mKplReturn = new WeakReference<>(kPl);
            this.mMakeReturn = new WeakReference<>(make);
            this.mModelReturn = new WeakReference<>(model);
        }

        @Override
        protected String[] doInBackground(String... strings) {
            String[] test = new String[2];
            test[0] = com.example.SA02.NetworkUtils.getMpgInfo(strings[0]);
            test[1] = com.example.SA02.NetworkUtils.getModelInfo(strings[0]);
            return test;
        }


        @Override
        protected void onPostExecute(String[] s) {
            super.onPostExecute(s);
            String aMpg = null;
            String uMpg = null;
            String eUMpg = null;
            double kPl = 0;
            String make = null;
            String model = null;

            try {
                HashMap<String, String> data = parseXml(s[0]);
                HashMap<String, String> data1 = parseXml(s[1]);

                try {
                    aMpg = data.get("avgMpg");
                    uMpg = data.get("cityPercent");
                    eUMpg = data.get("highwayPercent");
                    make = data1.get("make");
                    model = data1.get("model");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(aMpg != null && uMpg != null && eUMpg != null && make != null && model != null){
                    DecimalFormat nm = new DecimalFormat("#.##");
                    aMpg = nm.format(Double.valueOf(aMpg));
                    mAvReturn.get().setText(aMpg);
                    mUReturn.get().setText(uMpg);
                    mEuReturn.get().setText(eUMpg);

                    Calculator calc = new Calculator();
                    kPl = Double.valueOf(nm.format(calc.MPGtoKPL(Double.valueOf(aMpg))));
                    mKplReturn.get().setText(String.valueOf(kPl));
                    mMakeReturn.get().setText(make);
                    mModelReturn.get().setText(model);
                }
                else {
                    mAvReturn.get().setText(R.string.no_results);
                    mUReturn.get().setText(R.string.no_results);
                    mEuReturn.get().setText(R.string.no_results);
                    mKplReturn.get().setText(R.string.no_results);
                    mMakeReturn.get().setText(R.string.no_results);
                    mModelReturn.get().setText(R.string.no_results);
                }

            }catch (Exception e) {
                // update the UI to show failed results.
                mAvReturn.get().setText(R.string.no_results);
                mUReturn.get().setText(R.string.no_results);
                mEuReturn.get().setText(R.string.no_results);
                mKplReturn.get().setText(R.string.no_results);
                mMakeReturn.get().setText(R.string.no_results);
                mModelReturn.get().setText(R.string.no_results);
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

        //event types
        //3 = end tag
        //4 = data

    }
}
