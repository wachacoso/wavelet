import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    ArrayList<String> list = new ArrayList<>();

    public String handleRequest(URI url) {
        if(url.getPath().equals("/")) {
            return "New Empty List Created!";
        }
        else if(url.getPath().contains("/add")) {
            if(url.getQuery().contains("s=")) {
                String[] parameters = url.getQuery().split("=");
                this.list.add(parameters[1]);
                return "String \"" + parameters[1] + "\" Added!";
            }
            else {
                return "Missing Query!";
            }
        }
        else if(url.getPath().contains("/search")) {
            if(url.getQuery().contains("s=")) {
                String[] parameters = url.getQuery().split("=");
                ArrayList<String> searched = new ArrayList<>();
                for(int i = 0; i < this.list.size(); i++) {
                    if(this.list.get(i).contains(parameters[1])) {
                        searched.add(this.list.get(i));
                    }
                }
                if(searched.size() == 0) {
                    return "No Matching Strings Found!";
                }
                else {
                    String returned = searched.get(0);
                    for(int i = 1; i < searched.size(); i++) {
                        returned = returned + ", " + searched.get(i);
                    }
                    return returned;
                }
            }
            else {
                return "Missing Query!";
            }
        }
        return "404 not found!";
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
