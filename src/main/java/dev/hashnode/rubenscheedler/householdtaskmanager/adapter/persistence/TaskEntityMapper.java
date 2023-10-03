package dev.hashnode.rubenscheedler.householdtaskmanager.adapter.persistence;

import dev.hashnode.rubenscheedler.householdtaskmanager.adapter.persistence.entity.TaskEntity;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;

/**
 * Describes the contract for a task mapper in the persistence adapter.
 * Contains methods for two-way version between entities and domain objects.
 */
public interface TaskEntityMapper {
    Task toDomainTask(TaskEntity entity);
    TaskEntity toEntity(Task domainTask);
}
