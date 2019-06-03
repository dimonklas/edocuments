package rest.services.controllers.createDocument;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.log4j.Log4j;
import org.aeonbits.owner.ConfigFactory;
import rest.services.models.createDocument.CreateDocument;
import rest.services.models.singDocument.Document;
import utils.IConfigurationVariables;
import utils.sid.ProminSession;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

@Log4j
public class TestDocumentCtrl {

    private RequestSpecification spec;

    public TestDocumentCtrl() {
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectResponseTime(lessThan(15 * 1000L))
                .expectStatusCode(200)
                .build();

        this.spec = new RequestSpecBuilder()
                .setBaseUri(ConfigFactory.create(IConfigurationVariables.class, System.getProperties()).urlApiTest())
                .addQueryParam("token", new ProminSession().getAdminSession())
                .setAccept(ContentType.JSON)
                .build();
    }

    @Step("Создание документа")
    public Response createDocumentRemove(CreateDocument doc) {
        return given(spec)
                .queryParam("duplicate", "remove")
                .basePath("/api/documents/create")
                .when()
                .formParam("data", doc)
//                .log().all()
                .post().then().extract().response();
    }

    @Step("Создание документа")
    public Response createDocumentError(CreateDocument doc) {
        return given(spec)
                .queryParam("duplicate", "error")
                .basePath("/api/documents/create")
                .when()
                .formParam("data", doc)
                .post().then().extract().response();
    }

    @Step("Передача списка документов для подписания")
    public Response signDocuments(Document doc) {
        return given(spec)
                .basePath("/api/documents/sign")
                .when()
                .formParam("data", doc)
                .post()
                .then().extract().response();
    }

    @Step("Страница подписания документов")
    public Response signDocumentsPage(Document doc, String tmpToken) {
        return given(spec)
                .basePath("/api/page/sign")
                .queryParam("temporary_token", tmpToken)
                .when()
                .formParam("data", doc)
                .post()
                .then().extract().response();
    }

    @Step("Получение печатных форм документов")
    public Response getPrintedForms(Document doc) {
        return given(spec)
                .basePath("/api/printed_forms/get")
                .when().formParam("data", doc)
                .post()
                .then().extract().response();
    }

    @Step("Получение состояний документов")
    public Response getDocumentState(Document doc) {
        return given(spec)
                .basePath("/api/documents/get_state")
                .when().formParam("data", doc)
                .post()
                .then().extract().response();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    @Step("Передача списка документов для расшифровки квитанций")
//    public Response decryptDocument(Document doc) {
//        return given(spec)
//                .basePath("/api/receipts/decrypt")
//                .when()
//                .formParam("data", doc)
//                .post()
//                .then().extract().response();
//    }
}
