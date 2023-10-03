package dev.hashnode.rubenscheedler.householdtaskmanager.adapter.web.v1.mapping;

import dev.hashnode.rubenscheedler.householdtaskmanager.adapter.web.v1.model.TaskDto;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.Assignee;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.id.TaskId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class TaskMapperImplTest {
    TaskMapperImpl taskMapper = new TaskMapperImpl();
    private TaskId taskId = new TaskId(UUID.randomUUID());
    String description = "Clean the shower";
    Assignee assignee = Assignee.builder().nickname("Bob").build();

    private Task domainTask = Task.builder()
                .id(taskId)
                .description(description)
                .assignee(assignee)
                .completed(false)
                .build();

    private TaskDto taskDto = TaskDto.builder()
            .id(taskId.value())
            .description(description)
            .assignee(assignee.nickname())
            .build();

    @Test
    void toDto_mapsRespectiveFields() {
        // when
        TaskDto actual = taskMapper.toDto(domainTask);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(domainTask.getId().value());
        assertThat(actual.getDescription()).isEqualTo(domainTask.getDescription());
        assertThat(actual.getAssignee()).isEqualTo(domainTask.getAssignee().nickname());
    }

    @Test
    void toDto_assigneeNull_setsAssigneeToNull() {
        // given
        Task domainTask = Task.builder()
                .id(taskId)
                .description(description)
                .assignee(null)
                .completed(false)
                .build();

        // when
        TaskDto actual = taskMapper.toDto(domainTask);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getAssignee()).isNull();
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void toDomainTask_mapsRespectiveFields(boolean completed) {
        // when
        Task actual = taskMapper.toDomainObject(taskDto, completed);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getId().value()).isEqualTo(taskDto.getId());
        assertThat(actual.getDescription()).isEqualTo(taskDto.getDescription());
        assertThat(actual.getAssignee()).isNotNull();
        assertThat(actual.getAssignee().nickname()).isEqualTo(taskDto.getAssignee());
        assertThat(actual.isCompleted()).isEqualTo(completed);
    }

    @Test
    void toDtoList_mapsTasksSimilarlyToIndividualTask() {
        // when
        List<TaskDto> actual = taskMapper.toDtoList(List.of(domainTask));

        // then
        assertThat(actual).containsExactly(taskMapper.toDto(domainTask));
    }
}
