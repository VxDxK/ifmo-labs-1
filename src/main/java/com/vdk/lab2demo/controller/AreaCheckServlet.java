package com.vdk.lab2demo.controller;

import com.vdk.lab2demo.GraphValidator;
import com.vdk.lab2demo.Monadka;
import com.vdk.lab2demo.Pair;
import com.vdk.lab2demo.TimeMeter;
import com.vdk.lab2demo.bean.HitResult;
import com.vdk.lab2demo.bean.Hits;
import com.vdk.lab2demo.bean.HitsSingleton;
import com.vdk.lab2demo.bean.Shot;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import javax.ejb.EJB;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

@WebServlet(name = "AreaCheckServlet", value = "/area")
public class AreaCheckServlet extends HttpServlet {
    @EJB
    Hits hitsBean;
    @EJB
    GraphValidator validator;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Monadka<Shot> monadka = (Monadka<Shot>) request.getAttribute("shot");

        TimeMeter<Boolean> timeMeter = new TimeMeter<>(() -> validator.isHit(monadka));
        Pair<Long, Boolean> sd = timeMeter.start();
        HitResult result = monadka.map(x -> new HitResult(x.getX(), x.getY(), x.getR(), sd.second, sd.first)).get();


        Hits hits = (Hits) request.getSession().getAttribute("hits");
        if (hits == null) {
            hits = new Hits();
        }
        hits.getHits().add(result);

        request.getSession().setAttribute("hits", hits);
//        response.getWriter().println("Lolz kek");
        hitsBean.getHits().add(result);

        returnToPage(request, response);
    }

    private void returnToPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        StringBuilder ans = new StringBuilder();
        for (HitResult r: hitsBean.getHits()) {
            ans.append("<tr>");
                ans.append(String.format("<td>%s</td>", r.getX()));
                ans.append(String.format("<td>%s</td>", r.getY()));
                ans.append(String.format("<td>%s</td>", r.getR()));
                ans.append("<td>");
                    String hitString = "<div class=\"hit\">Hit</div>";
                    String loseString = "<div class=\"miss\">Miss</div>";
                    ans.append(r.isHit() ? hitString : loseString);
                ans.append("</td>");
            ans.append(String.format("<td>%s</td>", r.getTime().toString()));
            ans.append(String.format("<td>%s</td>", r.getProcessingTime()));
            ans.append("</tr>");
        }
        request.setAttribute("table", ans.toString());
        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }

}
