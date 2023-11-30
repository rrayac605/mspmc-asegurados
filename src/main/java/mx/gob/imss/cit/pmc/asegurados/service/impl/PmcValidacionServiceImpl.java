package mx.gob.imss.cit.pmc.asegurados.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.mspmccommons.dto.AseguradoDTO;
import mx.gob.imss.cit.mspmccommons.dto.AseguradoDTO2;
import mx.gob.imss.cit.mspmccommons.dto.BitacoraErroresDTO;
import mx.gob.imss.cit.mspmccommons.dto.DetalleRegistroDTO;
import mx.gob.imss.cit.mspmccommons.exception.BusinessException;
import mx.gob.imss.cit.pmc.asegurados.input.ValidacionLocalInput;
import mx.gob.imss.cit.pmc.asegurados.service.PmcAseguradoService;
import mx.gob.imss.cit.pmc.asegurados.service.PmcValidacionService;

@Component
public class PmcValidacionServiceImpl implements PmcValidacionService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private static final String MSG_ASEGURADO = "No existe el Asegurado en BDTU";

	@Autowired
	private PmcAseguradoService pmcAseguradoService;

	@Override
	public DetalleRegistroDTO validaRegistro(ValidacionLocalInput input) throws BusinessException {
		
		List<BitacoraErroresDTO> errores = new ArrayList<>();
		BitacoraErroresDTO bitacoraErroresDTO = null;
		AseguradoDTO aseguradoDTO = null;
		DetalleRegistroDTO detalle = new DetalleRegistroDTO();
		
		logger.info("Validando información");
		aseguradoDTO = pmcAseguradoService.existeAsegurado(input.getNss());
		
		if (aseguradoDTO == null) {
			logger.info("No existe el asegurado: {}", input.getNss());
			bitacoraErroresDTO = new BitacoraErroresDTO();
			bitacoraErroresDTO.setDesCodigoError(MSG_ASEGURADO);
			errores.add(bitacoraErroresDTO);
		}
		if (errores.isEmpty()) {
			detalle.setAseguradoDTO(aseguradoDTO);
		} else {
			detalle.setBitacoraErroresDTO(errores);
		}
		
		return detalle;
		
	}
	
	@Override
	public DetalleRegistroDTO validaRegistroFechaBaja(ValidacionLocalInput input) throws BusinessException {
		
		List<BitacoraErroresDTO> errores = new ArrayList<>();
		BitacoraErroresDTO bitacoraErroresDTO = null;
		AseguradoDTO aseguradoDTO = null;
		DetalleRegistroDTO detalle = new DetalleRegistroDTO();
		
		logger.info("Validando información");
		aseguradoDTO = pmcAseguradoService.existeAseguradoFechaBaja(input.getNss());
		
		if (aseguradoDTO == null) {
			logger.info("No existe el asegurado con fecha de baja: {}", input.getNss());
			bitacoraErroresDTO = new BitacoraErroresDTO();
			bitacoraErroresDTO.setDesCodigoError(MSG_ASEGURADO);
			errores.add(bitacoraErroresDTO);
		}
		if (errores.isEmpty()) {
			detalle.setAseguradoDTO(aseguradoDTO);
		} else {
			detalle.setBitacoraErroresDTO(errores);
		}
		
		return detalle;
		
	}
	
	@Override
	public DetalleRegistroDTO validaRegistroSinAdscrip(ValidacionLocalInput input) throws BusinessException {
		
		List<BitacoraErroresDTO> errores = new ArrayList<>();
		BitacoraErroresDTO bitacoraErroresDTO = null;
		AseguradoDTO aseguradoDTO = null;
		DetalleRegistroDTO detalle = new DetalleRegistroDTO();
		
		logger.info("Validando información");
		aseguradoDTO = pmcAseguradoService.existeAseguradoSinAdscrip(input.getNss());
		
		if (aseguradoDTO == null) {
			logger.info("No existe el asegurado sion adscripcion: {}", input.getNss());
			bitacoraErroresDTO = new BitacoraErroresDTO();
			bitacoraErroresDTO.setDesCodigoError(MSG_ASEGURADO);
			errores.add(bitacoraErroresDTO);
		}
		if (errores.isEmpty()) {
			detalle.setAseguradoDTO(aseguradoDTO);
		} else {
			detalle.setBitacoraErroresDTO(errores);
		}
		
		return detalle;
		
	}

	@Override
	public DetalleRegistroDTO validaRegistroCubetas(ValidacionLocalInput input) throws BusinessException {
		List<BitacoraErroresDTO> errores = new ArrayList<>();
		BitacoraErroresDTO bitacoraErroresDTO = null;
		AseguradoDTO aseguradoDTO = null;
		DetalleRegistroDTO detalle = new DetalleRegistroDTO();
		
		logger.info("Validando información");
		aseguradoDTO = pmcAseguradoService.existeAseguradoCubetas(input.getNss());
		
		if (aseguradoDTO == null) {
			logger.info("No existe el asegurado sion adscripcion: {}", input.getNss());
			bitacoraErroresDTO = new BitacoraErroresDTO();
			bitacoraErroresDTO.setDesCodigoError(MSG_ASEGURADO);
			errores.add(bitacoraErroresDTO);
		}
		if (errores.isEmpty()) {
			detalle.setAseguradoDTO(aseguradoDTO);
		} else {
			detalle.setBitacoraErroresDTO(errores);
		}
		
		return detalle;
	}
		
}
