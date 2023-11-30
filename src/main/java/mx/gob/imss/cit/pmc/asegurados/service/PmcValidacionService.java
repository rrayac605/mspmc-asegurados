package mx.gob.imss.cit.pmc.asegurados.service;

import mx.gob.imss.cit.mspmccommons.dto.DetalleRegistroDTO;
import mx.gob.imss.cit.mspmccommons.exception.BusinessException;
import mx.gob.imss.cit.pmc.asegurados.input.ValidacionLocalInput;

public interface PmcValidacionService {

	DetalleRegistroDTO validaRegistro(ValidacionLocalInput input) throws BusinessException;
	DetalleRegistroDTO validaRegistroFechaBaja(ValidacionLocalInput input) throws BusinessException;
	DetalleRegistroDTO validaRegistroSinAdscrip(ValidacionLocalInput input) throws BusinessException;
	DetalleRegistroDTO validaRegistroCubetas(ValidacionLocalInput input) throws BusinessException;
}

