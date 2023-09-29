package dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetTasksPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class ViewUncompletedTasksUseCaseTest {
    @Mock
    GetTasksPort getTasksPort;
    @InjectMocks
    ViewUncompletedTasksUseCase viewUncompletedTasksUseCase;

    @Test
    void execute_executesGetTasksPort() {
        // given
        List<Task> mockTasks = List.of(mock(Task.class));
        when(getTasksPort.execute()).thenReturn(mockTasks);

        // when
        viewUncompletedTasksUseCase.execute();

        // then
        verify(getTasksPort).execute();
    }

    @Test
    void execute_onlyReturnsUncompletedTasks() {
        // given
        Task expected = mock(Task.class);
        when(expected.isCompleted()).thenReturn(false);
        Task notExpected = mock(Task.class);
        when(notExpected.isCompleted()).thenReturn(true);

        List<Task> mockTasks = List.of(expected, notExpected);

        when(getTasksPort.execute()).thenReturn(mockTasks);

        // when
        List<Task> actual = viewUncompletedTasksUseCase.execute();

        // then
        assertThat(actual).containsExactly(expected);
    }
}
