package com.fiap.pedidos.controllers.requestValidations;

import com.fiap.pedidos.entities.Cliente;
import com.fiap.pedidos.entities.Cpf;
import com.fiap.pedidos.entities.Email;
import com.fiap.pedidos.entities.Nome;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ClienteRequestTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    @DisplayName("Deve criar um Cliente a partir de um ClienteRequest válido")
    void deveCriarClienteAPartirDeClienteRequestValido() {
        ClienteRequest clienteRequest = new ClienteRequest();
        clienteRequest.setNome("John Doe");
        clienteRequest.setCpf("11111111111");
        clienteRequest.setEmail("john.doe@example.com");

        Cliente cliente = clienteRequest.from(clienteRequest);

        assertThat(cliente).isNotNull();
        assertThat(cliente.getNome()).isEqualToComparingFieldByField(new Nome("John Doe"));
        assertThat(cliente.getCpf()).isEqualToComparingFieldByField(new Cpf("11111111111"));
        assertThat(cliente.getEmail()).isEqualToComparingFieldByField(new Email("john.doe@example.com"));
    }

    @Test
    @DisplayName("Deve falhar ao criar um Cliente a partir de um ClienteRequest inválido")
    void deveFalharAoCriarClienteAPartirDeClienteRequestInvalido() {
        ClienteRequest clienteRequest = new ClienteRequest();
        clienteRequest.setNome("");
        clienteRequest.setCpf("");
        clienteRequest.setEmail("");

        Set<ConstraintViolation<ClienteRequest>> violations = validator.validate(clienteRequest);

        assertThat(violations).hasSize(3);
        assertThat(violations).anyMatch(violation -> violation.getMessage().contains("nome não pode estar vazio"));
        assertThat(violations).anyMatch(violation -> violation.getMessage().contains("cpf não pode estar vazio"));
        assertThat(violations).anyMatch(violation -> violation.getMessage().contains("email não pode estar vazio"));
    }
}

