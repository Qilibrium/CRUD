package Client;

import Database.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private String createClient = "INSERT INTO client (name) VALUES (?)";
    private String getById = "SELECT id, name FROM client WHERE id =?";
    private String updateName = "UPDATE CLIENT SET NAME=? WHERE ID=?";
    private String deleteById = "DELETE FROM CLIENT WHERE ID = ?";
    private String listAll = "SELECT id, name FROM client GROUP BY id";

    public long create(String name) throws SQLException {
        long primkey = 0;
        PreparedStatement pstmt = Database.getInstance().getConnection()
                .prepareStatement(createClient, 1);
        pstmt.setString(1, name);
        if (pstmt.executeUpdate() > 0) {
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                primkey = generatedKeys.getInt(1);
            }
        }
        return primkey;
    }
    public String getById(long id) throws SQLException {
        Client client = new Client();
        PreparedStatement ps = Database.getInstance().getConnection().prepareStatement(getById);
        ps.setLong(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            if (!rs.next()) {
                return null;
            }
            return client.setName(rs.getString("name"));
        }
    }


    public void setName(long id, String name) throws SQLException {
        PreparedStatement ps = Database.getInstance().getConnection().prepareStatement(updateName);
        ps.setString(1, name);
        ps.setLong(2, id);
        ps.executeUpdate();
    }

    public void deleteById(long id) throws SQLException {
        PreparedStatement ps = Database.getInstance().getConnection().prepareStatement(deleteById);
        ps.setLong(1, id);
        ps.executeUpdate();
    }

    public List<Client> listAll() throws SQLException {
        PreparedStatement ps = Database.getInstance().getConnection().prepareStatement(listAll);
        List<Client> result = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getLong("id"));
                client.setName(rs.getString("name"));
                result.add(client);
            }
        }
        return result;
    }
}

