package dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.exception.ForbiddenException;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.User;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.id.TaskId;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetCurrentUserPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetTaskPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.SaveTaskPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EditTaskDescriptionUseCaseTest {
    @Mock
    GetTaskPort getTaskPort;
    @Mock
    SaveTaskPort saveTaskPort;
    @Mock
    GetCurrentUserPort getCurrentUserPort;
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

    User authorizedUser = User.builder()
            .nickname("mom")
            .isParent(true)
            .isFamilyMember(true)
            .build();

    @Test
    void execute_getsTask() {
        // given
        when(getTaskPort.getTask(any())).thenReturn(task);
        when(getCurrentUserPort.getCurrentUser()).thenReturn(authorizedUser);

        // when
        editTaskDescriptionUseCase.execute(command);

        // then
        verify(getTaskPort).getTask(assertArg(actual -> assertThat(actual).isEqualTo(task.getId())));
    }


    @Test
    void execute_savesTaskWithNewDescription() {
        // given
        when(getTaskPort.getTask(any())).thenReturn(task);
        when(getCurrentUserPort.getCurrentUser()).thenReturn(authorizedUser);

        // when
        editTaskDescriptionUseCase.execute(command);

        // then
        verify(saveTaskPort).saveTask(assertArg(actual -> {
            assertThat(actual.getDescription()).isEqualTo(expectedNewDescription);
        }));
    }
    @Test
    void execute_userNotAFamilyMember_throwsException() {
        when(getCurrentUserPort.getCurrentUser()).thenReturn(User.builder()
                .nickname("charlie")
                .isFamilyMember(false)
                .isParent(true)
                .build());

        // when + then
        assertThatThrownBy(() -> editTaskDescriptionUseCase.execute(command))
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
        assertThatThrownBy(() -> editTaskDescriptionUseCase.execute(command))
                .isInstanceOf(ForbiddenException.class);
    }
}
