package dev.hashnode.rubenscheedler.householdtaskmanager.adapter.web.v1;

import dev.hashnode.rubenscheedler.householdtaskmanager.adapter.web.v1.mapping.TaskMapper;
import dev.hashnode.rubenscheedler.householdtaskmanager.adapter.web.v1.model.TaskCreationDto;
import dev.hashnode.rubenscheedler.householdtaskmanager.adapter.web.v1.model.TaskDto;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.Assignee;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.id.TaskId;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input.AssignTaskUseCase;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input.CompleteTaskUseCase;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input.CreateTaskUseCase;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input.ViewUncompletedTasksUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {
    @Mock
    TaskMapper taskMapper;
    @Mock
    ViewUncompletedTasksUseCase viewUncompletedTasksUseCase;
    @Mock
    CreateTaskUseCase createTaskUseCase;
    @Mock
    AssignTaskUseCase assignTaskUseCase;
    @Mock
    CompleteTaskUseCase completeTaskUseCase;

    @InjectMocks
    TaskController taskController;

    TaskCreationDto taskCreationDto = TaskCreationDto.builder()
        .description("Clean the windows")
        .completed(false)
        .assignee("Alice")
        .build();

    @Test
    void getTasks_callsViewUncompletedTasksUseCase() {
        // given
        when(viewUncompletedTasksUseCase.execute()).thenReturn(Collections.emptyList());
        when(taskMapper.toDtoList(any())).thenReturn(Collections.emptyList());

        // when
        taskController.getTasks();

        // then
        verify(viewUncompletedTasksUseCase).execute();
    }

    @Test
    void getTasks_returnsMappedUseCaseResult() {
        // given
        List<Task> tasks = createStubTasks();
        when(viewUncompletedTasksUseCase.execute()).thenReturn(tasks);

        List<TaskDto> mappingResult = createStubTaskDtos();
        when(taskMapper.toDtoList(any())).thenReturn(mappingResult);

        // when
        ResponseEntity<List<TaskDto>> actual = taskController.getTasks();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getBody()).isNotNull();
        assertThat(actual.getBody()).isEqualTo(mappingResult);
    }

    @Test
    void createTask_assigneeSet_callsCreateTaskUseCase() {
        // when
        taskController.createTask(taskCreationDto);
        // then
        verify(createTaskUseCase).execute(assertArg(actual -> {
            assertThat(actual.description()).isEqualTo(taskCreationDto.getDescription());
            assertThat(actual.assignee()).isEqualTo(Assignee.builder().nickname(taskCreationDto.getAssignee()).build());
            assertThat(actual.completed()).isEqualTo(taskCreationDto.isCompleted());
        }));
    }

    @Test
    void createTask_assigneeNull_callsCreateTaskUseCase() {
        // given
        TaskCreationDto dtoWithoutAssignee = TaskCreationDto.builder()
                .description("Clean the windows")
                .completed(false)
                .assignee(null)
                .build();

        // when
        taskController.createTask(dtoWithoutAssignee);

        // then
        verify(createTaskUseCase).execute(assertArg(actual -> {
            assertThat(actual.description()).isEqualTo(dtoWithoutAssignee.getDescription());
            assertThat(actual.assignee()).isNull();
            assertThat(actual.completed()).isEqualTo(dtoWithoutAssignee.isCompleted());
        }));
    }

    @Test
    void createTask_returnsMappedTask() {
        // given
        TaskDto expected = createStubTaskDto();
        when(taskMapper.toDto(any())).thenReturn(expected);
        // when
        ResponseEntity<TaskDto> actual = taskController.createTask(taskCreationDto);
        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getBody()).isNotNull();
        assertThat(actual.getBody()).isEqualTo(expected);
    }

    @Test
    void assignTask_callsAssignTaskUseCase() {
        // given
        UUID taskId = UUID.randomUUID();
        String assignee = "Bob";
        // when
        taskController.assignTask(taskId, assignee);
        // then
        verify(assignTaskUseCase).execute(assertArg(command -> {
            assertThat(command.taskId()).isEqualTo(new TaskId(taskId));
            assertThat(command.assignee()).isEqualTo(Assignee.builder().nickname(assignee).build());
        }));
    }

    @Test
    void completeTask_callsCompleteTaskUseCase() {
        // given
        UUID taskId = UUID.randomUUID();
        // when
        taskController.completeTask(taskId);
        // then
        verify(completeTaskUseCase).execute(assertArg(actual -> {
            assertThat(actual).isEqualTo(new TaskId(taskId));
        }));
    }

    private static List<Task> createStubTasks() {
        return List.of(createStubTask(), createStubTask());
    }

    private static Task createStubTask() {
        TaskId taskId = new TaskId(UUID.randomUUID());
        Assignee assignee = Assignee.builder().nickname("Bob").build();
        return Task.builder().id(taskId).description("Task 1").completed(false).assignee(assignee).build();
    }

    private static List<TaskDto> createStubTaskDtos() {
        return List.of(createStubTaskDto(), createStubTaskDto());
    }

    private static TaskDto createStubTaskDto() {
        UUID taskId = UUID.randomUUID();
        return TaskDto.builder().id(taskId).description("Task 1").assignee("Alice").build();
    }
}

