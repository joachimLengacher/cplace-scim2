package cf.cplace.scim2.adapter.scim2;

import cf.cplace.platform.test.util.StartServerRule;
import com.unboundid.scim2.common.types.Email;
import com.unboundid.scim2.common.types.UserResource;
import io.restassured.response.Response;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.*;

public class IntegrationTest {

    public static final String USER_NAME = "bart.simpson@cplace.io";
    public static final String DISPLAY_NAME = "Bart Simpson";
    public static final String XSRF_TOKEN = "baae13ec-8516-4d22-aff2-b7b809065a6b";


    @Rule
    public TestRule startServer = new StartServerRule(this);

    @Test
    public void testUserLifecycle() {
        String id = createUser();
        findUser(id);
        updateUser(id);
        deleteUser(id);
    }

    private String createUser() {
        UserResource user = new UserResource()
                .setUserName(USER_NAME)
                .setDisplayName(DISPLAY_NAME)
                .setEmails(List.of(new Email().setPrimary(true).setValue(USER_NAME).setType("work")))
                .setActive(true);

        Response response = given()
                .auth()
                .preemptive()
                .basic("mustermann@test.tricia", "ottto")
                .header("Content-type", "application/json")
                .header("Accept", "application/scim+json; charset=utf-8")
                .header("X-XSRF-TOKEN", XSRF_TOKEN)
                .cookie("XSRF-TOKEN", XSRF_TOKEN)
                .and()
                .body(user)
                .when()
                .post("http://localhost:8083/intern/tricia/cplace-api/cf.cplace.scim2/Users")
                .then()
                .extract().response();

        assertThat(response.statusCode(), is(CREATED.value()));
        assertUserValues(response, USER_NAME, DISPLAY_NAME, USER_NAME, true);
        assertThat(response.jsonPath().getString("id"), is(not(nullValue())));

        return response.jsonPath().getString("id");
    }

    private UserResource findUser(String id) {
        Response response = given()
                .auth()
                .preemptive()
                .basic("mustermann@test.tricia", "ottto")
                .header("Content-type", "application/json")
                .header("Accept", "application/scim+json; charset=utf-8")
                .when()
                .get("http://localhost:8083/intern/tricia/cplace-api/cf.cplace.scim2/Users/" + id)
                .then()
                .extract().response();

        assertThat(response.statusCode(), is(OK.value()));
        assertUserValues(response, USER_NAME, DISPLAY_NAME, USER_NAME, true);
        assertThat(response.jsonPath().getString("id"), is(id));
        return response.as(UserResource.class);
    }


    private void updateUser(String id) {
        UserResource user = findUser(id);
        user.setDisplayName("Lisa Simpson").setUserName("lisa.simpson@cplace.io");

        Response response = given()
                .auth()
                .preemptive()
                .basic("mustermann@test.tricia", "ottto")
                .header("Content-type", "application/json")
                .header("Accept", "application/scim+json; charset=utf-8")
                .header("X-XSRF-TOKEN", XSRF_TOKEN)
                .cookie("XSRF-TOKEN", XSRF_TOKEN)
                .and()
                .body(user)
                .when()
                .put("http://localhost:8083/intern/tricia/cplace-api/cf.cplace.scim2/Users/" + id)
                .then()
                .extract().response();

        assertThat(response.statusCode(), is(OK.value()));
        assertUserValues(response, "lisa.simpson@cplace.io", "Lisa Simpson", "lisa.simpson@cplace.io", true);
        assertThat(response.jsonPath().getString("id"), is(id));
    }


    private void deleteUser(String id) {
        Response response = given()
                .auth()
                .preemptive()
                .basic("mustermann@test.tricia", "ottto")
                .header("Content-type", "application/json")
                .header("Accept", "application/scim+json; charset=utf-8")
                .header("X-XSRF-TOKEN", XSRF_TOKEN)
                .cookie("XSRF-TOKEN", XSRF_TOKEN)
                .when()
                .delete("http://localhost:8083/intern/tricia/cplace-api/cf.cplace.scim2/Users/" + id)
                .then()
                .extract().response();

        assertThat(response.statusCode(), is(NOT_IMPLEMENTED.value()));
    }

    private void assertUserValues(Response response, String userName, String displayName, String email, boolean isActive) {
        assertThat(response.jsonPath().getList("schemas"), hasSize(1));
        assertThat(response.jsonPath().getList("schemas"), contains("urn:ietf:params:scim:schemas:core:2.0:User"));
        assertThat(response.jsonPath().getString("userName"), is(userName));
        assertThat(response.jsonPath().getString("displayName"), is(displayName));
        assertThat(response.jsonPath().getBoolean("active"), is(isActive));
        assertThat(response.jsonPath().getList("emails", Email.class).get(0).getPrimary(), is(true));
        assertThat(response.jsonPath().getList("emails", Email.class).get(0).getValue(), is(email));
    }
}
