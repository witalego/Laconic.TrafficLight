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