import java.sql.*;

public class DatabaseInteraction {

    private static final String url = "jdbc:postgresql://localhost:5432/Assignment4DemoDB" ;
    private static final String user = "postgres";
    private static final String password = "password";

    public Connection conn;

    // Set up a connection to the PostgreSQL DB
    public DatabaseInteraction() {
        // JDBC & Database credentials
        try { // Load PostgreSQL JDBC Driver
            Class.forName( "org.postgresql.Driver" );
            // Connect to the database
            this.conn = DriverManager.getConnection(url, user,
                    password);
            if (this.conn != null) {
                System.out.println( "Connected to PostgreSQL successfully!" );
            } else {
                System.out.println( "Failed to establish connection. Exiting Application..." );
                System.exit(0);
            }
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Close the PostgreSQL connection
    public void closeConnection() throws SQLException{
        this.conn.close();
    }

    // Print the results of a ResultSet in a readable format
    public static void printResults(ResultSet rs) throws SQLException {
        // taken from https://stackoverflow.com/questions/19934591/what-is-the-most-efficient-way-to-print-all-query-results-with-column-names?rq=3
        ResultSetMetaData meta= rs.getMetaData();
        int columnNum=meta.getColumnCount();

        for(int i=1;i<=columnNum;i++){
            System.out.print(meta.getColumnName(i)+" ");
        }
        while(rs.next()){
            for(int i=1;i<=columnNum;i++){
                System.out.print(rs.getString(i)+" ");
            }
        }
    }

    // select and return all students

    public ResultSet getAllStudents() throws SQLException {
        String sql = "SELECT * FROM students";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        return pstmt.executeQuery();
    }

    // add the given student
    public void addStudent(String first_name, String last_name, String email, Date enrollment_date) throws SQLException {
        String sql = "INSERT INTO students(first_name, last_name, email, enrollment_date) VALUES (?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, first_name);
        pstmt.setString(2, last_name);
        pstmt.setString(3, email);
        pstmt.setDate(4, enrollment_date);
        pstmt.executeUpdate();
        System.out.println("student added successfully!");

    }

    // update the given student's email address
    public void updateStudentEmail(Integer student_id, String new_email) throws SQLException {
        String sql = "UPDATE students SET email=? WHERE student_id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, new_email);
        pstmt.setInt(2, student_id);
        pstmt.executeUpdate();
        System.out.println("email updated successfully!");
    }

    // delete the specified student
    public void deleteStudent(Integer student_id) throws SQLException {
        // delete the specified student
        String sql = "DELETE FROM students WHERE student_id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, student_id);
        pstmt.executeUpdate();
        System.out.println("student deleted successfully!");
    }
}
