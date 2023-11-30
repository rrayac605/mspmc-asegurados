package mx.gob.imss.cit.pmc.asegurados.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.gob.imss.cit.mspmccommons.dto.DetalleRegistroDTO;
import mx.gob.imss.cit.mspmccommons.dto.ErrorResponse;
import mx.gob.imss.cit.mspmccommons.enums.EnumHttpStatus;
import mx.gob.imss.cit.mspmccommons.exception.BusinessException;
import mx.gob.imss.cit.pmc.asegurados.input.ValidacionLocalInput;
import mx.gob.imss.cit.pmc.asegurados.service.PmcValidacionService;

@RestController
@Api(value = "Validación de  asegurados PMC", tags = { "Validación asegurados PMC Rest" })
@RequestMapping("/msasegurados/v1")
public class MpcAseguradosController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PmcValidacionService pmcValidacionService;

	@RequestMapping("/health/ready")
	@ResponseStatus(HttpStatus.OK)
	public void ready() {
		//Indica que el ms esta listo para recibir peticiones
	}

	@RequestMapping("/health/live")
	@ResponseStatus(HttpStatus.OK)
	public void live() {
		//Indica que el ms esta vivo
	}

	@ApiOperation(value = "Validaciones asegurados", nickname = "validarasegurados", notes = "Validaciones asegurados", response = Object.class, responseContainer = "List", tags = {})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Respuesta exitosa", response = Object.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Describe un error general del sistema", response = ErrorResponse.class) })
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(value = "/obtener", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object obtenerAsegurado(@RequestBody ValidacionLocalInput input) throws BusinessException {
		
		Object resultado = null;
		
		try {
			logger.info("MpcAseguradosController: obtenerAsegurado: try [{}]", input != null ? input.toString() : "");
			DetalleRegistroDTO listado = pmcValidacionService.validaRegistro(input);
			resultado = new ResponseEntity<Object>(listado, HttpStatus.OK);
			logger.info("MpcAseguradosController: obtenerAsegurado: try: returnOk");
		} catch(BusinessException be) {
			logger.info("MpcAseguradosController: obtenerAsegurado: catch:");
			ErrorResponse errorResponse = new ErrorResponse(EnumHttpStatus.SERVER_ERROR_INTERNAL, be.getMessage(), 
					                                        "Error de aplicación");
			int numberHTTPDesired = Integer.parseInt(errorResponse.getCode());
			resultado = new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(numberHTTPDesired));
			logger.info("MpcAseguradosController: obtenerAsegurado: catch: numberHTTPDesired");
		}
		
		logger.info("MpcAseguradosController: obtenerAsegurado: FinalReturn");
		return resultado;
		
	}
	
	//***********************  VALIDACION ASEGURADOS CON FECHA   ************************
	
	@ApiOperation(value = "Validaciones asegurados con fechas ", nickname = "validarasegurados", notes = "Validaciones asegurados", response = Object.class, responseContainer = "List", tags = {})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Respuesta exitosa", response = Object.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Describe un error general del sistema", response = ErrorResponse.class) })
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(value = "/obtenerFechaBaja", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object obtenerAseguradoFechaBaja(@RequestBody ValidacionLocalInput input) throws BusinessException {
		
		Object resultado = null;
		
		try {
			
			logger.info("MpcAseguradosController: obtenerAsegurado: try [{}]", input != null ? input.toString() : "");
			DetalleRegistroDTO listado = pmcValidacionService.validaRegistroFechaBaja(input);
			resultado = new ResponseEntity<Object>(listado, HttpStatus.OK);
			logger.info("MpcAseguradosController: obtenerAseguradoFechaBaja: try: returnOk");
			
		} catch(BusinessException be) {
			
			logger.info("MpcAseguradosController: obtenerAseguradoFechaBaja: catch:");
			ErrorResponse errorResponse = new ErrorResponse(EnumHttpStatus.SERVER_ERROR_INTERNAL, be.getMessage(), 
					                                        "Error de aplicación");
			int numberHTTPDesired = Integer.parseInt(errorResponse.getCode());
			resultado = new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(numberHTTPDesired));
			logger.info("MpcAseguradosController: obtenerAseguradoFechaBaja: catch: numberHTTPDesired");
			
		}
		
		logger.info("MpcAseguradosController: obtenerAseguradoFechaBaja: FinalReturn");
		return resultado;
		
	}
	
	/*** Validación Asegurados Sin Adscripción ***/
	@ApiOperation(value = "Validaciones asegurados", nickname = "validarasegurados", notes = "Validaciones asegurados sin adscripcion", response = Object.class, responseContainer = "List", tags = {})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Respuesta exitosa", response = Object.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Describe un error general del sistema", response = ErrorResponse.class) })
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(value = "/obtenerSinAdscripcion", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object obtenerAseguradoSinAdscripcion(@RequestBody ValidacionLocalInput input) throws BusinessException {
		
		Object resultado = null;
		
		try {
			logger.info("MpcAseguradosController: obtenerAsegurado: try [{}]", input != null ? input.toString() : "");
			DetalleRegistroDTO listado = pmcValidacionService.validaRegistroSinAdscrip(input);
			resultado = new ResponseEntity<Object>(listado, HttpStatus.OK);
			logger.info("MpcAseguradosController: obtenerAseguradoSinAdscrip: try: returnOk");
		} catch(BusinessException be) {
			logger.info("MpcAseguradosController: obtenerAseguradoSinAdscrip: catch:");
			ErrorResponse errorResponse = new ErrorResponse(EnumHttpStatus.SERVER_ERROR_INTERNAL, be.getMessage(), 
					                                        "Error de aplicación");
			int numberHTTPDesired = Integer.parseInt(errorResponse.getCode());
			resultado = new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(numberHTTPDesired));
			logger.info("MpcAseguradosController: obtenerAseguradoSinAdscrip: catch: numberHTTPDesired");
		}
		
		logger.info("MpcAseguradosController: obtenerAseguradoSinAdscrip: FinalReturn");
		return resultado;
		
	}
	
	@ApiOperation(value = "Validaciones asegurados", nickname = "validarasegurados", notes = "Validaciones asegurados cubetas", response = Object.class, responseContainer = "List", tags = {})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Respuesta exitosa", response = Object.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Describe un error general del sistema", response = ErrorResponse.class) })
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(value = "/obtenerCubetas", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object obtenerAseguradoCubetas(@RequestBody ValidacionLocalInput input) throws BusinessException {
		
		Object resultado = null;
		
		try {
			logger.info("MpcAseguradosController: obtenerAseguradoCubetas: try [{}]", input != null ? input.toString() : "");
			DetalleRegistroDTO listado = pmcValidacionService.validaRegistroCubetas(input);
			resultado = new ResponseEntity<Object>(listado, HttpStatus.OK);
			logger.info("MpcAseguradosController: obtenerAseguradoSinAdscrip: try: returnOk");
		} catch(BusinessException be) {
			logger.info("MpcAseguradosController: obtenerAseguradoSinAdscrip: catch:");
			ErrorResponse errorResponse = new ErrorResponse(EnumHttpStatus.SERVER_ERROR_INTERNAL, be.getMessage(), 
					                                        "Error de aplicación");
			int numberHTTPDesired = Integer.parseInt(errorResponse.getCode());
			resultado = new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(numberHTTPDesired));
			logger.info("MpcAseguradosController: obtenerAseguradoSinAdscrip: catch: numberHTTPDesired");
		}
		
		logger.info("MpcAseguradosController: obtenerAseguradoSinAdscrip: FinalReturn");
		return resultado;
		
	}

}
