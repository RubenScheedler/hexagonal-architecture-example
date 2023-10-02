package dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.id.TaskId;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetTaskPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.SaveTaskPort;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EditTaskDescriptionUseCase {
    private final GetTaskPort getTaskPort;
    private final SaveTaskPort saveTaskPort;

    public void execute(Command command) {
        Task task = getTaskPort.getTask(command.taskId());

        task.setDescription(command.newDescription());

        saveTaskPort.saveTask(task);
    }

    @Builder
    record Command(TaskId taskId, String newDescription) {}
}
