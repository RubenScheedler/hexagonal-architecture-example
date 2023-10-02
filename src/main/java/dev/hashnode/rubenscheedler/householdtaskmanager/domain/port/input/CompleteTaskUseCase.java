package dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.id.TaskId;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetTaskPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.SaveTaskPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CompleteTaskUseCase {
    private final GetTaskPort getTaskPort;
    private final SaveTaskPort saveTaskPort;

    public void execute(TaskId taskId) {
        Task task = getTaskPort.getTask(taskId);

        task.setCompleted(true);

        saveTaskPort.saveTask(task);
    }
}
