// Talal Abou Haiba - FileYourTaxes.com application
// Project A - IRS Tax Return Preparers

// Uses jsoup in order to obtain tax preparers from IRS website.

/*
    Remove non-breaking space and replace with whitespace

    Add functionality: print to file instead of console

*/


import java.awt.*;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;


public class Main {

    public static void main(String[] args) {

        int zip;
        char sort_type; // user input for sort type

        List<Tax_Return_Preparer> taxPreparers = new ArrayList<Tax_Return_Preparer>();


        Scanner in = new Scanner(System.in);

        do {
            System.out.println("Please enter a five digit zip code:");

            while (!in.hasNextInt()) { // catch any invalid non-numeric input
                System.out.println("Invalid input. Please enter a five digit zip code: ");
                in.next();
            }

            zip = in.nextInt();
        } while (zip <= 99 || zip >= 99999); // valid zip is 5 digits, however the IRS website allows searches using the first three digits. lowest searchable zip code is therefore '100'


        System.out.println("You entered: " +zip);
        try {

            Document doc = Jsoup.connect("http://search.irs.gov/search?q=" + zip + "&site=efile&client=efile_frontend&output=xml_no_dtd&proxystylesheet=efile_frontend&filter=0&getfields=*&partialfields=buszip%3A" + zip + "%7Czip_w%3A" + zip + "&num=1000").get();
            String title = doc.title();
            System.out.println();

            Elements first = doc.select("td[width=100%]");

            Iterator<Element> iterator = first.listIterator();

            while (iterator.hasNext()) {

                String Name = iterator.next().text();
                String Address = iterator.next().text();
                String City_State_Zip = iterator.next().text();
                String Point_of_contact = iterator.next().text();
                String Telephone = iterator.next().text();

                // Replace all non-breaking space with whitespace

                Name = Name.replaceAll( "\u00a0", " ");
                Address = Address.replaceAll( "\u00a0", " ");
                City_State_Zip = City_State_Zip.replaceAll( "\u00a0", " ");
                Point_of_contact = Point_of_contact.replaceAll("\u00a0", " ");


                // Create a new taxPrepares object with the above information
                taxPreparers.add(new Tax_Return_Preparer(Name, Address, City_State_Zip, Point_of_contact, Telephone)); // each tax preparer is stored as an object containing all relevant information
            }

            if (taxPreparers.isEmpty()) { // end program if no preparers found in zip code
                System.out.println("No preparers found near " + zip + ".");
                System.exit(0);
            }

            System.out.println("Found a total of: " + taxPreparers.size() + " results. \n");

            System.out.println("Please select sort type: [L]ast name or [P]hone number");
                sort_type = in.next().charAt(0);

            while (sort_type != 'l' && sort_type != 'L' && sort_type != 'P' && sort_type != 'p') {

                System.out.println(" Invalid input: Please select sort type: [L]ast name or [P]hone number");
                sort_type = in.next().charAt(0);
            }


            if (sort_type == 'l' || sort_type == 'L') { // sort and display by last name

                    System.out.println("Sorting by last name:");

                    Collections.sort(taxPreparers, Tax_Return_Preparer.compareByName);

                    System.out.println("\nContact Name, Number, Business, Address, City/State/Zip;\n ");

                    for (Tax_Return_Preparer t: taxPreparers){
                        System.out.println(t.getFullName() + ", " + t.getTelephone() + ", " + t.getName_Of_Business() + ", " + t.getAddress() + ", " + t.getCity_State_Zip() + "; " );
                    }
                }


            if (sort_type == 'p' || sort_type == 'P') { // sort and display by phone number
                    System.out.println("Sorting by phone number:");

                    Collections.sort(taxPreparers, Tax_Return_Preparer.compareByPhone);

                    System.out.println("\nNumber, Contact Name, Business, Address, City/State/Zip;\n ");

                    for (Tax_Return_Preparer t: taxPreparers) {
                        System.out.println(t.getTelephone() + ", " + t.getFullName() + ", " + t.getName_Of_Business() + ", " + t.getAddress() + ", " + t.getCity_State_Zip() + "; " );
                    }
            }


        }

        catch(IOException e) {
            System.out.print("An error has occurred. Exiting.");
            e.printStackTrace();
        }
    }
}
