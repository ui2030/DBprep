import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionRun {
    public static void main(String[] args) {
        Connection connection = null;
        final String connectionURL = """
                jdbc:oracle:thin:@10.10.108.133:1521/xe
                """;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            connection = DriverManager.getConnection(connectionURL, "c##madang", "1234");
            System.out.printf("%s\r\n", connection);
            System.out.printf("%s\r\n", "연결 성공");

        } catch (ClassNotFoundException e) {
            System.out.printf("%s\r\n", "jdbc11.jar 없습니다.");
        } catch (SQLException e) {
            System.out.printf("%s\r\n", "로그인 오류.");
        } finally {
            System.out.printf("%s\r\n", "연결 종료");
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.printf("%s\r\n", "close 오류");
            }
        }
    }
}