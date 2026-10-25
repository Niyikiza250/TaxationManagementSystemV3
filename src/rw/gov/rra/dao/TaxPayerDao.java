package rw.gov.rra.dao;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import rw.gov.rra.model.LoginFormClass;
import rw.gov.rra.model.TaxPayer;
import rw.gov.rra.model.User;


/**
 *
 * @author jeremie
 */
public class TaxPayerDao {
    // JDBC properties
    private String jdbcUrl = "jdbc:postgresql://localhost:5432/Student";
    private String dbUsername = "postgres";
    private String dbPassword ="0000";
    
    // CRUD
    // CREATE
   
    public int registerTaxPayer(TaxPayer taxPayerObj){
        // step 0: surround with try and catch
        try{
            // step 1: create connection 
            Connection con = DriverManager
                    .getConnection(jdbcUrl, dbUsername, dbPassword);
            // step 2: preprare statement
            String sql = "INSERT INTO taxpayers (tin,taxpayer_fname,taxpayer_lname,amount,email,phone_number) "
                    + "VALUES(?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(taxPayerObj.getTin()));
            pst.setString(2, taxPayerObj.getFirstName());
            pst.setString(3, taxPayerObj.getLastName());
            pst.setDouble(4, taxPayerObj.getAmount());
            pst.setString(5, taxPayerObj.getEmail());
            pst.setString(6, taxPayerObj.getPhoneNumber());
            // step 3: execute statement
            int rowsAffected = pst.executeUpdate();
            System.out.println("okay");
            if (rowsAffected>0) {
         
                return rowsAffected;
            }
            // step 4: close connection
            con.close();
            
        }catch(SQLException e){
           
            JOptionPane.showMessageDialog(null,"The Issue in Database Connection please "+e.getMessage());
        }
        return 0;
    }
    
    public TaxPayer searchTaxPayer(TaxPayer taxobj){
        String searchSql=" SELECT *FROM taxpayers WHERE tin=?";
        try {
            Connection conToSearch=DriverManager.getConnection(jdbcUrl,dbUsername,dbPassword);
            PreparedStatement stattoSearch=conToSearch.prepareStatement(searchSql);
            stattoSearch.setInt(1, Integer.parseInt(taxobj.getTin()));
            ResultSet res=stattoSearch.executeQuery();
            if (res.next()) {   
                taxobj.setTin(res.getString("tin"));
                taxobj.setFirstName(res.getString("taxpayer_fname"));
                taxobj.setLastName(res.getString("taxpayer_lname"));
                taxobj.setAmount(res.getDouble("amount"));
                taxobj.setEmail(res.getString("email"));
                taxobj.setPhoneNumber(res.getString("phone_number")); 
                return taxobj;
            }else{
                JOptionPane.showMessageDialog(null, " Tin not found");
            }
        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(null," Database Error"+ e.getMessage());
        }
        return null;
        
    }
    
    public int UpdateTaxPayer(TaxPayer taxUpdate){
        int rowsAffected;
        String updateSql;
        updateSql = " UPDATE  taxpayers SET taxpayer_fname=?,taxpayer_lname=?,amount=?,email=?,phone_number=? WHERE tin=?";
        try {
            try (Connection conToUpdate = DriverManager.getConnection(jdbcUrl,dbUsername,dbPassword)) {
                PreparedStatement statUpdate=conToUpdate.prepareStatement(updateSql);
                statUpdate.setString(1, taxUpdate.getFirstName());
                statUpdate.setString(2, taxUpdate.getLastName());
                statUpdate.setDouble(3,taxUpdate.getAmount());
                statUpdate.setString(4, taxUpdate.getEmail());
                statUpdate.setString(5, taxUpdate.getPhoneNumber());
                statUpdate.setInt(6, Integer.parseInt(taxUpdate.getTin()));
                rowsAffected=statUpdate.executeUpdate();
                if (rowsAffected>0) {
                    return rowsAffected;
                }
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null," Database Error Please " + e.getMessage());
        }
        return 0;
        
    }
    
    public int DeleteTaxPayer(TaxPayer taxDelet){
        String answer;
        String deleteSql;
        deleteSql=" DELETE  FROM taxpayers WHERE tin=?";
        try {
            Connection conDelete= DriverManager.getConnection(jdbcUrl,dbUsername,dbPassword);
            PreparedStatement statToDelete = conDelete.prepareStatement(deleteSql);
            statToDelete.setInt(1, Integer.parseInt(taxDelet.getTin()));
            int rowsAffected=statToDelete.executeUpdate();
            if (rowsAffected>0) {
                return rowsAffected;
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Databaze Error"+ e.getMessage());
        }
        return 0;
    }
    
    public int registerUser(User userObj){
        String registerUserSql;
        registerUserSql=" INSERT INTO users (firstname,lastname,username,password) VALUES (?,?,?,?)";
        
        try {
            Connection conToRegisterUser = DriverManager.getConnection(jdbcUrl,dbUsername,dbPassword);
            PreparedStatement statToRegisterUser = conToRegisterUser.prepareStatement(registerUserSql);
            statToRegisterUser.setString(1, userObj.getFirstName());
            statToRegisterUser.setString(2, userObj.getLastName());
            statToRegisterUser.setString(3, userObj.getUsername());
            statToRegisterUser.setString(4, userObj.getPassword());
            int rowsAffected = statToRegisterUser.executeUpdate();
            if (rowsAffected>0) {
                 return rowsAffected;
                
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Database Error"+ e.getMessage());
        }
        return 0;
    }
    
    public void Authenticate(LoginFormClass useAuth){
        String slqAuth;
        slqAuth=" SELECT username=?, password=? FROM users";
        try {
            Connection con=DriverManager.getConnection(jdbcUrl,dbUsername,dbPassword);
            PreparedStatement stat=con.prepareStatement(slqAuth);
            stat.setString(1, useAuth.getUsername());
            stat.setString(2, useAuth.getPassword());
            ResultSet res=stat.executeQuery();
            if (res.next()) {
                
                useAuth.setUsername(res.getString("username"));
                useAuth.setPassword(res.getString("password"));
                
            }
            
        } catch (SQLException e) {
        }
    }
    
    public  List<TaxPayer> getdataback(){
        List<TaxPayer> taxpay = new ArrayList<>();
        try {
            String sqlToLoadData="SELECT tin,taxpayer_fname,taxpayer_lname,amount,email,phone_number FROM taxpayers";
            Connection conToLoadData = DriverManager.getConnection(jdbcUrl,dbUsername,dbPassword);
            PreparedStatement statToLoadData = conToLoadData.prepareStatement(sqlToLoadData);
            ResultSet resToLoadData = statToLoadData.executeQuery();
            while (resToLoadData.next()) {
                TaxPayer taxpayerLoad = new TaxPayer();
                taxpayerLoad.setTin(resToLoadData.getString("tin"));
                taxpayerLoad.setFirstName(resToLoadData.getString("taxpayer_fname"));
                taxpayerLoad.setLastName(resToLoadData.getString("taxpayer_lname"));
                taxpayerLoad.setAmount(resToLoadData.getDouble("amount"));
                taxpayerLoad.setEmail(resToLoadData.getString("email"));
                taxpayerLoad.setPhoneNumber(resToLoadData.getString("phone_number"));
                taxpay.add(taxpayerLoad);
                
                
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Database Error"+ e.getMessage());
        }
        return taxpay;
    }
}
