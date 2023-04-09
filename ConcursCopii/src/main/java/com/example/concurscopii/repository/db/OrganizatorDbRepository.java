package com.example.concurscopii.repository.db;

import com.example.concurscopii.domain.Organizator;
import com.example.concurscopii.repository.Repository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class OrganizatorDbRepository implements Repository<String, Organizator> {

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public OrganizatorDbRepository(Properties props) {
        logger.info("Initializing OrganizatorDbRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public void add(Organizator elem) {

        logger.traceEntry("saving task {} ", elem);
        Connection con= dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into organizatori (username, parola) values (?, ?)")){
            preStmt.setString(1, elem.getUsername());
            preStmt.setString(2, elem.getParola());
            int result = preStmt.executeUpdate();
            logger.trace("Saved {} instances", result);
        }catch(SQLException ex){
            logger.error(ex);
            System.err.println("Error DB"+ex);
        }
        logger.traceExit();
    }

    @Override
    public Organizator delete(String aLong) {
        return null;
    }

    @Override
    public void update(String username, Organizator elem) {
        logger.traceEntry("Updating client with id {}", username);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement stmt = con.prepareStatement("UPDATE organizatori SET parola = ? WHERE username = ?")) {
            stmt.setString(1, elem.getParola());
            stmt.setString(2, username);
            int result = stmt.executeUpdate();
            logger.trace("Updated {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
    }

    @Override
    public List<Organizator> getAllAsList() {
        Iterable<Organizator> iterable = findAll();
        List<Organizator> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    @Override
    public Organizator findOne(String username) {
        Organizator user = null;

        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select * from organizatori where username = ?")){
            preStmt.setString(1, username);
            try(ResultSet result=preStmt.executeQuery()){
                if(result.next()){
                    String parola=result.getString("parola");

                    user = new Organizator(username, parola);

                    return user;
                }
            }
        } catch (SQLException e){
            logger.error(e);
            System.err.println("Error DB"+e);
        }
        logger.traceExit(user);
        return null;
    }

    @Override
    public Iterable<Organizator> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Organizator> users = new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from organizatori")){
            try(ResultSet result=preStmt.executeQuery()){
                while(result.next()){
                    int id = result.getInt("id");
                    String username=result.getString("username");
                    String parola=result.getString("parola");

                    Organizator user = new Organizator(username, parola);
                    users.add(user);
                }
            }
        } catch (SQLException e){
            logger.error(e);
            System.err.println("Error DB"+e);
        }
        logger.traceExit(users);
        return users;
    }

}