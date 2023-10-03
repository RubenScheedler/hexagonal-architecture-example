package dev.hashnode.rubenscheedler.householdtaskmanager.adapter.persistence.mapping;

import dev.hashnode.rubenscheedler.householdtaskmanager.adapter.persistence.entity.TaskEntity;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.Assignee;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.id.TaskId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class TaskEntityMapperImplTest {

    @InjectMocks
    private TaskEntityMapperImpl taskEntityMapper;

    @Test
    void toDomainTask_allFieldsAreSet_mapsFieldsCorrectly() {
        // Given
        TaskEntity taskEntity = TaskEntity.builder()
                .id(java.util.UUID.randomUUID())
                .description("Test Task")
                .assignee("John Doe")
                .completed(true)
                .build();

        // When
        Task actual = taskEntityMapper.toDomainTask(taskEntity);

        // Then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getId().value()).isEqualTo(taskEntity.getId());
        assertThat(actual.getDescription()).isEqualTo(taskEntity.getDescription());
        assertThat(actual.isCompleted()).isEqualTo(taskEntity.isCompleted());
        assertThat(actual.getAssignee()).isNotNull();
        assertThat(actual.getAssignee().nickname()).isEqualTo(taskEntity.getAssignee());
    }

    @Test
    void toDomainTask_assigneeNull_mapsAssigneeToNull() {
        // Given
        TaskEntity taskWithoutAssignee = TaskEntity.builder()
                .id(java.util.UUID.randomUUID())
                .description("Test Task")
                .assignee(null)
                .completed(true)
                .build();

        // When
        Task actual = taskEntityMapper.toDomainTask(taskWithoutAssignee);

        // Then
        assertThat(actual).isNotNull();
        assertThat(actual.getAssignee()).isNull();
    }

    @Test
    void toEntity_allFieldsAreSet_mapsFieldsCorrectly() {
        // Given
        Task domainTask = Task.builder()
                .id(new TaskId(UUID.randomUUID()))
                .description("Test Task")
                .assignee(Assignee.builder().nickname("John Doe").build())
                .completed(true)
                .build();

        // When
        TaskEntity actual = taskEntityMapper.toEntity(domainTask);

        // Then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(domainTask.getId().value());
        assertThat(actual.getDescription()).isEqualTo(domainTask.getDescription());
        assertThat(actual.isCompleted()).isEqualTo(domainTask.isCompleted());
        assertThat(actual.getAssignee()).isEqualTo(domainTask.getAssignee().nickname());
    }

    @Test
    void toEntity_assigneeNull_mapsAssigneeToNull() {
        // Given
        Task taskWithoutAssignee = Task.builder()
                .id(new TaskId(UUID.randomUUID()))
                .description("Test Task")
                .assignee(null)
                .completed(true)
                .build();

        // When
        TaskEntity actual = taskEntityMapper.toEntity(taskWithoutAssignee);

        // Then
        assertThat(actual).isNotNull();
        assertThat(actual.getAssignee()).isNull();
    }
}
