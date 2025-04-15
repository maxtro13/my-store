    $(document).on('click', '.add-to-cart', function() {
    const dishId = $(this).data('dish-id');
    const dishName = $(this).data('dish-name');
    const dishPrice = $(this).data('dish-price');

    // Отправка данных на бэкенд
    $.ajax({
    url: '/api/cart/add', // Укажите URL для добавления в заказ
    method: 'POST',
    contentType: 'application/json',
    data: JSON.stringify({
    id: dishId,
    name: dishName,
    price: dishPrice
}),
    success: function(response) {
    alert('Ролл добавлен в заказ!');
},
    error: function(xhr, status, error) {
    alert('Ошибка при добавлении в заказ: ' + error);
}
});
});


    // Функция для добавления товара в корзину
    function addToCart(dishId, name, price) {
        let cart = JSON.parse(localStorage.getItem('cart')) || [];

        // Проверьте, существует ли товар в корзине
        const existingItemIndex = cart.findIndex(item => item.dish_id === dishId);
        if (existingItemIndex !== -1) {
            // Если существует, увеличьте количество
            cart[existingItemIndex].quantity += 1;
        } else {
            // Если не существует, добавьте новый товар
            cart.push({
                dish_id: dishId,
                name: name,
                price: price,
                quantity: 1
            });
        }

        // Сохраните корзину в LocalStorage
        localStorage.setItem('cart', JSON.stringify(cart));
        updateCartView();
    }

    // Функция для обновления отображения корзины
    function updateCartView() {
        const cart = JSON.parse(localStorage.getItem('cart')) || [];
        const cartElement = document.getElementById('order-summary');
        cartElement.innerHTML = ''; // Очистите текущий вид

        // Добавьте элементы
        cart.forEach(item => {
            const itemElement = document.createElement('div');
            itemElement.textContent = `${item.name} x ${item.quantity} - ${item.price}₽`;
            cartElement.append(itemElement);
        });
    }

    document.addEventListener('DOMContentLoaded', (event) => {
        // Добавьте слушатели кликов для кнопок "Добавить в заказ"
        document.querySelectorAll('.add-to-cart').forEach(button => {
            button.addEventListener('click', (event) => {
                const dishId = parseInt(event.target.getAttribute('data-dish-id'));
                const name = event.target.getAttribute('data-dish-name');
                const price = parseFloat(event.target.getAttribute('data-dish-price'));
                addToCart(dishId, name, price);
            });
        });

        // Обновите отображение корзины при загрузке страницы
        updateCartView();
    });
    document.getElementById('create-order').addEventListener('click', () => {
        const cart = JSON.parse(localStorage.getItem('cart')) || [];
        const order = {
            delivery_address: 'Оборонная улица', // Или возьмите адрес откуда-то еще (например, из формы)
            order_details: cart
        };

        fetch('/api/orders', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(order)
        })
            .then(response => {
                if (response.ok) {
                    alert('Ваш заказ был успешно отправлен!');
                    localStorage.removeItem('cart'); // Очистить корзину после отправки
                    updateCartView(); // Обновить отображение корзины
                } else {
                    alert('Произошла ошибка при отправке заказа. Пожалуйста, попробуйте еще раз.');
                }
            })
            .catch(error => {
                console.error('Ошибка:', error);
            });
    });
