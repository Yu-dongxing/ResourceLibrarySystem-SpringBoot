package top.yuxs.resourcelibrarysystem.mapper;

import org.apache.ibatis.annotations.*;
import top.yuxs.resourcelibrarysystem.pojo.TaskUserFinish;

import java.util.List;

@Mapper
public interface TaskUserFinishMapper {
    @Insert("INSERT INTO tasks_user_finish"+
            "(task_id, creat_time,user_id,post_user_name ,user_finish_desc, user_finish_desc_resoursefileid)" +
            "VALUES(#{taskId},#{creatTime}, #{userId},#{postUserName},#{userFinishDesc},#{userFinishDescResoursefileid})")
    void add(TaskUserFinish taskUserFinish);

    @Select("SELECT * FROM tasks_user_finish")
    List<TaskUserFinish> findByAll();

    @Select("SELECT * FROM tasks_user_finish where id = #{id}")
    TaskUserFinish findById(Long id);

    @Select("SELECT * FROM tasks_user_finish where task_id = #{taskId} AND user_id = #{userId}")
    TaskUserFinish findByTaskIdAndUserID(Long taskId,Long userId);

    @Update("UPDATE tasks_user_finish "+
            "SET task_id = #{taskId},creat_time = #{creatTime},post_user_name = #{postUserName}, user_id = #{userId}, user_finish_desc=#{userFinishDesc}, user_finish_desc_resoursefileid= #{userFinishDescResoursefileid} " +
            "WHERE id = #{id} ")
    void Update(TaskUserFinish taskUserFinish);

    @Delete("DELETE FROM tasks_user_finish "+
            "WHERE task_id = #{taskId}")
    void deleteByTaskId(Long taskId);


    @Delete("DELETE FROM tasks_user_finish "+
            "WHERE id = #{Id}")
    void deleteById(Long Id);

//    根据任务id查询所有回答
    @Select("SELECT * FROM tasks_user_finish where task_id = #{taskId}")
    List<TaskUserFinish> findAllByTaskID(Long taskId);
}
