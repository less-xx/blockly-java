package org.teapotech.blockly.controller;

import java.time.format.DateTimeParseException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.teapotech.base.web.ErrorResponse;
import org.teapotech.user.exception.InvalidUserCredentialsException;
import org.teapotech.user.exception.UserNotLogonException;

@ControllerAdvice
public class ControllerExceptionHandler {

	private final static Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

	@ExceptionHandler({ InvalidUserCredentialsException.class })
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ResponseBody
	public ErrorResponse handleLoginFailedException(Exception e, HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) {
		return respondError(e, httpRequest, httpResponse, false);
	}

	@ExceptionHandler({ UserNotLogonException.class })
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public ErrorResponse handleUserNotLogonException(Exception e, HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) {
		return respondError(e, httpRequest, httpResponse, false);
	}

	@ExceptionHandler({ EntityNotFoundException.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorResponse handleEntityNotFoundException(Exception e, HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) {
		return respondError(e, httpRequest, httpResponse, false);
	}

	@ExceptionHandler({ EntityExistsException.class })
	@ResponseStatus(value = HttpStatus.CONFLICT)
	@ResponseBody
	public ErrorResponse handleEntityExistsException(Exception e, HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) {
		return respondError(e, httpRequest, httpResponse, false);
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
			HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		BindingResult r = e.getBindingResult();
		if (r.getErrorCount() > 0) {
			ObjectError error = r.getAllErrors().get(0);
			String msg = error.getDefaultMessage();
			return respondError(msg, e, httpRequest, httpResponse, false);
		}
		return respondError(e, httpRequest, httpResponse, false);
	}

	@ExceptionHandler({ IllegalArgumentException.class, ConversionFailedException.class,
			HttpMessageNotReadableException.class, PropertyReferenceException.class, DateTimeParseException.class,
			ResourceAccessException.class, MissingServletRequestPartException.class, })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse handleInvalidInputException(Exception e, HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) {

		return respondError(e, httpRequest, httpResponse, false);
	}

	@ExceptionHandler({ Exception.class })
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorResponse handleGeneralException(Exception e, HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) {
		return respondError(e, httpRequest, httpResponse, true);
	}

	private ErrorResponse respondError(Exception e, HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			boolean showStackTrace) {
		return respondError(null, e, httpRequest, httpResponse, showStackTrace);
	}

	private ErrorResponse respondError(String message, Exception e, HttpServletRequest httpRequest,
			HttpServletResponse httpResponse, boolean showStackTrace) {
		String msg = StringUtils.isBlank(message) ? e.getMessage() : message;
		if (showStackTrace) {
			LOG.error(e.getMessage(), e);
		} else {
			LOG.error(e.getClass() + ": " + e.getMessage());
		}
		return ErrorResponse.of(msg);
	}
}
