<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Category API Test</title>
  <script>
    async function getCategories() {
      const response = await fetch('${pageContext.request.contextPath}/api/categories?action=list');
      const data = await response.json();
      console.log('Categories:', data);
      alert(JSON.stringify(data, null, 2)); // Hiển thị dữ liệu danh mục trong alert
    }

    async function getCategoryById() {
      const id = document.getElementById('categoryId').value;
      const response = await fetch(`${pageContext.request.contextPath}/api/categories?action=get&id=${id}`);
      const data = await response.json();
      console.log('Category:', data);
      alert(JSON.stringify(data, null, 2)); // Hiển thị dữ liệu danh mục trong alert
    }

    async function createCategory() {
      const name = document.getElementById('categoryName').value;
      const response = await fetch('${pageContext.request.contextPath}/api/categories?action=create', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `name=${name}`
      });
      console.log(await response.text());
      alert('Category created successfully');
    }

    async function updateCategory() {
      const id = document.getElementById('updateCategoryId').value;
      const name = document.getElementById('updateCategoryName').value;
      const response = await fetch('${pageContext.request.contextPath}/api/categories?action=update', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `id=${id}&name=${name}`
      });
      console.log(await response.text());
      alert('Category updated successfully');
    }

    async function deleteCategory() {
      const id = document.getElementById('deleteCategoryId').value;
      const response = await fetch('${pageContext.request.contextPath}/api/categories?action=delete', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `id=${id}`
      });
      console.log(await response.text());
      alert('Category deleted successfully');
    }
  </script>
</head>
<body>
<h1>Category API Test</h1>
<button onclick="getCategories()">Get All Categories</button><br>

<input type="number" id="categoryId" placeholder="Category ID">
<button onclick="getCategoryById()">Get Category by ID</button><br>

<input type="text" id="categoryName" placeholder="New Category Name">
<button onclick="createCategory()">Create Category</button><br>

<input type="number" id="updateCategoryId" placeholder="Update Category ID">
<input type="text" id="updateCategoryName" placeholder="Update Category Name">
<button onclick="updateCategory()">Update Category</button><br>

<input type="number" id="deleteCategoryId" placeholder="Delete Category ID">
<button onclick="deleteCategory()">Delete Category</button><br>
</body>
</html>
