package repository;

import model.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TypeRepository {
    private final Connection connection;

    public TypeRepository(Connection connection) {
        this.connection = connection;
    }

    public boolean createType(Type type) {
        String sql = "INSERT INTO type (nom, code_couleur, admin_only) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, type.getNom());
            stmt.setString(2, type.getCodeCouleur());
            stmt.setBoolean(3, type.isAdminOnly());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        type.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Type> getAllTypes(boolean isAdmin) {
        List<Type> types = new ArrayList<>();
        String sql = isAdmin ? "SELECT * FROM type"
                : "SELECT * FROM type WHERE admin_only = false";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                types.add(new Type(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("code_couleur"),
                        rs.getBoolean("admin_only")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return types;
    }
}