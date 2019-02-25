package pages.document.s1605110;

import lombok.Builder;
import lombok.Getter;
import pages.document.FormDataAbstract;
import pages.document.FormFields;

import java.util.ArrayList;

@Builder
@Getter
public class FormDataS1605110 extends FormDataAbstract {
    private final String CODE = "S1605110";
    private final String DESCRIPTION = "51-авто. Звіт про перевезення вантажів та пасажирів автомобільним транспортом";
    private ArrayList<FormFields> fields;
}
