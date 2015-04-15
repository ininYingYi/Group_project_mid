package com.example.group_projectmid;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inin.dataType.postDataFormat;
import com.inin.dataType.userData;

import android.util.Log;
import android.view.Gravity;
import android.widget.TableRow;
import android.widget.TextView;

public class DataBaseConnector {
	private static boolean DeBugMode = true;
	private static userData user = new userData();

	public static int logIn(String account, String password) {
		if (DeBugMode) {
			user.ID = "100502521";
			user.name = "陳映亦";
			user.type = 0;
			// -1 is error
			// 0 is student
			// 1 is teacher
			return user.type;
		} else {
			String result = "";
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(
						"http://10.0.2.2/android_sql/logIn.php");
				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("account", account));
				params.add(new BasicNameValuePair("password", password));
				httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				HttpResponse httpResponse = httpClient.execute(httpPost);
				// view_account.setText(httpResponse.getStatusLine().toString());
				HttpEntity httpEntity = httpResponse.getEntity();
				InputStream inputStream = httpEntity.getContent();

				BufferedReader bufReader = new BufferedReader(
						new InputStreamReader(inputStream, "utf-8"), 8);
				StringBuilder builder = new StringBuilder();
				String line = null;
				while ((line = bufReader.readLine()) != null) {
					builder.append(line + "\n");
				}
				inputStream.close();
				result = builder.toString();
			} catch (Exception e) {
				Log.e("log_tag_DB", e.toString());
			}
			try {
				JSONObject jsonData = new JSONObject(result);
				user.name = jsonData.getString("name");
				user.ID = jsonData.getString("school_ID");
				user.type = jsonData.getInt("type");
				return jsonData.getInt("type");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.e("JSONObject", e.toString());
				return -1;
			}
		}
	}

	public static postDataFormat[] getPosts() {
		postDataFormat value[] = null;
		String result = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(
					"http://10.0.2.2/android_sql/getPost.php");
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("classId", String.valueOf(1)));
			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse httpResponse = httpClient.execute(httpPost);

			HttpEntity httpEntity = httpResponse.getEntity();
			InputStream inputStream = httpEntity.getContent();

			BufferedReader bufReader = new BufferedReader(
					new InputStreamReader(inputStream, "utf-8"), 8);
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = bufReader.readLine()) != null) {
				builder.append(line + "\n");
			}
			inputStream.close();
			result = builder.toString();
		} catch (Exception e) {
			Log.e("log_tag_DB", e.toString());
		}
		Log.e("log_tag_DB", result);
		try {
			JSONArray jsonArray = new JSONArray(result);
			value = new postDataFormat[jsonArray.length()];
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonData = jsonArray.getJSONObject(i);
                value[i] = new postDataFormat();
                value[i].id = jsonData.getInt("id");
                value[i].title = jsonData.getString("title");
                value[i].message = jsonData.getString("message");
                value[i].teacher = "teacher";
                value[i].time = jsonData.getString("submit_time");
                value[i].date = jsonData.getString("date");
            }
		} catch (JSONException e) {
			Log.e("JSONObject", e.toString());
		}

		return value;
	}

	public static userData getUserData() {
		if (DeBugMode) {
			user.ID = "100502521";
			user.name = "陳映亦";
			user.type = 0;
		}
		return user;
	}

}
