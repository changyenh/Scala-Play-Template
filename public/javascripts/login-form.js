function showSignUp() {
    $('#signupbox').hide();
    $('#loginbox').show()
}
function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}
$(function() {
    var redirect = getParameterByName('redirect');
    var form = $("#loginform");
    form.submit(function(ev) {
        ev.preventDefault();
        alert("Submit");
        var data = {};
        $.each(form.serializeArray(), function(i, field){
            data[field.name] = field.value;
        });
        console.log(data);
        $.ajax({
            type: "POST",
            url: form.action,
            data: JSON.stringify(data),
            dataType: "json",
            contentType : "application/json",
            success: function(response){
                if (redirect) {
                    console.log("redirect:" + redirect)
                    console.log("response:" + response);
                    alert("Success" + response);
                    $.ajax({
                        type: "POST",
                        url: redirect,
                        data: response,
                        dataType: "json",
                        contentType: "application/json"
                    });
                }
            },
            error: function (xhr, ajaxOptions, ex) {
                console.log(ex);
                alert("Error" + ex);
            }
        });
    });
});