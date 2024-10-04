package ru.averkiev.budget.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {

    /**
     * Временная метка, когда произошла ошибка.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    /**
     * HTTP статус ошибки.
     */
    private HttpStatus status;

    /**
     * Краткое описание ошибки.
     */
    private String error;

    /**
     * Сообщение об ошибке.
     */
    private String errorMessage;

    /**
     * Путь к ресурсу, который вызвал ошибку.
     */
    private String path;

    /**
     * Список ошибок
     */
    private List<String> errors;

    @SuppressWarnings("unused")
    public ErrorResponse() {
    }

    /**
     * Конструктор позволяет создать объект ErrorResponse.
     *
     * @param status       Http статус ошибки.
     * @param errorMessage сообщение ошибки.
     * @param path         путь к ресурсу, который вызвал ошибку.
     */
    public ErrorResponse(HttpStatus status, String errorMessage, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = status.getReasonPhrase();
        this.errorMessage = errorMessage;
        this.path = path;
    }

    /**
     * Конструктор позволяет создать объект ErrorResponse для валидации данных.
     *
     * @param status       Http статус ошибки.
     * @param errorMessage сообщение ошибки.
     * @param path         путь к ресурсу, который вызвал ошибку.
     * @param errors       список ошибок при валидации данных.
     */
    public ErrorResponse(HttpStatus status, String errorMessage, String path, List<String> errors) {
        this.status = status;
        this.errorMessage = errorMessage;
        this.path = path;
        this.errors = errors;
    }

    @SuppressWarnings("unused")
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @SuppressWarnings("unused")
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @SuppressWarnings("unused")
    public HttpStatus getStatus() {
        return status;
    }

    @SuppressWarnings("unused")
    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    @SuppressWarnings("unused")
    public String getError() {
        return error;
    }

    @SuppressWarnings("unused")
    public void setError(String error) {
        this.error = error;
    }

    @SuppressWarnings("unused")
    public String getErrorMessage() {
        return errorMessage;
    }

    @SuppressWarnings("unused")
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @SuppressWarnings("unused")
    public String getPath() {
        return path;
    }

    @SuppressWarnings("unused")
    public void setPath(String path) {
        this.path = path;
    }

    @SuppressWarnings("unused")
    public List<String> getErrors() {
        return errors;
    }

    @SuppressWarnings("unused")
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
