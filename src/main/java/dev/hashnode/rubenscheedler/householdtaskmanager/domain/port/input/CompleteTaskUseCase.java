package dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.exception.ForbiddenException;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.id.TaskId;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetCurrentUserPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetTaskPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.SaveTaskPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CompleteTaskUseCase {
    private final GetTaskPort getTaskPort;
    private final SaveTaskPort saveTaskPort;
    private final GetCurrentUserPort getCurrentUserPort;

    public void execute(TaskId taskId) {
        performAuthorization();

        Task task = getTaskPort.getTask(taskId);

        if (assignedToOtherUser(task)) {
            throw new ForbiddenException("This task is already assigned to another user");
        }

        task.setCompleted(true);

        saveTaskPort.saveTask(task);


    }

    private boolean assignedToOtherUser(Task task) {
        if (task.getAssignee() == null) {
            return false;
        }
        String currentAssignee = task.getAssignee().nickname();
        return !getCurrentUserPort.getCurrentUser().getNickname().equals(currentAssignee);
    }

    private void performAuthorization() {
        if (!getCurrentUserPort.getCurrentUser().isFamilyMember()) {
            throw new ForbiddenException("The user " + getCurrentUserPort.getCurrentUser().getNickname() + " is not a family member.");
        }
    }
}
