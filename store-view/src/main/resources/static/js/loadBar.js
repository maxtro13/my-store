document.getElementById('createForm').addEventListener('submit', function() {
    // Блокируем кнопку отправки
    this.querySelector('button[type="submit"]').disabled = true;
    // Отображаем индикатор загрузки
    document.getElementById('loadingIndicator').style.display = 'flex';
});