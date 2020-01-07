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
    $.ajax({
        url: './user-rest/add',
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        success: function(user){
            window.location.href = "./admin";
        },
        data: JSON.stringify(sendData)
    })
}

$( document ).ready(function() {
    $.ajax({url: "./user-rest/roles", success: function(roles){
            roles.forEach(function(item, i) {
                $('#roles').append(
                    $('<div>').addClass('form-check form-check-inline')
                    .append($('<input>').prop('type', 'checkbox').addClass('form-check-input').val(item.name))
                    .append($('<label>').text(item.name).addClass('form-check-label'))
                );
            });
    }});
});