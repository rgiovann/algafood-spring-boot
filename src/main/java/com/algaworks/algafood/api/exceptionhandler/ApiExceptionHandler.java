package com.algaworks.algafood.api.exceptionhandler;

import java.util.List;
import java.util.stream.Collectors;

import org.flywaydb.core.internal.util.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontradoException(EntidadeNaoEncontradaException ex,
			WebRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;
		Problem problem = createProblemBuilder(status, ProblemType.RECURSO_NAO_ENCONTRADO, ex.getMessage()).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		Problem problem = createProblemBuilder(status, ProblemType.PROBLEMA_NA_REQUISICAO, ex.getMessage()).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);

	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request) {

		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		
		ex.printStackTrace();  // #debug
		String details = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema "
				+"persistir, entre em contato com o administrador do sistema.";
		Problem problem = createProblemBuilder(status, ProblemType.ERRO_DO_SISTEMA, details).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<Object> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request) {

		HttpStatus status = HttpStatus.CONFLICT;
		Problem problem = createProblemBuilder(status, ProblemType.ENTIDADE_EM_USO, ex.getMessage()).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(
			NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		
		String recurso = ex.getRequestURL();
		String detail = String.format("O recurso '%s', que você tentou acessar, é inexistente",recurso);
		Problem problem = createProblemBuilder(status, ProblemType.RECURSO_NAO_ENCONTRADO, detail).build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		
		//System.out.println(rootCause.getClass().getName()); // #debug
		
		//ex.printStackTrace(); // #debug
		
		if (rootCause instanceof MethodArgumentTypeMismatchException) {

			return handleMethodArgumentTypeMismatchException((MethodArgumentTypeMismatchException) rootCause, headers, status, request);
		}  

		String detail = "Parâmetro da URL inválido. Verifique o erro de sintaxe.";
		Problem problem = createProblemBuilder(status, ProblemType.PARAMETRO_INVALIDO, detail).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Throwable rootCause = ExceptionUtils.getRootCause(ex);

		// rootCause.printStackTrace(); #debug
		// System.out.println(rootCause.getClass().getName()); #debug
		// .out.println(rootCause instanceof UnrecognizedPropertyException); #debug

		if (rootCause instanceof InvalidFormatException) {

			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		} else if (rootCause instanceof UnrecognizedPropertyException) {

			return handleUnrecognizedPropertyException((UnrecognizedPropertyException) rootCause, headers, status,
					request);

		} else if (rootCause instanceof IgnoredPropertyException) {

			return handleIgnoredPropertyException((IgnoredPropertyException) rootCause, headers, status, request);

		}

		String detail = "O corpo da requisição está corrompido. Verifique o erro de sintaxe.";
		Problem problem = createProblemBuilder(status, ProblemType.MENSAGEM_CORROMPIDA, detail).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if (body == null) {
			body = Problem.builder().title(status.getReasonPhrase()).status(status.value()).build();
		} else if (body instanceof String) {
			body = Problem.builder().title((String) body).status(status.value()).build();
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String propertyName = ex.getPropertyName();

		String detail = String.format(
				"O parâmetro de URL '%s' recebeu o valor '%s' "
						+ "que é de um tipo inválido. Corrija e informe o valor compatível com o tipo %s",
				propertyName, ex.getValue(), ex.getParameter().getParameterType().getSimpleName());
		Problem problem = createProblemBuilder(status, ProblemType.PARAMETRO_INVALIDO, detail).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	private ResponseEntity<Object> handleUnrecognizedPropertyException(UnrecognizedPropertyException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String path = joinPath(ex.getPath());

		String detail = String.format("A propriedade '%s' não é reconhecida para a entidade '%s'.", path,
				ex.getReferringClass().getSimpleName());

		Problem problem = createProblemBuilder(status, ProblemType.MENSAGEM_CORROMPIDA, detail).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	private ResponseEntity<Object> handleIgnoredPropertyException(IgnoredPropertyException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String path = joinPath(ex.getPath());

		String detail = String.format("A propriedade '%s' da entidade '%s' deve ser ignorada no corpo da requisição.",
				path, ex.getReferringClass().getSimpleName());

		Problem problem = createProblemBuilder(status, ProblemType.MENSAGEM_CORROMPIDA, detail).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String path = joinPath(ex.getPath());

		String detail = String.format(
				"A propriedade '%s' recebeu o valor '%s' "
						+ "que é do tipo inválido. Corrija e informe o valor compatível com o tipo %s",
				path, ex.getValue(), ex.getTargetType().getSimpleName());
		Problem problem = createProblemBuilder(status, ProblemType.MENSAGEM_CORROMPIDA, detail).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
		return Problem.builder().status(status.value()).type(problemType.getUri()).title(problemType.getTitle())
				.detail(detail);

	}

	private String joinPath(List<Reference> references) {

		return references.stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
	}

}
