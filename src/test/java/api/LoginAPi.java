package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import konfiqurasiya.Ayarlar;


import static io.restassured.RestAssured.given;

public class LoginAPi {

  static {
      RestAssured.baseURI=Ayarlar.baseUrl;
  }

  public static String loginOl(String email,String password){
      Response response=
              given()
                      .contentType(ContentType.JSON)
                      .body("""
                              {
                              "email":"%s",
                              "password":"%s"
                              }
                              """.formatted(email,password))
                      .when()
                      .post(Ayarlar.baseUrl+"/api/auth/login");
      if (response.statusCode()!=200){
          throw new RuntimeException("login alinmadi status:"+response.statusCode()+" Body:"+response.asString());
      }
      return response.jsonPath().getString("token");
  }







}
