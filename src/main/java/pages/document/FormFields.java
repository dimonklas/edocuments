package pages.document;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FormFields {
    private String codeField;
    private String dataType;
    private String fieldType;
    private String tabNumber;
    private String fieldsGroup;
    private String paramAutoSet;
    private String rule;
    private String hint;

}
