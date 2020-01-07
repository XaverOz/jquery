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
    fetch("./user-rest/users",
        {
            method: "POST"
        })
        .then(function(res){ return res.json(); })
        .then(function(users) {
            users.forEach(function(item, i) {
                var roles = "";
                item.roles.forEach(function(itemRole, j) {
                    roles += itemRole.name + ";";
                });
                $('#users tbody:last-child').append($('<tr>')
                    .append($('<td>').append(item.id))
                    .append($('<td>').append(roles))
                    .append($('<td>').append(item.login))
                    .append($('<td>').append(item.password))
                    .append($('<td>').append(item.email))
                    .append($('<td>')
                        .append(
                            $('<button>').text("Edit").addClass("btn btn-primary").click({id: item.id}, clickFunction)
                        )
                    )
                );
            });
        });
});

function clickFunction(event){
    fetch("./user-rest/user/" + event.data.id ,
        {
            method: "POST"
        })
        .then(function(res){ return res.json(); })
        .then(function(user) {
            $('#modalTitle').text("Edit user " + user.login);
            $('#modalDiv input').each( function (index) {
                if(index == 0) {
                    $( this ).val(user.id);
                } else if(index == 1) {
                    $( this ).val(user.email);
                } else if(index == 2) {
                    $( this ).val(user.login);
                } else if(index == 3) {
                    $( this ).val(user.password);
                }
            });
            $('#roles input').prop( "checked", false );
            user.roles.forEach(function(item, i) {
                $('#roles input[value='+ item.name + ']').prop( "checked", true );
            });
            $('#modal').modal();
        });
}

function edit(){
    var sendData = {};
    $('#modalDiv input').each( function (index) {
        if(index == 0) {
            sendData.id = $( this ).val();
        } else if(index == 1) {
            sendData.email = $( this ).val();
        } else if(index == 2) {
            sendData.login = $( this ).val();
        }  else if(index == 3) {
            sendData.password = $(this).val();
        }
    });
    sendData.roles = [];
    $('#roles input:checked').each( function (index) {
        sendData.roles.push({
            name: $( this ).val()
        });
    });
    fetch("./user-rest/edit",
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