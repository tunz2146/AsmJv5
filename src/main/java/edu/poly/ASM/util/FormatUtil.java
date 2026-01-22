package edu.poly.ASM.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FormatUtil {
    
    /**
     * Format tiền tệ VNĐ
     */
    public static String formatCurrency(BigDecimal amount) {
        if (amount == null) {
            return "0₫";
        }
        
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        return formatter.format(amount.longValue()) + "₫";
    }
    
    /**
     * Format số với dấu phẩy
     */
    public static String formatNumber(long number) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        return formatter.format(number);
    }
    
    /**
     * Format thời gian
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }
    
    /**
     * Format ngày
     */
    public static String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dateTime.format(formatter);
    }
    
    /**
     * Format giờ
     */
    public static String formatTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return dateTime.format(formatter);
    }
    
    /**
     * Format số điện thoại
     */
    public static String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() < 10) {
            return phoneNumber;
        }
        
        // Format: 0xxx xxx xxx
        return phoneNumber.substring(0, 4) + " " + 
               phoneNumber.substring(4, 7) + " " + 
               phoneNumber.substring(7);
    }
    
    /**
     * Rút gọn text
     */
    public static String truncateText(String text, int maxLength) {
        if (text == null || text.length() <= maxLength) {
            return text;
        }
        
        return text.substring(0, maxLength) + "...";
    }
    
    /**
     * Format phần trăm
     */
    public static String formatPercent(Integer percent) {
        if (percent == null) {
            return "0%";
        }
        
        return percent + "%";
    }
    
    /**
     * Format phần trăm với 2 chữ số thập phân
     */
    public static String formatPercent(BigDecimal percent) {
        if (percent == null) {
            return "0.00%";
        }
        
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(percent) + "%";
    }
}