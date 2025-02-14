package top.yuxs.resourcelibrarysystem.service;

import top.yuxs.resourcelibrarysystem.DTO.UpdateLogDTO;
import top.yuxs.resourcelibrarysystem.pojo.UpdateLog;

import java.util.List;

public interface UpdateLogService {
    void add(UpdateLog updateLog);

    List<UpdateLog> getAll();

    void update(UpdateLog updateLog);

    void deleteById(Integer id);

    UpdateLog findById(Integer id);

    void updateById(Integer id, UpdateLogDTO updateLog);
}
