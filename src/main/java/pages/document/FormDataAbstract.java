package pages.document;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public abstract class FormDataAbstract {

    private String CODE;
    private String DESCRIPTION;
    private ArrayList<FormFields> fields;

}
