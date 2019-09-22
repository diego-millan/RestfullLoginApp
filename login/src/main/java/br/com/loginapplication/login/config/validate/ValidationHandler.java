package br.com.loginapplication.login.config.validate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.loginapplication.login.config.validate.dto.InvalidFormDTO;

@RestControllerAdvice
public class ValidationHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<InvalidFormDTO> Handle(MethodArgumentNotValidException e) {
		
		List<InvalidFormDTO> invalidFormDTOList = new ArrayList<InvalidFormDTO>();
		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
		
		for (FieldError fieldError : fieldErrors) {
			String errorMessage = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			InvalidFormDTO invalidFormDTO = new InvalidFormDTO(fieldError.getField(), errorMessage);
			invalidFormDTOList.add(invalidFormDTO);
		}
		
		return invalidFormDTOList;
	}
}
