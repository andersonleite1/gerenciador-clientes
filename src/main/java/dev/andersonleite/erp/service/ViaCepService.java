package dev.andersonleite.erp.service;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViaCepService {

    private static final String VIACEP_URL = "https://viacep.com.br/ws/%s/json/";

    public String consultaCep(String cep) throws IOException {
        if (!isValidCep(cep)) {
            return null;
        }

        String url = String.format(VIACEP_URL, cep);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = client.execute(request)) {
                if (response.getStatusLine().getStatusCode() == 200) {
                    return EntityUtils.toString(response.getEntity());
                } else {
                    return null;
                }
            }
        }
    }

    private boolean isValidCep(String cep) {
        Pattern pattern = Pattern.compile("^\\d{8}$");
        Matcher matcher = pattern.matcher(cep);
        return matcher.matches();
    }
}


