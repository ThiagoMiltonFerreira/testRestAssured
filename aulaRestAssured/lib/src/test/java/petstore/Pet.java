package petstore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import org.testng.annotations.Test;

public class Pet {
     
	 String uri = "https://petstore.swagger.io/v2/pet";
	 
	 //Metodo para ler o arquivo json, este que o arquivo que vai ser enviado no body da requisição
	 public String lerJson(String caminhoJson) throws IOException {
		 return new String(Files.readAllBytes(Paths.get(caminhoJson)));
	 }
	 
	 @Test(priority=1)
	 public void CadastrarPetValido() throws IOException {

		 String jsonBody = lerJson(System.getProperty("user.dir")+"\\db\\pet1.json");
		
		// Sintexe Gherkin
		// Dado - Quando - Então
		// Given - When - Then
		
		// Dado que envio o body
		given()
			.contentType("application/json") 
			.log().all() // Captura o log no envio da requisição
			.body(jsonBody) // Passa o json para a requisição
		// Quando enviado para o url https://petstore.swagger.io/v2/pet
		.when()
			.post(uri)
		// Entao captura o log e valida o statusCode	
		.then()
			.log().all()
		    .statusCode(200)// Verifica statusCode da resposta
	        .body("name", is("Megg"))
	        .body("status", is("Disponivel"))
	        .body("category.name", is("Cachorro"))
	        .body("tags.name", contains("gramado"))	
	        ;
	 }
	 
	 @Test(priority=2)
	    public void consultarPet() throws IOException{
		    String jsonBody = lerJson(System.getProperty("user.dir")+"\\db\\pet1.json");
	        String petId = "2809";

	        given()
	                .contentType("application/json")
	                .log().all()
	                .body(jsonBody)
	        .when()
	                .get(uri + "/" + petId)
	        .then()
	                .log().all()
	                .statusCode(200)
	    	        .body("name", is("sheik"))
	    	        .body("status", is("vendido"))
	    	        .body("category.name", is("Cachorro"))
	    	        .body("tags.name", contains("gramado"))	
	        ;
	    }

	    @Test(priority=3)
	    public void alterarPet() throws IOException {
	        String jsonBody = lerJson("db/pet2.json");

	        given()
	                .contentType("application/json")
	                .log().all()
	                .body(jsonBody)
	        .when()
	                .put(uri)
	        .then()
	                .log().all()
	                .statusCode(200)
	                .body("name", is("sheik"))
	                .body("status",is("vendido"))
	        ;
	    }

	    @Test (priority = 4)
	    public void excluirPet(){
	        String petId = "2809";

	        given()
	                .contentType("application/json")
	                .log().all()
	        .when()
	                .delete(uri + "/" + petId)
	        .then()
	                .log().all()
	                .statusCode(200)
	                .body("code", is(200))
	                .body("type", is ("unknown"))
	                .body("message", is(petId))

	        ;
	    }

	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 

	 
}
