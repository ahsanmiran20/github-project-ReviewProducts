package service;

import db.DBConnection;
import model.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReviewService {

    public boolean addReview(Review review) {
        String sql = "INSERT INTO reviews(product_id, user_id, review_text, rating) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, review.getProductId());
            stmt.setInt(2, review.getUserId());
            stmt.setString(3, review.getReviewText());
            stmt.setInt(4, review.getRating());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Review> getReviewsByProductId(int productId) {
        ArrayList<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews WHERE product_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reviews.add(new Review(
                        rs.getInt("id"),
                        rs.getInt("product_id"),
                        rs.getInt("user_id"),
                        rs.getString("review_text"),
                        rs.getInt("rating")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reviews;
    }
}
