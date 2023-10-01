package dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.id.TaskId;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.DeleteTaskPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetTaskPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class DeleteTaskUseCaseTest {
    @Mock
    GetTaskPort getTaskPort;
    @Mock
    DeleteTaskPort deleteTaskPort;
    @InjectMocks
    DeleteTaskUseCase deleteTaskUseCase;

    Task task = Task.builder()
            .id(new TaskId(UUID.randomUUID()))
            .completed(false)
            .description("Clean the kitchen")
            .build();

    @Test
    void execute_getsTask() {
        // given
        when(getTaskPort.execute(any())).thenReturn(task);

        // when
        deleteTaskUseCase.execute(task.getId());

        // then
        verify(getTaskPort).execute(assertArg(actual -> assertThat(actual).isEqualTo(task.getId())));
    }

    @Test
    void execute_deletesTask() {
        // given
        when(getTaskPort.execute(any())).thenReturn(task);

        // when
        deleteTaskUseCase.execute(task.getId());

        // then
        verify(deleteTaskPort).execute(assertArg(actual -> assertThat(actual).isEqualTo(task.getId())));
    }
}
