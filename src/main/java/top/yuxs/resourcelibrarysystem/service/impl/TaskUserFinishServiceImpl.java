package top.yuxs.resourcelibrarysystem.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yuxs.resourcelibrarysystem.DTO.TaskUserFinishUpdateDTO;
import top.yuxs.resourcelibrarysystem.mapper.TaskMapper;
import top.yuxs.resourcelibrarysystem.mapper.TaskUserFinishMapper;
import top.yuxs.resourcelibrarysystem.pojo.Task;
import top.yuxs.resourcelibrarysystem.pojo.TaskUserFinish;
import top.yuxs.resourcelibrarysystem.service.TaskUserFinishService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class TaskUserFinishServiceImpl implements TaskUserFinishService {
    @Autowired
    private TaskUserFinishMapper taskUserFinishMapper;
    @Autowired
    private TaskMapper taskMapper;

    @Override
    public void add(TaskUserFinish taskUserFinish) {
        Long tsId = taskUserFinish.getTaskId();
        Task ts = taskMapper.getTaskById(tsId);
        taskUserFinish.setCreatTime(LocalDateTime.now());
        if(ts!=null){
            taskUserFinishMapper.add(taskUserFinish);
        }else {
            log.error("找不到任务！！");
            throw new RuntimeException("找不到任务！！");
        }

    }

    @Override
    public void update(TaskUserFinishUpdateDTO taskUserFinishUpdateDTO, Long id) {
        TaskUserFinish cs = taskUserFinishMapper.findById(id);
        cs.setCreatTime(LocalDateTime.now());
        cs.setUserFinishDesc(taskUserFinishUpdateDTO.getUserFinishDesc());
        taskUserFinishMapper.Update(cs);
    }

    @Override
    public TaskUserFinish findByUserIdAndTaskId(Long taskId, Long userId) {
        return taskUserFinishMapper.findByTaskIdAndUserID(taskId,userId);
    }

    @Override
    public void deleteById(Long id) {
        taskUserFinishMapper.deleteByTaskId(id);
    }

    @Override
    public List<TaskUserFinish> findAllByTaskId(Long taskid) {
        return taskUserFinishMapper.findAllByTaskID(taskid);
    }
}
