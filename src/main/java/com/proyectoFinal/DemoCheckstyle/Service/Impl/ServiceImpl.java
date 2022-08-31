package com.proyectoFinal.DemoCheckstyle.Service.Impl;
import com.proyectoFinal.DemoCheckstyle.Dao.IDao;
import com.proyectoFinal.DemoCheckstyle.Exception.NotFoundException;
import com.proyectoFinal.DemoCheckstyle.Model.Client;
import com.proyectoFinal.DemoCheckstyle.Service.IService;
import com.proyectoFinal.DemoCheckstyle.Service.ValidadorDNI;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ServiceImpl implements IService {

	private final IDao iDao;
	@Override
	public Mono<ResponseEntity<Void>>  deleteContact(String dni) {
		if (logicavalidarLongDNI(dni) == true) {
		return this.iDao.findById(dni)
				.flatMap(obj ->
						iDao.delete(obj)
								.then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
				)
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
		}
		throw new NotFoundException("El id debe ser de 8 caracteres");
	}

	@Override
	public Flux<Client> getAllContacts() {
		return this.iDao.findAll();
	}

	@Override
	public Mono<ResponseEntity<Client>> getClient(String dni) {
		if (logicavalidarLongDNI(dni) == true){
            return this.iDao.findById(dni)
                    .map(client -> new ResponseEntity<>(client, HttpStatus.OK))
                    .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
		}
        throw new NotFoundException("El id debe ser de 8 caracteres");
	}

	@Override
	public Mono<ResponseEntity<Client>> insertClient(Client client) {
		String[] myArray = new String[] { "Personal", "Empresarial" };
		/*Arrays.sort(myArray, (s1,s2) -> s1.compareToIgnoreCase(s2));*/
		Arrays.sort(myArray, String::compareToIgnoreCase);
		List<String> types = List.of(myArray);
		/*List<String> types = List.of("Personal", "Empresarial");*/

		if (!types.contains(client.getTypeAccount())) {
			throw new NotFoundException("Tipo de cuenta invalido");
		}

		client.setDateRegister(LocalDate.now().toString());
		return this.iDao.insert(client)
		.map(cli -> new ResponseEntity<>(cli, HttpStatus.ACCEPTED))
		.defaultIfEmpty(new ResponseEntity<>(client, HttpStatus.NOT_ACCEPTABLE));
	}

	@Override
	public Mono<ResponseEntity<Client>> updateClient(String email, String dni) {
		return this.iDao.findById(dni)
		.flatMap(client -> {
		  client.setEmail(email);
		  return this.iDao.save(client)
			.map(clientSave -> new ResponseEntity<>(clientSave, HttpStatus.ACCEPTED));
		}).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	public boolean logicavalidarLongDNI (String valor){
		ValidadorDNI vl = x -> (x.length()==8);
		return vl.validarLongDNI(valor);
	}

}