package top.yuxs.resourcelibrarysystem.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yuxs.resourcelibrarysystem.DTO.UpdateTaskDTO;
import top.yuxs.resourcelibrarysystem.mapper.TaskMapper;
import top.yuxs.resourcelibrarysystem.pojo.Task;
import top.yuxs.resourcelibrarysystem.service.TaskService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskMapper taskMapper;

    @Override
    public void add(Task task) {
//        String username = (String) StpUtil.getExtra("username");
        Long userId = StpUtil.getLoginIdAsLong();
        task.setCreatedAt(LocalDateTime.now());
        task.setCreatedBy(userId);
        taskMapper.insertTask(task);
    }

    @Override
    public List<Task> findAll() {
        return taskMapper.findByAll();
    }

    @Override
    public void updateById(UpdateTaskDTO updateTaskDTO, Long id) {
        Task cs = taskMapper.getTaskById(id);
        cs.setDescription(updateTaskDTO.getDescription());
        cs.setPriority(updateTaskDTO.getPriority());
        cs.setTitle(updateTaskDTO.getTitle());
        cs.setDifficultyLevel(updateTaskDTO.getDifficultyLevel());
        cs.setTaskTitle(updateTaskDTO.getTaskTitle());
        cs.setDueDate(updateTaskDTO.getDueDate());

        taskMapper.updateTask(cs);
    }

    @Override
    public Task findById(Long id) {
        return taskMapper.getTaskById(id);
    }

    @Override
    public void deleteById(Long id) {
        taskMapper.deleteById(id);
    }
}
