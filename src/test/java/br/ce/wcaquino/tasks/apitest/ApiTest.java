package br.ce.wcaquino.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ApiTest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8086/tasks-backend";
	}

	@Test
	public void mustReturnTasks() {
		RestAssured.given()
			.when().get("/todo")
			.then().statusCode(200);
	}
	
	@Test
	public void mustAddTaskSuccessfully() {
		RestAssured.given()
			.body("{ \"task\": \"Test API\", \"dueDate\": \"2021-10-10\" }")
				.contentType(ContentType.JSON)
			.when().post("/todo")
			.then()
				.statusCode(201);
	}
	
	@Test
	public void mustNotAddInvalidTask() {
		RestAssured.given()
			.body("{ \"task\": \"Test API\", \"dueDate\": \"2010-10-10\" }")
				.contentType(ContentType.JSON)
			.when().post("/todo")
			.then()
				.statusCode(400)
				.body("message", CoreMatchers.is("Due date must not be in past"));
	}
}