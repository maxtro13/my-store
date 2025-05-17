
    $(document).ready(function() {
        let cart = [];

        if (localStorage.getItem('cart')) {
            cart = JSON.parse(localStorage.getItem('cart'));
            updateCart();
        }

        $('.add-to-cart').click(function() {
            const dishId = $(this).data('dish-id');
            const dishName = $(this).data('dish-name');
            let dishPrice = $(this).data('dish-price');

            if (typeof dishPrice === 'string') {
                dishPrice = parseFloat(dishPrice.replace(/[^\d.]/g, ''));
            }

            if (isNaN(dishPrice)) {
                console.error('Invalid price:', $(this).data('dish-price'));
                showAlert('Ошибка: некорректная цена товара', 'danger');
                return;
            }

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

        $('#cart-icon').click(function() {
            $('#cartModal').modal('show');
        });

        $('#confirm-order').click(function() {
            if (cart.length === 0) {
                showAlert('Корзина пуста', 'danger');
                return;
            }

            if (!validateOrderForm()) {
                return;
            }

            const orderData = {
                deliveryAddress: $('#customer-address').val(),
                orderDetails: cart.map(item => ({
                    dishId: item.id,
                    name: item.name,
                    price: item.price,
                    quantity: item.quantity
                }))
            };

            sendOrder(orderData);
        });

        function sendOrder(orderData) {
            $('#confirm-order').prop('disabled', true).text('Оформление...');

            $.ajax({
                url: 'http://localhost:8081/store-api/v1/orders/create',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(orderData),
                success: function(response) {
                    $('#cartModal').modal('hide');
                    showAlert('Заказ успешно оформлен! Номер заказа: ' + response.orderId, 'success');

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
        <button class="remove-item-btn" title="Удалить">🗑️</button>
    </div>
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

        $(document).on('click', '.remove-item-btn', function() {
            const itemId = $(this).closest('.cart-item').data('id');
            cart = cart.filter(item => item.id !== itemId);
            updateCart();
            saveCart();
            showAlert('Товар удален из корзины', 'warning');
        });
    });