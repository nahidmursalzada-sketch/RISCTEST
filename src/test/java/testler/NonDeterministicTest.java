package testler;


import api.LoginAPi;
import api.VeoAiApi;
import io.restassured.response.Response;
import konfiqurasiya.Ayarlar;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class NonDeterministicTest {
    static String token;

    @BeforeAll
    static void login() {
        String email=System.getenv("VEO_EMAIL");
        String password=System.getenv("VEO_PASSWORD");
        token=LoginAPi.loginOl(email,password);
    }

    @Test
    public void eyniPrompt_20Defe_DavranisTesti() {

        String prompt = "sade bir seyler de meselen salam emi oglu necesen ";

        HashMap<String, Integer> netice = new HashMap<>();
        for (int i=1;i<=20;i++){
            Response response=VeoAiApi.promptGonder(token,prompt);
            String davranis=davranisiTanimla(response);
            netice.merge(davranis,1,Integer::sum);
            System.out.println("Run");
        }
        System.out.println("Yekun netice");
        netice.forEach((k,v)-> System.out.println(k+"->"+v));
    }

    private String davranisiTanimla(Response r) {

        if (r.statusCode() == 200) {

            return "STABIL_OK";

        }
        else if (r.statusCode() == 401 || r.statusCode() == 403) {
            return "AUTH_PROBLEM";
        }
        else if (r.statusCode() == 429) {
            return "RATE_LIMIT";
        }
       else if (r.statusCode() >= 500) {
            return "SERVER_RISK";
        }
       else{
            return "NAMELUM_DAVRANIS";
        }

    }


}
