package br.com.wasabisushi.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/error")
public class MeuErrorController implements ErrorController {


	@GetMapping
	public String handleError(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());

			if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
				return "Error/error401";
			} else if (statusCode == HttpStatus.FORBIDDEN.value()) {
				return "Error/error403";
			} else if (statusCode == HttpStatus.NOT_FOUND.value()) {
				return "Error/error404";
			} else if (statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()) {
				return "Error/error405";
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return "Error/error500";
			}
	}
		return "error";
	}

	public String getErrorPath() {
		// TODO Auto-generated method stub
		return null;
	}

}
