// Talal Abou Haiba
// Tax_Return_Preparer object, holds all relevant information for each preparer

import java.util.Comparator;

public class Tax_Return_Preparer {

    // Constructor

    public Tax_Return_Preparer (String n, String a, String csz, String c, String num) {

    Name_Of_Business = n;
    Address = a;
    City_State_Zip = csz;
    Name = c;

    lastName = splitLastName(c);
    telephone = num;

    }

    // Private variables

    private String Name_Of_Business;
    private String Address;
    private String City_State_Zip;
    private String Name , lastName;
    private String  telephone;


    // Accessors

    public String getName_Of_Business() {return Name_Of_Business;}
    public String getAddress() {return Address;}
    public String getCity_State_Zip() {return City_State_Zip;}
    public String getFullName() {return Name;}
    public String getLastName() {return lastName;}
    public String getTelephone() {return telephone;}

    // Utility Functions

    // Compare by phone number

    public static Comparator<Tax_Return_Preparer> compareByPhone = new Comparator<Tax_Return_Preparer>() {

        public int compare(Tax_Return_Preparer t1, Tax_Return_Preparer t2) {

            return t1.getTelephone().compareTo(t2.getTelephone());
        }

    };

    // Compare by Last Name

    public static Comparator<Tax_Return_Preparer> compareByName = new Comparator<Tax_Return_Preparer>() {

        public int compare (Tax_Return_Preparer t1, Tax_Return_Preparer t2) {

            String lastName1 = t1.getLastName().toUpperCase();
            String lastName2 = t2.getLastName().toUpperCase();

            return lastName1.compareTo(lastName2);
        }
    };

    // Split name, allow search by last name

    private String splitLastName (String name) {

        String[] tempNameArray = name.split("\u00a0"); // The IRS website uses '&nbsp', a non-breaking space in order to separate the first/middle/last name.

        return ( tempNameArray[tempNameArray.length - 1] ); // return last element of array, in this case - the last name

    }
}
