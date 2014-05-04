package com.baidu.push.example;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.android.common.logging.Log;
import com.baidu.android.pushservice.richmedia.MediaListActivity;
import com.baidu.android.pushservice.CustomPushNotificationBuilder;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PushDemoActivity extends Activity {

	RelativeLayout mainLayout = null;
	TextView infoText = null;
	Button initButton = null;
	Button initWithApiKey = null;
	Button displayRichMedia = null;
	Button setTags = null;
	Button delTags = null;
	public static int initialCnt = 0;
	private boolean isLogin = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Resources resource = this.getResources();
		String pkgName = this.getPackageName();
		
		setContentView(resource.getIdentifier("main", "layout", pkgName));
		
		initWithApiKey = (Button) findViewById(resource.getIdentifier("btn_initAK", "id", pkgName));
		initButton = (Button) findViewById(resource.getIdentifier("btn_init", "id", pkgName));
		displayRichMedia = (Button) findViewById(resource.getIdentifier("btn_rich", "id", pkgName));
		setTags = (Button) findViewById(resource.getIdentifier("btn_setTags", "id", pkgName));
		delTags = (Button) findViewById(resource.getIdentifier("btn_delTags", "id", pkgName));
		initWithApiKey.setOnClickListener(initApiKeyButtonListener());
		initButton.setOnClickListener(initButtonListner());
		setTags.setOnClickListener(setTagsButtonListner());
		delTags.setOnClickListener(deleteTagsButtonListner());
		displayRichMedia.setOnClickListener(richMediaButtonListner());
		
		// 以apikey的方式登录，一般放在主Activity的onCreate中
		PushManager.startWork(getApplicationContext(),
				PushConstants.LOGIN_TYPE_API_KEY, 
				Utils.getMetaValue(PushDemoActivity.this, "api_key"));
		
		
		//设置自定义的通知样式，如果想使用系统默认的可以不加这段代码
        CustomPushNotificationBuilder cBuilder = new CustomPushNotificationBuilder(
        		resource.getIdentifier("notification_custom_builder", "layout", pkgName), 
        		resource.getIdentifier("notification_icon", "id", pkgName), 
        		resource.getIdentifier("notification_title", "id", pkgName), 
        		resource.getIdentifier("notification_text", "id", pkgName));
        cBuilder.setNotificationFlags(Notification.FLAG_AUTO_CANCEL);
        cBuilder.setNotificationDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        cBuilder.setStatusbarIcon(this.getApplicationInfo().icon);
        cBuilder.setLayoutDrawable(resource.getIdentifier("simple_notification_icon", "drawable", pkgName));
		PushManager.setNotificationBuilder(this, 1, cBuilder);
	}

	private OnClickListener richMediaButtonListner() {
		return new View.OnClickListener() {

			public void onClick(View v) {
				Intent sendIntent = new Intent();
				sendIntent.setClass(PushDemoActivity.this,
						MediaListActivity.class);
				sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				PushDemoActivity.this.startActivity(sendIntent);
			}
		};
	}

	private OnClickListener deleteTagsButtonListner() {
		return new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				LinearLayout layout = new LinearLayout(PushDemoActivity.this);
				layout.setOrientation(LinearLayout.VERTICAL);

				final EditText textviewGid = new EditText(PushDemoActivity.this);
				textviewGid.setHint("请输入多个标签，以英文逗号隔开");
				layout.addView(textviewGid);

				AlertDialog.Builder builder = new AlertDialog.Builder(
						PushDemoActivity.this);
				builder.setView(layout);
				builder.setPositiveButton("删除标签",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {

								List<String> tags = getTagsList(textviewGid
										.getText().toString());

								PushManager.delTags(getApplicationContext(),
										tags);

							}
						});
				builder.show();

			}
		};
	}

	private OnClickListener setTagsButtonListner() {
		return new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 设置标签,以英文逗号隔开
				LinearLayout layout = new LinearLayout(PushDemoActivity.this);
				layout.setOrientation(LinearLayout.VERTICAL);

				final EditText textviewGid = new EditText(PushDemoActivity.this);
				textviewGid.setHint("请输入多个标签，以英文逗号隔开");
				layout.addView(textviewGid);

				AlertDialog.Builder builder = new AlertDialog.Builder(
						PushDemoActivity.this);
				builder.setView(layout);
				builder.setPositiveButton("设置标签",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {

								List<String> tags = getTagsList(textviewGid
										.getText().toString());
								PushManager.setTags(getApplicationContext(),
										tags);
							}

						});
				builder.show();

			}
		};
	}

	private OnClickListener initApiKeyButtonListener() {
		return new View.OnClickListener() {

			public void onClick(View v) {
				// 以apikey的方式登录
				PushManager.startWork(getApplicationContext(),
						PushConstants.LOGIN_TYPE_API_KEY, 
						Utils.getMetaValue(PushDemoActivity.this, "api_key"));
			}
		};
	}

	private OnClickListener initButtonListner() {
		return new View.OnClickListener() {

			public void onClick(View v) {
				if (isLogin) {
					// 已登录则清除Cookie, access token, 设置登录按钮
					CookieSyncManager.createInstance(getApplicationContext());
					CookieManager.getInstance().removeAllCookie();
					CookieSyncManager.getInstance().sync();

					isLogin = false;
					initButton.setText("登陆百度账号初始化Channel");
				}
				// 跳转到百度账号登录的activity
				Intent intent = new Intent(PushDemoActivity.this,
						LoginActivity.class);
				startActivity(intent);
			}
		};
	}

	@Override
	public void onStart() {
		super.onStart();

		PushManager.activityStarted(this);
	}

	@Override
	public void onResume() {
		super.onResume();

		showChannelIds();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// 如果要统计Push引起的用户使用应用情况，请实现本方法，且加上这一个语句
		setIntent(intent);

		handleIntent(intent);
	}

	@Override
	public void onStop() {
		super.onStop();
		PushManager.activityStoped(this);
	}

	/**
	 * 处理Intent
	 * 
	 * @param intent
	 *            intent
	 */
	private void handleIntent(Intent intent) {
		String action = intent.getAction();

		if (Utils.ACTION_RESPONSE.equals(action)) {

			String method = intent.getStringExtra(Utils.RESPONSE_METHOD);

			if (PushConstants.METHOD_BIND.equals(method)) {
				String toastStr = "";
				int errorCode = intent.getIntExtra(Utils.RESPONSE_ERRCODE, 0);
				if (errorCode == 0) {
					String content = intent
							.getStringExtra(Utils.RESPONSE_CONTENT);
					String appid = "";
					String channelid = "";
					String userid = "";

					try {
						JSONObject jsonContent = new JSONObject(content);
						JSONObject params = jsonContent
								.getJSONObject("response_params");
						appid = params.getString("appid");
						channelid = params.getString("channel_id");
						userid = params.getString("user_id");
					} catch (JSONException e) {
						Log.e(Utils.TAG, "Parse bind json infos error: " + e);
					}

					SharedPreferences sp = PreferenceManager
							.getDefaultSharedPreferences(this);
					Editor editor = sp.edit();
					editor.putString("appid", appid);
					editor.putString("channel_id", channelid);
					editor.putString("user_id", userid);
					editor.commit();

					showChannelIds();

					toastStr = "Bind Success";
				} else {
					toastStr = "Bind Fail, Error Code: " + errorCode;
					if (errorCode == 30607) {
						Log.d("Bind Fail", "update channel token-----!");
					}
				}

				Toast.makeText(this, toastStr, Toast.LENGTH_LONG).show();
			}
		} else if (Utils.ACTION_LOGIN.equals(action)) {
			String accessToken = intent
					.getStringExtra(Utils.EXTRA_ACCESS_TOKEN);
			PushManager.startWork(getApplicationContext(),
					PushConstants.LOGIN_TYPE_ACCESS_TOKEN, accessToken);
			isLogin = true;
			initButton.setText("更换百度账号初始化Channel");
		} else if (Utils.ACTION_MESSAGE.equals(action)) {
			String message = intent.getStringExtra(Utils.EXTRA_MESSAGE);
			String summary = "Receive message from server:\n\t";
			Log.e(Utils.TAG, summary + message);
			JSONObject contentJson = null;
			String contentStr = message;
			try {
				contentJson = new JSONObject(message);
				contentStr = contentJson.toString(4);
			} catch (JSONException e) {
				Log.d(Utils.TAG, "Parse message json exception.");
			}
			summary += contentStr;
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(summary);
			builder.setCancelable(true);
			Dialog dialog = builder.create();
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
		} else {
			Log.i(Utils.TAG, "Activity normally start!");
		}
	}

	private void showChannelIds() {
		String appId = null;
		String channelId = null;
		String clientId = null;

		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		appId = sp.getString("appid", "");
		channelId = sp.getString("channel_id", "");
		clientId = sp.getString("user_id", "");
		
		Resources resource = this.getResources();
		String pkgName = this.getPackageName();
		infoText = (TextView) findViewById(resource.getIdentifier("text", "id", pkgName));

		String content = "\tApp ID: " + appId + "\n\tChannel ID: " + channelId
				+ "\n\tUser ID: " + clientId + "\n\t";
		if (infoText != null) {
			infoText.setText(content);
			infoText.invalidate();
		}
	}

	private List<String> getTagsList(String originalText) {

		List<String> tags = new ArrayList<String>();
		int indexOfComma = originalText.indexOf(',');
		String tag;
		while (indexOfComma != -1) {
			tag = originalText.substring(0, indexOfComma);
			tags.add(tag);

			originalText = originalText.substring(indexOfComma + 1);
			indexOfComma = originalText.indexOf(',');
		}

		tags.add(originalText);
		return tags;
	}
}
