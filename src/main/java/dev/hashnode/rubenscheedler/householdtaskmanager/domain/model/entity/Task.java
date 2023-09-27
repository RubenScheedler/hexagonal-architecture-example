package dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.Owner;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.value.id.TaskId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class Task {
    /**
     * Unique id of the task.
     */
    @NonNull TaskId id;
    /**
     * Textual description of the task that needs to be done.
     */
    @NonNull String description;
    /**
     * Whether the task is already completed or not.
     */
    boolean completed;
    /**
     * (Optional) The current owner/person that is working on the task.
     */
    Owner owner;
}
