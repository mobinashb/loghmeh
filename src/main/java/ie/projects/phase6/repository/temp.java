//package ie.projects.phase6.repository;
//
//
//import java.sql.*;
//
//public class temp{
//    public void otherTest(){
//
//        String sql = "SELECT * FROM testT";
//
//        try (Connection con = ConnectionPool.getConnection();
//             PreparedStatement st = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//             ResultSet rs = st.executeQuery()
//        ) {
//            rs.next();
//            rs.updateString("name","rseza22221");
//            rs.updateRow();
//        }
//        catch (SQLException e1){
//            e1.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        temp t = new temp();
//        t.otherTest();
//    }
//}
