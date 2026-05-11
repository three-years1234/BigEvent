package com.example.bigevent.demos.web.Controller;

import com.example.bigevent.demos.web.entity.Result;
import com.example.bigevent.demos.web.utils.QiNiuCloudUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController

public class FileUploadController {
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        String url= QiNiuCloudUtil.uploadFile(file);
        return Result.success(url);
    }
}
