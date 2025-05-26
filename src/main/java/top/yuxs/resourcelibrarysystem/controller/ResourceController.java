package top.yuxs.resourcelibrarysystem.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.yuxs.resourcelibrarysystem.DTO.GetResourceFileListDTO;
import top.yuxs.resourcelibrarysystem.DTO.ResourceFileDTO;
import top.yuxs.resourcelibrarysystem.DTO.ResourceUpdateDto;
import top.yuxs.resourcelibrarysystem.DTO.ResourceCompleteUpdateDTO;
import top.yuxs.resourcelibrarysystem.pojo.FileData;
import top.yuxs.resourcelibrarysystem.pojo.Resource;
import top.yuxs.resourcelibrarysystem.pojo.Result;
import top.yuxs.resourcelibrarysystem.service.FileDataService;
import top.yuxs.resourcelibrarysystem.service.ResourceService;
import top.yuxs.resourcelibrarysystem.utils.FtpUtil;
import top.yuxs.resourcelibrarysystem.utils.SysOperLogUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/api/resources")
public class ResourceController {
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
    private SysOperLogUtil sysOperLogUtil;
    //资源库管理类接口

//    资源文件类型添加修改后接口**********************************************************************
    @PostMapping("/admin/add/new")
    public Result<String> addFileResourceNew(@RequestBody ResourceFileDTO resourceFileDTO){
        String name = (String) StpUtil.getExtra("username");
        resourceService.addFileResource(resourceFileDTO,name);
        return Result.success("添加成功！");
    }
    @PostMapping("/admin/add/new/files/{resourceUUID}")
    public Result<String> addFileResourceNewFile(@RequestPart("files") List<MultipartFile> files, @PathVariable String resourceUUID) throws IOException, InterruptedException {
        String name = (String) StpUtil.getExtra("username");
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue; // 跳过空文件
            }
            // 解析文件数据
            FileData fileData = new FileData();

            Long fileSize = file.getSize();
            String fileName = file.getOriginalFilename();
            String fileExtension = getFileExtension(fileName);
            String uuidFileName = UUID.randomUUID().toString() + fileExtension;
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            String remotePath = "/" + now.format(formatter);
            String filePath = remotePath + "/" + uuidFileName;
            String fileUrl = url + filePath;
            String md5 = DigestUtils.md5DigestAsHex(file.getInputStream());

            // 上传文件到FTP
            boolean success = ftpUtil.uploadFile(remotePath, uuidFileName, file.getInputStream(),3,500000);
            if (success) {
                fileData.setFileName(fileName);
                fileData.setFilePath(remotePath);
                fileData.setFileUrl(fileUrl);
                fileData.setFileMd5(md5);
                fileData.setUploadTime(LocalDateTime.now());
                fileData.setResourceId(resourceUUID);
                fileData.setUserName(name);
                fileData.setFileType(fileExtension);
                fileData.setFileSize(fileSize);
                fileData.setIsDeleted(0);
                fileData.setUuidFileName(uuidFileName);
                fileDataService.add(fileData);
                log.info("上传成功: " + fileUrl + " 原始文件名: " + fileName);
            } else {
                log.error("上传失败: " + fileName);
                return Result.error("部分文件上传失败");
            }
        }
        return Result.success("所有文件上传成功！");
    }

//    资源文件类型添加修改后接口**********************************************************************

    //资源文件类型添加
    @PostMapping("/admin/add")
    public Result<String> addFileResource(@RequestPart("files") List<MultipartFile> files, @RequestPart("resourceData") String resourceData) throws IOException {
        String username = (String) StpUtil.getExtra("username");

        ObjectMapper objectMapper = new ObjectMapper();
        ResourceFileDTO data = objectMapper.readValue(resourceData, ResourceFileDTO.class);
        data.setResourceFileId(String.valueOf(UUID.randomUUID()));

        try {
            resourceService.addFileResource(data, username);

            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    continue; // 跳过空文件
                }

                // 解析文件数据
                FileData fileData = new FileData();
                Long fileSize = file.getSize();
                String fileName = file.getOriginalFilename();
                String fileExtension = getFileExtension(fileName);
                String uuidFileName = UUID.randomUUID().toString() + fileExtension;
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                String remotePath = "/" + now.format(formatter);
                String filePath = remotePath + "/" + uuidFileName;
                String fileUrl = url + filePath;
                String md5 = DigestUtils.md5DigestAsHex(file.getInputStream());

                // 上传文件到FTP
                boolean success = ftpUtil.uploadFile(remotePath, uuidFileName, file.getInputStream(),3,1024);
                if (success) {
                    fileData.setFileName(fileName);
                    fileData.setFilePath(remotePath);
                    fileData.setFileUrl(fileUrl);
                    fileData.setFileMd5(md5);
                    fileData.setUploadTime(LocalDateTime.now());
                    fileData.setResourceId(data.getResourceFileId());
                    fileData.setUserName(username);
                    fileData.setFileType(fileExtension);
                    fileData.setFileSize(fileSize);
                    fileData.setIsDeleted(0);
                    fileData.setUuidFileName(uuidFileName);
                    fileDataService.add(fileData);
                    log.info("上传成功: " + fileUrl + " 原始文件名: " + fileName);
                } else {
                    log.error("上传失败: " + fileName);
                    return Result.error("部分文件上传失败");
                }
            }

            return Result.success("所有文件上传成功");

        } catch (IOException e) {
            log.error("上传异常: " + e.getMessage(), e);
            return Result.error("上传异常: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //资源库逻辑删除接口
    @GetMapping("/admin/delete/{id}")
    public Result logicDelete(@PathVariable Long id) {
        resourceService.logicDelete(id);
        return Result.success("删除成功");
    }
//    资源库查询待审核通过接口
    @GetMapping("/admin/audit/")
    public Result<List<Resource>> selectAudit(){
        List<Resource> cs = resourceService.selectAudit();
        return Result.success(cs);
    }
//    根据id将资源审核通过
    @PutMapping("/admin/audit/{id}")
    public Result<String> auditById(@PathVariable Long id){
        resourceService.auditById(id);
        return Result.success("审核通过！");
    }
    //资源库更新接口
//    @PutMapping("/admin/update")
//    public Result update(@RequestBody @Validated(Resource.Update.class) Resource resource){
//        String name = (String) StpUtil.getExtra("username");
//        resourceService.update(resource,name);
//        return Result.success(resource);
//    }
    @PutMapping("/admin/update/{id}")
    public Result update(@PathVariable long id , @RequestBody @Valid ResourceUpdateDto resourceUpdateDto){
        String name = (String) StpUtil.getExtra("username");
        resourceService.update(resourceUpdateDto,name,id);
        return Result.success("更新成功");
    }
    //资源库公开接口
//    @SaCheckLogin
    @GetMapping("/public/get")
    public Result<List<Resource>> list(){
        List<Resource> cs = resourceService.list();
        return Result.success(cs);
    }
//    资源文件类获取
    @GetMapping("/public/get/resourcefile")
    public Result<List<GetResourceFileListDTO>> resourceFileList(HttpServletRequest request){
        List<GetResourceFileListDTO> cs = resourceService.resourceFileList();
//        sysOperLogUtil.add("获取资源文件数据",
//                0,
//                "GET",
//                "GET",
//                0,
//                "/public/get/resourcefile",
//                "/public/get/resourcefile",
//                "/public/get/resourcefile",
//                "/public/get/resourcefile",
//                " "+cs.toString(),
//                0,
//                "null",
//                request
//                );
        return Result.success(cs);
    }
    //资源库添加接口
    @PostMapping("/public/add")
    public Result add(@RequestBody Resource resource ){
        String name = (String) StpUtil.getExtra("username");
        if (name==null){
            name="GUEST";
        }
        resourceService.add(resource,name);
        return Result.success(resource);
    }
//    根据id查询资源接口
    @GetMapping("/public/get/{id}")
    public Result<Resource> selectByID(@PathVariable long id){
        Resource cs = resourceService.selectById(id);
        return Result.success(cs);
    }

    //    根据id查询资源接口
    @GetMapping("/public/get/resourcefile/{id}")
    public Result<GetResourceFileListDTO> selectResourceFileByID(@PathVariable long id){
        GetResourceFileListDTO cs = resourceService.selectResourceFileByID(id);
        return Result.success(cs);
    }
    // 综合搜索接口
    @GetMapping("/public/search")
    public Result<List<Resource>> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {
        List<Resource> resources = resourceService.search(keyword, category, author, startTime, endTime);
        return Result.success(resources);
    }

    // 关键词搜索
    @GetMapping("/public/search/keyword")
    public Result<List<Resource>> searchByKeyword(@RequestParam String keyword) {
        List<Resource> resources = resourceService.searchByKeyword(keyword);
        return Result.success(resources);
    }

    // 分类搜索
    @GetMapping("/public/search/category")
    public Result<List<Resource>> searchByCategory(@RequestParam String category) {
        List<Resource> resources = resourceService.searchByCategory(category);
        return Result.success(resources);
    }

    // 作者搜索
    @GetMapping("/public/search/author")
    public Result<List<Resource>> searchByAuthor(@RequestParam String author) {
        List<Resource> resources = resourceService.searchByAuthor(author);
        return Result.success(resources);
    }

    // 时间范围搜索
    @GetMapping("/public/search/time")
    public Result<List<Resource>> searchByTimeRange(
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {
        List<Resource> resources = resourceService.searchByTimeRange(startTime, endTime);
        return Result.success(resources);
    }

//    资源更新接口
    @PutMapping("/admin/complete-update/{id}")
    public Result completeUpdate(
            @PathVariable Long id,
            @RequestPart("updateData") String updateDataStr,
            @RequestPart(value = "newFiles", required = false) List<MultipartFile> newFiles) {
        
        String username = (String) StpUtil.getExtra("username");
        
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ResourceCompleteUpdateDTO updateData = objectMapper.readValue(updateDataStr, ResourceCompleteUpdateDTO.class);
            
            // 1. 更新资源基本信息
            ResourceUpdateDto basicUpdate = new ResourceUpdateDto();
            basicUpdate.setName(updateData.getName());
            basicUpdate.setUrl(updateData.getUrl());
            basicUpdate.setTab(updateData.getTab());
            basicUpdate.setImg(updateData.getImg());
            
            resourceService.update(basicUpdate, username, id);
            
            // 2. 处理要删除的文件
            if (updateData.getFileIdsToDelete() != null) {
                for (Long fileId : updateData.getFileIdsToDelete()) {
                    FileData fileData = fileDataService.getById(fileId);
                    if (fileData != null) {
                        // 删除FTP上的文件
                        boolean ftpDeleteSuccess = ftpUtil.deleteFile(fileData.getFilePath(), fileData.getUuidFileName());
                        if (ftpDeleteSuccess) {
                            fileDataService.removeById(fileId);
                        } else {
                            return Result.error("删除文件失败: " + fileData.getFileName());
                        }
                    }
                }
            }
            
            // 3. 处理新上传的文件
            if (newFiles != null && !newFiles.isEmpty()) {
                for (MultipartFile file : newFiles) {
                    if (file.isEmpty()) continue;
                    // 处理新文件上传
                    FileData fileData = new FileData();
                    String fileName = file.getOriginalFilename();
                    String fileExtension = getFileExtension(fileName);
                    String uuidFileName = UUID.randomUUID().toString() + fileExtension;
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                    String remotePath = "/" + now.format(formatter);
                    String filePath = remotePath + "/" + uuidFileName;
                    String fileUrl = url + filePath;
                    String md5 = DigestUtils.md5DigestAsHex(file.getInputStream());
                    
                    // 上传到FTP
                    boolean uploadSuccess = ftpUtil.uploadFile(remotePath, uuidFileName, file.getInputStream(),3,10000);
                    if (uploadSuccess) {
                        // 保存文件信息
                        fileData.setFileName(fileName);
                        fileData.setFilePath(remotePath);
                        fileData.setFileUrl(fileUrl);
                        fileData.setFileMd5(md5);
                        fileData.setUploadTime(LocalDateTime.now());
                        fileData.setResourceId(resourceService.selectById(id).getResourceFileId());
                        fileData.setUserName(username);
                        fileData.setFileType(fileExtension);
                        fileData.setFileSize(file.getSize());
                        fileData.setIsDeleted(0);
                        fileData.setUuidFileName(uuidFileName);
                        fileDataService.add(fileData);
                    } else {
                        return Result.error("上传新文件失败: " + fileName);
                    }
                }
            }
            return Result.success("资源更新成功");
            
        } catch (IOException e) {
            log.error("资源更新失败: " + e.getMessage(), e);
            return Result.error("资源更新失败: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

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
