/*  Cory Campbell
    Data Structures
    MW 9:05 - 10:45 am
    Assignment #1
 */
import java.io.*;
import java.util.Scanner;
//Link list class created using node class
class TopStreamingArtists {
    protected Artist first;
    protected Artist head;
    protected int size;

    public TopStreamingArtists(){
        first = null;
        head = null;
        size = 0;
    }
    //Adds new data to Link List
    public void insertValue(String data){
        Artist temp = new Artist(data,null);
        if(first == null){
            first = temp;
        }
        else{
            head = temp;
            head.next = first;
            first = head;
        }
        size++;
    }
    //Displays all of the Link List properties
    public void displayValue(){
        if(size == 0){
            System.out.println("List is empty");
            return;
        }
        if(first.getLink() == null){
            System.out.println(first.getData());
            return;
        }
        Artist temp = new Artist();
        temp = first;
        System.out.println(temp.getData());
        temp = temp.getLink();
        while(temp.getLink() != null){
            System.out.println(temp.getData());
            temp = temp.getLink();
        }
        System.out.println(temp.getData());
    }
    //Allows for the insertion of data at the beginning of the List
    public void insertStart(String data) {
        Artist temp = new Artist(data, null);
        size++;
        if (first == null) {
            first = temp;
        }
        temp.setLink(first);
        first = temp;
    }
    //Allows for the insertion of data at the end of the List
    public void insertEnd(String data){
        Artist temp = new Artist(data,null);
        size++;
        Artist move = first.getLink();
        while(move.getLink() != null){
            move = move.getLink();
        }
        move.setLink(temp);
    }
    //Allows for the insertion of data at a specific position by using the size counter to traverse the list
    public void insertAtPos(String data, int pos){
        Artist temp = new Artist(data,null);
        Artist move = first;
        pos = pos - 1;
        for(int i = 0; i < size; i++){
            if(i == pos){
                Artist temp2= move.getLink();
                move.setLink(temp);
                temp.setLink(temp2);
                size++;
                break;
            }
            move = move.getLink();
        }
    }
}
//Node Class to be used by the Link List
class Artist{
    protected String data;
    protected Artist next;

    public Artist(){
        next = null;
        data = null;
    }
    public Artist(String data, Artist n){
        this.data = data;
        next = n;
    }

    public void setValue(String data){
        this.data = data;
    }
    public void setLink(Artist n){
        next = n;
    }
    public String getData(){
        return data;
    }
    public Artist getLink(){
        return next;
    }
}

class Assignment {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new FileReader("D:\\Assignment\\src\\Playlist.csv"));
        PrintStream file = new PrintStream("D:\\Assignment\\src\\Artists.txt");
        //Returns the array with all names
        String artist[] = name(sc,file);
        //Creates new List
        TopStreamingArtists list = new TopStreamingArtists();
        //Inserts all of the names into the link list in alphabetical order without names re-occurring
        for(int i = 0; i < artist.length;i++) {
            String name_holder = artist[i];
            if (i > 0)
                if (name_holder.equals(artist[i - 1])) {
                    for (int j = i + 1; j < artist.length; j++) {
                        if (name_holder.equals(artist[j])) {
                            continue;
                        }
                    }
                }
            else
                    list.insertValue(name_holder);
        }
            //Displays sorted List of artists
            list.displayValue();
    }
    //Sorts the names in an array to be sent to the link list
    public static String[] name(Scanner sc, PrintStream file) throws IOException {
        String input;
        int row = 0;
        String[][] arr = new String[199][10];
        String[] names = new String[199];
        String[] nameHolder = new String[199];
        String[] final_list = new String[199];
        input = sc.nextLine();

        //Check to see if line has a integer value/position at beginning to avoid reading unnecessary lines
        //Populates the arrays with lines from CSV files
        while (sc.hasNext()) {
            if (!(Character.isDigit(input.charAt(0)))) {
                input = sc.nextLine();
            } else if ((Character.isDigit(input.charAt(0)))) {

                String[] sep = input.split(",");

                for (int i = 0; i < sep.length; i++) {
                    arr[row][i] = sep[i];
                }
                input = sc.nextLine();
                row++;
            }
        }
        // Removes the quotations around names and then places them into an array
        for (int i = 0; i < arr.length; i++) {
            if (arr[i][2] != null) {
                nameHolder[i] = arr[i][2];
                if (nameHolder[i].charAt(0) == '\"') {
                    names[i] = nameHolder[i].substring(1, nameHolder[i].length() - 1).trim();
                } else {
                    names[i] = nameHolder[i];
                }
            }
        }
        // Sorts the names into alphabetical order
        for (int i = 0; i < names.length; i++) {
            for (int j = i + 1; j < names.length; j++) {
                names[i] = names[i].trim();
                String[] after = new String[199];
                String[] before = new String[199];
                before[i] = names[i].toLowerCase();
                after[i] = names[j].toLowerCase();
                if (before[i].compareTo(after[i]) < 0) {
                    String temp = names[i];
                    names[i] = names[j];
                    names[j] = temp;
                }
            }
        }
        //Counts how many times a name appears in the list and prints it to a text document named Artist
        for(int i = 0; i < names.length;i++){
            int count = 1;
            String holder = names[i];
            if(i > 0)
            if(!(holder.equals(names[i-1]))) {
                for (int j = i + 1; j < names.length; j++) {
                    if (holder.equals(names[j])) {
                        count++;
                    }
                }
                file.println(holder);
                file.println(count);
            }
            else
                count++;
        }
        file.close();
        return (names);
    }
}


