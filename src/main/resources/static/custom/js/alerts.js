var NotificationEventManager = (function () {
    var notificationsContainer = null;

    function initHideNotificationEvent() {
        notificationsContainer.on('click', '.hide-noti-box', function (eventArgs) {
            var notification = $(this).parent();
            if (window.onNotiClose) {
                window.onNotiClose(notification);
            }

            NotificationViewManager.removeNotification(notification);
        });
    }

    function initNotificationClickEvent() {
        notificationsContainer.on('click', '.noti-box', function (eventArgs) {
            if (NotificationViewManager.isNotificationClickOnCancel(eventArgs.target)) {
                return false;
            }

            if (window.onNotiClick) {
                window.onNotiClick($(this));
            }
        });
    }

    function initEvents(container) {
        notificationsContainer = container;
        initHideNotificationEvent();
        initNotificationClickEvent();
    }

    return {initEvents: initEvents};
})();

var NotificationViewManager = (function () {
    var notificationsContainer = null;

    function init(container) {
        notificationsContainer = container;
    }

    function showNotification(notificationElement, displayTime) {
        if (!displayTime) {
            displayTime = 3000;
        }

        if (isNaN(displayTime) || Math.floor(displayTime) !== displayTime) {
            throw new Error('Display time must be an integer.');
        }

        notificationsContainer.append(notificationElement);
        notificationElement.fadeIn(100);

        setInterval(function () {
            removeNotification(notificationElement);
        }, displayTime);
    }

    function removeNotification(notification, triggerClick) {
        if (triggerClick) {
            notification.find('.hide-noti-box').trigger('click');
        }

        notification.fadeOut(500, function (value) {
            notification.remove();
        });
    }

    function createNotification(text, notificationType, acceptCallback, declineCallback) {
        var notiId = utils.randomDomId();
        var notificationDiv = $(' <div class="noti-box">');
        notificationDiv.attr('id', notiId);

        var btnDecline = $('<div class="hide-noti-box"><i class="fa fa-times" aria-hidden="true"></i></div>');
        if (declineCallback) {
            btnDecline.on('click', function (eventArgs) {
                declineCallback(notiId);
                removeNotification(notificationDiv);
            });
        }

        notificationDiv.addClass(notificationType);
        notificationDiv.append(btnDecline);
        notificationDiv.append($('<span>' + text + '</span>'));

        if (acceptCallback) {
            notificationDiv.on('click', function (eventArgs) {
                if (!isNotificationClickOnCancel(eventArgs.target)) {
                    acceptCallback(notiId);
                    removeNotification(notificationDiv);
                }
            });
        }

        return notificationDiv;
    }

    function isNotificationClickOnCancel(target) {
        target = $(target);
        return target.hasClass('hide-noti-box') || target.parent().hasClass('hide-noti-box')
    }

    return {
        init: init,
        showNotification: showNotification,
        removeNotification: removeNotification,
        createNotification: createNotification,
        isNotificationClickOnCancel: isNotificationClickOnCancel,
    };

})();

$(function () {
    var alertsContainer = $('#alertsContainer');

    NotificationEventManager.initEvents(alertsContainer);
    NotificationViewManager.init(alertsContainer);


    //Example usage of NotificationViewManager...
    var exampleNoti = NotificationViewManager.createNotification(
        "Testing Noti",
        notificationType.NOTIFICATION_SUCCESS,
        function (notiId) { //accept
            console.log('accepted ' + notiId);
            alert('U accepted ' + notiId);
        },
        function (notiId) { //decline
            console.log('declined ' + notiId);
            alert('u declined ' + notiId);
        }
    );

    NotificationViewManager.showNotification(exampleNoti, 5000); //5s
});