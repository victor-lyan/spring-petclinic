const $visitBtn = $('#applyForVisitBtn');
const $visitModal = $('#visitModal');

$visitBtn.on('click', e => {
    let url = window.location.pathname + '/load-visit-modal';
    
    $.ajax({
        url: url,
        method: 'GET',
        dataType: 'html'
    }).done(data => {
        $visitModal.find('.modal-body').html(data);
        
        /*$(function() {
            $('#visitDateBlock').datetimepicker();
        });*/
        $('#saveVisitBtn').on('click', (e) => {
            saveVisit();
        });
        
        $visitModal.modal('show');
    }).fail((jqXHR, textStatus) => {
        console.log(textStatus);
    });
});

function saveVisit() {
    let url = window.location.pathname + '/save-visit';
    const $errorDiv = $visitModal.find('.invalid-feedback');
    let data = {};
    data.petId = $('#petId').val();
    data.visitDate = $('#visitDate').val();
    data.description = $('#description').val();
    
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
                $visitModal.modal('hide');
                showAlert(data.messages, 'success');
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
