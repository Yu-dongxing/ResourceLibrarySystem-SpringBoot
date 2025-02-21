package top.yuxs.resourcelibrarysystem.controller;

import cn.dev33.satoken.stp.StpUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.yuxs.resourcelibrarysystem.pojo.FileData;
import top.yuxs.resourcelibrarysystem.pojo.Result;
import top.yuxs.resourcelibrarysystem.service.FileDataService;
import top.yuxs.resourcelibrarysystem.utils.FtpUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static cn.dev33.satoken.SaManager.log;

@RestController
@RequestMapping("/api/resources/ftp")
public class FtpController {

    @Autowired
    private final FtpUtil ftpUtil = new FtpUtil();

    @Value("${File-Proxy-Website.url}")
    private String url;

    @Autowired
    private FileDataService fileDataService;

    @PostMapping("/upload")
    public Result<List<String>> upload(@RequestPart("files") List<MultipartFile> files, @RequestParam("resourceId") String resourceId) {
        List<String> resultMessages = new ArrayList<>();
        String username = (String) StpUtil.getExtra("username");

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                resultMessages.add("文件为空，跳过上传");
                continue; // 跳过空文件
            }
            try {
                String result = uploadFile(file, resourceId, username);
                resultMessages.add(result);
            } catch (Exception e) {
                resultMessages.add("文件上传异常: " + e.getMessage());
                log.error("文件上传异常: ", e);
            }
        }

        if (resultMessages.isEmpty()) {
            return Result.error("没有文件被上传");
        } else {
            return Result.success(resultMessages);
        }
    }

    private String uploadFile(MultipartFile file, String resourceId, String username) throws IOException {
        Long fileSize = file.getSize();
        String fileName = file.getOriginalFilename();
        String fileExtension = getFileExtension(fileName);
        String uuidFileName = UUID.randomUUID().toString() + fileExtension;
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String remotePath = "/" + now.format(formatter);
        String filePath = remotePath + "/" + uuidFileName;
        String fileUrl = url + filePath;

        try {
            String md5 = DigestUtils.md5DigestAsHex(file.getInputStream());
            boolean success = ftpUtil.uploadFile(remotePath, uuidFileName, file.getInputStream(),3,500000);
            if (success) {
                FileData fileData = new FileData();
                fileData.setFileName(fileName);
                fileData.setFilePath(remotePath);
                fileData.setFileUrl(fileUrl);
                fileData.setFileMd5(md5);
                fileData.setUploadTime(LocalDateTime.now());
                fileData.setResourceId(resourceId);
                fileData.setUserName(username);
                fileData.setFileType(fileExtension);
                fileData.setFileSize(fileSize);
                fileData.setIsDeleted(0);
                fileData.setUuidFileName(uuidFileName);
                fileDataService.add(fileData);
                log.info("上传成功: " + fileUrl + " 原始文件名: " + fileName);
                return "上传成功: " + fileUrl + " 原始文件名: " + fileName;
            } else {
                log.error("文件上传失败: " + fileName);
                return "文件上传失败: " + fileName;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            file.getInputStream().close(); // 确保关闭输入流
        }
    }


    @DeleteMapping("/delete")
    public Result<String> deleteFile(@RequestParam("fileId") Long fileId) {
        try {
            // 从数据库中获取文件信息
            FileData fileData = fileDataService.getById(fileId);
            if (fileData == null) {
                return Result.error("文件不存在");
            }

            // 提取文件路径和文件名
            String remotePath = fileData.getFilePath();
            String fileName = fileData.getUuidFileName();
//            String remotePath = filePath.substring(0, filePath.lastIndexOf('/'));
//            String fileName = filePath.substring(filePath.lastIndexOf('/') + 1);

            // 删除FTP上的文件
            boolean success = ftpUtil.deleteFile(remotePath, fileName);
            if (success) {
                // 删除数据库中的记录
                fileDataService.removeById(fileId);
                return Result.success("文件删除成功");
            } else {
                return Result.error("文件删除失败");
            }
        } catch (Exception e) {
            return Result.error("删除异常: " + e.getMessage());
        }
    }

//    @GetMapping("/download")
//    public void download(@RequestParam String remoteFilePath, HttpServletResponse response) {
//        try {
//            String fileName = remoteFilePath.substring(remoteFilePath.lastIndexOf("/") + 1);
//            response.setContentType("application/octet-stream");
//            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
//
//            OutputStream output = response.getOutputStream();
//            boolean success = ftpUtil.downloadFile(remoteFilePath, output);
//            if (!success) response.setStatus(HttpStatus.NOT_FOUND.value());
//        } catch (IOException e) {
//            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//        }
//    }

//    @DeleteMapping("/delete")
//    public ResponseEntity<String> delete(@RequestParam String remoteFilePath) {
//        try {
//            boolean success = ftpUtil.deleteFile(remoteFilePath);
//            return success ? ResponseEntity.ok("删除成功") : ResponseEntity.badRequest().body("删除失败");
//        } catch (IOException e) {
//            return ResponseEntity.internalServerError().body("删除异常: " + e.getMessage());
//        }
//    }
    /**
     * 获取文件后缀名
     * @param fileName 文件名
     * @return 文件后缀名（带点，如 .txt）
     */
    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex);
        }
        return "";
    }
}
