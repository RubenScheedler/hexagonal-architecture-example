package dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.exception.ForbiddenException;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.User;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.id.TaskId;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.DeleteTaskPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetCurrentUserPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteTaskUseCase {
    private final DeleteTaskPort deleteTaskPort;
    private final GetCurrentUserPort getCurrentUserPort;

    public void execute(TaskId taskId) {
        performAuthorization();

        deleteTaskPort.deleteTask(taskId);
    }

    private void performAuthorization() {
        User currentUser = getCurrentUserPort.getCurrentUser();
        if (!currentUser.isFamilyMember() || !currentUser.isParent()) {
            throw new ForbiddenException("The user " + currentUser.getNickname() + " must be a family member and a parent.");
        }
    }
}
