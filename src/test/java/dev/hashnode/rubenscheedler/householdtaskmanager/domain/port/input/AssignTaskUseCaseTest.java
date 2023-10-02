package dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.Assignee;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.id.TaskId;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetTaskPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.SaveTaskPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssignTaskUseCaseTest {
    @Mock
    GetTaskPort getTaskPort;
    @Mock
    SaveTaskPort saveTaskPort;
    @InjectMocks
    AssignTaskUseCase assignTaskUseCase;

    AssignTaskUseCase.Command command = AssignTaskUseCase.Command.builder()
            .taskId(new TaskId(UUID.randomUUID()))
            .assignee(Assignee.builder().nickname("Ruben").build())
            .build();
    private final Task task = Task.builder()
            .id(command.taskId())
            .description("")
            .completed(false)
            .build();

    @Test
    void execute_getsTaskByCommandTaskId() {
        // given
        when(getTaskPort.getTask(any())).thenReturn(task);

        // when
        assignTaskUseCase.execute(command);

        // then
        verify(getTaskPort).getTask(assertArg(actual -> actual.equals(command.taskId())));
    }

    @Test
    void execute_savesNewAssigneeToTask() {
        // given
        when(getTaskPort.getTask(any())).thenReturn(task);

        // when
        assignTaskUseCase.execute(command);

        // then
        verify(saveTaskPort).saveTask(assertArg(actual -> {
            assertThat(actual.getId()).isEqualTo(command.taskId());
            assertThat(actual.getAssignee()).isEqualTo(command.assignee());
        }));
    }
}
