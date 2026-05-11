package com.example.bigevent.demos.web.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.UUID;
@Component
public class QiNiuCloudUtil {
    private static final String ACCESS_KEY = "tiVAwc3d_yj5l5owqwCIsShmwICc1wt4jTKKGfiS";
    private static final String SECRET_KEY = "-dWVe-l0kT1KAqfEasI4eZMNYXLplID5HysfzKrJ";
    private static final String BUCKET_NAME = "bigevent02";
    private static final String DOMAIN_NAME = "http://te7b0bug9.hn-bkt.clouddn.com";
    private static Auth auth;
    private static UploadManager uploadManager;
    private static BucketManager bucketManager;

    static {
        Configuration cfg = new Configuration(Region.region2());
        auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        uploadManager = new UploadManager(cfg);
        bucketManager = new BucketManager(auth, cfg);
    }
    public static String getUploadToken() {
        return auth.uploadToken(BUCKET_NAME);
    }
    public  static String uploadFile(MultipartFile file) throws IOException {
            if (file == null || file.isEmpty()) {
                throw new IOException("上传文件不能为空");
            }

            try {
                // 生成文件名
                String originalFilename = file.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String fileName = UUID.randomUUID().toString().replace("-", "") + extension;

                // 获取上传凭证
                String uploadToken = getUploadToken();

                // 上传文件
                Response response = uploadManager.put(file.getBytes(), fileName, uploadToken);

                // 解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

                // 返回文件访问地址
                return DOMAIN_NAME + "/" + putRet.key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                throw new IOException("上传文件失败: " + r.toString());
            }
        }
}
