package com.proyectoFinal.DemoCheckstyle.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "clients")

public class Client {
    /**
    *
    */
    @Id
    private String dni;
    /**
    *
    */
    private String names;
    /**
    *
    */
    private String email;
    /**
    *
    */
    private String typeAccount;
    /**
    *
    */
    @JsonFormat(pattern = "dd:MM:yyyy KK:mm a")
    private String dateRegister;
}
