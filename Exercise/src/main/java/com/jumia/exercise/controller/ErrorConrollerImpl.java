package com.jumia.exercise.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author Hany
 *
 * HTML errors handling class
 */
@Controller
public class ErrorConrollerImpl implements ErrorController {

	/**
	 * Handles HTML errors 400, 404, and 500.
	 * 
	 * @param request
	 * @return error page
	 */
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		String errorPage = "/error/error";

		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());

			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				return "/error/error-404";
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return "/error/error-500";
			} else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
				return "/error/error-400";
			} else {
				return "/error/error";
			}
		}

		return errorPage;

	}

}
