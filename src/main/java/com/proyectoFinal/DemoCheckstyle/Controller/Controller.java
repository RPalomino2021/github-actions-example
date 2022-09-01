package com.proyectoFinal.DemoCheckstyle.Controller;

import com.proyectoFinal.DemoCheckstyle.Model.Client;
import com.proyectoFinal.DemoCheckstyle.Service.IService;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("api/v1/client")
@AllArgsConstructor
public class Controller {

    private static Logger LogJava = Logger.getLogger(Controller.class);
    private final IService iService;

    @GetMapping (value = "/getAll")
    public Flux<Client> getAllContacts() {
        LogJava.info("[Inicio Controller Client] Metodo getAll...");
        try {
            return this.iService.getAllContacts();
        } catch (Exception e) {
            LogJava.error("Ocurrio un error Metodo getAll " + e.getMessage());
        }finally {
            LogJava.info("[Fin Controller Client] Metodo getAll");
        }
        return null;
    }

    @GetMapping (value = "/getCliente/{dni}")
    public Mono<ResponseEntity<Client>> getClient(@PathVariable String dni) {
        LogJava.info("[Inicio Controller Client] Metodo getCliente...");
        try {
            return this.iService.getClient(dni);
        } catch (Exception e) {
            LogJava.error("Ocurrio un error Metodo getCliente" + e.getMessage());
        }finally {
            LogJava.info("[Fin Controller Client] Metodo getCliente");
        }
        return null;
    }
    
    @PostMapping (value = "/postCliente")
    public Mono<ResponseEntity<Client>> insertClient(@RequestBody Client client){
        LogJava.info("[Inicio Controller Client] Metodo postCliente...");
        try {
            return this.iService.insertClient(client);
        } catch (Exception e) {
            LogJava.error("Ocurrio un error Metodo postCliente " + e.getMessage());
        }finally {
            LogJava.info("[Fin Controller Client] Metodo postCliente");
        }
        return null;
    }
    
    @PutMapping (value = "/putCliente/{dni}/{email}")
    public Mono<ResponseEntity<Client>> updateClient(@PathVariable String dni, @RequestParam String email) {
        LogJava.info("[Inicio Controller Client] Metodo putCliente...");
        try {
            return this.iService.updateClient(email, dni);
        } catch (Exception e) {
            LogJava.error("Ocurrio un error Metodo putCliente" + e.getMessage());
        }finally {
            LogJava.info("[Fin Controller Client] Metodo putCliente");
        }
        return null;
    }

    @DeleteMapping (value = "/deleteCliente/{dni}")
	public Mono<ResponseEntity<Void>>  deleteContact(@PathVariable String dni) {
        LogJava.info("[Inicio Controller Client] Metodo deleteCliente...");
        try {
            return this.iService.deleteContact(dni);
        } catch (Exception e) {
            LogJava.error("Ocurrio un error Metodo deleteCliente " + e.getMessage());
        }finally {
            LogJava.info("[Fin Controller Client] Metodo deleteCliente");
        }
        return null;
    }


}
