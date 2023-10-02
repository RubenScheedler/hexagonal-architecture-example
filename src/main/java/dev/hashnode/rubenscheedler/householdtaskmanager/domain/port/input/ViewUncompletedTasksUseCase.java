package dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetTasksPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Retrieves all tasks that are not completed yet. Some of them may already be assigned.
 */
@RequiredArgsConstructor
public class ViewUncompletedTasksUseCase {
    private final GetTasksPort getTasksPort;

    public List<Task> execute() {
        List<Task> allTasks = getTasksPort.getTasks();

        return allTasks.stream().filter(t -> !t.isCompleted()).toList();
    }
}
