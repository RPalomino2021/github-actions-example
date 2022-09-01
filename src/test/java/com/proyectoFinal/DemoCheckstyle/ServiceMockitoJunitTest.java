package com.proyectoFinal.DemoCheckstyle;

import com.proyectoFinal.DemoCheckstyle.Controller.Controller;
import com.proyectoFinal.DemoCheckstyle.Dao.IDao;
import com.proyectoFinal.DemoCheckstyle.Model.Client;
import com.proyectoFinal.DemoCheckstyle.Service.Impl.ServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.time.LocalDate;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

/*@ExtendWith(MockitoExtension.class)*/
/*@SpringBootTest(classes = {ServiceMockitoJunitTest.class})*/
@ExtendWith(SpringExtension.class)
@WebFluxTest(Controller.class)
@Import(ServiceImpl.class)
public class ServiceMockitoJunitTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private IDao iDao;;

    @Test
    public  void  test_postCliente(){

        Client clientModel = new Client("75954927",
                "Reynaldo",
                "rpalomino@gmail.com",
                "Personal",
                LocalDate.now().toString());

        when(iDao.insert(clientModel)).thenReturn(Mono.just(clientModel));//Mocking

        webTestClient.post().uri("/api/v1/client/postCliente")
                .body(Mono.just(clientModel),Client.class)
                .exchange().
                expectStatus().
                isAccepted();
    }

    @Test
    public  void  test_getAllContacts(){

        Flux<Client> clientModelFlux =
                Flux.just(new Client("75954927", "Reynaldo", "rpalomino@gmail.com", "Personal", LocalDate.now().toString()),
                          new Client("70157142", "Lesly", "lesly@gmail.com", "Empresarial", LocalDate.now().toString()));

        when(iDao.findAll()).thenReturn(clientModelFlux);//Mocking

        Flux<Client> responseBody= webTestClient.get().uri("/api/v1/client/getAll")
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(Client.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(new Client("75954927",
                        "Reynaldo",
                        "rpalomino@gmail.com",
                        "Personal",
                        LocalDate.now().toString()))
                .expectNext(new Client("70157142",
                        "Lesly",
                        "lesly@gmail.com",
                        "Empresarial",
                        LocalDate.now().toString()))
                .verifyComplete();
    }

    @Test
    public void test_getClient(){

        Client clientModel = new Client("75954927",
                "Reynaldo",
                "rpalomino@gmail.com",
                "Personal",
                LocalDate.now().toString());

        when(iDao.findById(clientModel.getDni())).thenReturn(Mono.just(clientModel));//Mocking

        Flux<Client> responseBody = webTestClient.get().uri("/api/v1/client/getCliente/75954927")
                .exchange()
                .expectStatus().isOk()
                .returnResult(Client.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(p->p.getDni().equals("75954927"))
                .verifyComplete();
    }

    @Test
    public void deleteProductTest(){
        given(iDao.delete(any())).willReturn(Mono.empty());
        webTestClient.delete().uri("/api/v1/client/deleteCliente/72000000")
                .exchange()
                .expectStatus().isOk();//200
    }

}
