package org.fath.domain;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.*;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ListCommand extends BaseAuth implements Command {

    private final String[] args;

    public ListCommand(String[] args) {
        this.args = args;
        validateParameters();
    }

    public void validateParameters() {
        if (this.args.length < 3 || this.args.length > 4) {
            System.err.println("'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''");
            System.err.println("Invalid usage of 'list' command.");
            System.err.println("Usage of 'auth' command : list {accessToken} {path} {locale} ");
            System.err.println("'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''");
            System.exit(1);
        } else if (!args[2].startsWith("/")) {
            System.err.println("'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''");
            System.err.println("Invalid usage of 'path' parameter.");
            System.err.println("'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''");
            System.exit(1);
        }
    }


    public void execute() {
        DbxClientV2 client = getDBClient(args[1], args.length == 4 ? args[3] : null);
        String path = args[2];

        int lastFolder = path.lastIndexOf("/");
        String basePath = path.substring(0, lastFolder);
        String searchSrt = path.substring(lastFolder + 1, path.length());

        try {
            // Firstly, determine given path is a folder or file
            SearchResult res = client.files().searchBuilder(basePath, searchSrt).withMode(SearchMode.FILENAME).start();
            for (SearchMatch match : res.getMatches()) {
                if (match.getMetadata() instanceof FileMetadata && match.getMetadata().getName().equals(searchSrt)) {
                    printFile((FileMetadata) match.getMetadata());
                    System.exit(1);
                    return;
                }
            }
            findAndListFiles(client, path);
        } catch (DbxException e) {
            handleException(e);
            System.exit(1);
        }

    }

    private void findAndListFiles(DbxClientV2 client, String path) throws DbxException {
        ListFolderResult result = client.files().listFolder(path);
        for (Metadata data : result.getEntries()) {
            printInfo(data);
        }
    }

    private void printInfo(Metadata metadata) {
        if (metadata instanceof FolderMetadata) {
            printDir((FolderMetadata) metadata);
        } else if (metadata instanceof FileMetadata) {
            printFile((FileMetadata) metadata);
        }
    }

    private void handleException(DbxException e) {
        System.err.println("''''''''''''''''''''''''''''''''''''''''''''''''''''''");
        if (e instanceof SearchErrorException) {
            System.err.println("No such a file on given path.");
        } else if (e instanceof ListFolderErrorException) {
            System.err.println("Error on listing files on given path.");
        } else {
            System.err.println("Error on file listing process with given path.");
        }
        System.err.println("'''''''''''''''''''''''''''''''''''''''''''''''''''''''");
    }

    private void printDir(FolderMetadata folder) {
        String displayPath = folder.getPathDisplay();
        System.out.println("-" + displayPath + "                 : dir, modified at:");
    }


    private void printFile(FileMetadata file) {

        String fileName = file.getName();
        String filePathDisplay = file.getPathDisplay();
        String size = getFormattedFileSize(file.getSize());
        String mimeType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        Date modifiedAt = file.getServerModified();
        SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String strDate = df.format(modifiedAt);

        StringBuilder strBuilder = new StringBuilder("-");
        strBuilder.append(filePathDisplay)
                .append("              : file, ")
                .append(size)
                .append(", ")
                .append(mimeType)
                .append(", modified at: '")
                .append(strDate)
                .append("'");
        System.out.println(strBuilder.toString());

    }

    private String getFormattedFileSize(long fileSize) {
        StringBuilder stringBuilder = new StringBuilder();
        if (fileSize > 1024 * 1024) { // MB
            float mb = (float) fileSize / (1024 * 1024);
            String formattedValue = String.format("%.2f", mb);
            stringBuilder.append(formattedValue).append(" MB");
        } else if (fileSize > 1024) { //  KiloByte
            float kb = (float) fileSize / (1024);
            String formattedValue = String.format("%.2f", kb);
            stringBuilder.append(formattedValue).append(" KB");
        } else { // Byte
            stringBuilder.append(fileSize).append(" bytes");
        }
        return stringBuilder.toString();
    }
}
