import java.util.*; //Imports scanner objects
import java.io.*; //Imports file input/output abilities

/**
 * This program allows a user to create, modify, and view contact information.
 * The user interacts with the program through the command line.
 * The contact information is stored in a .csv file format.
 * 
 * @author Matthew
 */
public class Contacts {

    /**
     * The main class prepares the .csv file the contains contact information.
     * The main class also calls the method that starts the user interaction.
     * 
     * @param args 
     *      Command line arguments
     */
    public static void main (String[] args) {

            /**
             * An array list is created that will store
             *      all the contact objects while the program is running.
             */
            List <Contact> contactList = new ArrayList <> ();
            
            /**
             * File is created representing the eventual .csv file that the contact
             *      information will be stored in.
             */
            File contactsFile = null;
            
            /**
             * File object is assigned to an acutal file.
             */
            try {
                    contactsFile = prepareFile();
            } catch (Exception e) {
                    System.out.println(e);
                    System.exit(0);
            }
            
            contactFileToList(contactsFile, contactList); //Takes data in file and puts it in arraylist.
            userInterface(contactList); // Displays interface for user to select a menu item.
            contactListToFile(contactList); //Takes elements of arraylist and puts back in file.
    }

    /**
     * Creates a file object.  If actual file doesn't exist yet, it is created and formatted correctly.
     * @return The formatted file object.
     */
    public static File prepareFile () {
            File contactsFile = null;
            try {
                    contactsFile = new File ("Contacts.csv");
                    if (!contactsFile.exists()) {
                        contactsFile.createNewFile();
                        PrintStream output = new PrintStream (contactsFile);
                        /**
                         * Formats the file so that there are headers for each "column" of data.
                         */
                        output.print("First Name\tLast Name\tCell Phone\tWork Phone\tHome Phone\tStreetNumber\tStreet\tCity\tState\tZip Code\t");
                    }
                    return contactsFile;
            } catch (Exception e) {
                    System.out.println(e);
                    return contactsFile;
            }
    }

    /**
     * Takes information in the .csv file and translates it into objects that are elements in an arraylist.
     * Each line of the .csv file represents 1 contact.
     * @param contactsFile
     *                    The file object that data is pulled from.
     * @param contactList 
     *                    The arraylist of objects where each object is a different contact.
     */
    public static void contactFileToList (File contactsFile, List <Contact> contactList) {
            try {
                    Scanner fileScanner = new Scanner(contactsFile); //Created to read the file.
                    fileScanner.nextLine(); // Consumes header that contains labels for the columns of data in .csv file.
                    fileScanner.useDelimiter("\t");  //Sets scanner delimeter to tab.
                    int count = 0; //Count represents which contact is going to be scanned.
                    
                    /**
                     * While there is another line of contact information this loop will continue.
                     * The loop takes each line of contact info and adds the info to an arraylist element/Contact object.
                     */
                    while (fileScanner.hasNextLine()) {
                            String firstName = fileScanner.next();
                            String lastName = fileScanner.next();
                            String cell = fileScanner.next();
                            String work = fileScanner.next();
                            String home = fileScanner.next();
                            String addressNumber = fileScanner.next();
                            String street = fileScanner.next();
                            String city = fileScanner.next();
                            String state = fileScanner.next();
                            String zip = fileScanner.next();
                            fileScanner.nextLine();
                            /**
                             * Makes new contact.
                             * Then adds the information in the strings above to that Contact object.
                             */
                            contactList.add(new Contact (lastName, firstName));
                            contactList.get(count).setCellNum(cell);
                            contactList.get(count).setWorkNum(work);
                            contactList.get(count).setHomeNum(home);
                            contactList.get(count).setAddress(addressNumber, street, city, state, zip);
                            count++;
                    }
                    fileScanner.close(); //Closes the file scanner.
                    contactsFile.delete(); //Deletes the contacts file, but the arraylist remains.
            } catch (Exception e) {
                    System.out.println(e);
            }

    }

    /**
     * Does the opposite of the contactFileToList method.
     * Takes info from list that was created in contactFileToList and info that may have been added to the arraylist
     *          and puts it back into a new file.
     * @param contactList The arraylist that contains all of the contact information.
     */
    public static void contactListToFile (List <Contact> contactList) {
            File contactsFile = null;
            try {
                    contactsFile = new File ("Contacts.csv");
                    contactsFile.createNewFile();
                    PrintStream output = new PrintStream (contactsFile);
                    output.print("First Name\tLast Name\tCell Phone\tWork Phone\tHome Phone\tStreetNumber\tStreet\tCity\tState\tZip Code\t");
                    for (int i = 0; i < contactList.size(); i++) {
                            output.print("\n" + contactList.get(i).getFirstName() + "\t");
                            output.print(contactList.get(i).getLastName() + "\t");
                            output.print(contactList.get(i).getCellPhone() + "\t");
                            output.print(contactList.get(i).getWorkPhone() + "\t");
                            output.print(contactList.get(i).getHomePhone() + "\t");
                            output.print(contactList.get(i).getTabbedAddress());
                    }
                    output.close();
            } catch (Exception e) {
                    System.out.println(e);
            }

    }

    public static void userInterface(List <Contact> contactList) {

            Scanner input = new Scanner (System.in);
            String menuChoice = "";

            while (menuChoice.equals("")) {

                    System.out.println("What would you like to do? Type number of menu option to continue.");
                    System.out.println("1. Add new contact(s)");
                    System.out.println("2. Look up contact(s)");
                    System.out.println("3. Delete contact(s)");
                    System.out.println("4. Edit contact(s)");
                    System.out.println("5. Show all contacts");
                    System.out.println("6. Exit program");

                    menuChoice = input.next();

                    if (menuChoice.equals("1")) {
                            addContact(contactList, input);
                            Collections.sort(contactList,Contact.ContactNameComparator);
                            menuChoice = "";
                    } else if (menuChoice.equals("2")) {
                            lookupContact(contactList, input);
                            menuChoice = "";
                    } else if (menuChoice.equals("3")) {
                            deleteContact(contactList, input);
                            Collections.sort(contactList,Contact.ContactNameComparator);
                            menuChoice = "";
                    } else if (menuChoice.equals("4")) {
                            editContact(contactList, input);
                            Collections.sort(contactList,Contact.ContactNameComparator);
                            menuChoice = "";
                    } else if (menuChoice.equals("5")) {
                            Collections.sort(contactList,Contact.ContactNameComparator);
                            printAllContacts(contactList);
                            menuChoice = "";                        
                    } else if (menuChoice.equals("6")) {

                    } else {
                            System.out.println("Not a valid menu option.");
                            menuChoice = "";
                    }
            }

            input.close();
    }

    public static void addContact(List <Contact> contactList, Scanner input) {

            boolean addContact = true;
            int count = contactList.size();

            while (addContact) {
                    input.nextLine();
                    System.out.print("Enter contact's first name: ");
                    String firstName = input.nextLine();
                    System.out.print("Enter contact's last name: ");
                    String lastName = input.nextLine();
                    contactList.add(new Contact (lastName, firstName));
                    System.out.print("Enter " + firstName + " " + lastName + "'s cell phone number: ");
                    String cellNumber = input.nextLine();
                    System.out.print("Enter " + firstName + " " + lastName + "'s work phone number: ");
                    String workNumber = input.nextLine();
                    System.out.print("Enter " + firstName + " " + lastName + "'s home phone number: ");
                    String homeNumber = input.nextLine();
                    System.out.print("Enter address number: ");
                    String addressNumber = input.nextLine();
                    System.out.print("Enter street name: ");
                    String streetName = input.nextLine();
                    System.out.print("Enter city: ");
                    String city = input.nextLine();
                    System.out.print("Enter state abbreviation: ");
                    String state = input.nextLine();
                    System.out.print("Enter 5 digit zip code: ");
                    String zip = input.nextLine();
                    contactList.get(count).setCellNum(cellNumber);
                    contactList.get(count).setWorkNum(workNumber);
                    contactList.get(count).setHomeNum(homeNumber);
                    contactList.get(count).setAddress(addressNumber, streetName, city, state, zip);
                    String response = "";
                    count++;
                    while (!response.equals("Y") && !response.equals("N")){
                            System.out.println("Would you like to enter another contact? Enter Y for yes and N for no.");
                            response = input.next();
                            if (response.equals("N")) {
                                    addContact = false;
                            } else if (response.equals("Y")) {
                                    addContact = true;
                            } else {
                                    System.out.println("Not a valid option.");
                            }
                    }
            }

    }

    public static void lookupContact (List <Contact> contactList, Scanner input) {

        input.nextLine();
        System.out.print("Enter the contact's first name: ");
        String firstName = input.nextLine();
        System.out.print("Enter the contact's last name: ");
        String lastName = input.nextLine();
        displayContact(contactList, input, lastName, firstName);
    }
    
    public static void displayContact(List <Contact> contactList, Scanner input, String lastName, String firstName) {
        
        boolean contactExists = false;
        for (int i = 0; i < contactList.size(); i++) {
            if (contactList.get(i).getLastName().equals(lastName)) {
                if (contactList.get(i).getFirstName().equals(firstName)){
                        System.out.println();
                        System.out.println(firstName + " " + lastName);
                        System.out.println("Cell: " + contactList.get(i).getCellPhone());
                        System.out.println("Work: " + contactList.get(i).getWorkPhone());
                        System.out.println("Home: " + contactList.get(i).getHomePhone());
                        System.out.println("Address:");
                        System.out.println(contactList.get(i).getAddress());
                        System.out.println();
                        contactExists = true;
                }
            }
        }
        if (!contactExists) {
            System.out.println("Contact does not exist.");
        }
        
    }

    public static void deleteContact (List <Contact> contactList, Scanner input) {

        System.out.println("Enter the last name of the contact you would like to delete:");
        String lastName = input.next();
        System.out.println("Enter the first name of the contact you would like to delete:");
        String firstName = input.next();

        int elementToRemove = -1;

        for (int i = 0; i < contactList.size(); i++) {
            if (contactList.get(i).getLastName().equals(lastName)) {
                if (contactList.get(i).getFirstName().equals(firstName)){
                    elementToRemove = i;
                }
            }
        }

        if (elementToRemove >= 0) {
            contactList.remove (elementToRemove);
        } else {
            System.out.println("Contact does not exist.  Cannot perform deletion.");
        }
    }

    public static void editContact (List <Contact> contactList, Scanner input) {

        boolean continueEditing = true;
        boolean elementExists = false;
        int elementToEdit = -1;

        System.out.println("Enter the last name of the contact you would like to edit:");
        String lastName = input.next();
        System.out.println("Enter the first name of the contact you would like to edit:");
        String firstName = input.next();

        while (continueEditing) {

            for (int i = 0; i < contactList.size(); i++) {
                if (contactList.get(i).getLastName().equals(lastName)) {
                    if (contactList.get(i).getFirstName().equals(firstName)){
                        elementToEdit = i;
                        elementExists = true;
                    }
                }
            }

            if (elementExists = true) {
                String menuChoice = "";
                displayContact (contactList, input, lastName, firstName);

                while (menuChoice.equals("")) {
                    
                    System.out.println("What contact information would you like to change for " 
                    + firstName + " " + lastName + "?  "
                    + "Type the number of the menu option:");

                    System.out.println("1. First name");
                    System.out.println("2. Last name");
                    System.out.println("3. Cell number");
                    System.out.println("4. Work number");
                    System.out.println("5. Home number");
                    System.out.println("6. Address");
                    System.out.println("7. Done editing contact");

                    menuChoice = input.next();

                    if (menuChoice.equals("1")) {
                        System.out.println("Enter new first name:");
                        contactList.get(elementToEdit).setFirstName(input.next());
                        System.out.println("Updated information:");
                        displayContact (contactList, input, contactList.get(elementToEdit).getLastName(), contactList.get(elementToEdit).getFirstName());
                        menuChoice = "";
                    } else if (menuChoice.equals("2")) {
                        System.out.println("Enter new last name:");
                        contactList.get(elementToEdit).setLastName(input.next());
                        System.out.println("Updated information:");
                        displayContact (contactList, input, contactList.get(elementToEdit).getLastName(), contactList.get(elementToEdit).getFirstName());
                        menuChoice = "";
                    } else if (menuChoice.equals("3")) {
                        System.out.println("Enter new cell number:");
                        contactList.get(elementToEdit).setCellNum(input.next());
                        System.out.println("Updated information:");
                        displayContact (contactList, input, contactList.get(elementToEdit).getLastName(), contactList.get(elementToEdit).getFirstName());
                        menuChoice = "";
                    } else if (menuChoice.equals("4")) {
                        System.out.println("Enter new work number:");
                        contactList.get(elementToEdit).setWorkNum(input.next());
                        System.out.println("Updated information:");
                        displayContact (contactList, input, contactList.get(elementToEdit).getLastName(), contactList.get(elementToEdit).getFirstName());
                        menuChoice = "";
                    } else if (menuChoice.equals("5")) {
                        System.out.println("Enter new home number:");
                        contactList.get(elementToEdit).setHomeNum(input.next());
                        System.out.println("Updated information:");
                        displayContact (contactList, input, contactList.get(elementToEdit).getLastName(), contactList.get(elementToEdit).getFirstName());
                        menuChoice = "";                       				
                    } else if (menuChoice.equals("6")) {
                        input.nextLine();
                        System.out.print("Enter address number: ");
                        String addressNumber = input.nextLine();
                        System.out.print("Enter street name: ");
                        String streetName = input.nextLine();
                        System.out.print("Enter city: ");
                        String city = input.nextLine();
                        System.out.print("Enter state abbreviation: ");
                        String state = input.nextLine();
                        System.out.print("Enter 5 digit zip code: ");
                        String zip = input.nextLine();
                        contactList.get(elementToEdit).setAddress(addressNumber, streetName, city, state, zip);
                        System.out.println("Updated information:");
                        displayContact (contactList, input, contactList.get(elementToEdit).getLastName(), contactList.get(elementToEdit).getFirstName());
                    } else if (menuChoice.equals("7")) {
                        menuChoice = "quit";
                    } else {
                        System.out.println("Not a valid menu option.");
                        menuChoice = "";
                    }
                }  
            } else {
                System.out.println("Contact does not exist.  Cannot perform edit.");
            }
            continueEditing = false;
        }
    }

    public static void printAllContacts (List <Contact> contactList) {

        if (contactList.size() > 0) {
            for (int i = 0; i < contactList.size(); i++) {
                System.out.println();
                System.out.println(contactList.get(i).getFirstName() + " " + contactList.get(i).getLastName());
                System.out.println("Cell: " + contactList.get(i).getCellPhone());
                System.out.println("Work: " + contactList.get(i).getWorkPhone());
                System.out.println("Home: " + contactList.get(i).getHomePhone());
                System.out.println("Address:");
                System.out.println(contactList.get(i).getAddress());
                System.out.println();
            }
            int numContacts = contactList.size();
            System.out.println("Total of " + numContacts + " contacts.");
        } else {
            System.out.println("Total of 0 contacts.");
            System.out.println();
        }
    }
}