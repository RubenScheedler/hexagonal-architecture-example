package dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.id.TaskId;
import lombok.NonNull;

/**
 * Deletes a task from storage
 */
public interface DeleteTaskPort {
    void deleteTask(@NonNull TaskId taskId);
}
