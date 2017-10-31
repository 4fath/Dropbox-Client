package org.fath.domain;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.users.FullAccount;

public class InfoCommand extends BaseAuth implements Command {

    private final String[] args;

    public InfoCommand(String[] args) {
        this.args = args;
        validateParameters();
    }

    public void validateParameters() {
        if (args.length < 2 || args.length > 3){
            System.out.println("'''''''''''''''''''''''''''''''''''''''''''''''''''''");
            System.err.println("Invalid usage of 'info' command. ");
            System.err.println("Usage of 'auth' command : info {accessToken} {locale}");
            System.out.println("'''''''''''''''''''''''''''''''''''''''''''''''''''''");
            System.exit(1);
        }
    }

    public void execute() {
        DbxClientV2 client = getDBClient(args[1], args.length == 3 ? args[2] : null);
        FullAccount account;
        try {
            account = client.users().getCurrentAccount();
        } catch (DbxException e) {
            handleException(e);
            System.exit(1);
            return;
        }

        if (account != null && account.getName() != null) {
            printAccountInfo(account);
        }

    }

    private void handleException(DbxException e) {
        if (e instanceof DbxApiException){
            System.err.println("Error in Dropbox Api Connection, please check your internet connection. ");
        }else {
            System.err.println("Error in getting account info: " + e.getMessage());
        }
    }

    private void printAccountInfo(FullAccount account) {

        String accountId = account.getAccountId();
        String displayName = account.getName().getDisplayName();

        String givenName = account.getName().getGivenName();
        String surname = account.getName().getSurname();
        String familiarName = account.getName().getFamiliarName();

        String email = account.getEmail();

        String isEmailVerified = account.getEmailVerified() ? "verified" : "not verified";
        String country = account.getCountry();
        String referralLink = account.getReferralLink();

        System.out.println("'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''");
        System.out.println("Account ID:          " + accountId);
        System.out.println("Display name:        " + displayName);
        System.out.println("Name:                " + givenName + " " + surname + " (" + familiarName + ")" );
        System.out.println("E-mail:              " + email + " (" + isEmailVerified + ") ");
        System.out.println("Country:             " + country);
        System.out.println("Referral link:       " + referralLink);
        System.out.println("'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''");
    }
}
