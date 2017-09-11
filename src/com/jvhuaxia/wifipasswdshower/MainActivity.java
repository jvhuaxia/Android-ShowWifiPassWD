package com.jvhuaxia.wifipasswdshower;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.stericson.RootTools.RootTools;

public class MainActivity extends Activity {

    public final static String wifiPasswordConfigFileName = "wpa_supplicant.conf";
    public final static String wifiPasswordConfigFilePath = "/data/misc/wifi";
    public final static int MESSAGE_COPY_FINISH = 0;
    public final static int MESSAGE_E = 10;

    MyHandler mHandler = null;
    String MessageE = "";
    ListView wifiStatesListView = null;
    AlertDialog loadingAlertDialog;

    static class MyHandler extends Handler {
	MainActivity mainActivity = null;

	public MyHandler(MainActivity mainActivity) {
	    this.mainActivity = mainActivity;
	}

	@Override
	public void handleMessage(Message msg) {
	    super.handleMessage(msg);
	    switch (msg.what) {
	    case MESSAGE_COPY_FINISH:

		if (mainActivity.loadingAlertDialog != null)
		    mainActivity.loadingAlertDialog.cancel();

		try {
		    List<WIFIState> wifiStates = WIFIStateInflateUtils.readWIFIFile(mainActivity.getFilesDir() + "/" + MainActivity.wifiPasswordConfigFileName);
		    WifiStateAdapter adapter = new WifiStateAdapter(mainActivity, wifiStates);
		    mainActivity.wifiStatesListView.setAdapter(adapter);
		} catch (IOException e) {
		    mainActivity.MessageE = e.toString();
		    this.sendEmptyMessage(MESSAGE_E);
		}
		break;
	    case MESSAGE_E:
		Builder dialogBuilder = new Builder(mainActivity);
		dialogBuilder.setMessage("不支持你的手机\n异常为\n" + mainActivity.MessageE);
		dialogBuilder.setPositiveButton(android.R.string.ok, null);
		dialogBuilder.create().show();
		break;
	    default:
		break;
	    }
	}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	wifiStatesListView = (ListView) findViewById(R.id.listViewWifiStates);
	mHandler = new MyHandler(MainActivity.this);

	showLoadingDialog();

	new Thread() {
	    public void run() {
		try {
		    String cpWifiConfigFileShell = "/system/bin/cp " + wifiPasswordConfigFilePath + "/" + wifiPasswordConfigFileName + " " + getFilesDir() + "/" + wifiPasswordConfigFileName;
		    RootTools.sendShell(cpWifiConfigFileShell);
		    String chmodWifiConfigFileShell = "/system/bin/chmod 777 " + getFilesDir() + "/" + wifiPasswordConfigFileName;
		    RootTools.sendShell(chmodWifiConfigFileShell);
		    mHandler.sendEmptyMessage(MESSAGE_COPY_FINISH);
		} catch (Exception e) {
		    MessageE = e.toString();
		    mHandler.sendEmptyMessage(MESSAGE_E);
		}
	    }
	}.start();
    }

    private void showLoadingDialog() {
	Builder loadingAlertDialogBuilder = new Builder(MainActivity.this);
	ProgressBar progressBar = new ProgressBar(MainActivity.this);
	loadingAlertDialogBuilder.setMessage("正在加载中...");
	loadingAlertDialogBuilder.setView(progressBar);
	loadingAlertDialog = loadingAlertDialogBuilder.create();
	loadingAlertDialog.show();
    }

    public void aboutClick(View view) {

    }
}
