package top.yuxs.resourcelibrarysystem.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.yuxs.resourcelibrarysystem.DTO.GetResourceFileListDTO;
import top.yuxs.resourcelibrarysystem.DTO.ResourceFileDTO;
import top.yuxs.resourcelibrarysystem.DTO.ResourceUpdateDto;
import top.yuxs.resourcelibrarysystem.pojo.FileData;
import top.yuxs.resourcelibrarysystem.pojo.Resource;
import top.yuxs.resourcelibrarysystem.pojo.Result;
import top.yuxs.resourcelibrarysystem.service.FileDataService;
import top.yuxs.resourcelibrarysystem.service.ResourceService;
import top.yuxs.resourcelibrarysystem.utils.FtpUtil;

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
    //资源库管理类接口
    //资源文件类型添加
    @PostMapping("/admin/add")
    public Result<String> addFileResource(@RequestPart("file") MultipartFile file, @RequestPart("resourceData") String resourceData) throws IOException {
        String username = (String) StpUtil.getExtra("username");
//        解析文件数据
        FileData fileData = new FileData();
        Long fileSize = file.getSize();
        //文件原始文件名
        String fileName = file.getOriginalFilename();
        //获取文件后缀名
        String fileExtension = getFileExtension(fileName);
        //文件上传后的文件名
        String uuidFileName = UUID.randomUUID().toString() + fileExtension;
        //获取当前日期时间
        LocalDateTime now = LocalDateTime.now();
        //定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        //定义文件路径
        String remotePath = "/"+now.format(formatter);
        String filePath=remotePath+"/"+uuidFileName;
        //完整文件路径
        String fileUrl = url+remotePath+"/"+uuidFileName;
        String md5 = DigestUtils.md5DigestAsHex(file.getInputStream());
//        解析资源数据
        ObjectMapper objectMapper = new ObjectMapper();
        ResourceFileDTO data = objectMapper.readValue(resourceData,ResourceFileDTO.class);
        data.setResourceFileId(String.valueOf(UUID.randomUUID()));
        try {
            resourceService.addFileResource(data,username);
            boolean success = ftpUtil.uploadFile(remotePath, uuidFileName, file.getInputStream(),3);
            if(success){
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
                log.info("上传成功:"+fileUrl+"原始文件名"+fileName);
                return Result.success("上传成功:"+fileUrl+"原始文件名"+fileName);
            }else {
                log.error("上传失败");
                return Result.error("上传失败");
            }
        } catch (IOException e) {
            return Result.error("上传异常: " + e.getMessage());
        }

//        resourceService.addFileResource(data,username);
//        return Result.success("文件类型添加成功！！");
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
    public Result<List<GetResourceFileListDTO>> resourceFileList(){
        List<GetResourceFileListDTO> cs = resourceService.resourceFileList();
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
