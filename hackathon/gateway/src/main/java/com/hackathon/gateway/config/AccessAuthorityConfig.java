package com.hackathon.gateway.config;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hackathon.gateway.utils.enumeration.ApplicationRole;
import com.hackathon.gateway.utils.exception.AuthenticationException;

@Component
public class AccessAuthorityConfig {

    private final ConcurrentMap<ApplicationRole, ConcurrentMap<RequestMethod, List<String>>> accessMapping = new ConcurrentHashMap<>();

    private String inventoryUrlMapping;

    private String posUrlMapping;

    private String userUrlMapping;

    public AccessAuthorityConfig() {
    }

    public AccessAuthorityConfig(String inventoryUrlMapping, String posUrlMapping, String userUrlMapping) {
        this.inventoryUrlMapping = inventoryUrlMapping;
        this.posUrlMapping = posUrlMapping;
        this.userUrlMapping = userUrlMapping;
        writeApiAccesses(getAdminApis(), getCustomerApis(), getSaleApis(), getStoreApis());
    }

    public ConcurrentMap<ApplicationRole, ConcurrentMap<RequestMethod, List<String>>> getAccessMapping() {
        return accessMapping;
    }

    private void writeApiAccesses(ConcurrentMap<RequestMethod, List<String>> adminApis, ConcurrentMap<RequestMethod, List<String>> customerApis,
                                  ConcurrentMap<RequestMethod,
                                          List<String>> salesApis, ConcurrentMap<RequestMethod, List<String>> storeApis) {
        accessMapping.put(ApplicationRole.ADMIN, adminApis);
        accessMapping.put(ApplicationRole.CUSTOMER, customerApis);
        accessMapping.put(ApplicationRole.SALE, salesApis);
        accessMapping.put(ApplicationRole.MANAGER, storeApis);
    }

    private ConcurrentMap<RequestMethod, List<String>> getAdminApis() {
        ConcurrentMap<RequestMethod, List<String>> adminApis = new ConcurrentHashMap<>();


        adminApis.put(RequestMethod.POST, fetchAdminPostApis());
        adminApis.put(RequestMethod.GET, fetchAdminGetApis());
        return adminApis;
    }

    private ConcurrentMap<RequestMethod, List<String>> getCustomerApis() {
        ConcurrentMap<RequestMethod, List<String>> customerApis = new ConcurrentHashMap<>();
        return customerApis;
    }

    private ConcurrentMap<RequestMethod, List<String>> getSaleApis() {
        ConcurrentMap<RequestMethod, List<String>> salesApis = new ConcurrentHashMap<>();

        salesApis.put(RequestMethod.GET, fetchSaleGetApis());
        salesApis.put(RequestMethod.POST, fetchSalePostApis());
        salesApis.put(RequestMethod.DELETE, fetchSaleDeleteApis());
        return salesApis;
    }

    private ConcurrentMap<RequestMethod, List<String>> getStoreApis() {
        ConcurrentMap<RequestMethod, List<String>> storeApis = new ConcurrentHashMap<>();

        storeApis.put(RequestMethod.GET, fetchStoreGetApis());
        storeApis.put(RequestMethod.POST, fetchStorePostApis());
        storeApis.put(RequestMethod.DELETE, fetchStoreDeleteApis());
        return storeApis;
    }

    private List<String> fetchAdminGetApis() {
        List<String> getApis = new ArrayList<>();
        getApis.add("/list");
        getApis.add("/availability/{username}");
        getApis.add("/role/view");
        return getApis;
    }

    private List<String> fetchAdminPostApis() {
        List<String> postApis = new ArrayList<>();
        postApis.add("/register");
        postApis.add("/role");
        return postApis;
    }

    private List<String> fetchSaleGetApis() {
        List<String> getApis = new ArrayList<>();
        getApis.add("/customer/{mobile}");
        return getApis;
    }

    private List<String> fetchStoreGetApis() {
        List<String> getApis = new ArrayList<>();
        getApis.add("/order/invoice/{key}");
        getApis.add("/customer/{mobile}");
        return getApis;
    }

    private List<String> fetchSalePostApis() {
        List<String> postApis = new ArrayList<>();
        postApis.add("/customer");
        postApis.add("/order/shipment");
        postApis.add("/order/place");
        postApis.add("/order/change");
        postApis.add("/order/payment");
        return postApis;
    }

    private List<String> fetchStorePostApis() {
        List<String> postApis = new ArrayList<>(fetchSalePostApis());
        postApis.add("/order/refund");
        postApis.add("/order/exchange");
        return postApis;
    }

    private List<String> fetchSaleDeleteApis() {
        List<String> deleteApis = new ArrayList<>();
        deleteApis.add("/order/{orderId}");
        return deleteApis;
    }

    private List<String> fetchStoreDeleteApis() {
        List<String> deleteApis = new ArrayList<>(fetchSaleDeleteApis());
        return deleteApis;
    }

    public boolean isAuthorisedAccess(ApplicationRole role, RequestMethod method, String url) {
        ConcurrentMap<RequestMethod, List<String>> authorisedApis = accessMapping.get(role);
        if (!authorisedApis.isEmpty()) {
            List<String> apis = authorisedApis.get(method);
            if (!apis.isEmpty()) {
                return validateUrls(getActualServiceUrl(url), apis);
            }
        }

        throw new AuthenticationException("Unauthorised access to fetch this resource by this user's credentials");
    }

    private boolean validateUrls(String url, List<String> urls) {
        List<Boolean> status = new ArrayList<>();

        String[] urlParts = url.split("/");

        for (String apiUrl : urls) {
            String[] apiUrlParts = apiUrl.split("/");

            int size = apiUrlParts.length - 1;
            if (size >= 1 && apiUrlParts[size].startsWith("{") && apiUrlParts[size].endsWith("}")) {

                String[] urlNewParts = new String[urlParts.length - 2];
                String[] apiUrlNewParts = new String[size - 1];

                if (urlParts.length - 2 >= 0)
                    System.arraycopy(urlParts, 0, urlNewParts, 0, urlParts.length - 2);
                System.arraycopy(apiUrlParts, 0, apiUrlNewParts, 0, size - 1);
                status.add(Arrays.equals(urlNewParts, apiUrlNewParts));
            } else
                status.add(Arrays.equals(urlParts, apiUrlParts));
        }
        return status.contains(true);
    }

    private String getActualServiceUrl(String url) {
        String actualUrl = "";
        if (url.contains(posUrlMapping))
            actualUrl = url.replace(posUrlMapping, "");
        if (url.contains(inventoryUrlMapping))
            actualUrl = url.replace(inventoryUrlMapping, "");
        if (url.contains(userUrlMapping))
            actualUrl = url.replace(userUrlMapping, "");
        return actualUrl;
    }
}
