package dev.hashnode.rubenscheedler.householdtaskmanager.adapter.web.v1.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class TaskCreationDto {
    /**
     * What needs to be done
     */
    @NonNull String description;
    /**
     * Optionally, an initial assignee of the new task
     */
    String assignee;
    /**
     * Is the task already completed
     */
    boolean completed;
}
