package dev.hashnode.rubenscheedler.householdtaskmanager.adapter.persistence;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.id.TaskId;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.DeleteTaskPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetTaskPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetTasksPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.SaveTaskPort;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskService implements GetTaskPort, GetTasksPort, SaveTaskPort, DeleteTaskPort {

    @Override
    public void deleteTask(@NonNull TaskId taskId) {
        // TODO implement
    }

    @Override
    public Task getTask(@NonNull TaskId taskId) {
        // TODO implement
        return null;
    }

    @Override
    public List<Task> getTasks() {
        // TODO implement
        return null;
    }

    @Override
    public void saveTask(@NonNull Task task) {
        // TODO implement
    }
}
