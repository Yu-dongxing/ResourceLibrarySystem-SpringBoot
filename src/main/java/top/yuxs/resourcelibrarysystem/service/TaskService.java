package top.yuxs.resourcelibrarysystem.service;

import top.yuxs.resourcelibrarysystem.DTO.UpdateTaskDTO;
import top.yuxs.resourcelibrarysystem.pojo.Task;

import java.util.List;

public interface TaskService {
    void add(Task task);

    List<Task> findAll();

    void updateById(UpdateTaskDTO updateTaskDTO, Long id);

    Task findById(Long id);

    void deleteById(Long id);
}
