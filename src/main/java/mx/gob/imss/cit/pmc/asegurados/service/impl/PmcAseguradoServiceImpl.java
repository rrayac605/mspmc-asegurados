package mx.gob.imss.cit.pmc.asegurados.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import mx.gob.imss.cit.mspmccommons.dto.AseguradoDTO;
import mx.gob.imss.cit.mspmccommons.dto.AseguradoDTO2;
import mx.gob.imss.cit.mspmccommons.dto.AseguradoJsonDTO;
import mx.gob.imss.cit.mspmccommons.exception.BusinessException;
import mx.gob.imss.cit.mspmccommons.utils.DigitoVerificadorUtils;
import mx.gob.imss.cit.pmc.asegurados.service.PmcAseguradoService;

@Component
public class PmcAseguradoServiceImpl implements PmcAseguradoService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private Environment env;

	@Override
	public AseguradoDTO existeAsegurado(String nssAsegurado) throws BusinessException {
		
		AseguradoDTO aseguradoDTO = null;
		
		try {

			digitoVerificadorNSS(nssAsegurado);
			String urlAseguradoMSValicacionAlmacenes = env.getProperty("urlAseguradoMSValicacionAlmacenes");
			System.out.println("url " + urlAseguradoMSValicacionAlmacenes);
			
			aseguradoDTO = responseAsegurado(urlAseguradoMSValicacionAlmacenes, nssAsegurado);
			
		} catch (RestClientException rce) {

			String fechaMsg = rce.getMessage();
			Boolean fechaBaja = null;

			fechaBaja = fechaMsg != null ? fechaMsg.contains("Asegurado con fecha de  baja") : null;

			if (fechaBaja != null && fechaBaja) {
				aseguradoDTO = new AseguradoDTO();
				return aseguradoDTO;
			} else {
				logger.error("Error existeAsegurado:", rce);
				throw new BusinessException(rce);
			}

		} catch (Exception exc) {

			exc.printStackTrace();

		}

		return aseguradoDTO;
	}

	// ******************************** FECHA BAJA *******************************
	@Override
	public AseguradoDTO existeAseguradoFechaBaja(String nssAsegurado) throws BusinessException {
		ResponseEntity<AseguradoJsonDTO> responseAseguradoFecBaja = null;
		AseguradoDTO aseguradoDTO = null;
		AseguradoJsonDTO aseguradoFecBaja = null;
		try {
			digitoVerificadorNSS(nssAsegurado);
			String urlAseguradoMSValicacionAlmacenesVFBaja = env.getProperty("urlAseguradoMSValicacionAlmacenesVFBaja");
			responseAseguradoFecBaja = urlAseguradoMSValicacionAlmacenesVFBaja != null ? restTemplate.getForEntity(
					urlAseguradoMSValicacionAlmacenesVFBaja.replace("{nssAsegurado}", nssAsegurado),
					AseguradoJsonDTO.class) : null;
			if (responseAseguradoFecBaja != null) {
				aseguradoFecBaja = responseAseguradoFecBaja.getBody();
			}
			if (aseguradoFecBaja != null) {
				aseguradoDTO = new AseguradoDTO();
				aseguradoDTO.setCveIdPersona(aseguradoFecBaja.getCveIdPersona());
				aseguradoDTO.setRefCurp(aseguradoFecBaja.getCurp());
				aseguradoDTO.setNomAsegurado(aseguradoFecBaja.getNombre());
				aseguradoDTO.setRefPrimerApellido(aseguradoFecBaja.getPrimerApellido());
				aseguradoDTO.setRefSegundoApellido(aseguradoFecBaja.getSegundoApellido());
				aseguradoDTO.setNumNss(nssAsegurado);
				if (aseguradoFecBaja.getSubdelegacion() != null) {
					aseguradoDTO.setCveSubdelNss(aseguradoFecBaja.getSubdelegacion().getClave());
					aseguradoDTO.setDesSubDelNss(aseguradoFecBaja.getSubdelegacion().getDescripcion());
					if (aseguradoFecBaja.getSubdelegacion().getDelegacion() != null) {
						aseguradoDTO.setCveDelegacionNss(aseguradoFecBaja.getSubdelegacion().getDelegacion().getClave());
						aseguradoDTO.setDesDelegacionNss(aseguradoFecBaja.getSubdelegacion().getDelegacion().getDescripcion());
						aseguradoDTO.setCveIdSubdelNss(aseguradoFecBaja.getSubdelegacion().getId());
					}
				}
				if (aseguradoFecBaja.getUmf() != null) {
					aseguradoDTO.setCveUmfAdscripcion(aseguradoFecBaja.getUmf().getNumUMF());
					aseguradoDTO.setDesUmfAdscripcion(aseguradoFecBaja.getUmf().getDescUMF());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return aseguradoDTO;
	}

	/*** Asegurado Sin Información de Adscripción ***/
	@Override
	public AseguradoDTO existeAseguradoSinAdscrip(String nssAsegurado) throws BusinessException {

		ResponseEntity<AseguradoJsonDTO> responseAseguradoSinAdscrip = null;
		AseguradoDTO aseguradoDTO = null;

		try {

			digitoVerificadorNSS(nssAsegurado);
			String urlAseguradoSinAdscripcion = env.getProperty("urlAseguradoSinAdscripcion");
			responseAseguradoSinAdscrip = urlAseguradoSinAdscripcion != null
										  ? restTemplate.getForEntity(urlAseguradoSinAdscripcion.concat(nssAsegurado), 
										  AseguradoJsonDTO.class) : null;

			if (responseAseguradoSinAdscrip != null) {
				
				AseguradoJsonDTO aseguradoSinAdscrip = responseAseguradoSinAdscrip.getBody();
				
				if (aseguradoSinAdscrip != null) {
					aseguradoDTO = new AseguradoDTO();
					aseguradoDTO.setCveIdPersona(aseguradoSinAdscrip.getCveIdPersona());
					aseguradoDTO.setRefCurp(aseguradoSinAdscrip.getCurp());
					aseguradoDTO.setNomAsegurado(aseguradoSinAdscrip.getNombre());
					aseguradoDTO.setRefPrimerApellido(aseguradoSinAdscrip.getPrimerApellido());
					aseguradoDTO.setRefSegundoApellido(aseguradoSinAdscrip.getSegundoApellido());
					aseguradoDTO.setNumNss(nssAsegurado);
				}
				
			}

		} catch (RestClientException rce) {

			logger.error("Error existeAseguradoSinAdscrip: ", rce);

		} catch (Exception exc) {

			exc.printStackTrace();

		}

		return aseguradoDTO;
	}

	private String digitoVerificadorNSS(String nssAsegurado) {

		if ((nssAsegurado != null && !nssAsegurado.trim().equals("")) && nssAsegurado.length() == 10) {
			nssAsegurado = nssAsegurado.concat(DigitoVerificadorUtils.generaDigitoVerificador(nssAsegurado));
		}

		return nssAsegurado;

	}
	
	private AseguradoDTO responseAsegurado(String urlAseguradoMSValicacionAlmacenes, String nssAsegurado) {
		
		ResponseEntity<AseguradoJsonDTO> responseAsegurado = null;
		AseguradoJsonDTO aseguradoAlmacenes = null;
		AseguradoDTO aseguradoDTO = null;
		
		responseAsegurado = urlAseguradoMSValicacionAlmacenes != null
	                        ? restTemplate.getForEntity(urlAseguradoMSValicacionAlmacenes.concat(nssAsegurado),
	                        AseguradoJsonDTO.class) : null;
	
	    if (responseAsegurado != null) {
	    	aseguradoAlmacenes = responseAsegurado.getBody();
	    }
	    
	    if (aseguradoAlmacenes != null) {
			aseguradoDTO = new AseguradoDTO();
			aseguradoDTO.setCveIdPersona(aseguradoAlmacenes.getCveIdPersona());
			aseguradoDTO.setRefCurp(aseguradoAlmacenes.getCurp());
			aseguradoDTO.setNomAsegurado(aseguradoAlmacenes.getNombre());
			aseguradoDTO.setRefPrimerApellido(aseguradoAlmacenes.getPrimerApellido());
			aseguradoDTO.setRefSegundoApellido(aseguradoAlmacenes.getSegundoApellido());
			aseguradoDTO.setNumNss(nssAsegurado);
			if (aseguradoAlmacenes.getSubdelegacion() != null) {
				aseguradoDTO.setCveSubdelNss(aseguradoAlmacenes.getSubdelegacion().getClave());
				aseguradoDTO.setDesSubDelNss(aseguradoAlmacenes.getSubdelegacion().getDescripcion());
			}
			if (aseguradoAlmacenes.getSubdelegacion().getDelegacion() != null) {
				aseguradoDTO.setCveDelegacionNss(aseguradoAlmacenes.getSubdelegacion().getDelegacion().getClave());
				aseguradoDTO.setDesDelegacionNss(aseguradoAlmacenes.getSubdelegacion().getDelegacion().getDescripcion());
				aseguradoDTO.setCveIdSubdelNss(aseguradoAlmacenes.getSubdelegacion().getId());
			}
			if (aseguradoAlmacenes.getUmf() != null) {
				aseguradoDTO.setCveUmfAdscripcion(aseguradoAlmacenes.getUmf().getNumUMF());
				aseguradoDTO.setDesUmfAdscripcion(aseguradoAlmacenes.getUmf().getDescUMF());
			}
		}
	    
		return aseguradoDTO;
		
	}

	@Override
	public AseguradoDTO existeAseguradoCubetas(String nssAsegurado) throws BusinessException {
		AseguradoDTO aseguradoDTO = null;
		
		try {
			//vamos a estar usando la misma variable de entorno
			//ya que apunta al mismo servicio
			digitoVerificadorNSS(nssAsegurado);
			String urlAseguradoMSValicacionAlmacenes = env.getProperty("urlAseguradoSinAdscripcion");			
			aseguradoDTO = responseAsegurado2(urlAseguradoMSValicacionAlmacenes, nssAsegurado);
			
		} catch (Exception exc) {

			exc.printStackTrace();
		}

		return aseguradoDTO;
	}
	private AseguradoDTO responseAsegurado2(String urlAseguradoMSCubetas, String nssAsegurado) {
		
		ResponseEntity<AseguradoDTO2> responseAsegurado = null;
		AseguradoDTO2 aseguradoAlmacenes = null;
		AseguradoDTO aseguradoDTO = null;
		
		responseAsegurado = urlAseguradoMSCubetas != null
	                        ? restTemplate.getForEntity(urlAseguradoMSCubetas.concat(nssAsegurado).concat("/marcaAfiliatoria"),
	                        	AseguradoDTO2.class) : null;
	
	                        System.out.println("despues de realizar la peticion ");
	    if (responseAsegurado != null) {
	    	aseguradoAlmacenes = responseAsegurado.getBody();
	    	System.out.println(aseguradoAlmacenes);
	    	System.out.println(aseguradoAlmacenes.getNombre());
	    }
	    
	    
	    if (aseguradoAlmacenes != null) {
			aseguradoDTO = new AseguradoDTO();
			aseguradoDTO.setCveIdPersona(aseguradoAlmacenes.getCveIdPersona());
			aseguradoDTO.setRefCurp(aseguradoAlmacenes.getCurp());
			aseguradoDTO.setNomAsegurado(aseguradoAlmacenes.getNombre());
			aseguradoDTO.setRefPrimerApellido(aseguradoAlmacenes.getPrimerApellido());
			aseguradoDTO.setRefSegundoApellido(aseguradoAlmacenes.getSegundoApellido());
			aseguradoDTO.setNumNss(nssAsegurado);
			aseguradoDTO.setMarcaAfiliatoria(aseguradoAlmacenes.getCubeta());
			aseguradoDTO.setMarcaBaja(aseguradoAlmacenes.getBajaNss());
			if (aseguradoAlmacenes.getSubdelegacion() != null
					&& !StringUtils.isEmpty(aseguradoAlmacenes.getSubdelegacion().getClave())) {
				aseguradoDTO.setCveSubdelNss(Integer.parseInt(aseguradoAlmacenes.getSubdelegacion().getClave()));
				aseguradoDTO.setDesSubDelNss(aseguradoAlmacenes.getSubdelegacion().getDescripcion());
				aseguradoDTO.setIdSubDelNss(aseguradoAlmacenes.getSubdelegacion().getId());
			}
			if (aseguradoAlmacenes.getSubdelegacion().getDelegacion() != null
					&& !StringUtils.isEmpty(aseguradoAlmacenes.getSubdelegacion().getDelegacion().getClave())) {
				aseguradoDTO.setCveDelegacionNss(
						Integer.parseInt(aseguradoAlmacenes.getSubdelegacion().getDelegacion().getClave()));
				aseguradoDTO
						.setDesDelegacionNss(aseguradoAlmacenes.getSubdelegacion().getDelegacion().getDescripcion());
				aseguradoDTO.setCveIdSubdelNss(aseguradoAlmacenes.getSubdelegacion().getId());
			}
			if (aseguradoAlmacenes.getUmf() != null && !StringUtils.isEmpty(aseguradoAlmacenes.getUmf().getNumUMF())) {
				aseguradoDTO.setCveUmfAdscripcion(aseguradoAlmacenes.getUmf().getNumUMF());
				aseguradoDTO.setDesUmfAdscripcion(aseguradoAlmacenes.getUmf().getDescUMF());
			}
		}
	    	    
		return aseguradoDTO;
		
	}

}