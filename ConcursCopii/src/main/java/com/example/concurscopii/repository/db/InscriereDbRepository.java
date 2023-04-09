package com.example.concurscopii.repository.db;

import com.example.concurscopii.domain.Inscriere;
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

public class InscriereDbRepository implements Repository<Long, Inscriere> {

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public InscriereDbRepository(Properties props) {
        logger.info("Initializing InscriereDbRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public void add(Inscriere elem){

        logger.traceEntry("saving task {} ", elem);
        Connection con= dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into inscrieri (idproba, idcopil) values (?, ?)")){
            preStmt.setLong(1, elem.getIdProba());
            preStmt.setLong(2, elem.getIdCopil());
            int result = preStmt.executeUpdate();
            logger.trace("Saved {} instances", result);
        }catch(SQLException ex){
            logger.error(ex);
            System.err.println("Error DB"+ex);
        }
        logger.traceExit();
    }

    @Override
    public Inscriere delete(Long aLong) {
        return null;
    }

    @Override
    public void update(Long id, Inscriere elem) {
        logger.traceEntry("Updating inscriere with id {}", id);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement stmt = con.prepareStatement("UPDATE inscrieri SET idproba = ?, idcopil = ? WHERE id = ?")) {
            stmt.setLong(1, elem.getIdProba());
            stmt.setLong(2, elem.getIdCopil());
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
    public List<Inscriere> getAllAsList() {
        Iterable<Inscriere> iterable = findAll();
        List<Inscriere> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    @Override
    public Inscriere findOne(Long id) {
        Inscriere inscriere = null;

        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select * from inscrieri where id = ?")){
            preStmt.setLong(1, id);
            try(ResultSet result=preStmt.executeQuery()){
                if(result.next()){
                    Long idProba=result.getLong("idproba");
                    Long idCopil=result.getLong("idcopil");

                    inscriere = new Inscriere(idProba, idCopil);
                    inscriere.setID(id);
                    return inscriere;
                }
            }
        } catch (SQLException e){
            logger.error(e);
            System.err.println("Error DB"+e);
        }
        logger.traceExit(inscriere);
        return null;
    }

    @Override
    public Iterable<Inscriere> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Inscriere> inscrieri = new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from inscrieri")){
            try(ResultSet result=preStmt.executeQuery()){
                while(result.next()){
                    Long id = result.getLong("id");
                    Long idProba=result.getLong("idproba");
                    Long idCopil=result.getLong("idcopil");
                    Inscriere inscriere = new Inscriere(idCopil,idProba);
                    inscriere.setID(id);
                    inscrieri.add(inscriere);
                }
            }
        } catch (SQLException e){
            logger.error(e);
            System.err.println("Error DB"+e);
        }
        logger.traceExit(inscrieri);
        return inscrieri;
    }
}