package dev.hashnode.rubenscheedler.householdtaskmanager.adapter.web.v1.mapping;

import dev.hashnode.rubenscheedler.householdtaskmanager.adapter.web.v1.model.TaskDto;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import lombok.NonNull;

import java.util.List;

/**
 * Responsible for conversion between domain models and DTO's
 */
public interface TaskMapper {
    TaskDto toDto(@NonNull Task domainTask);
    Task toDomainObject(@NonNull TaskDto taskDto, boolean completed);
    List<TaskDto> toDtoList(@NonNull List<Task> domainTasks);
}
