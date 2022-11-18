package com.vdk.lab3aaaaaaaa.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vdk.lab3aaaaaaaa.Hit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

@WebServlet(name = "DownloadServlet", value = "/download")
public class DownloadServlet extends HttpServlet {
    EntityManagerFactory factory;

    public DownloadServlet() {
        factory = Persistence.createEntityManagerFactory("base");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManager manager = factory.createEntityManager();
        List<Hit> select_a_from_s338999_hits_a = manager.createQuery("SELECT a from s338999_hits a", Hit.class).getResultList();
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        mapper.writeValue(resp.getWriter(), select_a_from_s338999_hits_a);
        manager.close();
    }
}
