package top.yuxs.resourcelibrarysystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yuxs.resourcelibrarysystem.DTO.PageAccessLogUpdateDTO;
import top.yuxs.resourcelibrarysystem.mapper.PageAccessLogMapper;
import top.yuxs.resourcelibrarysystem.pojo.PageAccessLog;
import top.yuxs.resourcelibrarysystem.service.PageAccessLogService;

import java.util.List;

@Service
public class PageAccessLogServiceImpl implements PageAccessLogService {
    @Autowired
    private PageAccessLogMapper pageAccessLogMapper;
    @Override
    public void add(PageAccessLog pageAccessLog) {
        pageAccessLogMapper.insertPageAccessLog(pageAccessLog);
    }

    @Override
    public List<PageAccessLog> gatAll() {
        return pageAccessLogMapper.selectAllPageAccessLogs();
    }

    @Override
    public PageAccessLog getById(long id) {
        return pageAccessLogMapper.selectPageAccessLogById(id);
    }

    @Override
    public void deleteById(long id) {
        pageAccessLogMapper.deletePageAccessLogById(id);
    }

//    @Override
//    public void updateById(long id, PageAccessLogUpdateDTO pageAccessLogUpdateDTO) {
//        PageAccessLog log = pageAccessLogMapper.selectPageAccessLogById(id);
//        if(log!=null){
//            log.setLogDetails(pageAccessLogUpdateDTO.getLogDetails());
//            log.setIpAddress(pageAccessLogUpdateDTO.getIpAddress());
//            log.setPageUrl(pageAccessLogUpdateDTO.getPageUrl());
//        }
//    }
}
