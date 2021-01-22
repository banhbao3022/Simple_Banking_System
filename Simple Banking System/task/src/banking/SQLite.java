package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class SQLite {
    static String url ;
    static void createNewDataBase(String fileName) {
        url = "jdbc:sqlite:" + fileName;
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        try(Connection connection = dataSource.getConnection();){
            if (connection != null) {
                DatabaseMetaData metaData = connection.getMetaData();
                createNewTable();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    static void createNewTable() {
        String sql = "CREATE TABLE IF NOT EXISTS card (\n"
                + "  id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " number TEXT,\n"
                + " pin TEXT,\n"
                + " balance INTEGER DEFAULT 0\n"
                + ");";
        try(Connection connection = DriverManager.getConnection(url)) {
            Statement statement = connection.createStatement();
            statement.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    static Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    static void updateNewCard(String number,String pin) {
        String insertSQL = "INSERT INTO card(number,pin,balance) VALUES (?,?,0)";
        try ( Connection connection = connect() ) {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1,number);
            preparedStatement.setString(2,pin);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    static int checkBalance(String cardNumber) {
        String checkBalanceSql = "SELECT balance FROM card WHERE number = ?";
        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(checkBalanceSql);
            preparedStatement.setString(1,cardNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    static boolean checkCard(String cardNumber) {
        String checkBalanceSql = "SELECT COUNT(1) FROM card WHERE number = ?";
        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(checkBalanceSql);
            preparedStatement.setString(1,cardNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.getInt(1) == 1) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    static boolean checkLogin(String cardNumber, String pin) {
        String checkBalanceSql = "SELECT COUNT(1) FROM card WHERE number = ? AND pin = ?";
        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(checkBalanceSql);
            preparedStatement.setString(1,cardNumber);
            preparedStatement.setString(2,pin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.getInt(1) == 1) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    static void addIncome(int income,String cardNumber) {
        String addIncome = "UPDATE card SET balance = balance + ? WHERE number = ?";
        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(addIncome);
            preparedStatement.setInt(1,income);
            preparedStatement.setString(2,cardNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    static void closeAccount(String cardNumber) {
        String addIncome = "DELETE FROM card WHERE number = ?";
        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(addIncome);
            preparedStatement.setString(1,cardNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
