package opdracht1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/ConsumptieServletTeam1")
public class ConsumptieServletTeam1 extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ConsumptieRepositoryTeam1 consumptieRepository;
    // test

    public ConsumptieServletTeam1(){
        consumptieRepository = new ConsumptieRepositoryTeam1();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<ConsumptieTeam1> consumpties = consumptieRepository.getAllConsumpties();
        String consumptieJSON = this.toJSON(consumpties);
        response.setContentType("application/json");
        response.getWriter().write(consumptieJSON);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { /*report an error*/ }
        Gson g = new Gson();
        ConsumptieTeam1 c = null;
        try {
            c =  g.fromJson(jb.toString(), ConsumptieTeam1.class);
        } catch (Exception e) {
            // crash and burn
            throw new IOException("Error parsing JSON request string");
        }
        if (c != null){
            consumptieRepository.addConsumptie(c);
        }
    }

    private String toJSON (ArrayList<ConsumptieTeam1> consumpties) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(consumpties);
    }
}
