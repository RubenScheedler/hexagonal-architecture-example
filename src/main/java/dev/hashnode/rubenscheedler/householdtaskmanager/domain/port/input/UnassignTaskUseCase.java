package dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.exception.ForbiddenException;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.id.TaskId;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetCurrentUserPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetTaskPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.SaveTaskPort;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UnassignTaskUseCase {
    private final GetTaskPort getTaskPort;
    private final SaveTaskPort saveTaskPort;
    private final GetCurrentUserPort getCurrentUserPort;

    public void execute(Command command) {
        performAuthorization();

        Task task = getTaskPort.getTask(command.taskId());

        task.setAssignee(null);

        saveTaskPort.saveTask(task);
    }

    private void performAuthorization() {
        if (!getCurrentUserPort.getCurrentUser().isFamilyMember()) {
            throw new ForbiddenException("The user " + getCurrentUserPort.getCurrentUser().getNickname() + " is not a family member.");
        }
    }

    @Builder
    public record Command(TaskId taskId) {}
}
