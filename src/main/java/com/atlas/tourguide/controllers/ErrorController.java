package com.atlas.tourguide.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.atlas.tourguide.domain.dtos.ApiErrorResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@ControllerAdvice
@Slf4j
public class ErrorController {
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResponse> handleException(Exception ex) {
		ApiErrorResponse error = ApiErrorResponse.builder()
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.message("An unexpected error occured")
				.build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
				error
		);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
		ApiErrorResponse error = ApiErrorResponse.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.message(ex.getMessage())
				.build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
				error
		);
	}
	
	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ApiErrorResponse> handleIllegalStateException(IllegalStateException ex) {
		ApiErrorResponse error = ApiErrorResponse.builder()
				.status(HttpStatus.CONFLICT.value())
				.message(ex.getMessage())
				.build();

		return ResponseEntity.status(HttpStatus.CONFLICT).body(
				error
		);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiErrorResponse> handleBadCredentialsException(IllegalStateException ex) {
		ApiErrorResponse error = ApiErrorResponse.builder()
				.status(HttpStatus.UNAUTHORIZED.value())
				.message("Incorrect username or password.")
				.build();

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
				error
		);
	}
}
