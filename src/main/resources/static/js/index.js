
document.addEventListener("DOMContentLoaded", function () {
    const todoInput = document.getElementById("todo-input");
    const todoList = document.getElementById("todo-list");

    // 저장된 TODO 목록 불러오기
    let todos = JSON.parse(localStorage.getItem("todos")) || [];

    // 화면에 TODO 렌더링 함수
    function renderTodos() {
        todoList.innerHTML = ""; // 초기화
        todos.forEach((todo, index) => {
            const li = document.createElement("li");
            li.className = "list-group-item d-flex justify-content-between align-items-center";
            li.textContent = todo;

            const deleteBtn = document.createElement("button");
            deleteBtn.innerHTML = "&times;";
            deleteBtn.className = "btn btn-sm btn-danger ms-2";
            deleteBtn.onclick = function () {
                todos.splice(index, 1); // 배열에서 삭제
                localStorage.setItem("todos", JSON.stringify(todos)); // 저장
                renderTodos(); // 다시 렌더링
            };

            li.appendChild(deleteBtn);
            todoList.appendChild(li);
        });
    }

    renderTodos(); // 처음 페이지 진입 시 불러오기

    // Enter 키로 TODO 추가
    todoInput.addEventListener("keydown", function (e) {
        if (e.key === "Enter" && todoInput.value.trim() !== "") {
            e.preventDefault();
            todos.push(todoInput.value.trim());
            localStorage.setItem("todos", JSON.stringify(todos)); // 저장
            renderTodos(); // 다시 렌더링
            todoInput.value = "";
        }
    });
});