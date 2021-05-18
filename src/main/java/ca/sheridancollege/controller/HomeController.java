package ca.sheridancollege.controller;

import ca.sheridancollege.Database.DatabaseAccess;
import ca.sheridancollege.beans.movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    @Lazy
    private DatabaseAccess da;//connecting DB
    static ArrayList<Integer> reserved = new ArrayList<>();//Reserved seats Array List


    @GetMapping("/")
    public String goHome(HttpSession session){ // Map to landing page
        session.setMaxInactiveInterval(240); //end session after 240 sec
        return "landing.html";
    }
    @GetMapping("/user")//map to user index page
    public String goHomeUser(){
        return "/user/index.html";
    }
    @GetMapping("/login")//map to login page
    public String gologinPage(){
        return "login.html";
    }
    @GetMapping("/access-denied")//map to access denied page
    public String goAccessDenied(){
        return "/error/access-denied.html";
    }
    @GetMapping("/register")// map to register page
    public String goRegistration(){
        return "registration.html";
    }


    @PostMapping("/register") // process registration
    public String processRegistration(@RequestParam String name, @RequestParam String password){
        da.addUser(name, password);
        long userId=da.findUserAccount(name).getUserId();
        da.addRole(userId, 2);//regular user permission only
        return "redirect:/";
    }

    @GetMapping("/movie1")// map to movie page
    public String movie1(Model model){
       model.addAttribute("movie1", new movie());
        return "movie1.html";
    }
    @GetMapping("/movie2")// map to movie page
    public String movie2(Model model){
        model.addAttribute("movie1", new movie());
        return "movie2.html";
    }
    @GetMapping("/movie3")// map to movie page
    public String movie3(Model model){
        model.addAttribute("movie1", new movie());
        return "movie3.html";
    }
    @GetMapping("/movie4")// map to movie page
    public String movie4(Model model){
        model.addAttribute("movie1", new movie());
        return "movie4.html";
    }
    @GetMapping("/movie5")// map to movie page
    public String movie5(Model model){
        model.addAttribute("movie1", new movie());
        return "movie5.html";
    }

    @GetMapping("/movie6")// map to movie page
    public String movie6(Model model){
        model.addAttribute("movie1", new movie());
        return "movie6.html";
    }



    @GetMapping("/confirmation") // map to confirmation page and show a ticket
    public String goConfirm(Model model, @ModelAttribute movie movie, @RequestParam int movieId, @RequestParam String movieName, HttpSession session) throws Exception {
        movie.setMovieName(movieName);
        movie.setMovieId(movieId);
        movie.priceDiscount(movie.getDiscountCat(),movie.getMoviePrice());
        movie.checkSeat(reserved,movie.getSeatTaken());//check seat
        movie.addToAL(reserved,movie.getSeatTaken());
        movie.setAviSeats(100-reserved.size()); //reduce seats available
        if (!movie.isFlag()){ // checks if seat already taken and redirect
            return "redirect:/seatError";};
        model.addAttribute("movie1", movie.sb(movie));// show ticket
        movie.printTicket();// write ticket to a file

        da.addMov(// add ticket details to DB
                movie.getMovieId(),
                movie.getMovieName(),
                movie.getDiscountCat(),
                movie.getVisitor_name(),
                movie.getSeatTaken(),
                movie.getMTime(),
                movie.getMoviePrice(),
                movie.getAviSeats());

        session.setAttribute("visitor_name",movie.getVisitor_name()); //maintain buyer name as a session attribute
        return "confirmationPage.html";
    }

    @GetMapping("/viewTickets")// map to movie page
    public String goTickets(Model model, HttpSession session){
        model.addAttribute("movie1", new movie());
        model.addAttribute("m", da.getTickets((String)session.getAttribute("visitor_name")));
        return "viewTickets.html";
    }

    @GetMapping("/seatError")// show error if seat already taken
    public String goSeatError(Model model, @ModelAttribute movie movie){
        //model.addAttribute("movie1", new movie());
        //model.addAttribute("errorMsg", movie.seatsLeft());
        model.addAttribute("errorMsg", "Please Select a seat other than: "+reserved.toString());
        return "seatError.html";
    }

    @GetMapping("/adminpage")
    public String insertMovies(Model model, @ModelAttribute movie movie){
        model.addAttribute("m", da.getTicketsAdmin());

        System.out.println("Hello from insert movie");
        return "/admin/adminpage.html";
    }
    @GetMapping("/editTicket/{mpk}")
    public String editMovies(Model model, @PathVariable int mpk){
        movie m = da.getTicketsbyId(mpk);
        model.addAttribute("movie", m);
        System.out.println("Hello from edit movie");
        return "/admin/editTicket.html";
    }

    @GetMapping("/modify")
    public String modifyMovies(Model model, @ModelAttribute movie movie){
        da.editTickets(movie);
        System.out.println("Hello from modify movie");
        return "redirect:/adminpage";
    }
    @GetMapping("/delete/{mpk}")
    public String deleteMovies(Model model, @PathVariable int mpk){

        System.out.println("Hello from delete movie");
        da.deleteMovie(mpk);
        return "redirect:/adminpage";
    }



}
