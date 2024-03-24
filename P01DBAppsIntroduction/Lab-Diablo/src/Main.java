import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "1234");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/diablo", props);

        PreparedStatement statement = connection.prepareStatement
                ("SELECT user_name, first_name, last_name, COUNT(users_games.id) FROM users" +
                " JOIN users_games ON users_games.user_id = users.id" + " WHERE user_name = ?" + " GROUP BY users_games.user_id");

        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();

        statement.setString(1, username);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()){
            String dbUsername = resultSet.getString("user_name");
            String dbFirstName = resultSet.getString("first_name");
            String dbLastName = resultSet.getString("last_name");
            Integer dbGamesCount = resultSet.getInt("COUNT(users_games.id)");

            System.out.printf("User: %s\n%s %s has played %d games\n", dbUsername, dbFirstName, dbLastName, dbGamesCount);
        } else {
            System.out.println("No such user exists");
        }
    }
}