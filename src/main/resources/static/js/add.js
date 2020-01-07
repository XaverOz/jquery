function addJSON() {
    var sendData = {};
    $('#addDiv input').each( function (index) {
        if(index == 0) {
            sendData.email = $( this ).val();
        } else if(index == 1) {
            sendData.login = $( this ).val();
        } else if(index == 2) {
            sendData.password = $( this ).val();
        }
    });
    sendData.roles = [];
    $('#roles input:checked').each( function (index) {
        sendData.roles.push({
            name: $( this ).val()
        });
    });
    fetch("./user-rest/add",
        {
            method: "POST",
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(sendData)
        })
        .then(function(res){ return res.json(); })
        .then(function(user) {
            window.location.href = "./admin";
        });
}

$( document ).ready(function() {
    fetch("./user-rest/roles",
        {
            method: "POST"
        })
        .then(function(res){ return res.json(); })
        .then(function(roles) {
            roles.forEach(function(item, i) {
                $('#roles').append(
                    $('<div>').addClass('form-check form-check-inline')
                        .append($('<input>').prop('type', 'checkbox').addClass('form-check-input').val(item.name))
                        .append($('<label>').text(item.name).addClass('form-check-label'))
                );
            });
        });
});