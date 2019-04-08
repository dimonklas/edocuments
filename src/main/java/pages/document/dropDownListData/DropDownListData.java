package pages.document.dropDownListData;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DropDownListData {
    private String name;
    private List<ElementListData> elementListData;
}
