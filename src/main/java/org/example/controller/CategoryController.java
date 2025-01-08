package org.example.controller;

import org.example.model.Category_Entity;
import org.example.service.CategoryService;

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

@WebServlet("/api/categories")
public class CategoryController extends HttpServlet {
    private EntityManagerFactory emf;
    private CategoryService categoryService;

    @Override
    public void init() throws ServletException {
        // Khởi tạo EntityManagerFactory một lần
        emf = Persistence.createEntityManagerFactory("HibernatePersistenceUnit");
    }

    @Override
    public void destroy() {
        // Đóng EntityManagerFactory khi servlet ngừng hoạt động
        if (emf != null) {
            emf.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (EntityManager em = emf.createEntityManager()) { // Sử dụng try-with-resources cho EntityManager
            categoryService = new CategoryService(em);
            String action = request.getParameter("action");
            if (action == null) {
                action = "list";
            }

            switch (action) {
                case "list":
                    listCategories(request, response, em);
                    break;
                case "get":
                    getCategoryById(request, response, em);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (EntityManager em = emf.createEntityManager()) { // Sử dụng try-with-resources cho EntityManager
            categoryService = new CategoryService(em);
            String action = request.getParameter("action");

            switch (action) {
                case "create":
                    createCategory(request, response, em);
                    break;
                case "update":
                    updateCategory(request, response, em);
                    break;
                case "delete":
                    deleteCategory(request, response, em);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        }
    }

    private void listCategories(HttpServletRequest request, HttpServletResponse response, EntityManager em) throws IOException {
        try {
            List<Category_Entity> categories = categoryService.getFilteredCategories(
                    null,
                    request.getParameter("sortField"),
                    request.getParameter("sortOrder")
            );
            response.setContentType("application/json");
            response.getWriter().write(toJson(categories));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void getCategoryById(HttpServletRequest request, HttpServletResponse response, EntityManager em) throws IOException {
        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            Category_Entity category = categoryService.getFilteredCategories(new Category_Entity(id, null), null, null)
                    .stream()
                    .findFirst()
                    .orElse(null);
            if (category == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Category not found");
                return;
            }
            response.setContentType("application/json");
            response.getWriter().write(toJson(category));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void createCategory(HttpServletRequest request, HttpServletResponse response, EntityManager em) throws IOException {
        try {
            String name = request.getParameter("name");
            Category_Entity category = new Category_Entity(name);
            categoryService.createNewCategory(category);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("Category created successfully");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response, EntityManager em) throws IOException {
        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            Category_Entity category = new Category_Entity(id, name);
            categoryService.updateCategory(category);
            response.getWriter().write("Category updated successfully");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response, EntityManager em) throws IOException {
        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            categoryService.deleteCategory(id);
            response.getWriter().write("Category deleted successfully");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private String toJson(Object obj) {
        // Chuyển đổi đối tượng thành JSON (sử dụng thư viện Jackson hoặc Gson trong sản phẩm thực tế)
        return obj.toString(); // Thay thế bằng phương pháp chuyển đổi JSON thực sự
    }
}
