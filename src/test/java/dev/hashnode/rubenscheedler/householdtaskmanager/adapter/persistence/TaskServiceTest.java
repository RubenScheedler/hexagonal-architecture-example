package dev.hashnode.rubenscheedler.householdtaskmanager.adapter.persistence;

import dev.hashnode.rubenscheedler.householdtaskmanager.adapter.persistence.entity.TaskEntity;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.exception.TaskDoesNotExistException;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.Assignee;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.id.TaskId;
import lombok.NonNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @Mock
    CrudRepository<TaskEntity, UUID> repository;
    @Mock
    TaskEntityMapper taskEntityMapper;
    @InjectMocks
    TaskService taskService;

    TaskId taskId = new TaskId(UUID.randomUUID());
    TaskEntity taskEntity = TaskEntity.builder()
            .id(taskId.value())
            .assignee("Bob")
            .completed(false)
            .description("Do the dishes")
            .build();

    Task task = Task.builder()
            .id(taskId)
            .assignee(Assignee.builder().nickname("Bob").build())
            .completed(false)
            .description("Do the dishes")
            .build();

    @Test
    void deleteTask_deletesTaskViaRepository() {
        // when
        taskService.deleteTask(taskId);
        // then
        verify(repository).deleteById(taskId.value());
    }

    @Test
    void getTask_getsTaskViaRepository() {
        // given
        when(repository.findById(taskId.value())).thenReturn(Optional.of(taskEntity));
        when(taskEntityMapper.toDomainTask(any())).thenReturn(task);
        // when
        taskService.getTask(taskId);
        // then
        verify(repository).findById(taskId.value());
    }

    @Test
    void getTask_taskExists_mapsAndReturnsRepositoryResult() {
        // given
        when(repository.findById(taskId.value())).thenReturn(Optional.of(taskEntity));
        when(taskEntityMapper.toDomainTask(any())).thenReturn(task);

        // when
        Task actual = taskService.getTask(taskId);

        // then
        assertThat(actual).isEqualTo(task);
    }

    @Test
    void getTask_taskDoesNotExist_throwsException() {
        // given
        when(repository.findById(taskId.value())).thenReturn(Optional.empty());

        // when + then
        assertThatThrownBy(() -> taskService.getTask(taskId)).isInstanceOf(TaskDoesNotExistException.class);
    }

    @Test
    void getTasks_callsFindAllOnRepository() {
        // when
        taskService.getTasks();
        // then
        verify(repository).findAll();
    }

    @Test
    void getTasks_mapsAndReturnsRepositoryResult() {
        // given
        TaskEntity taskEntity1 = TaskEntity.builder()
                .id(UUID.randomUUID())
                .assignee("Bob")
                .completed(false)
                .description("Do the dishes")
                .build();
        Task domainTask1 = Task.builder()
                .id(new TaskId(taskEntity1.getId()))
                .assignee(Assignee.builder().nickname("Bob").build())
                .completed(false)
                .description("Do the dishes")
                .build();

        TaskEntity taskEntity2 = TaskEntity.builder()
                .id(UUID.randomUUID())
                .assignee("Bob")
                .completed(false)
                .description("Do the dishes")
                .build();
        Task domainTask2 = Task.builder()
                .id(new TaskId(taskEntity2.getId()))
                .assignee(Assignee.builder().nickname("Bob").build())
                .completed(false)
                .description("Do the dishes")
                .build();

        when(repository.findAll()).thenReturn(List.of(taskEntity1, taskEntity2));

        when(taskEntityMapper.toDomainTask(taskEntity1)).thenReturn(domainTask1);
        when(taskEntityMapper.toDomainTask(taskEntity2)).thenReturn(domainTask2);

        // when
        List<Task> actual = taskService.getTasks();

        // then
        assertThat(actual).containsExactly(domainTask1, domainTask2);
    }

    @Test
    void saveTask_savesMappedTask() {
        // given
        when(taskEntityMapper.toEntity(task)).thenReturn(taskEntity);
        // when
        taskService.saveTask(task);
        // then
        verify(repository).save(taskEntity);
    }
}
