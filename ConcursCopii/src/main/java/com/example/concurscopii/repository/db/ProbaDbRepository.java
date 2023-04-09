package com.example.concurscopii.repository.db;

import com.example.concurscopii.domain.Proba;
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

public class ProbaDbRepository implements Repository<Long, Proba> {

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public ProbaDbRepository(Properties props) {
        logger.info("Initializing ProbaDbRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public void add(Proba elem) {

        logger.traceEntry("saving task {} ", elem);
        Connection con= dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into probe (nume, varsta_min, varsta_max) values (?, ?, ?)")){
            preStmt.setString(1, elem.getNume());
            preStmt.setInt(2, elem.getVarsta_min());
            preStmt.setInt(3, elem.getVarsta_max());
            int result = preStmt.executeUpdate();
            logger.trace("Saved {} instances", result);
        }catch(SQLException ex){
            logger.error(ex);
            System.err.println("Error DB"+ex);
        }
        logger.traceExit();
    }

    @Override
    public Proba delete(Long aLong) {
        return null;
    }

    @Override
    public void update(Long id, Proba elem) {

        logger.traceEntry("Updating proba with id {}", id);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement stmt = con.prepareStatement("UPDATE probe SET nume = ?, varsta_min = ?, varsta_max = ? WHERE id = ?")) {
            stmt.setString(1, elem.getNume());
            stmt.setInt(2, elem.getVarsta_min());
            stmt.setInt(3, elem.getVarsta_max());
            stmt.setLong(4, id);
            int result = stmt.executeUpdate();
            logger.trace("Updated {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
    }

    @Override
    public List<Proba> getAllAsList() {
        Iterable<Proba> iterable = findAll();
        List<Proba> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    @Override
    public Proba findOne(Long id) {

        Proba proba = null;

        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select * from probe where id = ?")){
            preStmt.setLong(1, id);
            try(ResultSet result=preStmt.executeQuery()){
                if(result.next()){
                    String nume=result.getString("nume");
                    int varsta_min=result.getInt("varsta_min");
                    int varsta_max=result.getInt("varsta_max");
                    proba = new Proba(nume,varsta_min,varsta_max);
                    proba.setID(id);
                    return proba;
                }
            }
        } catch (SQLException e){
            logger.error(e);
            System.err.println("Error DB"+e);
        }
        logger.traceExit(proba);
        return null;
    }


    @Override
    public Iterable<Proba> findAll() {

        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Proba> probe = new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from probe")){
            try(ResultSet result=preStmt.executeQuery()){
                while(result.next()){
                    Long id = result.getLong("id");
                    String nume=result.getString("nume");
                    int varsta_min=result.getInt("varsta_min");
                    int varsta_max=result.getInt("varsta_max");
                    Proba proba = new Proba(nume,varsta_min,varsta_max);
                    proba.setID(id);
                    probe.add(proba);
                }
            }
        } catch (SQLException e){
            logger.error(e);
            System.err.println("Error DB"+e);
        }
        logger.traceExit(probe);
        return probe;
    }
}
