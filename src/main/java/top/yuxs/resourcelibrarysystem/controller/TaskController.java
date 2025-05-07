package top.yuxs.resourcelibrarysystem.controller;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.yuxs.resourcelibrarysystem.DTO.TaskUserFinishUpdateDTO;
import top.yuxs.resourcelibrarysystem.DTO.UpdateTaskDTO;
import top.yuxs.resourcelibrarysystem.pojo.Result;
import top.yuxs.resourcelibrarysystem.pojo.Task;
import top.yuxs.resourcelibrarysystem.pojo.TaskUserFinish;
import top.yuxs.resourcelibrarysystem.service.TaskService;
import top.yuxs.resourcelibrarysystem.service.TaskUserFinishService;
import top.yuxs.resourcelibrarysystem.service.impl.TaskServiceImpl;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/resources")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskUserFinishService taskUserFinishService;

//    添加任务
    @PostMapping("/task/add")
    public Result<String> addTask(@RequestBody Task task){
        taskService.add(task);
        return Result.success("添加任务成功！！");
    }
//    查询任务
    @GetMapping("/task/find/all")
    public Result<List<Task>> findByAll(){
        List<Task> cs = taskService.findAll();
        return Result.success(cs);
    }
//    更新任务
    @PutMapping("/task/update/{id}")
    Result<String> updateById(@RequestBody UpdateTaskDTO updateTaskDTO,@PathVariable Long id){
        taskService.updateById(updateTaskDTO,id);
        return Result.success("更新成功！！！");
    }
//    通过id获取任务
    @GetMapping("/task/find/{id}")
    Result<Task> findById(@PathVariable Long id){
        Task cs = taskService.findById(id);
        return Result.success(cs);
    }

//    根据任务id删除任务以及回答
    @GetMapping("/task/delete/{id}")
    Result<String> deleteByTaskId(@PathVariable Long id){
        try {
            taskService.deleteById(id);
            taskUserFinishService.deleteById(id);
            return Result.success("任务以及任务回答删除成功！！！");
        } catch (Exception e){
            log.error(e.getMessage());
            return Result.success("任务以及任务回答删除失败！！！");
        }

    }

//    提交任务回答
    @PostMapping("/task/Finish")
    Result<String> postTaskFinish(@RequestBody TaskUserFinish taskUserFinish){
        Long userId = StpUtil.getLoginIdAsLong();
        String username = (String) StpUtil.getExtra("username");
        Long taskId = taskUserFinish.getTaskId();
        taskUserFinish.setPostUserName(username);
        taskUserFinish.setUserId(userId);
        TaskUserFinish ts = taskUserFinishService.findByUserIdAndTaskId(taskId,userId);
        if(ts!=null){
            TaskUserFinishUpdateDTO up = new TaskUserFinishUpdateDTO();
            up.setUserFinishDesc(taskUserFinish.getUserFinishDesc());
            Long tsid = ts.getId();
            taskUserFinishService.update(up,tsid);
            return Result.success("更新成功！！！");
        }else {
            taskUserFinishService.add(taskUserFinish);
            return Result.success("回答成功！");
        }

    }

//    更新任务回答 ----
    @PutMapping("/task/Finish/update/{id}")
    Result<String> updateTaskFinish(@RequestBody TaskUserFinishUpdateDTO taskUserFinishUpdateDTO,@PathVariable Long id){
        taskUserFinishService.update(taskUserFinishUpdateDTO,id);
        return Result.success("更新成功！！！");
    }

//    根据任务id查询回答
    @GetMapping("/task/Finish/find/{taskId}")
    Result<TaskUserFinish> findByUserIdSelectTask(@PathVariable Long taskId){
        Long userId = StpUtil.getLoginIdAsLong();
        TaskUserFinish ts = taskUserFinishService.findByUserIdAndTaskId(taskId,userId);
        return Result.success(ts);
    }
//    根据任务id查询所有用户回答
    @GetMapping("/task/finish/findall/taskid/{taskid}")
    Result<List<TaskUserFinish>> findAllByTaskId(@PathVariable Long taskid){
        List<TaskUserFinish> cs = taskUserFinishService.findAllByTaskId(taskid);
        return Result.success(cs);
    }
//    根据回答id删除回答
    @DeleteMapping("/task/finish/delete/{id}")
    Result<String> deleteByFinish(@PathVariable Long id){
        taskUserFinishService.deleteByFinishId(id);
        return Result.success("删除成功");
    }
}
