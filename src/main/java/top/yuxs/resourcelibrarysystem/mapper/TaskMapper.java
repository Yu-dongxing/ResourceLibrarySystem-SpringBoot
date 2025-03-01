package top.yuxs.resourcelibrarysystem.mapper;

import org.apache.ibatis.annotations.*;
import top.yuxs.resourcelibrarysystem.pojo.Task;

import java.util.List;

@Mapper
public interface TaskMapper {
    @Insert("INSERT INTO tasks " +
            "(title,task_title,description, due_date, created_at, created_by, priority, difficulty_level,  resource_file_uuid) " +
            "VALUES " +
            "(#{title},#{taskTitle},#{description}, #{dueDate}, #{createdAt}, #{createdBy}, #{priority}, #{difficultyLevel}, #{resourceFileUuid})")
    void insertTask(Task task);

    @Select("SELECT * FROM tasks WHERE id = #{id}")
    Task getTaskById(Long id);

    @Update("UPDATE tasks " +
            "SET description = #{description}, " +
            "title = #{title}, " +
            "task_title = #{taskTitle}, "+
            "due_date = #{dueDate}, " +
            "priority = #{priority}, " +
            "difficulty_level = #{difficultyLevel}, " +
            "resource_file_uuid = #{resourceFileUuid} " +
            "WHERE id = #{id}")
    void updateTask(Task task);

    @Select("SELECT * FROM tasks")
    List<Task> findByAll();

    @Delete("DELETE FROM tasks WHERE id=#{id}")
    void deleteById(Long id);
}
