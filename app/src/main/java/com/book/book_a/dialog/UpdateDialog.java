package com.book.book_a.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.book.book_a.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.os.Environment.MEDIA_MOUNTED;

public class UpdateDialog {


    private ProgressBar progress;
    private TextView progressText;
    private Dialog dialog;

    private Activity activity;

    public UpdateDialog(Activity activity) {
        this.activity = activity;

        dialog = new Dialog(activity, R.style.fullscreenNotTitle);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        }
        dialog.setCanceledOnTouchOutside(false);

        View contentView = LayoutInflater.from(activity).inflate(R.layout.app_update_pop, null);

        progress = contentView.findViewById(R.id.update_progress);
        progressText = contentView.findViewById(R.id.progress_text);

        FILE_PATH =getCachePath(activity,"MyCache/videocache/");

        FILE_NAME = FILE_PATH + "temp.apk";

        dialog.setContentView(contentView);

        dialog.setOnKeyListener((dialog, keyCode, event) -> true);
    }


    public void show(String url) {

        if (dialog.isShowing()) return;

        dialog.show();

        new DownloadAsyncTask().execute(url);

    }



    /**
     * 获取缓存根路径
     *
     * @return
     */

    public static String getCachePath(Activity activity,String dir) {
        String directoryPath = "";
        //判断SD卡是否可用
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            directoryPath = activity.getExternalFilesDir(dir).getAbsolutePath();
            // directoryPath =context.getExternalCacheDir().getAbsolutePath() ;
        } else {
            //没内存卡就存机身内存
            directoryPath = activity.getFilesDir() + File.separator + dir;
            // directoryPath=context.getCacheDir()+File.separator+dir;
        }
        File file = new File(directoryPath);
        if (!file.exists()) {//判断文件目录是否存在
            file.mkdirs();
        }
        return directoryPath;
    }


    private static String FILE_PATH ="";


    private static String FILE_NAME = "";

    private static final int INSTALL_TOKEN = 1;

    /**
     * 下载新版本应用
     */
    class DownloadAsyncTask extends AsyncTask<String, Integer, Integer> {

        @Override
        protected Integer doInBackground(String... params) {

            URL url;
            HttpURLConnection connection = null;
            InputStream in = null;
            FileOutputStream out = null;
            try {
                url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();

                if (connection.getResponseCode() == 200) {
                    in = connection.getInputStream();
                    long fileLength = connection.getContentLength();
                    File file_path = new File(FILE_PATH);
                    if (!file_path.exists()) {
                        file_path.mkdir();
                    }
                    out = new FileOutputStream(new File(FILE_NAME));//为指定的文件路径创建文件输出流
                    byte[] buffer = new byte[1024 * 1024];
                    int len = 0;
                    long readLength = 0;

                    while ((len = in.read(buffer)) != -1) {

                        out.write(buffer, 0, len);//从buffer的第0位开始读取len长度的字节到输出流
                        readLength += len;

                        int curProgress = (int) (((float) readLength / fileLength) * 100);

                        publishProgress(curProgress);

                        if (readLength >= fileLength) {
                            break;
                        }
                    }

                    out.flush();
                    return INSTALL_TOKEN;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (connection != null) {
                    connection.disconnect();
                }
            }
            return 0;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progress.setProgress(values[0]);
            progressText.setText(values[0]  + "/100");
        }


        @Override
        protected void onPostExecute(Integer integer) {
            if (integer == INSTALL_TOKEN) {
                //安装应用
                installApp(activity, FILE_NAME, "com.org.temp.fileprovider");
                dialog.dismiss();
            } else {
                //downloadErr();
            }
        }
    }

    public static void installApp(Activity activity, String fileName, String authority) {
        File appFile = new File(fileName);
        if (!appFile.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(activity, authority,
                    appFile);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(appFile),
                    "application/vnd.android.package-archive");
        }
        activity.startActivity(intent);
    }


}
