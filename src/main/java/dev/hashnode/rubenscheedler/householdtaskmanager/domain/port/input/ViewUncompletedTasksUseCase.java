package dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.exception.ForbiddenException;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetCurrentUserPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetTasksPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Retrieves all tasks that are not completed yet. Some of them may already be assigned.
 */
@RequiredArgsConstructor
public class ViewUncompletedTasksUseCase {
    private final GetTasksPort getTasksPort;
    private final GetCurrentUserPort getCurrentUserPort;

    public List<Task> execute() {
        performAuthorization();

        List<Task> allTasks = getTasksPort.getTasks();

        return allTasks.stream().filter(t -> !t.isCompleted()).toList();
    }

    private void performAuthorization() {
        if (!getCurrentUserPort.getCurrentUser().isFamilyMember()) {
            throw new ForbiddenException("The user " + getCurrentUserPort.getCurrentUser().getNickname() + " is not a family member.");
        }
    }
}
