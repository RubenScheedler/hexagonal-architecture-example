package dev.hashnode.rubenscheedler.householdtaskmanager.configuration;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input.*;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class that is responsible for making all use case beans.
 */
@Configuration
public class UseCaseBeanConfiguration {
    @Bean
    AssignTaskUseCase assignTaskUseCase(GetTaskPort getTaskPort, SaveTaskPort saveTaskPort, GetCurrentUserPort getCurrentUserPort) {
        return new AssignTaskUseCase(getTaskPort, saveTaskPort, getCurrentUserPort);
    }

    @Bean
    CompleteTaskUseCase completeTaskUseCase(GetTaskPort getTaskPort, SaveTaskPort saveTaskPort, GetCurrentUserPort getCurrentUserPort) {
        return new CompleteTaskUseCase(getTaskPort, saveTaskPort, getCurrentUserPort);
    }

    @Bean
    CreateTaskUseCase createTaskUseCase(SaveTaskPort saveTaskPort, GetTaskPort getTaskPort, GetCurrentUserPort getCurrentUserPort) {
        return new CreateTaskUseCase(saveTaskPort, getTaskPort, getCurrentUserPort);
    }

    @Bean
    DeleteTaskUseCase deleteTaskUseCase(DeleteTaskPort deleteTaskPort, GetCurrentUserPort getCurrentUserPort) {
        return new DeleteTaskUseCase(deleteTaskPort, getCurrentUserPort);
    }

    @Bean
    EditTaskDescriptionUseCase editTaskDescriptionUseCase(GetTaskPort getTaskPort, SaveTaskPort saveTaskPort, GetCurrentUserPort getCurrentUserPort) {
        return new EditTaskDescriptionUseCase(getTaskPort, saveTaskPort, getCurrentUserPort);
    }

    @Bean
    UnassignTaskUseCase unassignTaskUseCase(GetTaskPort getTaskPort, SaveTaskPort saveTaskPort, GetCurrentUserPort getCurrentUserPort) {
        return new UnassignTaskUseCase(getTaskPort, saveTaskPort, getCurrentUserPort);
    }

    @Bean
    ViewUncompletedTasksUseCase viewUncompletedTasksUseCase(GetTasksPort getTasksPort, GetCurrentUserPort getCurrentUserPort) {
        return new ViewUncompletedTasksUseCase(getTasksPort, getCurrentUserPort);
    }
}
