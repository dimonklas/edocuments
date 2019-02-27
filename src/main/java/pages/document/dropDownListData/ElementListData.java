package pages.document.dropDownListData;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;


@Builder
@Getter
public class ElementListData implements Comparable<ElementListData> {
    private String key;
    private String value;

    @Override
    public int compareTo(@NonNull ElementListData e) {
        return key.compareTo(e.getKey());
    }
}
