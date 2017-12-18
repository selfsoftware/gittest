package com.example.my.godutch.activity.utility;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContentResolverCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

import static android.app.Activity.RESULT_OK;


/**
 * Created by Administrator on 2017/10/28.
 */

public class UpLoad {
    private static final int TIME_OUT = 10 * 1000;
    private static String CHARSET = "UTF-8";


    public static int UpLoadFile(File file, String path) {
        int res = 0;

        /* 1.UUID.randomUUID()分隔符
        * 2.头尾的连字符PREFIX
        * 3.LINE_END换行符
        * 4.请求时的content-type指定内容的类型（multipart/form-data;boundary=分隔符）
        * */

        String BOUNDARY = UUID.randomUUID().toString();
        String PREFIX = "--";
        String LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data";
        try {
            /*
            * 通过传入的地址，new URL，并且用HttpURLConnection打开连接
            * 设置连接参数
            * setConnectTimeout，setReadTimeout设置连接以及读取超时
            * setDoInput，setDoOutput打开连接的输入输出流
            * setRequestMethod设置方法，上传时setUseCaches关闭缓存防止出错
            * setRequestProperty设置请求头信心
            * */
            URL url = new URL(path);
            HttpURLConnection httpURLC = (HttpURLConnection) url.openConnection();
            httpURLC.setDoInput(true);
            httpURLC.setDoOutput(true);
            httpURLC.setRequestMethod("POST");
            httpURLC.setUseCaches(false);
            httpURLC.setConnectTimeout(TIME_OUT);
            httpURLC.setReadTimeout(TIME_OUT);
            httpURLC.setRequestProperty("Charset", CHARSET);
            httpURLC.setRequestProperty("Content-type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
            httpURLC.setRequestProperty("Context", "Keep-Alive");
            if (file != null) {
                /*
                * DataOutputStream通过连接打开上层输出流
                * 通过StringBuffer拼接上传文件开始符以及文件信息
                *   文件信息格式：
                *       1.Content-Disposition: form-data name = "file"; filename=""+file.getName()+"\"";
                *       2.Content-Type: application/octet-stream;Charset ="
                * 注意换行
                * */
                DataOutputStream dos = new DataOutputStream(httpURLC.getOutputStream());
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX + BOUNDARY + LINE_END);
                sb.append("Content-Disposition: form-data ; name = \"file\"; filename=\"" + file.getName() + "\"" + LINE_END);
                sb.append("Content-Type: application/octet-stream;Charset =" + CHARSET + LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                /*
                * FileInputStream(file)通过文件打开输入流读取文件，同时写入到上传地址
                * 最后注意流的关闭
                * */
                InputStream is = new FileInputStream(file);
                int len = 0;
                byte[] bytes = new byte[1024];
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                //结束符=开始符+连字符+换行符
                String END = PREFIX + BOUNDARY + PREFIX + LINE_END;
                dos.write(END.getBytes());
                dos.flush();
                dos.close();
                //最后通过httpURLC.getResponseCode() 反馈上传信息
                res = httpURLC.getResponseCode();
                if (res == 200) {
                    Log.i("TEST", "上传成功");
                } else {
                    Log.i("TEST", "上传失败");
                }
                httpURLC.disconnect();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }


    //    public void chooseFile(){
//        //通过INTENT,获取文件目录
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        //设置选着文件的类型，setType("***/*//*//**//*")为任意类型;addCategory增加一个可打开的分类
//        intent.setType("***/*//*//**//*");
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        //通过startActivityForResult，回传选着的文件
//        startActivityForResult(intent,1);
//        }
//    //接收startActivityForResult结果，并进一步处理
//        public  onActivityResult(int requestCode, int resultCode,Intent data){
//        switch (requestCode){
//            case 1:
//                if (resultCode == RESULT_OK){
//                    //获取数据路径
//                    Uri uri = data.getData() ;
//                    String path = uri.getPath();
//                }
//                break;
//            }
//        }
//

}
