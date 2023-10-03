package dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetTaskPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.SaveTaskPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateTaskUseCaseTest {
    @Mock
    SaveTaskPort saveTaskPort;
    @Mock
    GetTaskPort getTaskPort;
    @InjectMocks
    CreateTaskUseCase createTaskUseCase;

    CreateTaskUseCase.Command command = CreateTaskUseCase.Command.builder()
            .description("Do the dishes")
            .completed(true)
            .build();

    @Test
    void execute_callsCreateTaskPortWithTask() {
        // when
        createTaskUseCase.execute(command);

        // then
        verify(saveTaskPort).saveTask(assertArg(actual -> {
            assertThat(actual.getId()).isNotNull();
            assertThat(actual.getDescription()).isEqualTo(command.description());
            assertThat(actual.isCompleted()).isEqualTo(command.completed());
            assertThat(actual.getAssignee()).isEqualTo(command.assignee());
        }));
    }

    @Test
    void execute_callsGetTaskPort() {
        // when
        createTaskUseCase.execute(command);

        // then
        verify(getTaskPort).getTask(any());
    }

    @Test
    void execute_returnsGetTaskPortResult() {
        // given
        Task expected = mock(Task.class);
        when(getTaskPort.getTask(any())).thenReturn(expected);

        // when
        Task actual = createTaskUseCase.execute(command);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
