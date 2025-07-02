import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateRun {
    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        final String connectionURL = """
                jdbc:oracle:thin:@10.10.108.133:1521/xe
                """;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            connection = DriverManager.getConnection(connectionURL, "c##madang", "1234");
            final String update_sql = new StringBuilder()
                    .append("UPDATE customer SET\s")
                    .append("name=?, ")  //space
                    .append("address=?, ") //space바 필요
                    .append("phone=?\s") //space바 필요
                    .append("WHERE custid=?")
                    .toString();
            final PreparedStatement preparedStatement = connection.prepareStatement(update_sql);
            preparedStatement.setString(1, "세리 박");
            preparedStatement.setString(2, "대한민국 대전");
            preparedStatement.setString(3, "010-9999-9999");
            preparedStatement.setInt(4, 4);
            final int row = preparedStatement.executeUpdate();
            System.out.printf("%s %d\r\n", "수정된 데이터 행", row);
            preparedStatement.close();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.printf("%s\r\n", "ojdbc11.jar가 없거나 또는 로그인 오류");
        } finally {
            System.out.printf("%s\r\n", "연결 종료");
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.printf("%s\r\n", "connection.close() 오류");
            }
        }
    }
}
