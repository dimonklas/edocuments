package pages.documentObjects;

import pages.document.FormFields;

import java.util.ArrayList;

public class FormFieldsObjectS1605110 {

    public ArrayList<FormFields> originalFields() {
        ArrayList<FormFields> originalFields = new ArrayList<>();

        originalFields.add(FormFields.builder()
                .codeField("A10_1").dataType("decimal").fieldType("Число (?)").tabNumber("").fieldsGroup("").paramAutoSet("").rule("0").hint("")
                .build());

        originalFields.add(FormFields.builder()
                .codeField("FIRM_ADR").dataType("string").fieldType("Текст").tabNumber("").fieldsGroup("").paramAutoSet("").rule("0").hint("")
                .build());

        originalFields.add(FormFields.builder()
                .codeField("OBL").dataType("STATOBL").fieldType("Список").tabNumber("").fieldsGroup("").paramAutoSet("").rule("0").hint("")
                .build());

        originalFields.add(FormFields.builder()
                .codeField("RAY").dataType("STATRAY").fieldType("Текст").tabNumber("").fieldsGroup("").paramAutoSet("").rule("0").hint("")
                .build());

        return originalFields;
    }
}
