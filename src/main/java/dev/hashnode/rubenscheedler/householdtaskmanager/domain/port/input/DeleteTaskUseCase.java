package dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.id.TaskId;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.DeleteTaskPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetTaskPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteTaskUseCase {
    private final GetTaskPort getTaskPort;
    private final DeleteTaskPort deleteTaskPort;

    public void execute(TaskId taskId) {
        Task task = getTaskPort.getTask(taskId);

        deleteTaskPort.deleteTask(task.getId());
    }
}
