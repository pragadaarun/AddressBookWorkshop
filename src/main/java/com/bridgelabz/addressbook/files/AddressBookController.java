package com.bridgelabz.addressbook.files;
import java.io.IOException;
import java.util.Scanner;

public class AddressBookController {

    public static void main(String[] args) throws IOException {
        updateAddressBook();
    }

    public static void updateAddressBook() throws IOException {
        AddressBookService addressBookService = new AddressBookService();
        Scanner scanner = new Scanner(System.in);
        int execute=1;
        while (execute==1) {
            System.out.println(
                    "Enter choice 1. add addressBook or contact  2.Edit 3.Delete 4.search 5.view by city" +
                            " 6. count by city 7.sort by name 8.sort by city 9.sort by zip 10.to write into file");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addressBookService.addNewAddressBook();
                    break;
                case 2:
                    if (AddressBookService.addressBookList.isEmpty()) {
                        System.out.println(" Address book is empty ");
                        break;
                    }
                    addressBookService.editContact();
                    break;
                case 3:
                    if (AddressBookService.addressBookList.isEmpty()) {
                        System.out.println(" Address book is empty ");
                        break;
                    }
                    addressBookService.deleteContact();
                    break;
                case 4:
                    if (AddressBookService.addressBookList.isEmpty()) {
                        System.out.println(" Address book is empty ");
                        break;
                    }
                    addressBookService.searchContactByCity();
                    break;
                case 5:
                    if (AddressBookService.addressBookList.isEmpty()) {
                        System.out.println(" Address book is empty ");
                        break;
                    }
                    addressBookService.viewContactByCity();
                    break;
                case 6:
                    if (AddressBookService.addressBookList.isEmpty()) {
                        System.out.println(" Address book is empty ");
                        break;
                    }
                    addressBookService.countByCity();
                    break;
                case 7:
                    if (AddressBookService.addressBookList.isEmpty()) {
                        System.out.println(" Address book is empty ");
                        break;
                    }
                    addressBookService.sortByName();
                    break;
                case 8:
                    if (AddressBookService.addressBookList.isEmpty()) {
                        System.out.println(" Address book is empty ");
                        break;
                    }
                    addressBookService.sortByCityOrState();
                    break;
                case 9:
                    if (AddressBookService.addressBookList.isEmpty()) {
                        System.out.println(" Address book is empty ");
                        break;
                    }
                    addressBookService.sortByZip();
                    break;
                case 10:
                    addressBookService.addContactToFile();
                    System.out.println("Successfully Added to text file");
                    addressBookService.readDataFromFile();
                    System.out.println("Successfully read from file");
                    break;
                case 11:
                    addressBookService.addDataToCSVFile();
                    addressBookService.readDataFromCSVFile();
                    break;
                case 12:
                    addressBookService.readDataFromJsonFile();
                    addressBookService.addDataToJSONFile();
                    break;
                default:
                    System.out.println(" Enter a valid choice");
                    break;
            }
        }
    }
}