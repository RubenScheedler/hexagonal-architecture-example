package dev.hashnode.rubenscheedler.householdtaskmanager.adapter.persistence.mapping;

import dev.hashnode.rubenscheedler.householdtaskmanager.adapter.persistence.TaskEntityMapper;
import dev.hashnode.rubenscheedler.householdtaskmanager.adapter.persistence.entity.TaskEntity;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.Assignee;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.id.TaskId;
import org.springframework.stereotype.Component;

@Component
public class TaskEntityMapperImpl implements TaskEntityMapper {
    @Override
    public Task toDomainTask(TaskEntity entity) {
        return Task.builder()
                .id(new TaskId(entity.getId()))
                .description(entity.getDescription())
                .assignee(entity.getAssignee() != null ? Assignee.builder().nickname(entity.getAssignee()).build() : null)
                .completed(entity.isCompleted())
                .build();
    }

    @Override
    public TaskEntity toEntity(Task domainTask) {
        return TaskEntity.builder()
                .id(domainTask.getId().value())
                .description(domainTask.getDescription())
                .assignee(domainTask.getAssignee() != null ? domainTask.getAssignee().nickname() : null)
                .completed(domainTask.isCompleted())
                .build();
    }
}
