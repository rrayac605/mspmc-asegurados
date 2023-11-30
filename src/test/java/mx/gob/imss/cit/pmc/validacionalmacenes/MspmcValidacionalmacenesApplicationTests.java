package mx.gob.imss.cit.pmc.validacionalmacenes;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mx.gob.imss.cit.pmc.asegurados.service.PmcAseguradoService;

@SpringBootTest
class MspmcValidacionalmacenesApplicationTests {
	
	@Autowired
	PmcAseguradoService asegurado;

	@Test
	void contextLoads() {
		assertNotNull(asegurado);
	}

}
