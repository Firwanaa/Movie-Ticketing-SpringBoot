package ca.sheridancollege.beans;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import ca.sheridancollege.Database.DatabaseAccess;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


//Beans low: No Args constructor
@NoArgsConstructor
@AllArgsConstructor
@Data

public class movie implements Serializable { //Beans low: Implement Serializable
    private static final long serialVersionUID = -9032819894822476151L; //Beans low: All data are private
    private Integer mpk;
    private int movieId;
    private String movieName;
    private Double moviePrice = 15.00;
    private int seatTaken;
    private String visitor_name;
    private int discountCat;
    private String mTime;
    private int aviSeats;
    private StringBuilder strb = new StringBuilder();
    private String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    public StringBuilder sb(movie m) { //ticket in string object
        strb.append("++++++++++++++++++"+ "\r\n")
                .append("  Movie Ticket"+ "\r\n")
                .append("++++++++++++++++++"+ "\r\n")
                .append("Date: "+date+ "\r\n")
                .append("++++++++++++++++++"+ "\r\n")
                .append("Movie ID: " + getMovieId() + "\r\n")
                .append("------------------"+ "\r\n")
                .append("Movie: " + getMovieName() + "\r\n")
                .append("------------------"+ "\r\n")
                .append("Price: " + getMoviePrice() + "\r\n")
                .append("Seat: " + getSeatTaken()+ "\r\n")
                .append("------------------"+ "\r\n")
                //.append("Number of Visitors: "+numVisitors+"\r\n")
                //.append("------------------"+ "\r\n")
                .append("Companion : " + getVisitor_name() + "\r\n")
                .append("------------------"+ "\r\n")
                .append("Movie Time: " + getMTime() + "\r\n")
                .append("Available Seats: " + getAviSeats());
        ;
        return strb;
    }

    public double priceDiscount(int cat,double price){ // apply discount according to category
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        switch(cat){
            case 1: setMoviePrice(10.00);
            case 2: setMoviePrice(8.00);
            case 3:setMoviePrice(5.00);
        }
        // for users
        if (authentication.getName().equals("anonymousUser")) {
            System.out.println(" not a user");
        } else {
            setMoviePrice(moviePrice - (moviePrice * 0.2));
            //setMoviePrice(moviePrice*reserved.size());
        }

        return moviePrice;
    }

    private boolean flag = true; //to prevent selecting same seat twice
    public boolean checkSeat(ArrayList<Integer> a, int x) {
        //int[] cc = ((int)a).toArray();
        for (Integer i: a) {
            if (a.contains((Integer)x)) {
                System.out.println( "from ceckseat  "+a);
                System.out.println("from ceckseat "+x);
                flag = false;

                System.out.println(x);
                break;
            } else {
                flag = true;
                //a.add(x);
            }
        }
        return flag;
    }
    public void addToAL(ArrayList<Integer> a, int x){
        if(flag){
            a.add(x);
        }
    }
    private static int fileNum;// to number "tickets" files created
    public void printTicket() throws IOException { //print tickets to file > filename + num+ name+ moviename + date
        File file = new File("C:/receipts/ticket"+"-"+fileNum+"-"+getVisitor_name()+"-"+getMovieName()+"-"+getDate()+".txt");
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(strb.toString());
            fileNum++;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }
    }

    //Sets ticket details , show it and save it to DB
//    public String saveMovieToDB(Model model, @ModelAttribute movie movie, ArrayList reserved, DatabaseAccess da, @RequestParam int movieId, @RequestParam String movieName) throws IOException {
//        movie.setMovieName(movieName);
//        movie.setMovieId(movieId);
//        movie.priceDiscount(movie.getDiscountCat(),movie.getMoviePrice());
//        movie.checkSeat(reserved,movie.getSeatTaken());
//        movie.addToAL(reserved,movie.getSeatTaken());
//        movie.setAviSeats(100-reserved.size()); //reduce seats available
//        if (!movie.isFlag()){ // checks if seat already taken
//            return "redirect:/seatError";};
//        model.addAttribute("movie1", movie.sb(movie));// show ticket
//        movie.printTicket();// write ticket to a file
//
//        da.addMov(// add ticket details to DB
//                movie.getMovieId(),
//                movie.getMovieName(),
//                movie.getDiscountCat(),
//                movie.getVisitor_name(),
//                movie.getSeatTaken(),
//                movie.getMTime(),
//                movie.getMoviePrice(),
//                movie.getAviSeats());
//       return null;
//    }


}

