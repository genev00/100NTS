package com.example.a100nts.utils;

import static com.example.a100nts.utils.ActivityHolder.getActivity;
import static com.example.a100nts.ui.error.ErrorActivity.setErrorMessage;

import static java.util.logging.Level.SEVERE;

import android.content.Intent;
import android.os.StrictMode;

import com.example.a100nts.R;
import com.example.a100nts.entities.Site;
import com.example.a100nts.entities.User;
import com.example.a100nts.entities.UserUI;
import com.example.a100nts.entities.Vote;
import com.example.a100nts.ui.error.ErrorActivity;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;
import java.util.logging.Logger;

import lombok.SneakyThrows;

public final class RestService {

    private static final Logger logger;
    private static final RestTemplate REST_TEMPLATE;
    private static final String SERVER_URL;

    static {
        StrictMode.setThreadPolicy(
                new StrictMode.ThreadPolicy.Builder().permitAll().build()
        );

        logger = Logger.getLogger(RestService.class.getName());

        REST_TEMPLATE = new RestTemplate();
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(new MappingJackson2HttpMessageConverter());
        REST_TEMPLATE.setMessageConverters(converters);

        SERVER_URL = "http://IPv4:8080/api/v1";
    }

    private RestService() {
    }

    private static <T> T executeInExceptionContainer(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            logger.log(SEVERE, e.getMessage(), e);
            showError(e.getMessage());
        }
        return null;
    }

    public static Boolean checkIfEmailExists(String email) {
        return executeInExceptionContainer(() -> {
            ResponseEntity<? extends Boolean> response = REST_TEMPLATE.getForEntity(
                    SERVER_URL + "/users/emailExists" + '/' + email, Boolean.class
            );
            return response.getBody();
        });
    }

    public static Object loginUser(User user) {
        return executeInExceptionContainer(() -> {
            HttpEntity<User> entityUser = new HttpEntity<>(user);
            try {
                ResponseEntity<UserUI> response = REST_TEMPLATE.exchange(
                        SERVER_URL + "/users/login", HttpMethod.POST, entityUser, UserUI.class
                );
                return response.getBody();
            } catch (Exception e) {
                final String message = e.getMessage();
                if (message != null && message.contains(HttpStatus.FORBIDDEN.toString())) {
                    return getActivity().getString(R.string.wrong_password);
                }
                throw e;
            }
        });
    }

    public static UserUI registerUser(User user) {
        return executeInExceptionContainer(() -> {
            HttpEntity<User> entityUser = new HttpEntity<>(user);
            ResponseEntity<UserUI> response = REST_TEMPLATE.exchange(
                    SERVER_URL + "/users/register", HttpMethod.POST, entityUser, UserUI.class
            );
            return response.getBody();
        });
    }

    public static UserUI updateUser(User user) {
        return executeInExceptionContainer(() -> {
            HttpEntity<User> entityUser = new HttpEntity<>(user);
            ResponseEntity<UserUI> response = REST_TEMPLATE.exchange(
                    SERVER_URL + "/users/update", HttpMethod.POST, entityUser, UserUI.class
            );
            return response.getBody();
        });
    }

    public static Site[] getSites() {
        return executeInExceptionContainer(() -> {
            String restUrl = SERVER_URL + "/sites";
            if (Locale.getDefault().getLanguage().equals("bg")) {
                restUrl += "/bg";
            }
            ResponseEntity<? extends Site[]> response = REST_TEMPLATE.getForEntity(
                    restUrl, Site[].class
            );
            return response.getBody();
        });
    }

    public static UserUI[] getUsers() {
        return executeInExceptionContainer(() -> {
            ResponseEntity<? extends UserUI[]> response = REST_TEMPLATE.getForEntity(
                    SERVER_URL + "/users", UserUI[].class
            );
            return response.getBody();
        });
    }

    public static UserUI visitSites(Long userId, Long[] ids) {
        return executeInExceptionContainer(() -> {
            HttpEntity<Long[]> entityIds = new HttpEntity<>(ids);
            ResponseEntity<? extends UserUI> response = REST_TEMPLATE.exchange(
                    SERVER_URL + "/users/" + userId + "/visit",
                    HttpMethod.POST, entityIds, UserUI.class
            );
            return response.getBody();
        });
    }

    public static Site voteSite(Long userId, Long siteId, int voting) {
        return executeInExceptionContainer(() -> {
            HttpEntity<Vote> voteEntity = new HttpEntity<>(new Vote(
                    userId, siteId, voting, Locale.getDefault().getLanguage()
            ));
            ResponseEntity<Site> response = REST_TEMPLATE.exchange(
                    SERVER_URL + "/sites/vote", HttpMethod.POST, voteEntity, Site.class
            );
            return response.getBody();
        });
    }

    @SneakyThrows
    private static void showError(String message) {
        setErrorMessage(message);
        Intent errorIntent = new Intent(getActivity(), ErrorActivity.class);
        getActivity().startActivity(errorIntent);
        getActivity().finish();
    }

}
