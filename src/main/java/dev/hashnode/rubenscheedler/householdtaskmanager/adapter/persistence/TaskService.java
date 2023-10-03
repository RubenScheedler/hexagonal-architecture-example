package dev.hashnode.rubenscheedler.householdtaskmanager.adapter.persistence;

import dev.hashnode.rubenscheedler.householdtaskmanager.adapter.persistence.entity.TaskEntity;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.exception.TaskDoesNotExistException;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.id.TaskId;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.DeleteTaskPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetTaskPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetTasksPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.SaveTaskPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
public class TaskService implements GetTaskPort, GetTasksPort, SaveTaskPort, DeleteTaskPort {
    private final CrudRepository<TaskEntity, UUID> crudRepository;
    private final TaskEntityMapper taskEntityMapper;

    @Override
    public void deleteTask(@NonNull TaskId taskId) {
        crudRepository.deleteById(taskId.value());
    }

    @Override
    public Task getTask(@NonNull TaskId taskId) {
        Optional<TaskEntity> taskEntity = crudRepository.findById(taskId.value());

        return taskEntity.map(taskEntityMapper::toDomainTask)
                .orElseThrow(() -> new TaskDoesNotExistException("Task with id " + taskId.value() + " does not exist"));
    }

    @Override
    public List<Task> getTasks() {
        Iterable<TaskEntity> taskEntities = crudRepository.findAll();

        return StreamSupport.stream(taskEntities.spliterator(), false)
                .map(taskEntityMapper::toDomainTask)
                .toList();
    }

    @Override
    public void saveTask(@NonNull Task task) {
        crudRepository.save(taskEntityMapper.toEntity(task));
    }
}
