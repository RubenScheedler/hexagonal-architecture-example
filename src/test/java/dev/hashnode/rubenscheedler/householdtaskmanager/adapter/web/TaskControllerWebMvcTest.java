package dev.hashnode.rubenscheedler.householdtaskmanager.adapter.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hashnode.rubenscheedler.householdtaskmanager.adapter.web.v1.TaskController;
import dev.hashnode.rubenscheedler.householdtaskmanager.adapter.web.v1.mapping.TaskMapper;
import dev.hashnode.rubenscheedler.householdtaskmanager.adapter.web.v1.model.TaskCreationDto;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input.AssignTaskUseCase;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input.CreateTaskUseCase;
import dev.hashnode.rubenscheedler.householdtaskmanager.domain.port.input.ViewUncompletedTasksUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = TaskController.class)
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

    @Test
    void getTasks_withoutAuthentication_gives200() throws Exception {
        // when
        mockMvc.perform(get("/api/v1/tasks")
                        .contentType("application/json"))
        // then
                .andExpect(status().isOk());
    }

    @Test
    void createTask_withoutAuthentication_gives200() throws Exception {
        // when
        mockMvc.perform(post("/api/v1/tasks")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(TaskCreationDto.builder()
                                        .description("Clean the windows")
                                        .completed(false)
                                        .assignee(null)
                                        .build()
                                ))
                )
        // then
                .andExpect(status().isOk());
    }

    @Test
    void create_taskWithInvalidBody_gives400badRequest() throws Exception {
        // when
        mockMvc.perform(post("/api/v1/tasks")
                        .contentType("application/json")
                        .content("{\"someField\": \"someValue\"}")
                )
                // then
                .andExpect(status().isBadRequest());
    }
}
