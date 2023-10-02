package dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;

import java.util.List;

/**
 * Retrieves all tasks from storage.
 */
public interface GetTasksPort {
    List<Task> getTasks();
}
