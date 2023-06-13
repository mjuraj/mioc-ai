package com.mindsmiths.dashboard;


import com.fasterxml.jackson.databind.JsonNode;
import com.mindsmiths.dashboard.api.UpdateOrCreateClient;
import com.mindsmiths.dashboard.api.UpdateOrCreateManager;
import com.mindsmiths.dashboard.models.Client;
import com.mindsmiths.dashboard.models.Manager;


public class DashboardAPI {
    private static final String serviceId = "dashboard";

    public static void updateOrCreateClient(Client client) {
        new UpdateOrCreateClient(client).send(serviceId);
    }

    public static void updateOrCreateManager(Manager manager) {
        new UpdateOrCreateManager(manager).send(serviceId);
    }
}