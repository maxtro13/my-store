document.getElementById('createForm').addEventListener('submit', function() {
    this.querySelector('button[type="submit"]').disabled = true;
    document.getElementById('loadingIndicator').style.display = 'flex';
});