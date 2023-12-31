package dev.hashnode.rubenscheedler.householdtaskmanager.adapter.web.v1;

import dev.hashnode.rubenscheedler.householdtaskmanager.adapter.web.v1.mapping.TaskMapper;
import dev.hashnode.rubenscheedler.householdtaskmanager.adapter.web.v1.model.TaskCreationDto;
import dev.hashnode.rubenscheedler.householdtaskmanager.adapter.web.v1.model.TaskDto;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.Assignee;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.id.TaskId;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final ViewUncompletedTasksUseCase viewUncompletedTasksUseCase;
    private final CreateTaskUseCase createTaskUseCase;
    private final AssignTaskUseCase assignTaskUseCase;
    private final CompleteTaskUseCase completeTaskUseCase;
    private final EditTaskDescriptionUseCase editTaskDescriptionUseCase;
    private final UnassignTaskUseCase unassignTaskUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;
    private final TaskMapper taskMapper;

    /**
     * Gives an overview of all uncompleted tasks. Some may already have an assignee.
     * @return List of uncompleted tasks
     */
    @GetMapping
    ResponseEntity<List<TaskDto>> getTasks() {
        List<Task> domainTasks = viewUncompletedTasksUseCase.execute();
        return ResponseEntity.ok(taskMapper.toDtoList(domainTasks));
    }

    /**
     * Creates a new task. Possibly with a certain assignee, possible a task that is already completed.
     * @param taskCreationDto Values for the initialization of the task
     * @return The created task with generated taskId
     */
    @PostMapping
    ResponseEntity<TaskDto> createTask(@RequestBody TaskCreationDto taskCreationDto) {
        Task createdTask = createTaskUseCase.execute(CreateTaskUseCase.Command.builder()
                .assignee(taskCreationDto.getAssignee() != null ? Assignee.builder().nickname(taskCreationDto.getAssignee()).build() : null)
                .completed(taskCreationDto.isCompleted())
                .description(taskCreationDto.getDescription())
                .build());

        return ResponseEntity.ok(taskMapper.toDto(createdTask));
    }

    /**
     * Assigns a task
     * @param taskId Id of the task to assign
     * @param assignee Nickname of the user to assign the task to
     */
    @PatchMapping("/{taskId}/assignee")
    ResponseEntity<Void> assignTask(@PathVariable @NonNull UUID taskId, @RequestBody @NonNull String assignee) {
        assignTaskUseCase.execute(AssignTaskUseCase.Command.builder()
                        .taskId(new TaskId(taskId))
                        .assignee(Assignee.builder().nickname(assignee).build())
                .build());
        return ResponseEntity.ok().build();
    }

    /**
     * Completes a task
     * @param taskId Id of the task to complete
     */
    @PatchMapping("/{taskId}/complete")
    ResponseEntity<Void> completeTask(@PathVariable @NonNull UUID taskId) {
        completeTaskUseCase.execute(new TaskId(taskId));
        return ResponseEntity.ok().build();
    }

    /**
     * Edit a task's description
     * @param taskId Id of the task to edit
     * @param description New description of the task
     */
    @PatchMapping("/{taskId}/description")
    ResponseEntity<Void> editTaskDescription(@PathVariable @NonNull UUID taskId, @RequestBody @NonNull String description) {
        editTaskDescriptionUseCase.execute(EditTaskDescriptionUseCase.Command.builder()
                .taskId(new TaskId(taskId))
                .newDescription(description)
                .build());

        return ResponseEntity.ok().build();
    }
    /**
     * Unassigns a task
     * @param taskId Id of the task to unassign
     */
    @PatchMapping("/{taskId}/unassign")
    ResponseEntity<Void> unassignTask(@PathVariable @NonNull UUID taskId) {
        unassignTaskUseCase.execute(UnassignTaskUseCase.Command.builder()
                .taskId(new TaskId(taskId))
                .build());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{taskId}")
    ResponseEntity<Void> deleteTask(@PathVariable @NonNull UUID taskId) {
        deleteTaskUseCase.execute(new TaskId(taskId));
        return ResponseEntity.ok().build();
    }
}
