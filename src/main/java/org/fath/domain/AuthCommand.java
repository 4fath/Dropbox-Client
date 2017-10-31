package org.fath.domain;

import com.dropbox.core.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AuthCommand implements Command {

    private final String[] args;

    public AuthCommand(String[] args) {
        this.args = args;
        validateParameters();
    }

    public void validateParameters() {
        if (args.length != 3) {
            System.err.println("''''''''''''''''''''''''''''''''''''''''''''''''''''");
            System.err.println("Invalid usage of 'auth' command. ");
            System.err.println("Usage of 'auth' command : auth {appKey} {appSecret}");
            System.err.println("''''''''''''''''''''''''''''''''''''''''''''''''''''");
            System.exit(1);
        }
    }

    public void execute() throws IOException {

        String key = args[1];
        String secret = args[2];
        DbxAppInfo appInfo = new DbxAppInfo(key, secret);

        DbxRequestConfig requestConfig = new DbxRequestConfig("Aw-Client");
        DbxWebAuth webAuth = new DbxWebAuth(requestConfig, appInfo);
        DbxWebAuth.Request webAuthRequest = DbxWebAuth.newRequestBuilder()
                .withNoRedirect()
                .build();

        String authorizeUrl = webAuth.authorize(webAuthRequest);
        System.out.println("1. Go to : " + authorizeUrl);
        System.out.println("2. Click \"Allow\" (you might have to log in first).");
        System.out.println("3. Copy the authorization code and paste it here :");

        String code = new BufferedReader(new InputStreamReader(System.in)).readLine();
        if (code == null) {
            System.err.println("You have to paste the authorization code.");
            System.exit(1);
            return;
        }

        code = code.trim();
        DbxAuthFinish authFinish;
        try {
            authFinish = webAuth.finishFromCode(code);
        } catch (DbxException ex) {
            handleException(ex);
            System.exit(1);
            return;
        }
        // System.out.println("- User ID: " + authFinish.getUserId());
        System.out.println("Your access token: " + authFinish.getAccessToken());

    }

    private void handleException(DbxException ex) {
        if (ex instanceof DbxApiException) {
            System.err.println("Error in Dropbox Api Connection, please check your internet connection.");
        } else {
            System.err.println("Error in authorization process invalid or expired code.");
        }
        // TODO : write logfile
    }

}
