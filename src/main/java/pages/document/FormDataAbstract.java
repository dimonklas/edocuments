package pages.document;

import lombok.Getter;

import java.util.List;

@Getter
public abstract class FormDataAbstract {

    private String CODE;
    private String DESCRIPTION;
    private List<FormFields> fields;

}
