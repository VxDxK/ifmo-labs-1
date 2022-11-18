package com.vdk.lab2demo.controller;

import io.prometheus.client.Collector;
import io.prometheus.client.CollectorRegistry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Iterator;

@WebServlet(name = "MetricsEndPoint", value = "/metrics")
public class Metrics extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        for (Iterator<Collector.MetricFamilySamples> it = CollectorRegistry.defaultRegistry.metricFamilySamples().asIterator(); it.hasNext(); ) {
            Collector.MetricFamilySamples sample = it.next();
            resp.getWriter().println(sample.toString());
        }
    }
}
