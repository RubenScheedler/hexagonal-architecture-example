package dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.User;

public interface GetCurrentUserPort {
    User getCurrentUser();
}
