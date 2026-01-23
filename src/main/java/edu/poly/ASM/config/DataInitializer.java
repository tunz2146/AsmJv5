package edu.poly.ASM.config;

import edu.poly.ASM.entity.User;
import edu.poly.ASM.entity.Category;
import edu.poly.ASM.entity.Brand;
import edu.poly.ASM.entity.Product;
import edu.poly.ASM.repository.UserRepository;
import edu.poly.ASM.repository.CategoryRepository;
import edu.poly.ASM.repository.BrandRepository;
import edu.poly.ASM.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private BrandRepository brandRepository;
    
    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("========================================");
        System.out.println("🚀 Starting Data Initialization...");
        System.out.println("========================================");
        
    //     try {
    //         // 1. TẠO USERS
    //         if (userRepository.count() == 0) {
    //             createUsers();
    //         } else {
    //             System.out.println("ℹ️  Users already exist (" + userRepository.count() + "), skipping...");
    //         }
            
    //         // 2. TẠO CATEGORIES
    //         if (categoryRepository.count() == 0) {
    //             createCategories();
    //         } else {
    //             System.out.println("ℹ️  Categories already exist (" + categoryRepository.count() + "), skipping...");
    //         }
            
    //         // 3. TẠO BRANDS
    //         if (brandRepository.count() == 0) {
    //             createBrands();
    //         } else {
    //             System.out.println("ℹ️  Brands already exist (" + brandRepository.count() + "), skipping...");
    //         }
            
    //         // 4. TẠO PRODUCTS
    //         if (productRepository.count() == 0) {
    //             createProducts();
    //         } else {
    //             System.out.println("ℹ️  Products already exist (" + productRepository.count() + "), skipping...");
    //         }
            
    //         System.out.println("========================================");
    //         System.out.println("✅ Data Initialization Completed!");
    //         System.out.println("📊 Database Status:");
    //         System.out.println("   - Users: " + userRepository.count());
    //         System.out.println("   - Categories: " + categoryRepository.count());
    //         System.out.println("   - Brands: " + brandRepository.count());
    //         System.out.println("   - Products: " + productRepository.count());
    //         System.out.println("========================================");
            
    //     } catch (Exception e) {
    //         System.err.println("❌ ERROR during data initialization!");
    //         System.err.println("Message: " + e.getMessage());
    //         e.printStackTrace();
    //     }
    // }
    
    // private void createUsers() {
    //     System.out.println("\n👤 Creating Users...");
        
    //     try {
    //         // ADMIN ACCOUNT
    //         User admin = new User();
    //         admin.setSoDienThoai("0123456789");
    //         admin.setTen("Admin Gaming Shop");
    //         admin.setPassword("admin123");
    //         admin.setRole("ADMIN");
    //         admin.setTrangThai(true);
    //         admin.setEmail("admin@gamingshop.vn");
    //         admin.setGioiTinh(true);
    //         userRepository.save(admin);
    //         System.out.println("   ✅ Admin created - Phone: 0123456789, Password: admin123");
            
    //         // USER ACCOUNT
    //         User user = new User();
    //         user.setSoDienThoai("0987654321");
    //         user.setTen("Nguyễn Văn A");
    //         user.setPassword("user123");
    //         user.setRole("USER");
    //         user.setTrangThai(true);
    //         user.setEmail("user@example.com");
    //         user.setGioiTinh(true);
    //         userRepository.save(user);
    //         System.out.println("   ✅ User created - Phone: 0987654321, Password: user123");
            
    //     } catch (Exception e) {
    //         System.err.println("   ❌ Error creating users: " + e.getMessage());
    //         throw e;
    //     }
    // }
    
    // private void createCategories() {
    //     System.out.println("\n📁 Creating Categories...");
        
    //     try {
    //         String[][] categoryData = {
    //             {"Chuột Gaming", "Chuột gaming chuyên nghiệp với độ chính xác cao"},
    //             {"Bàn Phím Cơ", "Bàn phím cơ gaming với switch cao cấp"},
    //             {"Tai Nghe Gaming", "Tai nghe gaming âm thanh vòm 7.1"},
    //             {"Mousepad", "Bàn di chuột gaming kích thước lớn"},
    //             {"Ghế Gaming", "Ghế gaming ergonomic cho game thủ"}
    //         };
            
    //         for (String[] data : categoryData) {
    //             Category category = new Category();
    //             category.setTen(data[0]);
    //             category.setMoTa(data[1]);
    //             categoryRepository.save(category);
    //         }
            
    //         System.out.println("   ✅ Created " + categoryData.length + " categories");
            
    //     } catch (Exception e) {
    //         System.err.println("   ❌ Error creating categories: " + e.getMessage());
    //         throw e;
    //     }
    // }
    
    // private void createBrands() {
    //     System.out.println("\n🏷️  Creating Brands...");
        
    //     try {
    //         String[] brandNames = {
    //             "Logitech", "Razer", "Corsair", 
    //             "SteelSeries", "HyperX", "Asus ROG"
    //         };
            
    //         for (String name : brandNames) {
    //             Brand brand = new Brand();
    //             brand.setTenHang(name);
    //             brand.setMoTa("Thương hiệu gaming hàng đầu thế giới");
    //             brandRepository.save(brand);
    //         }
            
    //         System.out.println("   ✅ Created " + brandNames.length + " brands");
            
    //     } catch (Exception e) {
    //         System.err.println("   ❌ Error creating brands: " + e.getMessage());
    //         throw e;
    //     }
    // }
    
    // private void createProducts() {
    //     System.out.println("\n🛒 Creating Products...");
        
    //     try {
    //         List<Category> categories = categoryRepository.findAll();
    //         List<Brand> brands = brandRepository.findAll();
            
    //         if (categories.isEmpty() || brands.isEmpty()) {
    //             System.err.println("   ❌ Cannot create products: Categories or Brands not found!");
    //             return;
    //         }
            
    //         Category chuotGaming = categories.get(0);
    //         Category banPhimCo = categories.get(1);
    //         Category taiNghe = categories.get(2);
            
    //         Brand logitech = brands.get(0);
    //         Brand razer = brands.get(1);
    //         Brand corsair = brands.get(2);
    //         Brand steelseries = brands.get(3);
    //         Brand hyperx = brands.get(4);
            
    //         // // === PRODUCT 1: Logitech G502 HERO ===
    //         // Product p1 = new Product();
    //         // p1.setTenSanPham("Logitech G502 HERO Gaming Mouse");
    //         // p1.setGiaSanPham(new BigDecimal("1590000"));
    //         // p1.setGiaGiam(new BigDecimal("1290000"));
    //         // p1.setChietKhau(19);
    //         // p1.setTonKho(50);
    //         // p1.setHinhAnh("https://images.unsplash.com/photo-1527814050087-3793815479db?w=500");
    //         // p1.setMoTaNgan("Chuột gaming cao cấp với cảm biến HERO 25K DPI");
    //         // p1.setMoTaChiTiet("Logitech G502 HERO với cảm biến HERO 25K DPI, 11 nút lập trình, hệ thống trọng lượng điều chỉnh.");
    //         // p1.setBrand(logitech);
    //         // p1.setTrangThai(true);
    //         // p1.setLuotXem(1250);
    //         // Set<Category> p1Categories = new HashSet<>();
    //         // p1Categories.add(chuotGaming);
    //         // p1.setCategories(p1Categories);
    //         // productRepository.save(p1);
            
    //         // // === PRODUCT 2: Razer DeathAdder V3 ===
    //         // Product p2 = new Product();
    //         // p2.setTenSanPham("Razer DeathAdder V3 Pro");
    //         // p2.setGiaSanPham(new BigDecimal("3290000"));
    //         // p2.setGiaGiam(new BigDecimal("2890000"));
    //         // p2.setChietKhau(12);
    //         // p2.setTonKho(35);
    //         // p2.setHinhAnh("https://images.unsplash.com/photo-1538481199705-c710c4e965fc?w=500");
    //         // p2.setMoTaNgan("Chuột wireless gaming nhẹ nhất thế giới, 30K DPI");
    //         // p2.setMoTaChiTiet("Razer DeathAdder V3 Pro chỉ 63g, cảm biến Focus Pro 30K, pin 90 giờ.");
    //         // p2.setBrand(razer);
    //         // p2.setTrangThai(true);
    //         // p2.setLuotXem(980);
    //         // Set<Category> p2Categories = new HashSet<>();
    //         // p2Categories.add(chuotGaming);
    //         // p2.setCategories(p2Categories);
    //         // productRepository.save(p2);
            
    //         // // === PRODUCT 3: Corsair K70 RGB ===
    //         // Product p3 = new Product();
    //         // p3.setTenSanPham("Corsair K70 RGB MK.2 Mechanical");
    //         // p3.setGiaSanPham(new BigDecimal("3590000"));
    //         // p3.setGiaGiam(new BigDecimal("2990000"));
    //         // p3.setChietKhau(17);
    //         // p3.setTonKho(28);
    //         // p3.setHinhAnh("https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=500");
    //         // p3.setMoTaNgan("Bàn phím cơ gaming full-size với Cherry MX Switch");
    //         // p3.setMoTaChiTiet("Corsair K70 RGB với Cherry MX Red, khung nhôm, LED RGB per-key.");
    //         // p3.setBrand(corsair);
    //         // p3.setTrangThai(true);
    //         // p3.setLuotXem(1450);
    //         // Set<Category> p3Categories = new HashSet<>();
    //         // p3Categories.add(banPhimCo);
    //         // p3.setCategories(p3Categories);
    //         // productRepository.save(p3);
            
    //         // // === PRODUCT 4: SteelSeries Rival 5 ===
    //         // Product p4 = new Product();
    //         // p4.setTenSanPham("SteelSeries Rival 5 Gaming Mouse");
    //         // p4.setGiaSanPham(new BigDecimal("1290000"));
    //         // p4.setTonKho(45);
    //         // p4.setHinhAnh("https://images.unsplash.com/photo-1563297007-0686b7003af7?w=500");
    //         // p4.setMoTaNgan("Chuột gaming đa năng với 9 nút lập trình");
    //         // p4.setMoTaChiTiet("SteelSeries Rival 5 với cảm biến TrueMove Air, 9 nút programmable.");
    //         // p4.setBrand(steelseries);
    //         // p4.setTrangThai(true);
    //         // p4.setLuotXem(720);
    //         // Set<Category> p4Categories = new HashSet<>();
    //         // p4Categories.add(chuotGaming);
    //         // p4.setCategories(p4Categories);
    //         // productRepository.save(p4);
            
    //         // // === PRODUCT 5: HyperX Cloud II ===
    //         // Product p5 = new Product();
    //         // p5.setTenSanPham("HyperX Cloud II Wireless Gaming Headset");
    //         // p5.setGiaSanPham(new BigDecimal("2990000"));
    //         // p5.setGiaGiam(new BigDecimal("2490000"));
    //         // p5.setChietKhau(17);
    //         // p5.setTonKho(40);
    //         // p5.setHinhAnh("https://images.unsplash.com/photo-1599669454699-248893623440?w=500");
    //         // p5.setMoTaNgan("Tai nghe gaming wireless với âm thanh 7.1 surround");
    //         // p5.setMoTaChiTiet("HyperX Cloud II Wireless với pin 30 giờ, driver 53mm.");
    //         // p5.setBrand(hyperx);
    //         // p5.setTrangThai(true);
    //         // p5.setLuotXem(890);
    //         // Set<Category> p5Categories = new HashSet<>();
    //         // p5Categories.add(taiNghe);
    //         // p5.setCategories(p5Categories);
    //         // productRepository.save(p5);
            
    //         // // === PRODUCT 6: Razer BlackWidow V3 ===
    //         // Product p6 = new Product();
    //         // p6.setTenSanPham("Razer BlackWidow V3 Pro Wireless");
    //         // p6.setGiaSanPham(new BigDecimal("4990000"));
    //         // p6.setGiaGiam(new BigDecimal("3990000"));
    //         // p6.setChietKhau(20);
    //         // p6.setTonKho(20);
    //         // p6.setHinhAnh("https://images.unsplash.com/photo-1595225476474-87563907a212?w=500");
    //         // p6.setMoTaNgan("Bàn phím cơ wireless cao cấp với Razer Green Switch");
    //         // p6.setMoTaChiTiet("Razer BlackWidow V3 Pro với Razer Green Switch, kết nối 3 mode.");
    //         // p6.setBrand(razer);
    //         // p6.setTrangThai(true);
    //         // p6.setLuotXem(1120);
    //         // Set<Category> p6Categories = new HashSet<>();
    //         // p6Categories.add(banPhimCo);
    //         // p6.setCategories(p6Categories);
    //         // productRepository.save(p6);
            
    //         // // === PRODUCT 7: Logitech G Pro X ===
    //         // Product p7 = new Product();
    //         // p7.setTenSanPham("Logitech G Pro X Mechanical Keyboard");
    //         // p7.setGiaSanPham(new BigDecimal("2890000"));
    //         // p7.setGiaGiam(new BigDecimal("2390000"));
    //         // p7.setChietKhau(17);
    //         // p7.setTonKho(32);
    //         // p7.setHinhAnh("https://images.unsplash.com/photo-1511467687858-23d96c32e4ae?w=500");
    //         // p7.setMoTaNgan("Bàn phím TKL dành cho esports với GX Switch");
    //         // p7.setMoTaChiTiet("Logitech G Pro X TKL với GX switch có thể thay đổi.");
    //         // p7.setBrand(logitech);
    //         // p7.setTrangThai(true);
    //         // p7.setLuotXem(950);
    //         // Set<Category> p7Categories = new HashSet<>();
    //         // p7Categories.add(banPhimCo);
    //         // p7.setCategories(p7Categories);
    //         // productRepository.save(p7);
            
    //         // // === PRODUCT 8: Corsair Dark Core RGB Pro ===
    //         // Product p8 = new Product();
    //         // p8.setTenSanPham("Corsair Dark Core RGB Pro SE Wireless");
    //         // p8.setGiaSanPham(new BigDecimal("2490000"));
    //         // p8.setTonKho(38);
    //         // p8.setHinhAnh("https://images.unsplash.com/photo-1605034313761-73ea0173e23c?w=500");
    //         // p8.setMoTaNgan("Chuột gaming wireless với sạc Qi không dây");
    //         // p8.setMoTaChiTiet("Corsair Dark Core RGB Pro SE với cảm biến 18K DPI, sạc Qi.");
    //         // p8.setBrand(corsair);
    //         // p8.setTrangThai(true);
    //         // p8.setLuotXem(680);
    //         // Set<Category> p8Categories = new HashSet<>();
    //         // p8Categories.add(chuotGaming);
    //         // p8.setCategories(p8Categories);
    //         // productRepository.save(p8);
            
    //         // System.out.println("   ✅ Created 8 gaming products successfully!");
            
    //     } catch (Exception e) {
    //         System.err.println("   ❌ Error creating products: " + e.getMessage());
    //         e.printStackTrace();
    //         throw e;
    //     }
    }
}