package mx.gob.imss.cit.pmc.asegurados.service;

import mx.gob.imss.cit.mspmccommons.dto.AseguradoDTO;
import mx.gob.imss.cit.mspmccommons.dto.AseguradoDTO2;
import mx.gob.imss.cit.mspmccommons.exception.BusinessException;

public interface PmcAseguradoService {

	AseguradoDTO existeAsegurado(String nssAsegurado) throws BusinessException;
	AseguradoDTO existeAseguradoFechaBaja(String nssAsegurado) throws BusinessException;
	AseguradoDTO existeAseguradoSinAdscrip(String nssAsegurado) throws BusinessException;
	AseguradoDTO existeAseguradoCubetas(String nssAsegurado) throws BusinessException;

}
