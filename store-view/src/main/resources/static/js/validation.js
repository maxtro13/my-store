// function validateForm() {
//     let isValid = true;
//     const nameField = document.getElementById("name");
//     const priceField = document.getElementById("price");
//
//     if (nameField.value.trim() === "") {
//         alert("Поле 'Название блюда' обязательно для заполнения.");
//         nameField.focus();
//         isValid = false;
//     }
//
//     if (isNaN(parseFloat(priceField.value)) || parseFloat(priceField.value) <= 0) {
//         alert("Поле 'Цена' должно быть числом больше 0.");
//         priceField.focus();
//         isValid = false;
//     }
//
//     return isValid;
// }
//
// // Прикрепите функцию к событию отправки формы:
// const form = document.getElementById("");
// form.addEventListener("submit", function(event) {
//     if (!validateForm()) {
//         event.preventDefault(); // Предотвращает отправку формы
//     }
// });