package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.RemoteEndpoint;

import domain.GroupService;
import domain.PersonService;
import opdracht1.ScoreRepository;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private PersonService personmodel = new PersonService();
	private GroupService groupmodel = new GroupService(personmodel);
	private ScoreRepository scoreRepository = new ScoreRepository();
	private controller.ControllerFactory controllerFactory = new controller.ControllerFactory();

	public Controller() {
		super();
	}

	protected void doGet(HttpServletRequest request,
						 HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request,
						  HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request,
								  HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String destination = "index.jsp";

		controller.RequestHandler handler = null;
		if (action != null) {
			try {
				handler = controllerFactory.getController(action, personmodel, groupmodel, scoreRepository);
				destination = handler.handleRequest(request, response);
			}
			catch (controller.NotAuthorizedException exc) {
				List<String> errors = new ArrayList<String>();
				errors.add(exc.getMessage());
				request.setAttribute("errors", errors);
				destination="index.jsp";
			}
		}
		if(handler instanceof controller.AsyncRequesthandler){
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(destination);
		}
		else{
			RequestDispatcher view = request.getRequestDispatcher(destination);
			view.forward(request, response);
		}

	}

}
