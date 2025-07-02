import oracle.sql.DATE;

import java.sql.*;

public class InsertRun {
    public static void main(String[] args) {
        Connection connection = null;
        final String connectionUrl = """
                jdbc:oracle:thin:@10.10.108.133:1521:xe
                """;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            connection = DriverManager.getConnection(connectionUrl,"c##madang","1234");
            /*
            SQL 문 만들기 : PreparedStatement
             */
            final String insert_sql = """
                    INSERT INTO orders(orderid, custid, bookid, saleprice, orderdate)\s
                    VALUES(?, ?, ?, ?, ?)
                    """;
            final PreparedStatement preparedStatement = connection.prepareStatement(insert_sql);
            preparedStatement.setInt(1, 11);
            preparedStatement.setInt(2, 3);
            preparedStatement.setInt(3, 4);
            preparedStatement.setInt(4, 20000);
            preparedStatement.setDate(5, Date.valueOf("2025-07-02"));
            final int row = preparedStatement.executeUpdate();
            System.out.printf("%s : %d\r\n", "저장 된 행", row);
            preparedStatement.close();

        } catch (ClassNotFoundException e) {
            System.out.printf("%s\r\n", "jdbc11.jar 없습니다.");
        } catch (SQLException e) {
            System.out.printf("%s\r\n", "계정이 없습니다.,");
        } finally {
            System.out.printf("%s\r\n", "연결 종료");
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.printf("%s\r\n", "close() 오류");
            }
        }
    }
}
