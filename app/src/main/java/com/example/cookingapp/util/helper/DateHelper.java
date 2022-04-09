package com.example.cookingapp.util.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHelper {
    public static String format(Date date) {
        return new SimpleDateFormat("dd/MM/yy", Locale.US).format(date);
    }
}
