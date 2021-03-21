package com.bridgelabz.addressbook.files;

import com.google.gson.Gson;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AddressBookService {

    static Scanner scanner = new Scanner(System.in);
    static List<AddressBook> addressBookList = new ArrayList<AddressBook>();
    Map<String,List<AddressBook>> addressBookMap=new HashMap<String,List<AddressBook>>();
    AddressBook contact;


    public void editContact() {
        System.out.println(" Enter the book name ");
        String bookName = scanner.nextLine();
        List<AddressBook> addressBooks = addressBookMap.get(bookName);
        for (int index = 0; index < addressBooks.size(); index++) {
            if (addressBooks.get(index).getFirstName().equals(bookName)) {
                System.out.println(addressBooks.get(index));
                Scanner updateContact = new Scanner(System.in);
                System.out.println(" Enter a choice 	1.first name 2.last name 3. city 4.state 5.zip 6.phone 7.email ");
                int selection = scanner.nextInt();
                switch (selection) {
                    case 1:
                        System.out.println(" Enter first name ");
                        String first_Name = updateContact.nextLine();
                        addressBooks.get(index).setFirstName(first_Name);
                        System.out.println(addressBooks.get(index).getFirstName());
                        break;
                    case 2:
                        System.out.println(" Enter last name ");
                        String second_Name = updateContact.nextLine();
                        addressBooks.get(index).setLastName(second_Name);
                        break;
                    case 3:
                        System.out.println(" Enter city name ");
                        String input_City = updateContact.nextLine();
                        addressBooks.get(index).setCity(input_City);
                        break;
                    case 4:
                        System.out.println(" Enter State ");
                        String input_State = updateContact.nextLine();
                        addressBooks.get(index).setCity(input_State);
                        break;
                    case 5:
                        System.out.println(" Enter pincode ");
                        String input_Zip = updateContact.nextLine();
                        addressBooks.get(index).setCity(input_Zip);
                        break;
                    case 6:
                        System.out.println(" Enter Mobile number ");
                        String input_Phone = updateContact.nextLine();
                        addressBooks.get(index).setZip(input_Phone);
                        break;
                    case 7:
                        System.out.println(" Enter Email id ");
                        String input_Email = updateContact.nextLine();
                        addressBooks.get(index).setCity(input_Email);
                        break;
                    default:
                        System.out.println(" Enter valid input ");
                        break;
                }
                System.out.println(addressBookMap);
            }
        }
    }

    public void deleteContact() {
        System.out.println(" Enter the book name ");
        String bookName = scanner.nextLine();
        List<AddressBook> addressBooks = addressBookMap.get(bookName);
        IntStream.range(0, addressBooks.size()).forEach(index -> {
            System.out.println("Enter First Name : ");
            Scanner sc = new Scanner(System.in);
            String firstName = sc.nextLine();
            if (addressBooks.get(index).getFirstName().equalsIgnoreCase(firstName)) addressBooks.remove(index);
            else System.out.println("No Data Found");
        });
        System.out.println(addressBooks);
    }

    public void addNewAddressBook() {

        System.out.println("Enter the addressBook name");
        String addressBookName = scanner.nextLine();
        System.out.println("Enter 1 to add new addressBook 2 to existing addressBook 3.add to file to Exit");
        int nextInt = scanner.nextInt();
        switch (nextInt){
            case 1:
                addNewContact(addressBookName);
                break;
            case 2:
                boolean containsKey = addressBookMap.containsKey(addressBookName);
                if (containsKey) {
                    System.out.println(addressBookMap);
                    AddressBook addressBook = addNewContact();
                     addressBookList = addressBookMap.get(addressBookName);
                    addressBookList.add(addressBook);
                    System.out.println(addressBookMap);
                }
                else{
                    System.out.println("No book is present");
                }
                break;
            case 3:
                break;
            default:
                break;

        }

    }

    private void addNewContact(String addressBookName) {
        AddressBook addressBook = addNewContact();
        List<AddressBook> addressBookList = new ArrayList<AddressBook>();
        addressBookList.add(addressBook);
        addressBookMap.put(addressBookName, addressBookList);
        System.out.println(addressBookMap);
    }

    private AddressBook addNewContact() {
        scanner.nextLine();
        System.out.println("Enter your firstName : ");
        String firstName = scanner.nextLine();
        System.out.println("Enter your lastName : ");
        String lastName = scanner.nextLine();
        System.out.println("Enter your address : ");
        String address = scanner.nextLine();
        System.out.println("Enter your city : ");
        String city = scanner.nextLine();
        System.out.println("Enter your state : ");
        String state = scanner.nextLine();
        System.out.println("Enter your zipCode : ");
        String zip = scanner.nextLine();
        System.out.println("Enter your phoneNo : ");
        String phoneNo = scanner.nextLine();
        System.out.println("Enter your emailId : ");
        String email = scanner.nextLine();
        AddressBook addressBook = new AddressBook(firstName, lastName, address, city, state, zip, phoneNo, email);
        return addressBook;
    }

    public void searchContactByCity() {
        System.out.println("Enter City Name : ");
        Scanner sc = new Scanner(System.in);
        String city = sc.nextLine();
        addressBookList.stream().filter(contact -> contact.getCity().equals(city)).forEach(i -> System.out.println(i));
    }

    public void viewContactByCity() {
        System.out.println("Enter City Name : ");
        Scanner sc = new Scanner(System.in);
        String city = sc.nextLine();
        addressBookList.stream().filter(contact -> contact.getCity().equals(city)).forEach(addressBook -> System.out.println(addressBook));
    }

    public void countByCity() {
        System.out.println("Enter City Name : ");
        Scanner sc = new Scanner(System.in);
        String city = sc.nextLine();
        long count = addressBookList.stream().filter(addressBook -> addressBook.getCity().equals(city)).count();
        System.out.println(count);
    }

    public void sortByName() {
        addressBookList.stream()
                .sorted(Comparator.comparing(AddressBook::getFirstName))
                .collect(Collectors.toList())
                .forEach(addressBook -> System.out.println(addressBook));
    }

    public void sortByCityOrState() {
        addressBookList.stream().sorted(Comparator.comparing(AddressBook::getState)).collect(Collectors.toList())
                .forEach(addressBook -> System.out.println(addressBook));
    }

    public void sortByZip() {
        addressBookList.stream().sorted(Comparator.comparing(AddressBook::getZip)).collect(Collectors.toList())
                .forEach(addressBook -> System.out.println(addressBook));
    }

    public void addDataToFile(String firstName, String lastName, String address, String city, String state,
                              String phoneNumber, String zip, String email) {
        System.out.println("Enter name for txt written file : ");
        String fileName = scanner.nextLine();
        File file = new File("C:\\Users\\Heros\\Desktop\\fileIo" + fileName + ".txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("Contact:" + "\n1.First name: " + firstName + "\n2.Last name: " + lastName + "\n3.Address: "
                    + address + "\n4.City: " + city + "\n5.State: " + state + "\n6.Phone number: " + phoneNumber
                    + "\n7.Zip: " + zip + "\n8.email: " + email + "\n");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addContactToFile() {
        System.out.println("Enter the address book name");
        AddressBook addressBook = addNewContact();
        addDataToFile(addressBook.firstName,addressBook.lastName,addressBook.address,addressBook.city,addressBook.state,
                addressBook.phoneNo,addressBook.zip,addressBook.email);
    }

    public void readDataFromFile() {
        System.out.println("Enter address book name : ");
        String fileName = scanner.nextLine();
        Path filePath = Paths.get("C:\\Users\\Heros\\Desktop\\fileIo" + fileName + ".txt");
        try {
            Files.lines(filePath).map(line -> line.trim()).forEach(line -> System.out.println(line));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addDataToCSVFile() throws IOException {
        System.out.println("Enter name of the file : ");
        String fileName = scanner.nextLine();
        Path filePath = Paths.get("C:\\Users\\Heros\\Desktop\\fileIo" + fileName + ".csv");

        if (Files.notExists(filePath))
            Files.createFile(filePath);
        File file = new File(String.valueOf(filePath));

        try {
            FileWriter outputfile = new FileWriter(file, true);
            CSVWriter writer = new CSVWriter(outputfile);
            List<String[]> data = new ArrayList<>();
            for (AddressBook detail : addressBookList) {
                data.add(new String[] { "Contact:" + "\n1.First name: " + detail.firstName + "\n2.Last name: "
                        + detail.lastName + "\n3.Address: " + detail.address + "\n4.City: " + detail.city
                        + "\n5.State: " + detail.state + "\n6.Phone number: " + detail.phoneNo + "\n7.Zip: "
                        + detail.zip + "\n8.email: " + detail.email + "\n" });
            }
            writer.writeAll(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readDataFromCSVFile() {
        System.out.println("Enter address book name : ");
        String fileName = scanner.nextLine();
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader("C:\\Users\\Heros\\Desktop\\fileIo" + fileName + ".csv"));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                for (String token : nextLine) {
                    System.out.println(token);
                }
                System.out.print("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addDataToJSONFile() throws IOException {
        System.out.println("Enter name for json written file : ");
        String fileName = scanner.nextLine();
        Path filePath = Paths.get("C:\\Users\\Heros\\Desktop\\fileIo" + fileName + ".json");
        Gson gson = new Gson();
        String json = gson.toJson(addressBookList);
        FileWriter writer = new FileWriter(String.valueOf(filePath));
        writer.write(json);
        writer.close();
    }

    public void readDataFromJsonFile() throws FileNotFoundException {
        System.out.println("Enter address book name : ");
        String fileName = scanner.nextLine();
        Path filePath = Paths.get("C:\\Users\\Heros\\Desktop\\fileIo" + fileName + ".json");
        Gson gson = new Gson();
        BufferedReader br = new BufferedReader(new FileReader(String.valueOf(filePath)));
        AddressBook[] data = gson.fromJson(br, AddressBook[].class);
        List<AddressBook> lst = Arrays.asList(data);
        for (AddressBook details : lst) {
            System.out.println("Firstname : " + details.firstName);
            System.out.println("Lastname : " + details.lastName);
            System.out.println("Address : " + details.address);
            System.out.println("City : " + details.city);
            System.out.println("State : " + details.state);
            System.out.println("Zip : " + details.zip);
            System.out.println("Phone no : " + details.phoneNo);
            System.out.println("Email : " + details.email);
        }
    }

}