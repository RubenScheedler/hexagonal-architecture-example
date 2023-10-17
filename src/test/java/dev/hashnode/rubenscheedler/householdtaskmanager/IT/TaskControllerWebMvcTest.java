package dev.hashnode.rubenscheedler.householdtaskmanager.IT;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hashnode.rubenscheedler.householdtaskmanager.adapter.usermanagement.UserDetailsConfig;
import dev.hashnode.rubenscheedler.householdtaskmanager.adapter.web.v1.TaskController;
import dev.hashnode.rubenscheedler.householdtaskmanager.adapter.web.v1.configuration.WebSecurityConfig;
import dev.hashnode.rubenscheedler.householdtaskmanager.adapter.web.v1.mapping.TaskMapper;
import dev.hashnode.rubenscheedler.householdtaskmanager.adapter.web.v1.model.TaskCreationDto;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.exception.ForbiddenException;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(TaskController.class)
class TaskControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TaskMapper taskMapper;
    @MockBean
    private ViewUncompletedTasksUseCase viewUncompletedTasksUseCase;
    @MockBean
    private CreateTaskUseCase createTaskUseCase;
    @MockBean
    private AssignTaskUseCase assignTaskUseCase;
    @MockBean
    private CompleteTaskUseCase completeTaskUseCase;
    @MockBean
    private EditTaskDescriptionUseCase editTaskDescriptionUseCase;
    @MockBean
    private UnassignTaskUseCase unassignTaskUseCase;
    @MockBean
    private DeleteTaskUseCase deleteTaskUseCase;

    @Test
    void getTasks_withoutAuthentication_gives401() throws Exception {
        // when
        mockMvc.perform(get("/api/v1/tasks")
                        .contentType("application/json"))
        // then
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = "dad")
    void getTasks_withAuthentication_gives200() throws Exception {
        // when
        mockMvc.perform(get("/api/v1/tasks")
                        .contentType("application/json"))
                // then
                .andExpect(status().isOk());
    }

    @Test
    void createTask_withoutAuthentication_gives401() throws Exception {
        // when
        mockMvc.perform(post("/api/v1/tasks")
                        .with(csrf())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(TaskCreationDto.builder()
                                        .description("Clean the windows")
                                        .completed(false)
                                        .assignee(null)
                                        .build()
                                ))
                )
        // then
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = "dad")
    void createTask_withInvalidBody_gives400badRequest() throws Exception {
        // when
        mockMvc.perform(post("/api/v1/tasks")
                        .with(csrf())
                        .contentType("application/json")
                        .content("{\"someField\": \"someValue\"}")
                )
        // then
                .andExpect(status().isBadRequest());
    }

    @Test
    void assignTask_gives401() throws Exception {
        // when
        UUID taskId = UUID.randomUUID();
        mockMvc.perform(patch("/api/v1/tasks/" + taskId + "/assignee")
                        .with(csrf())
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("Alice")
                )
        // then
                .andExpect(status().isUnauthorized());
    }

    @Test
    void completeTask_gives401() throws Exception {
        // when
        UUID taskId = UUID.randomUUID();
        mockMvc.perform(patch("/api/v1/tasks/" + taskId + "/complete")
                        .with(csrf())
                        .contentType(MediaType.ALL)
                )
        // then
                .andExpect(status().isUnauthorized());
    }

    @Test
    void editTaskDescription_gives401() throws Exception {
        // when
        UUID taskId = UUID.randomUUID();
        mockMvc.perform(patch("/api/v1/tasks/" + taskId + "/description")
                        .with(csrf())
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("Do laundry")
                )
                // then
                .andExpect(status().isUnauthorized());
    }

    @Test
    void unassignTask_gives401() throws Exception {
        // when
        UUID taskId = UUID.randomUUID();
        mockMvc.perform(patch("/api/v1/tasks/" + taskId + "/unassign")
                        .with(csrf())
                )
        // then
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteTask_gives401() throws Exception {
        // when
        UUID taskId = UUID.randomUUID();
        mockMvc.perform(delete("/api/v1/tasks/" + taskId)
                        .with(csrf())
                )
        // then
        .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void addTask_usecasesThrowsException_gives403() throws Exception {
        // given
        when(createTaskUseCase.execute(any())).thenThrow(ForbiddenException.class);

        // when
        mockMvc.perform(post("/api/v1/tasks")
                        .with(csrf())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(TaskCreationDto.builder()
                                .description("Clean the windows")
                                .completed(false)
                                .assignee(null)
                                .build()
                        ))
                )
                // then
                .andExpect(status().isUnauthorized());
    }
}
