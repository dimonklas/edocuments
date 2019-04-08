package pages.documentObjects;

import pages.document.FormFields;

import java.util.ArrayList;
import java.util.List;

public class FormFieldsObjectS1605110 {

    public List<FormFields> originalFields() {
        List<FormFields> formFields = new ArrayList<>();

        formFields.add(FormFields.builder()
                .codeField("A10_1").dataType("decimal").fieldType("Число (?)").tabNumber("").fieldsGroup("").paramAutoSet("").rule("0").hint("")
                .build());

        formFields.add(FormFields.builder()
                .codeField("FIRM_ADR").dataType("string").fieldType("Текст").tabNumber("").fieldsGroup("").paramAutoSet("").rule("0").hint("")
                .build());

        formFields.add(FormFields.builder()
                .codeField("OBL").dataType("STATOBL").fieldType("Список").tabNumber("").fieldsGroup("").paramAutoSet("").rule("0").hint("")
                .build());

        formFields.add(FormFields.builder()
                .codeField("RAY").dataType("STATRAY").fieldType("Текст").tabNumber("").fieldsGroup("").paramAutoSet("").rule("0").hint("")
                .build());

        return formFields;
    }

    public List<FormFields> editFields() {
        List<FormFields> formFields = new ArrayList<>();

        formFields.add(FormFields.builder()
                .codeField("A10_1").dataType("decimal").fieldType("Цифры").tabNumber("").fieldsGroup("").paramAutoSet("").rule("0").hint("")
                .build());

        formFields.add(FormFields.builder()
                .codeField("FIRM_ADR").dataType("string").fieldType("Чек-бокс").tabNumber("").fieldsGroup("").paramAutoSet("").rule("0").hint("")
                .build());

        formFields.add(FormFields.builder()
                .codeField("OBL").dataType("STATOBL").fieldType("Число (1)").tabNumber("").fieldsGroup("").paramAutoSet("").rule("0").hint("")
                .build());

        formFields.add(FormFields.builder()
                .codeField("RAY").dataType("STATRAY").fieldType("Время").tabNumber("").fieldsGroup("").paramAutoSet("").rule("0").hint("")
                .build());

        return formFields;
    }
}
