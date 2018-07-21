package org.example;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/")
@SuppressWarnings("serial")
public class TestServlet extends HttpServlet {

    @Inject
    private QueueEJB queueEJB;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletOutputStream out = resp.getOutputStream();
        out.println("<html><body><form method=\"post\">"
                + "<input type=\"submit\" value=\"Execute\">"
                + "</form></body></html>");
        for (Test test : queueEJB.list()) {
            out.println("ID: " + test.getId() + "<br>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        queueEJB.execute();
        resp.sendRedirect("");
    }
}
