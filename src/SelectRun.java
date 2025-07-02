import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class SelectRun {
    public static void main(String[] args) {
        Connection connection = null;
        final String connectionUrl = """
                jdbc:oracle:thin:@10.10.108.133:1521/xe
                """;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            connection = DriverManager.getConnection(connectionUrl,"c##madang","1234");
            final String select_sql = """
                    SELECT book_id, book_name, publisher, price\s
                    FROM book
                    """;
            final PreparedStatement preparedStatement = connection.prepareStatement(select_sql);
            final ResultSet resultSet = preparedStatement.executeQuery();
            final OutputStream outputStream = new FileOutputStream("./oracle.txt");
            final Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);

            while (resultSet.next()) {
                final Book book = new Book(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4));
                System.out.printf("%s\r\n", book);
                writer.write(String.valueOf(book));
                writer.write("\r\n");
                writer.flush();  //버퍼를 비워주라는 뜻
            }
            writer.close();
            resultSet.close();
            outputStream.close();
            preparedStatement.close();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.printf("%s\r\n", "로그인 오류 또는 오타");
        } catch (FileNotFoundException e) {
            System.out.printf("%s\r\n", "파일이 없습니다.");
        } catch (IOException e) {
            System.out.printf("%s\r\n", "write() 함수 오류");
        } finally {
            System.out.printf("%s\r\n", "연결 종료");
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.printf("%s\r\n", "connection close() 오류");
            }
        }
    }
}
