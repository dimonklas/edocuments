package utils.sid;

import io.restassured.http.ContentType;
import lombok.extern.log4j.Log4j;
import org.aeonbits.owner.ConfigFactory;
import utils.IConfigurationVariables;

import static io.restassured.RestAssured.given;


@Log4j
public class ProminSession {

    private final IConfigurationVariables CV = ConfigFactory.create(IConfigurationVariables.class, System.getProperties());
    private final String URL_PROMIN = CV.urlPromin();

    private Session session = new Session();
    private User user = User.builder()
            .login(CV.techLogin())
            .password(CV.techPassword())
            .auth("EXCL")
            .build();


    public String getAdminSession() {
        session.setUser(user);
        return given()
                .header("Content-type", "application/json;charset=UTF-8")
                .header("Accept", "application/json;charset=UTF-8")
                .baseUri(URL_PROMIN)
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
//                .log().all()

                .when()
                .body(session)
                .post().as(Id.class).getValue();
    }
}
