import java.util.Comparator;

public class Contact {

    String lastName;
    String firstName;
    String cellNumber;
    String workNumber;
    String homeNumber;
    String address;
    String tabbedAddress;
    
    public static Comparator <Contact> ContactNameComparator = new Comparator <Contact>() {
        
        @Override
	public int compare(Contact c1, Contact c2) {
	   String ContactLastName1 = c1.getLastName().toUpperCase();
	   String ContactLastName2 = c2.getLastName().toUpperCase();
           
           //ascending order
           if (ContactLastName1.compareTo(ContactLastName2) == 0) {
               String ContactFirstName1 = c1.getFirstName().toUpperCase();
               String ContactFirstName2 = c2.getFirstName().toUpperCase();
               return ContactFirstName1.compareTo(ContactFirstName2);
           } else {
               return ContactLastName1.compareTo(ContactLastName2);
           }
        }
    };

    public Contact (String lastName, String firstName) {
            this.lastName = lastName;
            this.firstName = firstName;
    }

    public void setFirstName (String firstName) {
            this.firstName = firstName;
    }

    public void setLastName (String lastName) {
            this.lastName = lastName;
    }

    public void setCellNum (String number) {
            this.cellNumber = isCellNumber (number);
    }

    public void setWorkNum (String number) {
            this.workNumber = isWorkNumber (number);
    }

    public void setHomeNum (String number) {
            this.homeNumber = isHomeNumber (number);
    }

    public void setAddress (String number, String street, String city, String state, String zip) {
            this.address = formatAddress (number, street, city, state, zip);
            this.tabbedAddress = formatTabbedAddress (number, street, city, state, zip);

    }

    public String getLastName () {
            return this.lastName;
    }

    public String getFirstName () {
            return this.firstName;
    }

    public String getCellPhone () {
            return this.cellNumber;
    }

    public String getWorkPhone () {
            return this.workNumber;
    }

    public String getHomePhone () {
            return this.homeNumber;
    }

    public String getAddress () {
            return this.address;
    }

    public String getTabbedAddress() {
            return this.tabbedAddress;
    }

    private String isCellNumber (String number) {
            if (isPhoneNumber(number)){
                    return number;
            } else {
                    return "incorrect format";
            }
    }

    private String isWorkNumber (String number) {
            if (isPhoneNumber(number)){
                    return number;
            } else {
                    return "incorrect format";
            }
    }

    private String isHomeNumber (String number) {
            if (isPhoneNumber(number)){
                    return number;
            } else {
                    return "incorrect format";
            }
    }

    private String formatAddress (String number, String street, String city, String state, String zip) {
            if (!isNumeric(number)) {
                    return "incorrect format\t\t\t\t\t";
            }
            if (!isZipCode(zip)) {
                    return "incorrect format\t\t\t\t\t";
            }
            String addressString = number + " " + street + ", " + city + ", " + state + "  " + zip;
            return addressString;
    }

    private String formatTabbedAddress (String number, String street, String city, String state, String zip) {

            if (!isNumeric(number)) {
                    return "incorrect format\t\t\t\t\t";
            }
            if (!isZipCode(zip)) {
                    return "incorrect format\t\t\t\t\t";
            }
            String tabbedAddressString = number + "\t" + street + "\t" + city + "\t" + state + "\t" + zip + "\t";
            return tabbedAddressString;
    }

    private static boolean isPhoneNumber(String s) {

            if(s.isEmpty()) {
                return false;
            }
            if(s.length() != 10) {
                return false;
            }
            if(!isNumeric (s)) {
                return false;
            }
            return true;
    }

    private static boolean isZipCode (String s) {

            if(s.isEmpty()) {
                return false;
            }
            if(s.length() != 5) {
                return false;
            }
            if(!isNumeric (s)) {
                return false;
            }
            return true;
    }

    private static boolean isNumeric (String s) {
            int stringLength = s.length();

            for (int i = 0; i < stringLength; i++) {
                    if (s.charAt(i) != '1' && s.charAt(i) != '2' && s.charAt(i) != '3' &&
                                    s.charAt(i) != '4' && s.charAt(i) != '5' && s.charAt(i) != '6' &&
                                    s.charAt(i) != '7' && s.charAt(i) != '8' && s.charAt(i) != '9' &&
                                    s.charAt(i) != '0') {
                            return false;
                    }
            }
            return true;
    }

}