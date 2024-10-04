package ru.averkiev.budget.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.averkiev.budget.utils.ErrorResponse;

import java.util.List;

@ControllerAdvice
@SuppressWarnings("unused")
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        List<String> errorMessages = fieldErrors.stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        ErrorResponse response = new ErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Ошибки при валидации данных",
                request.getRequestURI(),
                errorMessages
        );
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistException(EmailAlreadyExistException eaeEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.CONFLICT,
                eaeEx.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(PasswordAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handlePasswordAlreadyExistException(PasswordAlreadyExistException eaeEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.CONFLICT,
                eaeEx.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(CategoryAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleCategoryAlreadyExistException(CategoryAlreadyExistException eaeEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.CONFLICT,
                eaeEx.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(EmailValidationException.class)
    public ResponseEntity<ErrorResponse> handleEmailValidationException(EmailValidationException evEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                evEx.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(CategoryValidationException.class)
    public ResponseEntity<ErrorResponse> handleCategoryValidationException(CategoryValidationException evEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                evEx.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(UsernameValidationException.class)
    public ResponseEntity<ErrorResponse> handleUsernameValidationException(UsernameValidationException uvEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                uvEx.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException unfEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                unfEx.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFoundException(AccountNotFoundException unfEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                unfEx.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(TransactionTypeNotFound.class)
    public ResponseEntity<ErrorResponse> handleTransactionTypeNotFound(TransactionTypeNotFound unfEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                unfEx.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFoundException(CategoryNotFoundException unfEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                unfEx.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}