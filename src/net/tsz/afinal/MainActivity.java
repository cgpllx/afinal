package net.tsz.afinal;

import java.io.File;
import java.io.FileNotFoundException;

import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import net.tsz.afinal.http.HttpHandler;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends FinalActivity {
	@ViewInject(id = R.id.action_settings)
    private Button button1;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void test1() {
		final TextView textView = null;
		FinalHttp fh = new FinalHttp();
		//		fh.d
		//调用download方法开始下载
		HttpHandler<File> handler = fh.download("http://www.xxx.com/下载路径/xxx.apk", //这里是下载的路径
				//true:断点续传 false:不断点续传（全新下载）
				"/mnt/sdcard/testapk.apk", true,//这是保存到本地的路径
				new AjaxCallBack<File>() {
					@SuppressWarnings("null")
					@Override
					public void onLoading(long count, long current) {
						textView.setText("下载进度：" + current + "/" + count);
					}

					@SuppressWarnings("null")
					@Override
					public void onSuccess(File t) {
						textView.setText(t == null ? "null" : t.getAbsoluteFile().toString());
					}

				});

		//调用stop()方法停止下载
		handler.stop();

	}

	public void test2() {
		try {
			final TextView textView = new TextView(this);
			AjaxParams params = new AjaxParams();
			params.put("username", "michael yang");
			params.put("password", "123456");
			params.put("email", "test@tsz.net");

			params.put("profile_picture", new File("/mnt/sdcard/pic.jpg"));
			// 上传文件
			//		params.put("profile_picture2", inputStream); // 上传数据流
			//		params.put("profile_picture3", new ByteArrayInputStream(bytes)); // 提交字节流

			FinalHttp fh = new FinalHttp();
			fh.post("http://www.yangfuhai.com", params, new AjaxCallBack<String>() {
				@Override
				public void onLoading(long count, long current) {
					textView.setText(current + "/" + count);
				}

				@Override
				public void onSuccess(String t) {
					textView.setText(t == null ? "null" : t);
				}
			});
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void test3() {
		FinalHttp fh = new FinalHttp();
		fh.get("http://www.yangfuhai.com", new AjaxCallBack<String>() {

			@Override
			public void onLoading(long count, long current) { //每1秒钟自动被回调一次
				//		            textView.setText(current+"/"+count);
			}

			@Override
			public void onSuccess(String t) {
				//		            textView.setText(t==null?"null":t);
			}

			@Override
			public void onStart() {
				//开始http请求的时候回调
			}

			//			@Override
			//			public void onFailure(Throwable t, String strMsg) {
			//				//加载失败的时候回调
			//			}
		});

	}
}
