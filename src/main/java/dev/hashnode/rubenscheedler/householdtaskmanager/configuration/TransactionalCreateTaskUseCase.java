package dev.hashnode.rubenscheedler.householdtaskmanager.configuration;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.model.entity.Task;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input.CreateTaskUseCase;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetCurrentUserPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.GetTaskPort;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.output.SaveTaskPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class TransactionalCreateTaskUseCase extends CreateTaskUseCase {
    public TransactionalCreateTaskUseCase(SaveTaskPort saveTaskPort, GetTaskPort getTaskPort, GetCurrentUserPort getCurrentUserPort) {
        super(saveTaskPort, getTaskPort, getCurrentUserPort);
    }

    @Override
    public Task execute(Command command) {
        return super.execute(command);
    }
}
