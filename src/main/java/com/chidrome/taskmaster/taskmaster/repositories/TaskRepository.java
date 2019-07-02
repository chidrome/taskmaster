package com.chidrome.taskmaster.taskmaster.repositories;

import com.chidrome.taskmaster.taskmaster.models.TaskInfo;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@EnableScan
public interface TaskRepository extends CrudRepository<TaskInfo, String> {
    @Override
    Optional<TaskInfo> findById(String id);
}
