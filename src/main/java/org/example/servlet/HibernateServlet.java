package org.example.servlet;

import org.example.dao.UserDao;
import org.example.model.User_Entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        UserDao userDAO = new UserDao(em); // Sử dụng DAO

        try {
            // Lấy danh sách người dùng từ DAO
            List<User_Entity> users = userDAO.getAllUsers();

            // Trả về JSON
            resp.setContentType("application/json");
            resp.getWriter().write(users.toString()); // Chuyển đổi thành JSON bằng thư viện như Gson hoặc Jackson
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
