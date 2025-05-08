package top.yuxs.resourcelibrarysystem.service;

import top.yuxs.resourcelibrarysystem.DTO.TaskUserFinishUpdateDTO;
import top.yuxs.resourcelibrarysystem.pojo.TaskUserFinish;

import java.util.List;

public interface TaskUserFinishService {
    void add(TaskUserFinish taskUserFinish);

    void update(TaskUserFinishUpdateDTO taskUserFinishUpdateDTO, Long id);

    TaskUserFinish findByUserIdAndTaskId(Long taskId, Long userId);

    void deleteById(Long id);

    List<TaskUserFinish> findAllByTaskId(Long taskid);

    void deleteByFinishId(Long id);
}
