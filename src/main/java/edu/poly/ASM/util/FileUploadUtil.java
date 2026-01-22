package edu.poly.ASM.util;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FileUploadUtil {
    
    /**
     * Upload file và trả về tên file đã lưu
     */
    public static String saveFile(String uploadDir, MultipartFile file) throws IOException {
        // Tạo thư mục nếu chưa tồn tại
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        // Tạo tên file unique
        String fileName = generateUniqueFileName(file.getOriginalFilename());
        
        // Copy file vào thư mục
        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Không thể lưu file: " + fileName, e);
        }
        
        return fileName;
    }
    
    /**
     * Upload nhiều file
     */
    public static String[] saveMultipleFiles(String uploadDir, MultipartFile[] files) throws IOException {
        String[] savedFileNames = new String[files.length];
        
        for (int i = 0; i < files.length; i++) {
            savedFileNames[i] = saveFile(uploadDir, files[i]);
        }
        
        return savedFileNames;
    }
    
    /**
     * Xóa file
     */
    public static void deleteFile(String uploadDir, String fileName) throws IOException {
        Path filePath = Paths.get(uploadDir).resolve(fileName);
        Files.deleteIfExists(filePath);
    }
    
    /**
     * Kiểm tra file có phải là ảnh không
     */
    public static boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
    
    /**
     * Tạo tên file unique
     */
    private static String generateUniqueFileName(String originalFileName) {
        String extension = "";
        int dotIndex = originalFileName.lastIndexOf('.');
        if (dotIndex > 0) {
            extension = originalFileName.substring(dotIndex);
        }
        
        return UUID.randomUUID().toString() + extension;
    }
    
    /**
     * Validate kích thước file (MB)
     */
    public static boolean isValidFileSize(MultipartFile file, long maxSizeMB) {
        long fileSizeInBytes = file.getSize();
        long maxSizeInBytes = maxSizeMB * 1024 * 1024;
        return fileSizeInBytes <= maxSizeInBytes;
    }
    
    /**
     * Validate extension file
     */
    public static boolean hasValidExtension(MultipartFile file, String[] allowedExtensions) {
        String fileName = file.getOriginalFilename();
        if (fileName == null) return false;
        
        String extension = "";
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            extension = fileName.substring(dotIndex + 1).toLowerCase();
        }
        
        for (String allowedExt : allowedExtensions) {
            if (extension.equals(allowedExt.toLowerCase())) {
                return true;
            }
        }
        
        return false;
    }
}