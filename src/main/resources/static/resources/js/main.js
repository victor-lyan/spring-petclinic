const $visitBtn = $('#applyForVisitBtn');
const $visitModal = $('#visitModal');
const $addNewPetBtn = $('#addNewPetBtn');
const $petModal = $('#petModal');

$visitBtn.on('click', e => {
    let url = window.location.pathname + '/load-visit-modal';
    
    loadModalContent(url, $visitModal, saveVisit, 'Apply for a visit');
});

$addNewPetBtn.on('click', e => {
    e.preventDefault();

    loadModalContent('/pets/load-pet-modal', $petModal, savePet, 'Add new pet');
});

$(document).on('click', '.editPetBtn', e => {
    e.preventDefault();
    
    const petId = e.target.dataset['petid'];
    loadModalContent('/pets/load-pet-modal?petId=' + petId, $petModal, savePet, 'Edit pet');
});

$(document).on('click', '.deletePetBtn', e => {
    e.preventDefault();

    if (confirm('Are you sure you want to remove this pet?')) {
        const petId = e.target.dataset['petid'];
        deletePet(petId);
    }
});

function loadModalContent(url, $modal, callback, title) {
    $.ajax({
        url: url,
        method: 'GET',
        dataType: 'html'
    }).done(data => {
        $modal.find('.modal-body').html(data);
        $modal.find('.modal-title').html(title);

        $('#saveModalBtn').on('click', () => {
            callback();
        });

        $modal.modal('show');
    }).fail((jqXHR, textStatus) => {
        console.log(textStatus);
    });
}

function saveVisit() {
    let url = window.location.pathname + '/save-visit';
    let data = {};
    data.petId = $('#petId').val();
    data.visitDate = $('#visitDate').val();
    data.description = $('#description').val();
    
    sendSaveRequest(url, data, $visitModal);
}

function savePet() {
    let url = '/pets/save-pet';
    let data = {};
    data.petId = $('#id').val();
    data.name = $('#name').val();
    data.birthDate = $('#birthDate').val();
    data.typeId = $('#typeId').val();

    sendSaveRequest(url, data, $petModal, (savedElem) => {
        let found = false;
        $('#profilePetTable').find('tbody').find('tr').each((i, e) => {
            if (e.dataset['petid'] === savedElem.id.toString()) {
                found = true;
                
                let updatedRow = '';
                updatedRow += `<td>${savedElem.name}</td>`;
                updatedRow += `<td>${savedElem.type}</td>`;
                updatedRow += `<td>${savedElem.birthDate}</td>`;
                updatedRow += `<td></td>`;
                updatedRow += `<td><a href="#" class="editPetBtn" data-petid="${savedElem.id}">Edit Pet</a><br>
                <a href="#" class="deletePetBtn" data-petid="${savedElem.id}">Remove Pet</a></td>`;
                
                e.innerHTML = updatedRow;
            }
        });
        
        if (!found) {
            let newRow = `<tr data-petid="${savedElem.id}">`;
            newRow += `<td>${savedElem.name}</td>`;
            newRow += `<td>${savedElem.type}</td>`;
            newRow += `<td>${savedElem.birthDate}</td>`;
            newRow += `<td></td>`;
            newRow += `<td><a href="#" class="editPetBtn" data-petid="${savedElem.id}">Edit Pet</a><br>
                <a href="#" class="deletePetBtn" data-petid="${savedElem.id}">Remove Pet</a></td>`;
            newRow += '</tr>';
            $('#profilePetTable').find('tbody').append(newRow);
        }
    });
}

function sendSaveRequest(url, data, $modal, callback) {
    const $errorDiv = $modal.find('.invalid-feedback');
    
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(data => {
            if (data.valid) {
                $modal.modal('hide');
                showAlert(data.messages, 'success');
                if (callback !== undefined) {
                    callback(data.savedElem);
                }
            } else {
                let errors = '';
                data.messages.forEach(error => {
                    errors += '<p>' + error + '</p>';
                });
                $errorDiv.html(errors);
                $errorDiv.show();
            }
        });
}

function deletePet(petId) {
    fetch('/pets/delete-pet/' + petId, {
        method: 'POST',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify({petId: petId})
    })
        .then(response => response.json())
        .then(data => {
            if (data.valid) {
                showAlert(data.messages, 'success');

                // remove elem from the table
                $('#profilePetTable').find('tbody').find('tr').each((i, e) => {
                    if (e.dataset['petid'] === data.savedElem.id.toString()) {
                        e.remove();
                    }
                });
            } else {
                showAlert(data.messages, 'danger');
            }
        });
}

function showAlert(message, type) {
    let template = `
        <div class="alert alert-${type} alert-dismissible fade show" role="alert">
            ${message}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    `;
    
    $('.container-fluid').find('.container:first-child').prepend(template);
}
