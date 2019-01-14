var NotificationEventManager = (function () {
    var notificationsContainer = $('#alertsContainer');

    function initHideNotificationEvent() {
        notificationsContainer.on('click', '.hide-noti-box', function (eventArgs) {
            var notification = $(this).parent();
            if (window.onNotiClose) {
                window.onNotiClose(notification);
            }

            notification.fadeOut(500, function (value) {
                notification.remove();
            });
        });
    }

    function initNotificationClickEvent() {
        notificationsContainer.on('click', '.noti-box', function (eventArgs) {
            var target = $(eventArgs.target);
            if (target.hasClass('hide-noti-box') || target.parent().hasClass('hide-noti-box')) {
                return false;
            }

            if (window.onNotiClick) {
                window.onNotiClick($(this));
            }
        });
    }

    function initEvents() {
        initHideNotificationEvent();
        initNotificationClickEvent();
    }

    return {initEvents: initEvents};
})();

$(function () {
    NotificationEventManager.initEvents();
});