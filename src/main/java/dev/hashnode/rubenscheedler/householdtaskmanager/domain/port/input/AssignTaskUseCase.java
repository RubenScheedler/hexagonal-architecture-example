package dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.Assignee;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.id.TaskId;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetTaskPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.SaveTaskPort;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AssignTaskUseCase {
    private final GetTaskPort getTaskPort;
    private final SaveTaskPort saveTaskPort;

    public void execute(@NonNull Command command) {
        Task task = getTaskPort.getTask(command.taskId());

        task.setAssignee(command.assignee());

        saveTaskPort.saveTask(task);
    }

    @Builder
    public record Command(TaskId taskId, Assignee assignee) {}
}
