package dev.hashnode.rubenscheedler.householdtaskmanager.adapter.web.v1;

import dev.hashnode.rubenscheedler.householdtaskmanager.adapter.web.v1.mapping.TaskMapper;
import dev.hashnode.rubenscheedler.householdtaskmanager.adapter.web.v1.model.TaskCreationDto;
import dev.hashnode.rubenscheedler.householdtaskmanager.adapter.web.v1.model.TaskDto;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.Assignee;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input.CreateTaskUseCase;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input.ViewUncompletedTasksUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final ViewUncompletedTasksUseCase viewUncompletedTasksUseCase;
    private final CreateTaskUseCase createTaskUseCase;
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

    @PostMapping
    ResponseEntity<TaskDto> createTask(@RequestBody TaskCreationDto taskCreationDto) {
        Task createdTask = createTaskUseCase.execute(CreateTaskUseCase.Command.builder()
                .assignee(taskCreationDto.getAssignee() != null ? Assignee.builder().nickname(taskCreationDto.getAssignee()).build() : null)
                .completed(taskCreationDto.isCompleted())
                .description(taskCreationDto.getDescription())
                .build());

        return ResponseEntity.ok(taskMapper.toDto(createdTask));
    }
}
