package dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input;

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
    @InjectMocks
    CreateTaskUseCase createTaskUseCase;

    @Test
    void execute_callsCreateTaskPortWithTask() {
        // given
        CreateTaskUseCase.Command command = CreateTaskUseCase.Command.builder()
                .description("Do the dishes")
                .completed(true)
                .build();

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
}
