package pages.document.dropDownListData;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;

@Getter
@Builder
public class DropDownListData {
    private String name;
    private ArrayList<ElementListData> elementListData;
}
