package dev.hashnode.rubenscheedler.householdtaskmanager.configuration;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input.*;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.DeleteTaskPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetTaskPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetTasksPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.SaveTaskPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class that is responsible for making all use case beans.
 */
@Configuration
public class UseCaseBeanConfiguration {
    @Bean
    AssignTaskUseCase assignTaskUseCase(GetTaskPort getTaskPort, SaveTaskPort saveTaskPort) {
        return new AssignTaskUseCase(getTaskPort, saveTaskPort);
    }

    @Bean
    CompleteTaskUseCase completeTaskUseCase(GetTaskPort getTaskPort, SaveTaskPort saveTaskPort) {
        return new CompleteTaskUseCase(getTaskPort, saveTaskPort);
    }

    @Bean
    CreateTaskUseCase createTaskUseCase(SaveTaskPort saveTaskPort, GetTaskPort getTaskPort) {
        return new CreateTaskUseCase(saveTaskPort, getTaskPort);
    }

    @Bean
    DeleteTaskUseCase deleteTaskUseCase(DeleteTaskPort deleteTaskPort) {
        return new DeleteTaskUseCase(deleteTaskPort);
    }

    @Bean
    EditTaskDescriptionUseCase editTaskDescriptionUseCase(GetTaskPort getTaskPort, SaveTaskPort saveTaskPort) {
        return new EditTaskDescriptionUseCase(getTaskPort, saveTaskPort);
    }

    @Bean
    UnassignTaskUseCase unassignTaskUseCase(GetTaskPort getTaskPort, SaveTaskPort saveTaskPort) {
        return new UnassignTaskUseCase(getTaskPort, saveTaskPort);
    }

    @Bean
    ViewUncompletedTasksUseCase viewUncompletedTasksUseCase(GetTasksPort getTasksPort) {
        return new ViewUncompletedTasksUseCase(getTasksPort);
    }
}
