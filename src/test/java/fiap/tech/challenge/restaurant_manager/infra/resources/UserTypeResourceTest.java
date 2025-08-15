package fiap.tech.challenge.restaurant_manager.infra.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import fiap.tech.challenge.restaurant_manager.application.DTOs.request.userTypes.CreateUserTypeRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.userTypes.UserTypeResponse;
import fiap.tech.challenge.restaurant_manager.application.controllers.userTypes.UserTypeController;
import fiap.tech.challenge.restaurant_manager.infrastructure.resources.userTypes.UserTypeResource;
import fiap.tech.challenge.restaurant_manager.utils.UserTypeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserTypeResourceTest {

    @InjectMocks
    private UserTypeResource userTypeResource;

    @Mock
    private UserTypeController userTypeController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    private CreateUserTypeRequest createUserTypeRequest;
    private UserTypeResponse userTypeResponse;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userTypeResource)
                .setCustomArgumentResolvers(new org.springframework.data.web.PageableHandlerMethodArgumentResolver())
                .build();
        createUserTypeRequest = new CreateUserTypeRequest("ADMIN");
        userTypeResponse = UserTypeUtils.getValidUserTypeResponse();
    }

    @Test
    void testCreateUserType() throws Exception {
        when(userTypeController.createUserType(any(CreateUserTypeRequest.class)))
                .thenReturn(userTypeResponse);

        mockMvc.perform(post("/userTypes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserTypeRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(userTypeResponse.id().intValue())))
                .andExpect(jsonPath("$.userTypeName", is(userTypeResponse.userTypeName())));
    }

    @Test
    void testFindUserTypeById() throws Exception {
        when(userTypeController.findById(eq(1L))).thenReturn(userTypeResponse);

        mockMvc.perform(get("/userTypes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userTypeResponse.id().intValue())))
                .andExpect(jsonPath("$.userTypeName", is(userTypeResponse.userTypeName())));
    }

//    @Test
//    void testFindAllUserTypes() throws Exception {
//        Page<UserTypeResponse> page = new PageImpl<>(List.of(userTypeResponse));
//        when(userTypeController.findAll(any(Pageable.class))).thenReturn(page);
//
//        mockMvc.perform(get("/userTypes")
//                        .param("page", "0")
//                        .param("size", "10")
//                        .param("direction", "Sort.Direction.ASC")
//                        .param("sort", "id"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content[0].id", is(userTypeResponse.id().intValue())))
//                .andExpect(jsonPath("$.content[0].userTypeName", is(userTypeResponse.userTypeName())));
//    }

    @Test
    void testUpdateUserType() throws Exception {
        when(userTypeController.updateUser(eq(1L), any(CreateUserTypeRequest.class)))
                .thenReturn(userTypeResponse);

        mockMvc.perform(put("/userTypes/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserTypeRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userTypeResponse.id().intValue())))
                .andExpect(jsonPath("$.userTypeName", is(userTypeResponse.userTypeName())));
    }

    @Test
    void testDeleteUserType() throws Exception {
        mockMvc.perform(delete("/userTypes/{id}", 1L));
    }
}