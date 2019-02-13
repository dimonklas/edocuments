package pages.document;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateDocumentData {
    String docName;
    String service;
    String gateway;
    boolean groupJournal;
    boolean finReport;
    String vddoc;
    String groupId;
}
