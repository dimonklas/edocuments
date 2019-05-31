package rest.services.controllers.createDocument;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j;
import rest.services.models.createDocument.CreateDocument;
import rest.services.models.singDocument.SingDocument;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;
import static org.testng.Assert.assertEquals;
import static utils.SupportActions.getRequestSpec;

@Log4j
public class TestDocumentCtrl {

    public TestDocumentCtrl() {
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectResponseTime(lessThan(15 * 1000L))
                .expectStatusCode(200)
                .build();
    }

    @Step("Создание документа")
    public void createDocument(CreateDocument doc) {
        String result = given(getRequestSpec())
                .queryParam("duplicate", "remove")
                .basePath("/api/documents/create")
                .when()
                .formParam("data", doc)
//                .log().all()
                .post()
                .jsonPath().getString("result");

        assertEquals(result, "success");
    }

    @Step("Передача списка документов для подписания")
    public String signDocuments(SingDocument doc) {
        Response data = given(getRequestSpec())
                .basePath("/api/documents/sign")
                .when()
                .formParam("data", doc)
//                .log().all()
                .post()
                .then().extract().response();
        String result = data.jsonPath().getString("result");
        assertEquals(result, "success");
        return data.jsonPath().getString("temporary_token");
    }

    @Step("Страница подписания документов")
    public void signDocumentsPage(SingDocument doc) {
        String contentType = given(getRequestSpec())
                .basePath("/api/page/sign")
                .queryParam("temporary_token", signDocuments(doc))
                .when()
                .formParam("data", doc)
//                .log().all()
                .post()
                .then().extract().response()
                .getContentType();
        assertEquals(contentType, "text/html");
    }
}
