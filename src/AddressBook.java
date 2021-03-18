import java.util.*;

public class AddressBook{
    Scanner sc = new Scanner(System.in);
    Map<String, Map<Integer, Map<String, String>>> bookName= new HashMap<>();
    Map<String, Map<String, String>> searchContacts = new HashMap<>();
    Map<String, Map<String, String>> sortContactsByCity = new HashMap<>();
    Map<String, Map<String, String>> sortContactsByState = new HashMap<>();
    Map<String, Map<String, String>> sortContactsByZip= new HashMap<>();
    String[] key = {"FirstName","LastName","Address","City","State","ZIP","PhoneNumber","Email"};
    List<String> firstNameList = new ArrayList<>();
    int numOfContact = 0;

    public void displayContacts(){
        if(bookName.isEmpty()){
            System.out.println("Please create a Address Book before you edit");
            return;
        }
        bookName.entrySet().stream().forEachOrdered(System.out::println);
        System.out.println("Enter the book name to be printed: ");
        Map<Integer, Map<String, String>> contacts = bookName.get(sc.next());
        contacts.values().stream().forEach(System.out::println);
    }

    public void addContacts(String  name, int num) {
        System.out.println("Enter Contact Details: ");
        Map<Integer, Map<String, String>> contact = new HashMap<>();
        Map<String, String> userInfo = new HashMap<>();
        for(int i = 0; i<key.length; i++){
            if ( i == 0){
                System.out.println("Enter "+key[i]+": ");
                userInfo.put(key[i], checkDuplicatesForFirstName());
                continue;
            }
            System.out.println("Enter "+key[i]+": ");
            userInfo.put(key[i], sc.next());
        }
        contact.put(num,userInfo);
        searchContacts.put(userInfo.get("FirstName"), userInfo);
        sortContactsByCity.put(userInfo.get("City"), userInfo);
        sortContactsByState.put(userInfo.get("State"), userInfo);
        sortContactsByState.put(userInfo.get("ZIP"), userInfo);
        bookName.put(name, contact);
    }

    public void editContacts() {
        if(bookName.isEmpty()){
            System.out.println("Please create a book before you edit");
            return;
        }
        System.out.println("Enter the firstName of the person contact to be edited: ");
        String name = sc.next();
        Set<String> keysBook = bookName.keySet();
        for (String str : keysBook) {
            Map<Integer, Map<String, String>> contacts = bookName.get(str);
            int flag = 0;
            Set<Integer> keysContact = contacts.keySet();
            for (Integer i : keysContact){
                Map user = (Map) contacts.get(i);
                if (user.get("FirstName").equals(name)){
                    flag= 1;
                    System.out.println("Press the respective number you want to edit\n" +
                            "1  First Name\n2 Last Name\n3 Address \n4 City\n5 State\n" +
                            "6 Pin Code\n7 phone number\n8 email");
                    int choice = sc.nextInt();
                    System.out.println(key[choice-1]+" to be edited: ");
                    user.put(key[choice-1],sc.next());
                }
            }
            if (flag ==0) {
                System.out.println("Name Not Found");
            }
        }
    }

    public void removeContacts() {
        if(bookName.isEmpty()){
            System.out.println("Please create a book before you display ");
            return;
        }
        System.out.println("Enter the firstName of the person contact to be deleted");
        String name = sc.next();
        Set<String> keysBook = bookName.keySet();
        for (String str : keysBook) {
            Map<Integer, Map<String, String>> contacts = bookName.get(str);
            int flag = 0;
            Set<Integer> keysContact = contacts.keySet();
            for (Integer i : keysContact){
                HashMap user = (HashMap) contacts.get(i);
                if (user.get("FirstName").equals(name)){
                    flag= 1;
                    contacts.remove(i);
                    break;
                }
            }
            if (flag ==0) {
                System.out.println("Name Not Found");
            }
        }
    }

    public String checkDuplicatesForFirstName(){
        while(true){
            String firstName = sc.next();
            if(firstNameList.contains(firstName.toLowerCase()))
                System.out.println("Name already exists, PLease Try another name");
            else {
                firstNameList.add(firstName.toLowerCase());
                return firstName;
            }
        }
    }

    void searchContactsByCityOrState(){
        if(bookName.isEmpty()){
            System.out.println("Please create a book before you search");
            return;
        }
        System.out.println("Enter the city or state name to view persons in them");
        String name = sc.next();
        List<Integer> count = new ArrayList<>();
        int flag = 0;
        for (String str : bookName.keySet()) {
            Map<Integer, Map<String, String>> contacts = bookName.get(str);
            for (Integer i : contacts.keySet()) {
                HashMap user = (HashMap) contacts.get(i);
                System.out.println(contacts.get(i));
                if (user.get("City").equals(name) | user.get("State").equals(name)){
                    flag++;
                    System.out.println(user.values());
                }
            }
        }
        if (flag ==0)
            System.out.println("Name Not Found");
        else
            System.out.println("Number of Search Results found: "+flag);
    }

    public void sortTheMap(){
        searchContacts.keySet().stream().sorted().forEach(System.out::println);
        sortContactsByCity.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(System.out::println);
        sortContactsByState.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(System.out::println);
        sortContactsByZip.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(System.out::println);
    }

    public static void main(String[] args) {
        AddressBook book = new AddressBook();
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        while(!exit){
            System.out.println("1. Add Contact\n2. Edit Entries\n3. Remove Entries\n4. Display Entries" +
                                "\n5. Search by City or State\n6. Sort by name\n7. Exit");
            int action = sc.nextInt();
            switch (action){
                case 1:
                    System.out.println("Enter book name");
                    String name = sc.next();
                    book.addContacts(name, ++book.numOfContact);
                    break;
                case 2:
                    book.editContacts();
                    break;
                case 3:
                    book.removeContacts();
                    break;
                case 4:
                    book.displayContacts();
                    break;
                case 5:
                    book.searchContactsByCityOrState();
                    break;
                case 6:
                    book.sortTheMap();
                    break;
                case 7:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid Choice Please try again");
                    break;
            }
        }
    }
}