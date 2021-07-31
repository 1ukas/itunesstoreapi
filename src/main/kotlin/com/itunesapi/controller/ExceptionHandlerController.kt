package com.itunesapi.controller

import com.itunesapi.dto.ErrorMessageModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import javax.validation.ConstraintViolationException

@ControllerAdvice
class ExceptionHandlerController {
    @ExceptionHandler(value = [ConstraintViolationException::class, MethodArgumentTypeMismatchException::class, HttpMessageNotReadableException::class])
    fun handleConstraintExceptions(): ResponseEntity<ErrorMessageModel> =
        ResponseEntity<ErrorMessageModel>(ErrorMessageModel("Bad request parameters"), HttpStatus.BAD_REQUEST)

    @ExceptionHandler(value = [MissingServletRequestParameterException::class])
    fun handleMissingRequestParams(msrpe: MissingServletRequestParameterException): ResponseEntity<ErrorMessageModel> =
        ResponseEntity(ErrorMessageModel(msrpe.message), HttpStatus.BAD_REQUEST)
}