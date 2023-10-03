package dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.Assignee;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.id.TaskId;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetTaskPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.SaveTaskPort;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class CreateTaskUseCase {
    private final SaveTaskPort saveTaskPort;
    private final GetTaskPort getTaskPort;

    public Task execute(Command command) {
        // Generate a unique id for the new task
        TaskId taskId = new TaskId(UUID.randomUUID());

        // Save the task in persistent storage
        saveTaskPort.saveTask(Task.builder()
                        .id(taskId)
                        .description(command.description())
                        .completed(command.completed())
                        .assignee(command.assignee())
                .build());

        return getTaskPort.getTask(taskId);
    }

    @Builder
    public record Command(@NonNull String description, boolean completed, Assignee assignee) {}
}
