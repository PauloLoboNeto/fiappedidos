package com.fiap.pedidos.exceptions.handlers;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class StandardErrorTest {

    @Test
    void deveCriarStandardErrorVazio() {
        var erro = new StandardError();
        assertThat(erro).isNotNull();
        assertThat(erro).isInstanceOf(StandardError.class);
    }
    @Test
    void deveCriarStandardErrorBuilder() {
        var erro = StandardError.builder().build();
        assertThat(erro).isNotNull();
        assertThat(erro).isInstanceOf(StandardError.class);
    }
    @Test
    void deveCriarStandardError() {
        var erro = new StandardError();
        erro.setTimeStamp(new Date().getTime());
        erro.setStatus(500);
        erro.setError("erro");
        erro.setMessage("message");
        erro.setPath("/");

        assertThat(erro).isNotNull();
        assertThat(erro.getTimeStamp()).isNotNull();
        assertThat(erro.getStatus()).isNotNull();
        assertThat(erro.getError()).isNotNull();
        assertThat(erro.getMessage()).isNotNull();
        assertThat(erro.getPath()).isNotNull();
    }

}
