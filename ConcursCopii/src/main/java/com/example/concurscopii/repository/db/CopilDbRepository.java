package com.example.concurscopii.repository.db;

import com.example.concurscopii.domain.Copil;
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

public class CopilDbRepository implements Repository<Long, Copil> {

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public CopilDbRepository(Properties props) {
        logger.info("Initializing CopilDbRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public void add(Copil elem){
        logger.traceEntry("saving task {} ", elem);
        Connection con= dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into copii (nume, varsta) values (?, ?)")){
            preStmt.setString(1, elem.getNume());
            preStmt.setInt(2, elem.getVarsta());
            int result = preStmt.executeUpdate();
            logger.trace("Saved {} instances", result);
        }catch(SQLException ex){
            logger.error(ex);
            System.err.println("Error DB"+ex);
        }
        logger.traceExit();
    }

    @Override
    public Copil delete(Long aLong) {
        return null;
    }

    @Override
    public void update(Long id, Copil elem) {
        logger.traceEntry("Updating client with id {}", id);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement stmt = con.prepareStatement("UPDATE copii SET nume = ?, varsta = ? WHERE id = ?")) {
            stmt.setString(1, elem.getNume());
            stmt.setInt(2, elem.getVarsta());
            stmt.setLong(3, id);
            int result = stmt.executeUpdate();
            logger.trace("Updated {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
    }

    @Override
    public List<Copil> getAllAsList() {
        Iterable<Copil> iterable = findAll();
        List<Copil> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    @Override
    public Copil findOne(Long id) {
        Copil copil = null;

        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select * from copii where id = ?")){
            preStmt.setLong(1, id);
            try(ResultSet result=preStmt.executeQuery()){
                if(result.next()){
                    String nume=result.getString("nume");
                    Integer varsta=result.getInt("varsta");

                    copil = new Copil(nume, varsta);
                    copil.setID(id);
                    return copil;
                }
            }
        } catch (SQLException e){
            logger.error(e);
            System.err.println("Error DB"+e);
        }
        logger.traceExit(copil);
        return null;
    }

    @Override
    public Iterable<Copil> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Copil> copii = new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from copii")){
            try(ResultSet result=preStmt.executeQuery()){
                while(result.next()){
                    Long id = result.getLong("id");
                    String nume=result.getString("nume");
                    Integer varsta=result.getInt("varsta");
                    Copil client = new Copil(nume, varsta);
                    client.setID(id);
                    copii.add(client);
                }
            }
        } catch (SQLException e){
            logger.error(e);
            System.err.println("Error DB"+e);
        }
        logger.traceExit(copii);
        return copii;
    }
}