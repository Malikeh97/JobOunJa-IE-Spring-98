package Models;

import java.nio.charset.Charset;

public class HTMLResponse {

    private String title;
    private String style;
    private String body;

    public HTMLResponse(String title, String style, String body) {
        this.title = title;
        this.style = style;
        this.body = body;
    }

    public HTMLResponse(String title, String body) {
        this.title = title;
        this.style = "";
        this.body = body;
    }
    
    public byte[] getResponse(Charset charset) {
        String response = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>" + this.title + "</title>\n" +
                "    <style>" + this.style + "</style>\n" +
                "</head>\n" +
                "<body>\n" + this.body + "</body>\n" +
                "</html>";
        return response.getBytes(charset);
    }
}
