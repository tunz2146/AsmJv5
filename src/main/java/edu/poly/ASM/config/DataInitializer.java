package edu.poly.ASM.config;

import edu.poly.ASM.entity.Category;
import edu.poly.ASM.entity.Product;
import edu.poly.ASM.entity.User;
import edu.poly.ASM.repository.CategoryRepository;
import edu.poly.ASM.repository.ProductRepository;
import edu.poly.ASM.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Kiểm tra nếu đã có dữ liệu thì không insert lại
        if (userRepository.count() > 0) {
            return;
        }

        // Tạo tài khoản admin mặc định
        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@gamingshop.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setFullName("Administrator");
        admin.setPhone("0123456789");
        admin.setRole("ADMIN");
        admin.setIsActive(true);
        userRepository.save(admin);

        // Tạo tài khoản user mặc định
        User user = new User();
        user.setUsername("user");
        user.setEmail("user@gamingshop.com");
        user.setPassword(passwordEncoder.encode("user123"));
        user.setFullName("Normal User");
        user.setPhone("0987654321");
        user.setRole("USER");
        user.setIsActive(true);
        userRepository.save(user);

        // Tạo các danh mục sản phẩm
        Category mouse = new Category();
        mouse.setName("Chuột Gaming");
        mouse.setDescription("Chuột gaming chuyên dụng");
        mouse.setIsActive(true);
        categoryRepository.save(mouse);

        Category keyboard = new Category();
        keyboard.setName("Bàn Phím");
        keyboard.setDescription("Bàn phím cơ gaming");
        keyboard.setIsActive(true);
        categoryRepository.save(keyboard);

        Category headset = new Category();
        headset.setName("Tai Nghe");
        headset.setDescription("Tai nghe gaming 7.1");
        headset.setIsActive(true);
        categoryRepository.save(headset);

        Category mousepad = new Category();
        mousepad.setName("Mousepad");
        mousepad.setDescription("Mousepad gaming");
        mousepad.setIsActive(true);
        categoryRepository.save(mousepad);

        // Tạo các sản phẩm mẫu
        createProduct("Logitech G Pro X Superlight", "Chuột gaming siêu nhẹ, cảm biến 25600 DPI", 
                     new BigDecimal("2490000"), new BigDecimal("1990000"), 50, 
                     "https://via.placeholder.com/300x300/ff6b6b/ffffff?text=Logitech+G+Pro", mouse);

        createProduct("Razer DeathAdder V3", "Chuột gaming tuyệt đối với tuổi thọ 80 triệu click", 
                     new BigDecimal("2290000"), new BigDecimal("1890000"), 30, 
                     "https://via.placeholder.com/300x300/4ecdc4/ffffff?text=Razer+DeathAdder", mouse);

        createProduct("SteelSeries Rival 5", "Chuột 18 nút có thể lập trình", 
                     new BigDecimal("1890000"), null, 25, 
                     "https://via.placeholder.com/300x300/95e1d3/ffffff?text=SteelSeries", mouse);

        createProduct("Corsair K95 Platinum", "Bàn phím cơ cao cấp với 8 nút macro", 
                     new BigDecimal("3990000"), new BigDecimal("2990000"), 20, 
                     "https://via.placeholder.com/300x300/f38181/ffffff?text=Corsair+K95", keyboard);

        createProduct("Ducky One 2 Mini", "Bàn phím cơ 60% RGB", 
                     new BigDecimal("2290000"), new BigDecimal("1890000"), 40, 
                     "https://via.placeholder.com/300x300/aa96da/ffffff?text=Ducky+One", keyboard);

        createProduct("HyperX Cloud Flight", "Tai nghe không dây với pin 30 giờ", 
                     new BigDecimal("3490000"), new BigDecimal("2890000"), 15, 
                     "https://via.placeholder.com/300x300/fcbad3/ffffff?text=HyperX+Cloud", headset);

        createProduct("Sennheiser GSP 670", "Tai nghe gaming không dây với âm thanh 7.1", 
                     new BigDecimal("4890000"), new BigDecimal("3990000"), 10, 
                     "https://via.placeholder.com/300x300/a8d8ea/ffffff?text=Sennheiser", headset);

        createProduct("SteelSeries QcK XXL", "Mousepad gaming cỡ lớn", 
                     new BigDecimal("890000"), new BigDecimal("590000"), 100, 
                     "https://via.placeholder.com/300x300/ffffd2/000000?text=SteelSeries+QcK", mousepad);

        System.out.println("✅ Data initialized successfully!");
        System.out.println("📝 Default Accounts:");
        System.out.println("  Admin: admin / admin123");
        System.out.println("  User: user / user123");
    }

    private void createProduct(String name, String description, BigDecimal price, 
                               BigDecimal discountPrice, int stock, String imageUrl, Category category) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setDiscountPrice(discountPrice);
        product.setStockQuantity(stock);
        product.setImageUrl(imageUrl);
        product.setCategory(category);
        product.setIsActive(true);
        productRepository.save(product);
    }
}
