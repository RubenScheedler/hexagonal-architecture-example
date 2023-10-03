package dev.hashnode.rubenscheedler.householdtaskmanager.adapter.web.v1.mapping;

import dev.hashnode.rubenscheedler.householdtaskmanager.adapter.web.v1.model.TaskDto;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.Assignee;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.id.TaskId;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskMapperImpl implements TaskMapper {
    @Override
    public TaskDto toDto(@NonNull Task domainTask) {
        return TaskDto.builder()
                .id(domainTask.getId().value())
                .description(domainTask.getDescription())
                .assignee(domainTask.getAssignee() != null ? domainTask.getAssignee().nickname() : null)
                .build();
    }

    @Override
    public Task toDomainObject(@NonNull TaskDto taskDto, boolean completed) {
        return Task.builder()
                .id(new TaskId(taskDto.getId()))
                .description(taskDto.getDescription())
                .assignee(Assignee.builder().nickname(taskDto.getAssignee()).build())
                .completed(completed)
                .build();
    }

    @Override
    public List<TaskDto> toDtoList(@NonNull List<Task> domainTasks) {
        return domainTasks.stream().map(this::toDto).toList();
    }
}
