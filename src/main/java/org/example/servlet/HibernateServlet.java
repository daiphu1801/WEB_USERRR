package org.example.servlet;

import com.google.gson.Gson;
import org.example.dao.UserDao;
import org.example.model.User_Entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "HibernateServlet", urlPatterns = "/api/users")
public class HibernateServlet extends HttpServlet {
    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = Persistence.createEntityManagerFactory("HibernatePersistenceUnit");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManager em = emf.createEntityManager();
        UserDao userDAO = new UserDao(em);

        try {
            // Lấy danh sách người dùng từ DAO
            List<User_Entity> users = userDAO.getAllUsers();

            // Chuyển đổi danh sách người dùng sang JSON
            Gson gson = new Gson();
            String json = gson.toJson(users);

            // Trả về JSON
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(json);

        } finally {
            em.close();
        }
    }

    @Override
    public void destroy() {
        if (emf != null) {
            emf.close();
        }
    }
}
