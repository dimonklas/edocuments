package tests;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import rest.services.controllers.createDocument.TestDocumentCtrl;
import rest.services.models.createDocument.CreateDocument;
import rest.services.models.singDocument.Document;

import static org.testng.Assert.assertEquals;


public class RestApiTest extends BaseTest {

    private TestDocumentCtrl documentCtrl = new TestDocumentCtrl();

    @Test(description = "создание нового документа через api")
    public void createNewDocument() {
        CreateDocument doc = new CreateDocument().getDocumentDataFromFile();
        Response res = documentCtrl.createDocumentRemove(doc);
        assertEquals(res.jsonPath().getString("result"), "success");
    }

    @Test(description = "создание нового документа через api, с уже существующим id")
    public void createNewDocumentError() {
        CreateDocument doc = new CreateDocument().getDocumentDataFromFile();
        documentCtrl.createDocumentRemove(doc);
        Response res = documentCtrl.createDocumentError(doc);
        assertEquals(res.jsonPath().getString("result"), "error");
    }

    @Test(description = "передача списка документов для подписания")
    public void singDocument() {
        Document doc = new Document().getDocumentDataFromFile();
        Response res = documentCtrl.signDocuments(doc);
        assertEquals(res.jsonPath().getString("result"), "success");
    }

    @Test(description = "передача списка документов для подписания")
    public void singDocumentPage() {
        Document doc = new Document().getDocumentDataFromFile();
        Response resDocument = documentCtrl.signDocuments(doc);
        String tmpToken = resDocument.jsonPath().getString("temporary_token");
        Response res = documentCtrl.signDocumentsPage(doc, tmpToken);
        String resultTitle = res.xmlPath().getString("html.head.title");

        assertEquals(res.getContentType(), "text/html");
        assertEquals(resultTitle, "Сервис подписания");
    }

    @Test(description = "передача списка документов для подписания с недействительным токеном")
    public void singDocumentPageWithExpiredToken() {
        Document doc = new Document().getDocumentDataFromFile();
        Response resDocument = documentCtrl.signDocuments(doc);
        String tmpToken = resDocument.jsonPath().getString("temporary_token");
        documentCtrl.signDocumentsPage(doc, tmpToken);
        Response res = documentCtrl.signDocumentsPage(doc, tmpToken);
        assertEquals(res.jsonPath().getString("result"), "error");
    }

    @Test(description = "Получение печатных форм документов")
    public void getPrintedForm() {
        Document doc = new Document().getDocumentDataFromFile();
        Response res = documentCtrl.getPrintedForms(doc);
        assertEquals(res.jsonPath().getString("result"), "success");
    }

    @Test(description = "Получение состояний документов")
    public void getDocumentState() {
        Document doc = new Document().getDocumentDataFromFile();
        Response res = documentCtrl.getDocumentState(doc);
        assertEquals(res.jsonPath().getString("result"), "success");
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    @Test(description = "Передача списка документов для расшифровки квитанций")
//    public void decryptDocument() {
//        Document sing = new Document().getDocumentDataFromFile();
//        Response res = documentCtrl.decryptDocument(sing);
//        assertEquals(res.jsonPath().getString("result"), "success");
//    }
}