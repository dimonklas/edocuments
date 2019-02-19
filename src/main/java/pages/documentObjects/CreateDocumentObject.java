package pages.documentObjects;

import lombok.AccessLevel;
import lombok.Getter;
import org.aeonbits.owner.ConfigFactory;
import pages.document.CreateDocumentData;
import utils.DateUtil;
import utils.IConfigurationVariables;

@Getter
public class CreateDocumentObject {

    @Getter(AccessLevel.NONE)
    private final IConfigurationVariables CV = ConfigFactory.create(IConfigurationVariables.class, System.getProperties());

    CreateDocumentData documentDataFirst = CreateDocumentData.builder()
            .docName(CV.docName() + new DateUtil().getCurrentDateTime("hhmmssSSS"))
            .service("Фискальная служба")
            .gateway("Шлюз службы статистики")
            .groupJournal(true)
            .finReport(true)
            .vddoc("123")
            .groupId("777")
            .build();

    CreateDocumentData documentDataSecond = CreateDocumentData.builder()
            .docName(CV.docName() + new DateUtil().getCurrentDateTime("hhmmssSSS"))
            .service("Фискальная служба")
            .gateway("Шлюз службы статистики")
            .groupJournal(true)
            .finReport(true)
            .vddoc("123")
            .groupId("777")
            .build();

    CreateDocumentData documentIncorrectDataFirst = CreateDocumentData.builder()
            .docName("")
            .service("Фискальная служба").gateway("Шлюз службы статистики")
            .groupJournal(true)
            .finReport(true)
            .vddoc("123")
            .groupId("777")
            .build();


    CreateDocumentData documentIncorrectDataSecond = CreateDocumentData.builder()
            .docName(CV.docName() + new DateUtil()
             .getCurrentDateTime("hhmmssSSS"))
            .service("")
            .gateway("Шлюз пенсионного фонда")
            .groupJournal(false)
            .finReport(true)
            .vddoc("123")
            .groupId("777")
            .build();


    CreateDocumentData documentIncorrectDataThird = CreateDocumentData.builder()
            .docName(CV.docName() + new DateUtil()
             .getCurrentDateTime("hhmmssSSS"))
            .service("Служба статистики")
            .gateway("").groupJournal(false)
            .finReport(true)
            .vddoc("123")
            .groupId("777")
            .build();


    CreateDocumentData documentIncorrectDataFour = CreateDocumentData.builder()
            .docName(CV.docName() + new DateUtil()
             .getCurrentDateTime("hhmmssSSS"))
            .service("Пенсионный фонд")
            .gateway("Шлюз пенсионного фонда")
            .groupJournal(false)
            .finReport(true)
            .vddoc("0")
            .groupId("777")
            .build();
}
