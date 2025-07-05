import model.Product;
import model.Review;
import model.User;
import service.ProductService;
import service.ReviewService;
import service.UserService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        User loggedInUser = null;

        UserService userService = new UserService();
        ProductService productService = new ProductService();
        ReviewService reviewService = new ReviewService();

        while (true) {
            System.out.println("\n--- Product Review App ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Add Product");
            System.out.println("4. View Products");
            System.out.println("5. Add Review");
            System.out.println("6. View Reviews for a Product");
            System.out.println("7. Exit");
            System.out.print("Choose: ");

            int choice = sc.nextInt();
            sc.nextLine();  // consume newline

            if (choice == 1) {
                System.out.print("Enter username: ");
                String username = sc.nextLine();

                System.out.print("Enter password: ");
                String password = sc.nextLine();

                User user = new User(0, username, password);
                boolean registered = userService.registerUser(user);

                if (registered) {
                    System.out.println("Registration successful!");
                } else {
                    System.out.println("Registration failed. Try again.");
                }

            } else if (choice == 2) {
                System.out.print("Enter username: ");
                String username = sc.nextLine();

                System.out.print("Enter password: ");
                String password = sc.nextLine();

                User user = userService.loginUser(username, password);
                if (user != null) {
                    loggedInUser = user;
                    System.out.println("Login successful! Welcome, " + user.getUsername());
                } else {
                    System.out.println("Invalid username or password.");
                }

            } else if (choice == 3) {
                System.out.print("Enter product name: ");
                String pname = sc.nextLine();

                System.out.print("Enter product description: ");
                String pdesc = sc.nextLine();

                Product product = new Product(0, pname, pdesc);
                boolean added = productService.addProduct(product);

                if (added) {
                    System.out.println("Product added successfully.");
                } else {
                    System.out.println("Failed to add product.");
                }

            } else if (choice == 4) {
                var products = productService.getAllProducts();
                if (products.isEmpty()) {
                    System.out.println("No products found.");
                } else {
                    System.out.println("Products:");
                    for (Product p : products) {
                        System.out.printf("ID: %d, Name: %s, Description: %s%n", p.getId(), p.getName(), p.getDescription());
                    }
                }

            } else if (choice == 5) {
                if (loggedInUser == null) {
                    System.out.println("You must login first to add a review.");
                    continue;
                }

                System.out.print("Enter product ID to review: ");
                int prodId = sc.nextInt();
                sc.nextLine();

                System.out.print("Enter your review text: ");
                String reviewText = sc.nextLine();

                System.out.print("Enter rating (1 to 5): ");
                int rating = sc.nextInt();
                sc.nextLine();

                Review review = new Review(0, prodId, loggedInUser.getId(), reviewText, rating);
                boolean reviewAdded = reviewService.addReview(review);

                if (reviewAdded) {
                    System.out.println("Review added successfully.");
                } else {
                    System.out.println("Failed to add review.");
                }

            } else if (choice == 6) {
                System.out.print("Enter product ID to view reviews: ");
                int prodId = sc.nextInt();
                sc.nextLine();

                var reviews = reviewService.getReviewsByProductId(prodId);

                if (reviews.isEmpty()) {
                    System.out.println("No reviews found for this product.");
                } else {
                    System.out.println("Reviews:");
                    for (Review r : reviews) {
                        System.out.printf("User ID: %d, Rating: %d, Review: %s%n", r.getUserId(), r.getRating(), r.getReviewText());
                    }
                }

            } else if (choice == 7) {
                System.out.println("Exiting... Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }

        sc.close();
    }
}
