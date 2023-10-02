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
class CompleteTaskUseCaseTest {
    @Mock
    GetTaskPort getTaskPort;
    @Mock
    SaveTaskPort saveTaskPort;
    @InjectMocks
    CompleteTaskUseCase completeTaskUseCase;

    Task task = Task.builder()
            .id(new TaskId(UUID.randomUUID()))
            .completed(false)
            .description("Clean the kitchen")
            .build();

    @Test
    void execute_getsTask() {
        // given
        when(getTaskPort.getTask(any())).thenReturn(task);

        // when
        completeTaskUseCase.execute(task.getId());

        // then
        verify(getTaskPort).getTask(assertArg(actual -> assertThat(actual).isEqualTo(task.getId())));
    }

    @Test
    void execute_savesTaskAsCompleted() {
        // given
        when(getTaskPort.getTask(any())).thenReturn(task);

        // when
        completeTaskUseCase.execute(task.getId());

        // then
        verify(saveTaskPort).saveTask(assertArg(actual -> {
            assertThat(actual.isCompleted()).isTrue();
        }));
    }
}
