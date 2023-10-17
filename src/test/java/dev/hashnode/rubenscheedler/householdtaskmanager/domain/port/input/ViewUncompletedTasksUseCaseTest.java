package dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.exception.ForbiddenException;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.User;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetCurrentUserPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetTasksPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ViewUncompletedTasksUseCaseTest {
    @Mock
    GetTasksPort getTasksPort;
    @Mock
    GetCurrentUserPort getCurrentUserPort;

    @InjectMocks
    ViewUncompletedTasksUseCase viewUncompletedTasksUseCase;

    User authorizedUser = User.builder()
            .nickname("bob")
            .isParent(false)
            .isFamilyMember(true)
            .build();

    @Test
    void execute_executesGetTasksPort() {
        // given
        List<Task> mockTasks = List.of(mock(Task.class));
        when(getTasksPort.getTasks()).thenReturn(mockTasks);
        when(getCurrentUserPort.getCurrentUser()).thenReturn(authorizedUser);

        // when
        viewUncompletedTasksUseCase.execute();

        // then
        verify(getTasksPort).getTasks();
    }

    @Test
    void execute_onlyReturnsUncompletedTasks() {
        // given
        Task expected = mock(Task.class);
        when(expected.isCompleted()).thenReturn(false);
        Task notExpected = mock(Task.class);
        when(notExpected.isCompleted()).thenReturn(true);

        List<Task> mockTasks = List.of(expected, notExpected);

        when(getTasksPort.getTasks()).thenReturn(mockTasks);
        when(getCurrentUserPort.getCurrentUser()).thenReturn(authorizedUser);

        // when
        List<Task> actual = viewUncompletedTasksUseCase.execute();

        // then
        assertThat(actual).containsExactly(expected);
    }

    @Test
    void execute_userNotAFamilyMember_throwsException() {
        when(getCurrentUserPort.getCurrentUser()).thenReturn(User.builder()
                        .nickname("charlie")
                        .isFamilyMember(false)
                        .isParent(true)
                .build());

        // when + then
        assertThatThrownBy(() -> viewUncompletedTasksUseCase.execute())
                .isInstanceOf(ForbiddenException.class);
    }
}
