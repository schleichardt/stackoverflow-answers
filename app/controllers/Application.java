package controllers;

import play.libs.F.Function;
import play.libs.F.Promise;
import play.mvc.*;
import play.libs.WS;

public class Application extends Controller {
    /**
     * This action serves as proxy for the Google start page
     */
    public static Result index() {
        //Phase 1 get promise of the webservice request
        final Promise<WS.Response> responsePromise = WS.url("http://google.de").get();
        //phase 2 extract the usable data from the response
        final Promise<String> bodyPromise = responsePromise.map(new Function<WS.Response, String>() {
            @Override
            public String apply(WS.Response response) throws Throwable {
                final int statusCode = response.getStatus();
                return response.getBody();//assumed you checked the response code for 200
            }
        });
        //phase 3 transform the promise into a result/HTTP answer
        return async(
                bodyPromise.map(
                        new Function<String,Result>() {
                            public Result apply(String s) {
                                return ok(s).as("text/html");
                            }
                        }
                )
        );
    }
}