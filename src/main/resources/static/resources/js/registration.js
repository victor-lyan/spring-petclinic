const roleSelect = document.getElementById('roleSelect');
const ownerForm = document.getElementById('ownerForm');
const vetForm = document.getElementById('vetForm');

roleSelect.addEventListener('change', (e) => {
    const val = parseInt(e.target.value);
    
    if (val === 1) {
        //OWNER
        ownerForm.style.display = 'block';
        vetForm.style.display = 'none';
    } else if (val === 2) {
        //vet
        ownerForm.style.display = 'none';
        vetForm.style.display = 'block';
    } else {
        //none
        ownerForm.style.display = 'none';
        vetForm.style.display = 'none';
    }
});
