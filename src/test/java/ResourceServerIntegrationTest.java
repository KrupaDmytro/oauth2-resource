import com.JacksonUtil;
import com.ResourceServerApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.web.dto.Bar;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceServerApplication.class)
@AutoConfigureMockMvc
public class ResourceServerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EurekaClient eurekaClient;

    private AuthorizationComponent authorizationComponent = new AuthorizationComponent();

    @Test
    public void whenLoadApplication_thenSuccess() throws Exception {
    }

    @Test
    public void whenGetBar_thenReturnBarAndOkStatus() throws Exception {
        mockMvc.perform(get("/bars/1")
                .header("Authorization", "Bearer " + authorizationComponent.getJWT(getAuthServerPort()).getAccessToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(14))
                .andExpect(jsonPath("$.name").value("test"));
    }

    @Test
    public void whenPostBar_thenReturnBarAndCreatedStatus() throws Exception {

        Bar bar = new Bar();

        bar.setName("T-NAME");

        mockMvc.perform(post("/bars")
                .content(JacksonUtil.serialize(bar))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + authorizationComponent.getJWT(getAuthServerPort()).getAccessToken()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("T-NAME"));
    }

    private String getAuthServerPort(){
        Application application
                = eurekaClient.getApplication("spring-cloud-eureka-client");
        InstanceInfo instanceInfo = application.getInstances().get(0);
        return String.valueOf(instanceInfo.getPort());
    }
}