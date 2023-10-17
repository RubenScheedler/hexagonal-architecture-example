package dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.exception.ForbiddenException;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.User;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.id.TaskId;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetCurrentUserPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetTaskPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.SaveTaskPort;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EditTaskDescriptionUseCase {
    private final GetTaskPort getTaskPort;
    private final SaveTaskPort saveTaskPort;
    private final GetCurrentUserPort getCurrentUserPort;

    public void execute(Command command) {
        performAuthorization();

        Task task = getTaskPort.getTask(command.taskId());

        task.setDescription(command.newDescription());

        saveTaskPort.saveTask(task);
    }

    private void performAuthorization() {
        User currentUser = getCurrentUserPort.getCurrentUser();
        if (!currentUser.isFamilyMember() || !currentUser.isParent()) {
            throw new ForbiddenException("The user " + currentUser.getNickname() + " must be a family member and a parent.");
        }
    }

    @Builder
    public record Command(TaskId taskId, String newDescription) {}
}
