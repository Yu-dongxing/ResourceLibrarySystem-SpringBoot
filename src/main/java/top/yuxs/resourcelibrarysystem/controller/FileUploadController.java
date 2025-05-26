package top.yuxs.resourcelibrarysystem.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Dict;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.yuxs.resourcelibrarysystem.pojo.FileData;
import top.yuxs.resourcelibrarysystem.pojo.Result;
import top.yuxs.resourcelibrarysystem.service.ApiKeyService;
import top.yuxs.resourcelibrarysystem.service.FileDataService;
import top.yuxs.resourcelibrarysystem.service.ResourceService;
import top.yuxs.resourcelibrarysystem.utils.FtpUtil;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/api/resources")
public class FileUploadController {
    //    资源文件类型添加
    @Autowired
    private final FtpUtil ftpUtil = new FtpUtil();
    @Value("${File-Proxy-Website.url}")
    private String url;
    @Autowired
    private FileDataService fileDataService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private ApiKeyService apiKeyService;
    // 获取项目根目录路径
    private String uploadDir = System.getProperty("user.dir") + "/uploads/";

    //获取所有文件信息
    @GetMapping("/file/all")
    public Result<List<FileData>> findAll(){
        List<FileData> cs = fileDataService.findAll();
        return Result.success("所有文件查询成功！",cs);

    }

    //  文件上传 -- v1.0 （分片上传）
    @PostMapping("/file/upload/chunk")
    public Result<String> uploadFileChunk(
            @RequestParam("file") MultipartFile file, //分片文件
            @RequestParam("chunkNumber") int chunkNumber, //当前分片编号
            @RequestParam("totalChunks") int totalChunks,//分片总数
            @RequestParam("fileMd5") String fileMd5,//文件md5
            @RequestParam("fileName") String fileName,//文件名
            @RequestParam("FileAssociationId") String FileAssociationId //文件与资源关联id
            //@RequestParam("chunkMd5") String chunkMd5 // 分片md5
    ) throws IOException {
        fileDataService.saveChunk(file,fileMd5,FileAssociationId, fileName, chunkNumber, totalChunks);
        return Result.success("分片上传成功！");
    }
    //   文件上传 -- v1.0 （接受合并请求）
    @PostMapping("/file/upload/chunk/merge")
    public  Result<String> uploadFileChunkMerge(
            @RequestParam("fileName") String fileName,
            @RequestParam("fileMd5") String fileMd5,
            @RequestParam("FileAssociationId") String FileAssociationId,
            @RequestParam("totalChunks") int totalChunks
            //@RequestParam("chunkMd5") String chunkMd5 // 分片md5
    ) throws IOException {
        fileDataService.mergeChunksAndUpload(fileMd5, fileName, FileAssociationId,totalChunks);
        return Result.success("上传成功！");
    }

    //编辑器图片上传
    @PostMapping("/public/fileupload/{key}")
    public Map<String,Object> addFileResource(@RequestPart("file") MultipartFile file, @PathVariable String key) throws IOException, InterruptedException {
        log.info(key);
        String fileName = file.getOriginalFilename();
        String fileExtension = getFileExtension(fileName,true);
        String uuidFileName = UUID.randomUUID().toString() + fileExtension;
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String remotePath = "/wangeditor/" + now.format(formatter);
        String filePath = remotePath + "/" + uuidFileName;

        Long fileSize = file.getSize();
        String fileUrl = url + filePath;
        String md5 = DigestUtils.md5DigestAsHex(file.getInputStream());

        // 上传文件到FTP
         boolean success = ftpUtil.uploadFile(remotePath, uuidFileName, file.getInputStream(),3,500000);
         if (success){
             log.info("上传成功: " + fileUrl + " 原始文件名: " + fileName);
             Map<String,Object> resMap = new HashMap<>();
             resMap.put("errno",0);
             resMap.put("data", CollUtil.newArrayList(Dict.create().set("url",fileUrl)));
             return resMap;
         } else {
             Map<String,Object> resMap = new HashMap<>();
             resMap.put("errno",1);
             resMap.put("message", "上传失败！！！");
             return resMap;
         }

    }
    @GetMapping("/file/find/uuid/{fileuuid}")
    Result<List<FileData>> findByFileUuid(@PathVariable String fileuuid){
        List<FileData> cs = fileDataService.getByResourceId(fileuuid);
        return Result.success(cs);
    }

//    文件上传 - 0.1
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
    /**
     * 获取文件后缀名
     * @param fileName 文件名
     * @param isdi(true,false) 返回的后缀名是否带  .
     * @return 文件后缀名（如果isdi为true 带点，如 .txt；isdi为false则不带点，如txt）
     */
    private String getFileExtension(String fileName, boolean isdi) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            if (isdi) {
                // 如果isdi为true，返回带点的后缀名
                return fileName.substring(lastDotIndex);
            } else {
                // 如果isdi为false，返回不带点的后缀名
                return fileName.substring(lastDotIndex + 1);
            }
        }
        return "";
    }
}
