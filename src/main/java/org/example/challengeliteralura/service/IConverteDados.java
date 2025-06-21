package org.example.challengeliteralura.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
