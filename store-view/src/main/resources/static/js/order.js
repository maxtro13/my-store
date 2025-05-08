//     $(document).on('click', '.add-to-cart', function() {
//     const dishId = $(this).data('dish-id');
//     const dishName = $(this).data('dish-name');
//     const dishPrice = $(this).data('dish-price');
//
//     // Отправка данных на бэкенд
//     $.ajax({
//     url: '/api/cart/add', // Укажите URL для добавления в заказ
//     method: 'POST',
//     contentType: 'application/json',
//     data: JSON.stringify({
//     id: dishId,
//     name: dishName,
//     price: dishPrice
// }),
//     success: function(response) {
//     alert('Ролл добавлен в заказ!');
// },
//     error: function(xhr, status, error) {
//     alert('Ошибка при добавлении в заказ: ' + error);
// }
// });
// });
//
//
//     // Функция для добавления товара в корзину
//     function addToCart(dishId, name, price) {
//         let cart = JSON.parse(localStorage.getItem('cart')) || [];
//
//         // Проверьте, существует ли товар в корзине
//         const existingItemIndex = cart.findIndex(item => item.dish_id === dishId);
//         if (existingItemIndex !== -1) {
//             // Если существует, увеличьте количество
//             cart[existingItemIndex].quantity += 1;
//         } else {
//             // Если не существует, добавьте новый товар
//             cart.push({
//                 dish_id: dishId,
//                 name: name,
//                 price: price,
//                 quantity: 1
//             });
//         }
//
//         // Сохраните корзину в LocalStorage
//         localStorage.setItem('cart', JSON.stringify(cart));
//         updateCartView();
//     }
//
//     // Функция для обновления отображения корзины
//     function updateCartView() {
//         const cart = JSON.parse(localStorage.getItem('cart')) || [];
//         const cartElement = document.getElementById('order-summary');
//         cartElement.innerHTML = ''; // Очистите текущий вид
//
//         // Добавьте элементы
//         cart.forEach(item => {
//             const itemElement = document.createElement('div');
//             itemElement.textContent = `${item.name} x ${item.quantity} - ${item.price}₽`;
//             cartElement.append(itemElement);
//         });
//     }
//
//     document.addEventListener('DOMContentLoaded', (event) => {
//         // Добавьте слушатели кликов для кнопок "Добавить в заказ"
//         document.querySelectorAll('.add-to-cart').forEach(button => {
//             button.addEventListener('click', (event) => {
//                 const dishId = parseInt(event.target.getAttribute('data-dish-id'));
//                 const name = event.target.getAttribute('data-dish-name');
//                 const price = parseFloat(event.target.getAttribute('data-dish-price'));
//                 addToCart(dishId, name, price);
//             });
//         });
//
//         // Обновите отображение корзины при загрузке страницы
//         updateCartView();
//     });
//     document.getElementById('create-order').addEventListener('click', () => {
//         const cart = JSON.parse(localStorage.getItem('cart')) || [];
//         const order = {
//             delivery_address: 'Оборонная улица',
//             order_details: cart
//         };
//
//         fetch('/api/orders', {
//             method: 'POST',
//             headers: {
//                 'Content-Type': 'application/json'
//             },
//             body: JSON.stringify(order)
//         })
//             .then(response => {
//                 if (response.ok) {
//                     alert('Ваш заказ был успешно отправлен!');
//                     localStorage.removeItem('cart');
//                     updateCartView();
//                 } else {
//                     alert('Произошла ошибка при отправке заказа. Пожалуйста, попробуйте еще раз.');
//                 }
//             })
//             .catch(error => {
//                 console.error('Ошибка:', error);
//             });
//     });
    $(document).ready(function() {
        let cart = [];

        // Инициализация корзины из localStorage
        if (localStorage.getItem('cart')) {
            cart = JSON.parse(localStorage.getItem('cart'));
            updateCart();
        }

        // Обработчик добавления в корзину
        $('.add-to-cart').click(function() {
            const dishId = $(this).data('dish-id');
            const dishName = $(this).data('dish-name');
            let dishPrice = $(this).data('dish-price');

            // Преобразуем цену в число, если нужно
            if (typeof dishPrice === 'string') {
                dishPrice = parseFloat(dishPrice.replace(/[^\d.]/g, ''));
            }

            // Проверяем, что цена - валидное число
            if (isNaN(dishPrice)) {
                console.error('Invalid price:', $(this).data('dish-price'));
                showAlert('Ошибка: некорректная цена товара', 'danger');
                return;
            }

            // Проверяем, есть ли уже такой товар в корзине
            const existingItem = cart.find(item => item.id === dishId);

            if (existingItem) {
                existingItem.quantity += 1;
            } else {
                cart.push({
                    id: dishId,
                    name: dishName,
                    price: dishPrice,
                    quantity: 1
                });
            }

            updateCart();
            saveCart();
            $(this).closest('.modal').modal('hide');
            showAlert('Товар добавлен в корзину', 'success');
        });

        // Открытие модального окна корзины
        $('#cart-icon').click(function() {
            $('#cartModal').modal('show');
        });

        // Обработчик подтверждения заказа
        $('#confirm-order').click(function() {
            if (cart.length === 0) {
                showAlert('Корзина пуста', 'danger');
                return;
            }

            if (!validateOrderForm()) {
                return;
            }

            // Подготовка данных для отправки
            const orderData = {
                deliveryAddress: $('#customer-address').val(),
                orderDetails: cart.map(item => ({
                    dishId: item.id,
                    name: item.name,
                    price: item.price,
                    quantity: item.quantity
                }))
            };

            // Отправка заказа на сервер
            sendOrder(orderData);
        });

        // Функция отправки заказа
        function sendOrder(orderData) {
            $('#confirm-order').prop('disabled', true).text('Оформление...');

            $.ajax({
                url: '/store-api/v1/orders/create',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(orderData),
                success: function(response) {
                    $('#cartModal').modal('hide');
                    showAlert('Заказ успешно оформлен! Номер заказа: ' + response.orderId, 'success');

                    // Очищаем корзину
                    cart = [];
                    updateCart();
                    saveCart();
                    $('#order-form')[0].reset();
                },
                error: function(xhr) {
                    let errorMessage = 'Ошибка при оформлении заказа';
                    if (xhr.responseJSON && xhr.responseJSON.message) {
                        errorMessage = xhr.responseJSON.message;
                    }
                    showAlert(errorMessage, 'danger');
                },
                complete: function() {
                    $('#confirm-order').prop('disabled', false).text('Подтвердить заказ');
                }
            });
        }

        // Остальные функции остаются без изменений
        function updateCart() {
            const cartCount = cart.reduce((sum, item) => sum + item.quantity, 0);
            $('#cart-count').text(cartCount);

            const cartItemsContainer = $('#cart-items');
            cartItemsContainer.empty();

            if (cart.length === 0) {
                cartItemsContainer.append('<p>Корзина пуста</p>');
                $('#cart-total').text('0₽');
                return;
            }

            cart.forEach(item => {
                // Преобразуем цену в число, если она передана как строка
                const price = typeof item.price === 'string' ?
                    parseFloat(item.price.replace(/[^\d.]/g, '')) :
                    item.price;
                const itemTotal = price * item.quantity;

                const itemElement = $(`
            <div class="cart-item" data-id="${item.id}">
                <div>
                    <h6>${item.name}</h6>
                    <span>${price}₽ × ${item.quantity} = ${itemTotal}₽</span>
                </div>
                <div class="cart-item-controls">
                    <button class="btn btn-sm btn-outline-secondary decrease-item">-</button>
                    <span class="cart-item-quantity">${item.quantity}</span>
                    <button class="btn btn-sm btn-outline-secondary increase-item">+</button>
                    <span class="remove-item">&times;</span>
                </div>
            </div>
        `);

                cartItemsContainer.append(itemElement);
            });

            const total = cart.reduce((sum, item) => {
                const price = typeof item.price === 'string' ?
                    parseFloat(item.price.replace(/[^\d.]/g, '')) :
                    item.price;
                return sum + (price * item.quantity);
            }, 0);

            $('#cart-total').text(`${total.toFixed(2)}₽`);
        }


        function saveCart() {
            localStorage.setItem('cart', JSON.stringify(cart));
        }

        function calculateTotal() {
            return cart.reduce((sum, item) => sum + (item.price * item.quantity), 0);
        }

        function validateOrderForm() {
            let isValid = true;

            if ($('#customer-name').val().trim() === '') {
                showAlert('Пожалуйста, введите ваше имя', 'danger');
                isValid = false;
            }

            if ($('#customer-phone').val().trim() === '') {
                showAlert('Пожалуйста, введите ваш телефон', 'danger');
                isValid = false;
            }

            if ($('#customer-address').val().trim() === '') {
                showAlert('Пожалуйста, введите адрес доставки', 'danger');
                isValid = false;
            }

            return isValid;
        }

        function showAlert(message, type) {
            const alert = $(`<div class="alert alert-${type} alert-dismissible fade show" role="alert">
            ${message}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>`);

            $('body').append(alert);

            setTimeout(() => {
                alert.alert('close');
            }, 3000);
        }

        $(document).on('click', '.increase-item', function() {
            const itemId = $(this).closest('.cart-item').data('id');
            const item = cart.find(item => item.id === itemId);

            if (item) {
                item.quantity += 1;
                updateCart();
                saveCart();
            }
        });

        $(document).on('click', '.decrease-item', function() {
            const itemId = $(this).closest('.cart-item').data('id');
            const item = cart.find(item => item.id === itemId);

            if (item && item.quantity > 1) {
                item.quantity -= 1;
                updateCart();
                saveCart();
            }
        });

        $(document).on('click', '.remove-item', function() {
            const itemId = $(this).closest('.cart-item').data('id');
            cart = cart.filter(item => item.id !== itemId);
            updateCart();
            saveCart();
        });
    });