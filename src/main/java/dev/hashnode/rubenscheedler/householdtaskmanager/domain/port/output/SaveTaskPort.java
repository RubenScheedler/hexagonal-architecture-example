package dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import lombok.NonNull;

public interface SaveTaskPort {
    void execute(@NonNull Task task);
}
