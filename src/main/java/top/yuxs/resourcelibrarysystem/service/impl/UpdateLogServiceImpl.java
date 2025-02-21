package top.yuxs.resourcelibrarysystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yuxs.resourcelibrarysystem.DTO.UpdateLogDTO;
import top.yuxs.resourcelibrarysystem.mapper.UpdateLogMapper;
import top.yuxs.resourcelibrarysystem.pojo.UpdateLog;
import top.yuxs.resourcelibrarysystem.service.UpdateLogService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UpdateLogServiceImpl  implements UpdateLogService {

    @Autowired
    private UpdateLogMapper updateLogMapper;

    @Override
    public void add(UpdateLog updateLog) {
        updateLog.setLogCreatTime(LocalDateTime.now());
        updateLogMapper.insert(updateLog);
    }

    @Override
    public List<UpdateLog> getAll() {
        return updateLogMapper.findAll();
    }

    @Override
    public void update(UpdateLog updateLog) {
        updateLog.setLogCreatTime(LocalDateTime.now());
        updateLogMapper.update(updateLog);
    }

    @Override
    public void deleteById(Integer id) {
        updateLogMapper.deleteById(id);
    }

    @Override
    public UpdateLog findById(Integer id) {
        return updateLogMapper.findById(id);
    }

    @Override
    public void updateById(Integer id, UpdateLogDTO updateLog) {
        UpdateLog log = findById(id);
        log.setLogTitle(updateLog.getLogTitle());
        log.setDesc(updateLog.getDesc());
        log.setType(updateLog.getType());
        log.setHollow(updateLog.getHollow());
        log.setLogCreatTime(LocalDateTime.now());
        updateLogMapper.update(log);
    }
}
