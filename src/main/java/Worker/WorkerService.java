package Worker;

import Client.Client;
import Database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkerService {
    private String getBiIdFromWorker = "SELECT id, name, birthday, level, salary FROM worker WHERE id=?";
    public String getByIdWorker(long id) throws SQLException {
        Worker worker = new Worker();
        PreparedStatement ps = Database.getInstance().getConnection().prepareStatement(getBiIdFromWorker);
        ps.setLong(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            if (!rs.next()) {
                return null;
            }
            worker.setName(rs.getString("name"));
            worker.setBirthday(rs.getDate("birthday"));
            worker.setLevel(rs.getString("level"));
            worker.setSalary(rs.getInt("salary"));
        }
        return worker.toString();
    }
    
public List<Worker>allWorkers() throws SQLException {
        PreparedStatement preparedStatement = Database.getInstance().getConnection()
                .prepareStatement("SELECT id, name, birthday, level, salary FROM worker");
        ResultSet rs = preparedStatement.executeQuery();
        List<Worker>result=new ArrayList<>();
        while (rs.next()){
            Worker worker = new Worker();
            worker.setId(rs.getLong("id"));
            worker.setName(rs.getString("name"));
            worker.setBirthday(rs.getDate("birthday"));
            worker.setLevel(rs.getString("level"));
            worker.setSalary(rs.getInt("salary"));
            result.add(worker);
        }

        return result;
    }

}
