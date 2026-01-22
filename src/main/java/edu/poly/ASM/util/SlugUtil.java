package edu.poly.ASM.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class SlugUtil {
    
    /**
     * Tạo slug từ tiếng Việt
     */
    public static String createSlug(String text) {
        if (text == null || text.trim().isEmpty()) {
            return "";
        }
        
        // Chuyển về lowercase
        text = text.toLowerCase().trim();
        
        // Thay thế các ký tự tiếng Việt
        text = text.replaceAll("[àáạảãâầấậẩẫăằắặẳẵ]", "a");
        text = text.replaceAll("[èéẹẻẽêềếệểễ]", "e");
        text = text.replaceAll("[ìíịỉĩ]", "i");
        text = text.replaceAll("[òóọỏõôồốộổỗơờớợởỡ]", "o");
        text = text.replaceAll("[ùúụủũưừứựửữ]", "u");
        text = text.replaceAll("[ỳýỵỷỹ]", "y");
        text = text.replaceAll("[đ]", "d");
        
        // Xóa dấu (nếu còn sót)
        text = Normalizer.normalize(text, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        text = pattern.matcher(text).replaceAll("");
        
        // Xóa các ký tự đặc biệt, chỉ giữ chữ, số và khoảng trắng
        text = text.replaceAll("[^a-z0-9\\s-]", "");
        
        // Thay khoảng trắng bằng dấu gạch ngang
        text = text.replaceAll("\\s+", "-");
        
        // Xóa dấu gạch ngang thừa
        text = text.replaceAll("-+", "-");
        text = text.replaceAll("^-|-$", "");
        
        return text;
    }
    
    /**
     * Tạo slug unique bằng cách thêm số
     */
    public static String createUniqueSlug(String text, int number) {
        String baseSlug = createSlug(text);
        return baseSlug + "-" + number;
    }
}