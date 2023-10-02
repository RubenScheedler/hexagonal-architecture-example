package dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import lombok.NonNull;

/**
 * Saves or creates a task.
 */
public interface SaveTaskPort {
    void saveTask(@NonNull Task task);
}
