var YammerNotifierAdmin =
{
    save: function ()
    {
        $('saving').style.display = '';
        jQuery
            .ajax(
                {
                    url: $("trafficLightSettingsAdminForm").action,
                    data:
                    {
                        edit: 1,
                        type: $("type").value
                    },
                    type: "POST"
                })
            .done(
                function ()
                {
                    $('saving').style.display = 'hidden';
                    BS.reload();
                })
             .fail(
                function ()
                {
                    alert("Failed to save configuration!")
                });

        return false;
    }
};

$(function() {
    $('#trafficLightSettingsAdminForm').submit(function(event) {
        event.preventDefault();

        $.post("/trafficLight/adminSettings.html", { type: $("#type").val() })
            .done(function () {
                alert("Done!")
                //BS.reload();
            })
            .fail(function () {
                alert("Failed to save configuration!")
            });
    });
});