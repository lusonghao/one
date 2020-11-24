package com.tanxin.test.fastdfs;

import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.PublicKey;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Testfastdfs {

    @Test
    public void testUpload(){

        try {
            //加载fast-client.properties配置文件
            ClientGlobal.initByProperties("config/fastdfs-client.properties");
            //定义trackerclient,yongyu
            TrackerClient trackerClient = new TrackerClient();

            //连接tracker
            TrackerServer trackerServer = trackerClient.getConnection();

            //获取storage
            StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);

            //创建storageclient
            StorageClient1 storageClient1 = new StorageClient1(trackerServer,storageServer);


            String filename = "D:/test.png";

            String png = storageClient1.upload_appender_file1(filename, "png", null);
            System.out.println(png);


        } catch (Exception e) {
            e.printStackTrace();
        }





    }

    @Test
    public void testDonwnload(){
        try {
            //加载fast-client.properties配置文件
            ClientGlobal.initByProperties("config/fastdfs-client.properties");
            //定义trackerclient,yongyu
            TrackerClient trackerClient = new TrackerClient();

            //连接tracker
            TrackerServer trackerServer = trackerClient.getConnection();

            //获取storage
            StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);

            //创建storageclient
            StorageClient1 storageClient1 = new StorageClient1(trackerServer,storageServer);

            String filename = "group1/M00/00/00/wKiJel-48EaETVgrAAAAAMdipqE063.png";

            byte[] bytes = storageClient1.download_file1(filename);

            FileOutputStream fileOutputStream = new FileOutputStream(new File("D:/logoin.png"));
            fileOutputStream.write(bytes);




        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
