package com.fiap.pedidos.controllers;

import com.fiap.pedidos.adapters.ClienteDTO;
import com.fiap.pedidos.controllers.requestValidations.ClienteRequest;
import com.fiap.pedidos.entities.Cliente;
import com.fiap.pedidos.entities.Cpf;
import com.fiap.pedidos.interfaces.usecases.IClienteUseCasePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/tech-challenge")
@RequiredArgsConstructor
public class ClienteController {

    private final IClienteUseCasePort clienteUseCasePort;

    @GetMapping(value = "/clientes/{id}", produces = "application/json")
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable(name = "id") UUID uuid) {
        Optional<Cliente> cliente = clienteUseCasePort.buscarPorId(uuid);
        return cliente
                .map(value -> ResponseEntity.ok(new ClienteDTO().from(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/clientes")
    public ResponseEntity<?>  cadastrar(@RequestBody @Validated ClienteRequest clienteRequest) {
        if (Objects.nonNull(clienteRequest)) {
            Cliente clienteDb = clienteUseCasePort.cadastrar(clienteRequest.from(clienteRequest));
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(clienteDb.getId()).toUri();
            return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, uri.toString()).body(new ClienteDTO().from(clienteDb));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/clientes/{cpf}")
    public ResponseEntity<?> identificarPorCpf(@PathVariable(name = "cpf") String cpf) {
        if (Objects.nonNull(cpf)) {
            Cliente clienteDb = clienteUseCasePort.identificarPorCpf(new Cpf(cpf));
            URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/tech-challenge/clientes/{id}").buildAndExpand(clienteDb.getId()).toUri();
            return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, uri.toString()).body(new ClienteDTO().from(clienteDb));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/clientes/id")
    public ResponseEntity<?> identificarSemCPF() {
        UUID idCliente = clienteUseCasePort.gerarId();
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/tech-challenge/clientes/{id}").buildAndExpand(idCliente).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, uri.toString()).body(idCliente);
    }

}
