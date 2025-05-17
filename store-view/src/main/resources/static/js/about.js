document.addEventListener('DOMContentLoaded', function() {
    const backButton = document.getElementById('backToMain');

    backButton.addEventListener('click', function(e) {
        e.preventDefault();

        fetch('main.html')
            .then(response => {
                if (response.ok) {
                    window.location.href = 'main.html';
                } else {
                    window.location.href = '/';
                }
            })
            .catch(() => {
                window.location.href = '/';
            });
    });

    backButton.addEventListener('mouseenter', function() {
        this.style.transform = 'translateY(-3px)';
        this.style.boxShadow = '0 6px 12px rgba(0, 0, 0, 0.3)';
    });

    backButton.addEventListener('mouseleave', function() {
        this.style.transform = '';
        this.style.boxShadow = '0 4px 8px rgba(0, 0, 0, 0.2)';
    });
});