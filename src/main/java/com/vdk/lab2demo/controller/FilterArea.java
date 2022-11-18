package com.vdk.lab2demo.controller;

import com.vdk.lab2demo.ExceptionToHtml;
import com.vdk.lab2demo.Monadka;
import com.vdk.lab2demo.bean.Shot;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

import javax.ejb.embeddable.EJBContainer;
import java.io.IOException;
import java.util.*;

@WebFilter(urlPatterns = { "/area"}, dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST})
public class FilterArea implements Filter {
    private final static String[] whatYouShouldDo = {"kill yourself", "eat shit", "specify parameter with instruction",
            "alt+f4", "sudo rm -rf /*", "....... i dont know, what you should do", "reborn", "change difficult",
            "attend a Nirvana`s concert", "contact with me using email: noreply@gmail.com", "check mOm",
            "java.lang.NullPointerException: Cannot invoke String.length() because str is null",
            "java.lang.ArrayIndexOutOfBoundsException: Index 5 out of bounds for length 3",
            "get some bitches", "touch women (not you'r mom, oops i forgot that she's dead)"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        StringBuilder stringBuilder = new StringBuilder();
        ExceptionToHtml exceptionToHtml = new ExceptionToHtml();

        List<String> strings = Arrays.asList(whatYouShouldDo);
        Collections.shuffle(strings);

        Shot shot = new Shot(0, 0, 0);

        int re = 0;
        Monadka<Integer> xMon = Monadka.of(request.getParameter("x"))
                .checkFinal(Objects::nonNull, "X is missed, try to specify it in request")
                .map(Integer::parseInt, "X is not a int. Try: " + strings.get(re))
                .check(z -> z >= -5, "X should be greater then -5. Try: " + strings.get(re))
                .check(z -> z <= 3, "X should be lower then 3. Try: " + strings.get(re))
                .onFailure(x -> stringBuilder.append(exceptionToHtml.produce(x)))
                .onSucces(shot::setX);
        re = 1;
        Monadka<Double> yMon = Monadka.of(request.getParameter("y"))
                .checkFinal(Objects::nonNull, "Y is missed, try to specify it in request")
                .map(Double::parseDouble, "Y is not a double. Try: ")
                .check(z -> z >= -3, "Y should be greater then -3. Try: " + strings.get(re))
                .check(z -> z <= 5, "Y should be lower then 5. Try: " + strings.get(re))
                .onFailure(x -> stringBuilder.append(exceptionToHtml.produce(x)))
                .onSucces(shot::setY);;
        re = 2;
        Monadka<Integer> rMon = Monadka.of(request.getParameter("r"))
                .checkFinal(Objects::nonNull, "R is missed, try to specify it in request")
                .map(Integer::parseInt, "R is not a int. Try:" + strings.get(re))
                .check(z -> z >= 1, "R should be greater then 0. Try: " + strings.get(re))
                .check(z -> z <= 5, "R should be lower then 6. Try: " + strings.get(re))
                .onFailure(x -> stringBuilder.append(exceptionToHtml.produce(x)))
                .onSucces(shot::setR);
        response.getWriter().println(request.getParameter("x"));
        response.getWriter().println(request.getParameter("y"));
        response.getWriter().println(request.getParameter("r"));

        response.getWriter().println(shot.toString());


        if(!stringBuilder.toString().equals("")){
            String ans = stringBuilder.toString();
            request.setAttribute("error", stringBuilder.substring(0, ans.length() - 4));
            request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
        }else{
            request.setAttribute("shot", Monadka.of(shot));
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
