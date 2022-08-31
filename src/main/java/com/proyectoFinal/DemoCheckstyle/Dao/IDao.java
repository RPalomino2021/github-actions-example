package com.proyectoFinal.DemoCheckstyle.Dao;

import com.proyectoFinal.DemoCheckstyle.Model.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


public interface IDao extends ReactiveMongoRepository<Client, String> {
}
