package top.yuxs.resourcelibrarysystem.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/resources")
public class FileUploadController {
    private static final Logger log = LogManager.getLogger(FileUploadController.class);

//    @Value("${spring.servlet.multipart.location}")
    // 获取项目根目录路径
    private String uploadDir = System.getProperty("user.dir") + "/uploads/";

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "上传文件为空，请选择文件后再次上传。";
        }

        // 检查文件大小
        if (file.getSize() > 10 * 1024 * 1024) { // 限制为10MB
            return "文件大小超出限制（最大10MB）";
        }

        // 检查文件类型
        String contentType = file.getContentType();
        if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
            return "不支持的文件类型";
        }

        try {
            // 生成新的文件名
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            // 获取文件存储路径
            String filePath = uploadDir + fileName;

            // 确保目录存在
            File uploadPath = new File(uploadDir);
            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }

            // 保存文件
            file.transferTo(new File(filePath));

            return "文件上传成功，文件路径：" + filePath;
        } catch (IOException e) {
            log.error("文件上传失败", e);
            return "文件上传失败：" + e.getMessage();
        }
    }
}
