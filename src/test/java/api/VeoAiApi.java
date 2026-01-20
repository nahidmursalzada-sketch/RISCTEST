package api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import konfiqurasiya.Ayarlar;

import static io.restassured.RestAssured.given;

public class VeoAiApi {
  public static Response promptGonder(String token,String prompt){

      return
              given()
                      .header("Authorization","Bearer"+token)
                      .contentType(ContentType.JSON)
                      .body(
                              """
                                      {
                                      "prompt":"%s"
                                      }""".formatted(prompt)
                      )
                      .when()
                      .post(Ayarlar.baseUrl+Ayarlar.endpoint);





  }
}
