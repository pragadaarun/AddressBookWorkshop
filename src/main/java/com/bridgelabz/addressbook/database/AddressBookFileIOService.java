package com.bridgelabz.addressbook.database;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class AddressBookFileIOService {
    public static String PAYROll_FILE_NAME = "payroll-file.txt";

    public long countEntries() {
        long entries = 0;
        try {
            entries = Files.lines(new File(PAYROll_FILE_NAME).toPath()).count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries;
    }
}
