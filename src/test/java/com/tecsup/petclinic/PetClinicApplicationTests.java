package com.tecsup.petclinic;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PetClinicApplicationTests {

	@Test
	public void contextLoads() throws InterruptedException {
		System.out.println("====== TEST CONGELADO: Abre http://localhost:8080/h2-console ======");

		// Detiene el test durante 3 minutos (180,000 milisegundos) antes de que H2 se destruya
		Thread.sleep(60000);
	}

}
