package Data;

public class Prova {
    public static void main(String[] args)
    {
        String name = "Anna Dalla Vecchia";
        String lastName = "";
        String firstName= "";
        if(name.split("\\w+").length>1){

            lastName = name.substring(name.lastIndexOf(" ")+1);
            firstName = name.substring(0, name.lastIndexOf(' '));
        }
        else{
            firstName = name;
        }

        System.out.println(firstName);
        System.out.println(lastName);

        int firstSpace = name.indexOf(" "); // detect the first space character
        String firstName2 = name.substring(0, firstSpace);  // get everything upto the first space character
        String lastName2 = name.substring(firstSpace).trim(); // get everything after the first space, trimming the spaces off

        System.out.println(firstName2);
        System.out.println(lastName2);

    }
}
