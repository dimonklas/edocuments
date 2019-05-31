package rest.services.models.createDocument;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class DocumentsItem{

	@JsonProperty("company_id")
	private String companyId;

	@JsonProperty("year")
	private int year;

	@JsonProperty("id")
	private int id;

	@JsonProperty("period_type")
	private String periodType;

	@JsonProperty("forms")
	private List<FormsItem> forms;

	@JsonProperty("quarter")
	private String quarter;
}