(function ($)
{
    var sendAction = function (enable) {
        $.post(
            url,
            {
                action: "enable",
                enabled: enable
            },
            function ()
            {
                BS.reload(true);
            });

        return false;
    };

    $("#enable-btn").click(function ()
    {
        return sendAction(true);
    });

    $("#disable-btn").click(function ()
    {
        if (!confirm("Build notifications will not be sent until enabled. Disable the plugin?"))
        {
            return false;
        }

        return sendAction(false);
    });

    var changeOptionsVisibility = function (protocolType) {
        if (protocolType == "Udp")
        {
            $(".tcp").hide();
            $(".udp").show();
        }
        else if (protocolType == "Tcp")
        {
            $(".udp").hide();
            $(".tcp").show();
        }
    };

    $("#trafficLightAdminForm").find("[name=protocolType]").change(function() {
        var protocolType = $(this).val()
        if (protocolType == "Udp")
        {
            $(".tcp").hide();
            $(".udp").show();
        }
        else if (protocolType == "Tcp")
        {
            $(".udp").hide();
            $(".tcp").show();
        }
    });

    var showErrors = function(errorsElement, errors) {
        errorsElement.empty();

        if (errors && errors.length == 0)
        {
            return;
        }

        var errorList = $('<ul/>');

        $.each(
            errors,
            function(index, error)
            {
                var li = $('<li/>')
                    .addClass('config-error')
                    .text(error)
                    .appendTo(errorList);
            });

        errorList.appendTo(errorsElement);
    };

    $("#trafficLightAdminForm").submit(function (event) {
        var form = $(this);
        var errors = $("#errors");

        var saving = $("#saving");
        saving.show();

        $.post(
            url,
            {
                action: "save",
                protocolType: form.find("[name=protocolType]").val(),
                udp: {
                    port: form.find("[name=udp-port]").val(),
                    ipAddress: form.find("[name=udp-ipAddress]").val(),
                    period: form.find("[name=udp-period]").val()
                },
                tcp: {
                    port: form.find("[name=tcp-port]").val()
                }
            }
        )
        .done(function (data) {
            saving.hide();

            var response = $(data).find("response").text();
            if (response == "Saved")
            {
                BS.reload();
            }
            else
            {
                showErrors(errors, ["Failed to save configuration: " + response]);
            }
        })
        .fail(function () {
            saving.hide();

            showErrors(errors, ["Failed to save configuration!"]);
        });

        event.preventDefault();
    });
})(jQuery)