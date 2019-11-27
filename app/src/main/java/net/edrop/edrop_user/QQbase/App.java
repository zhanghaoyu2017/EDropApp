package net.edrop.edrop_user.QQbase;

import android.app.Application;
import android.util.Log;

import com.vise.log.ViseLog;
import com.vise.log.inner.DefaultTree;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;

import net.edrop.edrop_user.utils.SharedPreferencesUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


public class App extends Application {

    private static SharedPreferencesUtils shared;

    public static SharedPreferencesUtils getShared() {
        return shared;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        shared = new SharedPreferencesUtils(this,"Login");
        initLogs();
        initTest();
    }

    private void initTest() {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }

    /**
     * 初始化日志工具
     */
    private void initLogs() {
//        ViseLog.init("Supplier");
        ViseLog.getLogConfig()
                .configAllowLog(true)//是否输出日志
                .configShowBorders(true)//是否排版显示
                .configTagPrefix("ViseLog")//设置标签前缀
                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}")//个性化设置标签，默认显示包名
                .configLevel(Log.VERBOSE);//设置日志最小输出级别，默认Log.VERBOSE
        ViseLog.plant(new DefaultTree());//添加打印日志信息到Logcat的树
    }

}
