package dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.id.TaskId;
import lombok.NonNull;

/**
 * Retrieves a task with a certain id from storage
 */
public interface GetTaskPort {
    Task getTask(@NonNull TaskId taskId);
}
