package tests;

import org.testng.annotations.Test;
import rest.services.controllers.createDocument.TestDocumentCtrl;
import rest.services.models.createDocument.CreateDocument;
import rest.services.models.singDocument.SingDocument;


public class RestApiTest extends BaseTest {

    private TestDocumentCtrl documentCtrl = new TestDocumentCtrl();

    @Test(description = "создание нового документа через api")
    public void createNewDocument() {
        CreateDocument doc = new CreateDocument().getDocumentDataFromFile();
        documentCtrl.createDocument(doc);
    }

    @Test(description = "передача списка документов для подписания")
    public void singDocument() {
        SingDocument sing = new SingDocument().getDocumentDataFromFile();
        documentCtrl.signDocuments(sing);
    }

    @Test(description = "передача списка документов для подписания")
    public void singDocumentPage() {
        SingDocument sing = new SingDocument().getDocumentDataFromFile();
        documentCtrl.signDocumentsPage(sing);
    }
}