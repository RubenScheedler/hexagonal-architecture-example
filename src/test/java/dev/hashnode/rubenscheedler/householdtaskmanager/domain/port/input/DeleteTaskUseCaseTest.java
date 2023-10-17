package dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.exception.ForbiddenException;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.User;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.id.TaskId;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.DeleteTaskPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetCurrentUserPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteTaskUseCaseTest {
    @Mock
    DeleteTaskPort deleteTaskPort;
    @Mock
    GetCurrentUserPort getCurrentUserPort;
    @InjectMocks
    DeleteTaskUseCase deleteTaskUseCase;

    Task task = Task.builder()
            .id(new TaskId(UUID.randomUUID()))
            .completed(false)
            .description("Clean the kitchen")
            .build();

    User authorizedUser = User.builder()
            .nickname("dad")
            .isParent(true)
            .isFamilyMember(true)
            .build();

    @Test
    void execute_deletesTask() {
        // Given
        when(getCurrentUserPort.getCurrentUser()).thenReturn(authorizedUser);

        // when
        deleteTaskUseCase.execute(task.getId());

        // then
        verify(deleteTaskPort).deleteTask(assertArg(actual -> assertThat(actual).isEqualTo(task.getId())));
    }
    @Test
    void execute_userNotAFamilyMember_throwsException() {
        when(getCurrentUserPort.getCurrentUser()).thenReturn(User.builder()
                .nickname("charlie")
                .isFamilyMember(false)
                .isParent(true)
                .build());

        // when + then
        assertThatThrownBy(() -> deleteTaskUseCase.execute(task.getId()))
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
        assertThatThrownBy(() -> deleteTaskUseCase.execute(task.getId()))
                .isInstanceOf(ForbiddenException.class);
    }
}
