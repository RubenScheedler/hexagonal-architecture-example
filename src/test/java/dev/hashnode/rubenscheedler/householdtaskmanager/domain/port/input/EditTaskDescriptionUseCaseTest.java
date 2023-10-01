package dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
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
class EditTaskDescriptionUseCaseTest {
    @Mock
    GetTaskPort getTaskPort;
    @Mock
    SaveTaskPort saveTaskPort;
    @InjectMocks
    EditTaskDescriptionUseCase editTaskDescriptionUseCase;

    Task task = Task.builder()
            .id(new TaskId(UUID.randomUUID()))
            .completed(false)
            .description("Cleann the kitchen")
            .build();

    String expectedNewDescription = "Clean the kitchen";
    EditTaskDescriptionUseCase.Command command = EditTaskDescriptionUseCase.Command.builder()
                .taskId(task.getId())
                .newDescription(expectedNewDescription)
                .build();

    @Test
    void execute_getsTask() {
        // given
        when(getTaskPort.execute(any())).thenReturn(task);

        // when
        editTaskDescriptionUseCase.execute(command);

        // then
        verify(getTaskPort).execute(assertArg(actual -> assertThat(actual).isEqualTo(task.getId())));
    }


    @Test
    void execute_savesTaskWithNewDescription() {
        // given
        when(getTaskPort.execute(any())).thenReturn(task);

        // when
        editTaskDescriptionUseCase.execute(command);

        // then
        verify(saveTaskPort).execute(assertArg(actual -> {
            assertThat(actual.getDescription()).isEqualTo(expectedNewDescription);
        }));
    }
}
