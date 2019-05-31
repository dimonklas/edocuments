package rest.services.models.createDocument;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class FormsItem{

	@JsonProperty("code")
	private String code;

	@JsonProperty("fields")
	private Fields fields;
}