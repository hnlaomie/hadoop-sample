/**
 * @Company:
 * @Author: lancer
 * @Date: 14-12-9 下午5:26
 * @Version: 1.0
 */
package com.github.sample.hadoop;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;


/**
 * <b>HadoopTest</b>
 * <p><b>详细说明：</b></p>
 * 在这里添加详细说明
 * <p><b>修改列表：</b></p>
 * <table width="" cellspacing=1 cellpadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!--在此添加修改列表，参考第一行内容-->
 * <tr bgcolor="#CCCCFF"><td>1</td><td>lancer</td><td>14-12-9 下午5:26</td><td>新建内容</td></tr>
 */
public class HadoopTest {

    public static void main(String[] args) {
        String uri = "hdfs://localhost:9000/lancer/data/get-pip.py";
        String output = "hdfs://localhost:9000/user/lancer/data/output2";

        readHadoop2(uri);
    }

    static void readHadoop(String uri) {
        InputStream in = null;
        Configuration configuration = new Configuration();
        try {
            FileSystem fs = FileSystem.get(URI.create(uri), configuration);
            in = fs.open(new Path(uri));
            IOUtils.copyBytes(in, System.out, 4096, false);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeStream(in);
        }
    }

    static void readHadoop2(String uri) {
        // must be setting this config
        URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
        InputStream in = null;
        try {
            in = new URL(uri).openStream();
            IOUtils.copyBytes(in, System.out, 4096, false);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeStream(in);
        }
    }

}
