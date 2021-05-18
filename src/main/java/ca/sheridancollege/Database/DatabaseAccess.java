package ca.sheridancollege.Database;

import ca.sheridancollege.beans.User;
import ca.sheridancollege.beans.movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class DatabaseAccess {

    @Autowired
    protected NamedParameterJdbcTemplate jdbc;


    public User findUserAccount(String userName) {

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = "SELECT * FROM sec_user where userName=:userName";
        parameters.addValue("userName", userName);
        ArrayList<User> users = (ArrayList<User>)jdbc.query(query, parameters,
                new BeanPropertyRowMapper<User>(User.class));
        if (users.size() > 0)
            return users.get(0);
        else
            return null;
    }

    public List<String> getRolesById(long userId) {
        ArrayList<String> roles = new ArrayList<String>();
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = "select user_role.userId, sec_role.roleName "
                + "FROM user_role, sec_role "
                + "WHERE user_role.roleId=sec_role.roleId "
                + "and userId=:userId";
        parameters.addValue("userId", userId);
        List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
        for (Map<String, Object> row : rows) {
            roles.add((String)row.get("roleName"));
        }
        return roles;
    }
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void addUser(String userName, String password) { //populating DB user table, name and password
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = "insert into SEC_User "
                + "(userName, encryptedPassword, ENABLED)"
                + " values (:userName, :encryptedPassword, 1)";
        parameters.addValue("userName", userName);
        parameters.addValue("encryptedPassword",
                passwordEncoder.encode(password));
        jdbc.update(query, parameters);
    }

    public void addRole(long userId, long roleId) { // populate DB tables, user id and role
        System.out.println(roleId);
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = "insert into user_role (userId, roleId)"
                + "values (:userId, :roleId);";
        parameters.addValue("userId", userId);
        parameters.addValue("roleId", roleId);
        System.out.println(roleId);
        jdbc.update(query, parameters);
    }

    public void addMov(
            int movieId,
            String movieName,
            int discountCat,
            String visitor_name,
            int seatTaken,
            String mTime,
            Double moviePrice,
            int aviSeats) { // populate DB, movies table "Tickets"
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = "insert into movie_info"
                + "(movieId, movieName,discountCat,visitor_name, seatTaken,mTime,moviePrice,aviSeats)"
                +"values(" +
                ":movieId," +
                ":movieName," +
                ":discountCat," +
                ":visitor_name," +
                ":seatTaken," +
                ":mTime," +
                ":moviePrice," +
                ":aviSeats)";
        parameters.addValue("movieId", movieId);
        parameters.addValue("movieName", movieName);
        parameters.addValue("discountCat", discountCat);
        parameters.addValue("visitor_name", visitor_name);
        parameters.addValue("seatTaken", seatTaken);
        parameters.addValue("mTime", mTime);
        parameters.addValue("moviePrice", moviePrice);
        parameters.addValue("aviSeats", aviSeats);


        jdbc.update(query, parameters);
    }


    // return tickets from DB according to name given
    public ArrayList<movie> getTickets(String  visitor_name){
        ArrayList <movie> tickets = new ArrayList <movie> ();
        String q = "SELECT * FROM movie_info WHERE visitor_name = :visitor_name";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("visitor_name", visitor_name);
        List<Map<String,Object>> rows = jdbc.queryForList(q, parameters);
        //iterate over DB table for each row and store it in ArrayList , To return movie tickets
        for (Map<String,Object> row : rows) {
            movie m = new movie();
            m.setMovieId((Integer)row.get("movieId"));
            m.setMovieName((String)(row.get("movieName")));
            m.setDiscountCat((Integer)(row.get("discountCat")));
            m.setVisitor_name((String)(row.get("visitor_name")));
            m.setSeatTaken((Integer)(row.get("seatTaken")));
            m.setMTime((String)(row.get("mTime")));
            m.setMoviePrice(((BigDecimal)(row.get("moviePrice"))).doubleValue());
            m.setAviSeats((Integer)(row.get("aviSeats")));
            tickets.add(m);
        }
        return tickets;
    }

    public ArrayList<movie> getTicketsAdmin(){
        ArrayList <movie> tickets = new ArrayList <movie> ();
        String q = "SELECT * FROM movie_info";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        //parameters.addValue("visitor_name", visitor_name);
        List<Map<String,Object>> rows = jdbc.queryForList(q, parameters);
        //iterate over DB table for each row and store it in ArrayList , To return movie tickets
        for (Map<String,Object> row : rows) {
            movie m = new movie();
            m.setMpk((Integer)row.get("mpk"));
            m.setMovieId((Integer)row.get("movieId"));
            m.setMovieName((String)(row.get("movieName")));
            m.setDiscountCat((Integer)(row.get("discountCat")));
            m.setVisitor_name((String)(row.get("visitor_name")));
            m.setSeatTaken((Integer)(row.get("seatTaken")));
            m.setMTime((String)(row.get("mTime")));
            m.setMoviePrice(((BigDecimal)(row.get("moviePrice"))).doubleValue());
            m.setAviSeats((Integer)(row.get("aviSeats")));
            tickets.add(m);
        }
        return tickets;
    }

    public movie getTicketsbyId(int mpk){
        ArrayList <movie> tickets = new ArrayList <movie> ();
        String q = "SELECT * FROM movie_info WHERE mpk =:mpk";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("mpk", mpk);
        List<Map<String,Object>> rows = jdbc.queryForList(q, parameters);
        //iterate over DB table for each row and store it in ArrayList , To return movie tickets
        for (Map<String,Object> row : rows) {
            movie m = new movie();
            m.setMpk((Integer)row.get("mpk"));
            m.setMovieId((Integer)row.get("movieId"));
            m.setMovieName((String)(row.get("movieName")));
            m.setDiscountCat((Integer)(row.get("discountCat")));
            m.setVisitor_name((String)(row.get("visitor_name")));
            m.setSeatTaken((Integer)(row.get("seatTaken")));
            m.setMTime((String)(row.get("mTime")));
            m.setMoviePrice(((BigDecimal)(row.get("moviePrice"))).doubleValue());
            m.setAviSeats((Integer)(row.get("aviSeats")));
            tickets.add(m);
        }
        if(tickets.size() > 0)
            return tickets.get(0);
        return null;
    }



    public void editTickets(movie  movie){
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String q = "UPDATE movie_info SET " +
                "movieId=:movieId, " +
                "movieName=:movieName," +
                "discountCat=:discountCat," +
                "visitor_name=:visitor_name," +
                "seatTaken=:seatTaken," +
                "mTime=:mTime," +
                "moviePrice=:moviePrice," +
                "aviSeats=:aviSeats " +
                "WHERE mpk=:mpk";
        //iterate over DB table for each row and store it in ArrayList , To return movie ticket
        System.out.println(0);
            parameters.addValue("mpk",movie.getMpk());
        System.out.println(1);
            parameters.addValue("movieId",movie.getMovieId());
        System.out.println(2);
            parameters.addValue("movieName",movie.getMovieName());
        System.out.println(3);
            parameters.addValue("discountCat",movie.getDiscountCat());
        System.out.println(4);
            parameters.addValue("visitor_name",movie.getVisitor_name());
        System.out.println(5);
            parameters.addValue("seatTaken",movie.getSeatTaken());
        System.out.println(6);
            parameters.addValue("mTime",movie.getMTime());
        System.out.println(7);
            parameters.addValue("moviePrice",movie.getMoviePrice());
        System.out.println(8);
            parameters.addValue("aviSeats",movie.getAviSeats());

        jdbc.update(q, parameters);
    }

    public void deleteMovie(int mpk){
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String q = "Delete from movie_info WHERE mpk=:mpk";
        parameters.addValue("mpk",mpk);
        jdbc.update(q, parameters);
    }

}