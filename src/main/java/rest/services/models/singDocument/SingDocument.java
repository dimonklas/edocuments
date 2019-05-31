package rest.services.models.singDocument;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j;

import javax.annotation.Generated;

@Log4j
@Generated("com.robohorse.robopojogenerator")
public class SingDocument{

	@JsonProperty("user_id")
	private String userId;

	@JsonProperty("documents")
	private List<DocumentsItem> documents;

	@JsonIgnore
	public SingDocument getDocumentDataFromFile() {
		ObjectMapper mapper = new ObjectMapper();
		{
			try {
				SingDocument doc = mapper.readValue(new File("src/main/resources/supportFiles/jsonData/singDocument.json"), SingDocument.class);
				return doc;
			} catch (IOException e) {
				log.info(e + " а файла то нету, Карл");
				return null;
			}
		}
	}
}