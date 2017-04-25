package com.trafengineproject.owner.traff_engine;

/**
 * Created by Raymond Klutse Ewoenam on 21/04/2017.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;



public class BackgroundTask extends AsyncTask<String,Void,String> {

    Context context;

    BackgroundTask(Context context) {
        this.context = context;
    }


    @Override
    protected String doInBackground(String... params) {

        String method =  params[0];

        String insertdata_url = "http://192.168.1.149/userinfo.php";
        String searchinfo_url = "http://192.168.1.149/trafficsearch.php";

        if (method.equals("insertdata")) {
            String currentLatitude = params[1];
            String currentLongitude =  params[2];
            String currentSpeed =  params[3];
            String city =  params[4];
            String road_name =  params[5];
            String cur_date=  params[6];
            String cur_time=  params[7];
            try {

                URL url = new URL(insertdata_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("currentLatitude","UTF-8")+"="+URLEncoder.encode(currentLatitude,"UTF-8")+"&"
                        +URLEncoder.encode("currentLongitude","UTF-8")+"="+URLEncoder.encode(currentLongitude,"UTF-8")+"&"
                        +URLEncoder.encode("currentSpeed","UTF-8")+"="+URLEncoder.encode(currentSpeed,"UTF-8")+"&"
                        +URLEncoder.encode("city","UTF-8")+"="+URLEncoder.encode(city, "UTF-8")+"&"
                        +URLEncoder.encode("road_name","UTF-8")+"="+URLEncoder.encode(road_name,"UTF-8")+"&"
                        +URLEncoder.encode("cur_date","UTF-8")+"="+URLEncoder.encode(cur_date,"UTF-8")+"&"
                        +URLEncoder.encode("cur_time","UTF-8")+"="+URLEncoder.encode(cur_time,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();


                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null){
                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }


        }
        if(method.equals("searchinfo")){
            String searchLatitude = params[1];
            String searchLongitude =  params[2];


            try {
                URL url = new URL(searchinfo_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String search_data = URLEncoder.encode("searchLatitude","UTF-8")+"="+URLEncoder.encode(searchLatitude,"UTF-8")+"&"
                        +URLEncoder.encode("searchLongitude","UTF-8")+"="+URLEncoder.encode(searchLongitude,"UTF-8");

                bufferedWriter.write(search_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();


                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null){
                    response+=line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;
            }

            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (ProtocolException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }


        }
        return null;



    }


    @Override
    protected void onPreExecute () {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute (String result){
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate (Void...values){
        super.onProgressUpdate(values);
    }

}
