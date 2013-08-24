package controllers;

import play.*;
import play.libs.F;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
  
    public static Result index() {
        session("username", "fake-user");
        return ok(index.render("Your new application is ready."));
    }

    public static WebSocket<String> chat(){
        final Http.Session session = session();
        final String name = session("username");//better version

        return new WebSocket<String>() {
            public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out) {
                System.out.println("ready");
                in.onMessage(new F.Callback<String>() {
                    public void invoke(String event) {
                        System.out.println(event);
                    }
                });

                in.onClose(new F.Callback0() {
                    public void invoke() {
                        System.out.println("Disconnected");
                    }
                });

                out.write("Hello " + session.get("username"));
                out.write("Hello " + name.toUpperCase());
            }
        };
    }
}
