package dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.exception.ForbiddenException;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.User;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetCurrentUserPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetTaskPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.SaveTaskPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateTaskUseCaseTest {
    @Mock
    SaveTaskPort saveTaskPort;
    @Mock
    GetTaskPort getTaskPort;
    @Mock
    GetCurrentUserPort getCurrentUserPort;
    @InjectMocks
    CreateTaskUseCase createTaskUseCase;

    CreateTaskUseCase.Command command = CreateTaskUseCase.Command.builder()
            .description("Do the dishes")
            .completed(true)
            .build();

    User authorizedUser = User.builder()
            .nickname("dad")
            .isParent(true)
            .isFamilyMember(true)
            .build();

    @Test
    void execute_callsCreateTaskPortWithTask() {
        // Given
        when(getCurrentUserPort.getCurrentUser()).thenReturn(authorizedUser);

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
        // given
        when(getCurrentUserPort.getCurrentUser()).thenReturn(authorizedUser);

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
        when(getCurrentUserPort.getCurrentUser()).thenReturn(authorizedUser);

        // when
        Task actual = createTaskUseCase.execute(command);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void execute_userNotAFamilyMember_throwsException() {
        when(getCurrentUserPort.getCurrentUser()).thenReturn(User.builder()
                .nickname("charlie")
                .isFamilyMember(false)
                .isParent(true)
                .build());

        // when + then
        assertThatThrownBy(() -> createTaskUseCase.execute(command))
                .isInstanceOf(ForbiddenException.class);
    }

    @Test
    void execute_userNotAParent_throwsException() {
        when(getCurrentUserPort.getCurrentUser()).thenReturn(User.builder()
                .nickname("charlie")
                .isFamilyMember(true)
                .isParent(false)
                .build());

        // when + then
        assertThatThrownBy(() -> createTaskUseCase.execute(command))
                .isInstanceOf(ForbiddenException.class);
    }
}
